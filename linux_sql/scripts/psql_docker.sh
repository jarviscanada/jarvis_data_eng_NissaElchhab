#! /bin/sh
# This script creates a postgresql docker container, and provides facilities to start and stop it
# after the creation of the container, it also creates the host_info and host_usage tables
# TODO add more checks for different artifacts for docker and postgresql's docker, such as volume
# TODO add a remove and/or cleanup option for previously existing containers
# TODO add a volume check and options such as backup/remove/rename to ensure postgres volume data is safe

validate_args () {
  case $1 in
    create)
    if [ $# -ne 3 ]; then
      echo 'Error: argument(s) missing. Exiting'
      echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
      exit 1
    fi
    ;;

    start|stop)
      if [ $# -ne 1 ]; then
      echo 'Error: too many arguments. Exiting'
      echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
      exit 1
     fi
  	;;

    *)
  	echo 'Error: Illegal command or missing arguments'
  	echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
  	exit 1
  	;;
  esac
}

# try_running "Purpose comment" "bash_script_to_eval" ["Ok message" "Fail message"]
try_running() {
  # This branch is interested in the return value $?
  if [ $# -eq 2 ]; then
    echo "$1"
    out=$(eval "$2") # NOTE might need to add  > /dev/null 2>&1
    return $?
  fi

    # This branch is interested in validation and return xor script exit with $?
  if [ $# -ne 4 ]; then
    echo "Debug: try_running() function inside this \"$0\" script wrong number of arguments. This is a bug. Aborting."
    exit 255
  fi

  echo "$1"
  out=$(eval "$2") # NOTE might need to add  > /dev/null 2>&1
  if [ $? -ne 0 ]; then
    echo "Error: your command: \"$2\""
    echo "Error: $4"
    exit $?
  else
    echo "$out" | head -3
    echo "(Output truncated to 3 lines. See the logs for more details)"
    echo "Ok: $3"
  fi
}

# global arguments
validate_args "$@" # TODO check and replace argument logic "$1" "$2" "$3" with array logic everywhere
cmd=$1
postgres_user=$2
postgres_password="$3"
postgres_db='host_agent' # postgres docker default in case not specified, as per docs
export PGPASSWORD="$postgres_password" # TODO POSSIBLE SECURITY ISSUE; export to make psql on admins' workstation easier
container_name='jrvs-psql'
image_name='postgres:9.6-alpine'
pgdata_container='/var/lib/postgresql/data'
pgdata_local='pgdata'
port_container='5432'
port_local=$port_container
#local_ipv4_addr=$("127.0.0.1") # TODO in case of deploy of a remote docker host
working_dir=$(dirname "$0")
ddl_file_path="$working_dir/../sql/ddl.sql"

try_running 'Checking if docker service is available...' \
'sudo systemctl status docker || systemctl systemctl start docker' \
'docker service running.' \
'docker service cannot be started or does not exit. Please check system or application log files for more information'

try_running "Checking if container $container_name exists..."  \
"docker container inspect $container_name"

container_status=$?
case $cmd in
  create)
  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
		echo "Error: Trying to create $container_name but a container with the same name already exists."
		echo "Error: Remove the container $container_name first. Exiting with error(s)"
		echo "WARNING: Check if you need to back up data stored on volume \"$pgdata_local\" before any changes."
		echo "WARNING: Container $container_name is likely mapping its directory \"$pgdata_container\"..."
		echo "WARNING:...to local docker volume \"$pgdata_local\"."
		echo "WARNING: You can run \"docker volume inspect $pgdata_local\" for more information."
		exit 1
  fi

  #Create container
	try_running "Creating local volume $pgdata_local ..." \
	"docker volume create \"$pgdata_local\"" \
	"Volume created" \
	"Volume NOT created. Please check logs with: docker volume ls -a"

	docker run --name $container_name -e POSTGRES_USER=$postgres_user -e POSTGRES_PASSWORD="$postgres_password" \
	-e POSTGRES_DB="$postgres_db" -d -v "$pgdata_local":"$pgdata_container" -p $port_local:$port_container $image_name

    sleep 5s
    echo "Waiting for the container to start..."
	psql_url="postgresql://$postgres_user:$postgres_password@127.0.0.1:$port_local/$postgres_db"
    psql $psql_url -a -f "$ddl_file_path"
    exit $?
	;;

  start)
  if [ $container_status -ne 0 ]; then
  		echo "Error: Trying to start $container_name ; but container does not exist"
  		exit 1
  fi
  container_status=$(docker container inspect --format='{{ .State.Running}}' $container_name)
  if [ "$container_status" = 'true' -a "$cmd" = 'start' ]; then
       echo "Warning: Container $container_name already running."
       echo "{{ .State.Running}} is $container_status. Nothing to do"
       exit 0
  fi

  try_running "Starting $container_name in progress..." \
  "docker container start $container_name" \
  "Container $container_name started successfully" \
  "Container $container_name failed to start"

  exit $?
  ;;

 stop)
  if [ $container_status -ne 0 ]; then
  		echo "Error: Trying to stop $container_name ; but container does not exist"
  		exit 1
  fi
  container_status=$(docker container inspect --format='{{ .State.Running}}' $container_name)

  if [ "$container_status" = 'false' -a "$cmd" = 'stop' ]; then
       echo "Warning: Container $container_name already stopped."
       echo "{{ .State.Running}} is $container_status. Nothing to do"
       exit 0
  fi

  try_running "Stopping $container_name in progress..." \
  "docker container stop $container_name" \
  "Container $container_name stopped successfully" \
  "Container $container_name failed to stop"

  exit $?
  ;;


  *)
	echo 'Error: Illegal command or missing arguments'
	echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
	exit 1
	;;
esac