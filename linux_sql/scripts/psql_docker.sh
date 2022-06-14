#! /bin/sh
# This script creates a postgresql docker container, and provides facilities to start and stop it

# capture cli arguments
cmd=$1
psql_username=$2
psql_password=$3

# global definitions
container_name='jrvs-psql'
image_name='postgres:9.6-alpine'
pgdata_docker='/var/lib/postgresql/data'
pgdata_local='pgdata'
port_docker='5432'
port_local=$port_docker

# ensure docker service is started and get container status
sudo systemctl status docker || systemctl systemctl start docker
# TODO check error in case docker not starting or not found
docker container inspect $container_name
container_status=$?

case $cmd in
  create)

  # Check if the container is already created
  if [ $container_status -eq '0' ]; then
		echo 'Error: Container already exists'
		exit 1
	fi

  #check # of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Error: argument(s) missing. Exiting'
    echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
    exit 1
  fi

  #Create container
	docker volume create $pgdata_local
	PGPASSWORD=$psql_password \
	docker run --name $psql_docker \
	-e POSTGRES_PASSWORD=$PGPASSWORD -d \
	-v $pgdata_local:$pgdata_docker \
	-p $port_local:$port_docker\
	 $image_name

	exit $?
	;;

  start|stop)
  #check instance status; exit 1 if container has not been created
  if [ $container_status -ne '0' ]; then
      echo "Error: Container $container_name has not been created yet. Exiting"
      echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
      exit 1
  fi

  container_status=$(docker container inspect --format='{{ .State.Running}}' $container_name )

  if [ $container_status -eq 'true' -and $cmd -eq 'start' ]; then
        echo "Warning: Container $container_name already running"
  fi

  if [ $container_status -eq 'false' -and $cmd -eq 'stop' ]; then
          echo "Warning: Container $container_name already stopped"
  fi

  #Start or stop the container
	docker container $cmd $container_name
	exit $?
	;;

  *)
	echo 'Error: Illegal command'
	echo 'Usage: psql_docker.sh start|stop|create [db_username][db_password]'
	exit 1
	;;
esac