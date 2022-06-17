#! /bin/sh
# This script creates a postgresql docker container, and provides facilities to start and stop it
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

# validate_service "Purpose comment" "bash_script_to_eval" "Ok message" "Fail message"
try_running() {
  echo "$1"
  out=$(eval "$2") # NOTE might need to add  > /dev/null 2>&1
  if [ $? -ne 0 ]; then
    echo "Error: your command:"
    echo "\"$2\""
    echo "Error: $4"
    exit $?
  else
    echo "$out" | head -3
    echo "Ok: $3"
  fi
}

# global arguments
validate_args "$1" "$2" "$3"
cmd=$1
postgres_user=$2
postgres_password="$3"
postgres_db=$postgres_user # postgres docker default in case not specified, as per docs
export PGPASSWORD="$postgres_password" # TODO POSSIBLE SECURITY ISSUE; export to make psql on admins' workstation easier
container_name='jrvs-psql'
image_name='postgres:9.6-alpine'
pgdata_container='/var/lib/postgresql/data'
pgdata_local='pgdata'
port_container='5432'
port_local=$port_container

try_running 'Checking if docker service is available...' \
'sudo systemctl status docker || systemctl systemctl start docker' \
'docker service running.' \
'docker service cannot be started or does not exit. Please check system or application log files for more information'

try_running "Checking if container $container_name exists..." "docker container inspect $container_name"\
"$container_name exists" "$container_name does not exist"

container_status=$?

case $cmd in
  create)
  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
		echo "Error: Trying to create $container_name ; but container already exists"
		exit 1
  fi

  #Create container
	try_running "Creating local volume $pgdata_local ..." "docker volume create \"$pgdata_local\"" \
	"Volume created" "Volume NOT created. Please check logs with: docker volume ls -a"

	docker run --name $container_name -e POSTGRES_USER=$postgres_user -e POSTGRES_PASSWORD="$postgres_password" \
	POSTGRES_DB="postgres_db" -d -v "$pgdata_local":"$pgdata_container" -p $port_local:$port_container $image_name

	exit $?
	;;

  start|stop)
  if [ $container_status -ne 0 ]; then
  		echo "Error: Trying start|stop $container_name ; but container does not exist"
  		exit 1
  fi
  container_status=$(docker container inspect --format='{{ .State.Running}}' $container_name)
  if [ "$container_status" = 'true' -a "$cmd" = 'start' ]; then
       echo "Warning: Container $container_name already running"
       echo
  fi

  if [ "$container_status" = 'false' -a "$cmd" = 'stop' ]; then
       echo "Warning: Container $container_name already stopped"
       echo
  fi

  #Start or stop the container
  echo "$cmd $container_name in progress..."
  docker container "$cmd" $container_name 2>&1
	exit $?
	;;

  *)
	echo 'Error: Illegal command or missing arguments'
	echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
	exit 1
	;;
esac