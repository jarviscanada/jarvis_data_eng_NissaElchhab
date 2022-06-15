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

validate_args "$1" "$2" "$3"

cmd=$1
psql_username=$2
export PGPASSWORD=$3 # TODO POSSIBLE SECURITY ISSUE; export to make psql use on admins' workstation easier
# global constants
container_name='jrvs-psql'
image_name='postgres:9.6-alpine'
pgdata_container='/var/lib/postgresql/data'
pgdata_local='pgdata'
port_container='5432'
port_local=$port_container

# ensure docker service is started and get container status
sudo systemctl status docker || systemctl systemctl start docker > /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "Error: your command: $0"
  echo "Error: docker service  cannot be started or does not exit. Please check the log files for more information"
  exit 1
fi

docker container inspect $container_name >  /dev/null 2>&1
container_status=$?

case $cmd in
  create)
  # Check if the container is already created
  if [ $container_status -eq '0' ]; then
		echo 'Error: Container already exists'
		exit 1
	fi

  #Create container
	docker volume create "$pgdata_local"
	docker run --name "$container_name" -e POSTGRES_PASSWORD="$PGPASSWORD" -e POSTGRES_USER="$psql_username" -d \
	-v "$pgdata_local":"$pgdata_container" -p "$port_local":"$port_container" "$image_name"

	exit $?
	;;

  start|stop)
  container_status=$(docker container inspect --format='{{ .State.Running}}' $container_name > /dev/null 2>&1)
  if [ "$container_status" = 'true' -a "$cmd" = 'start' ]; then
       echo "Warning: Container $container_name already running"
  fi

  if [ "$container_status" = 'false' -a "$cmd" = 'stop' ]; then
       echo "Warning: Container $container_name already stopped"
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