#! /bin/sh
# resource agent script
# collects periodic resource usage  information;
# collection is based on a timer configured on cron
# then updates database (psql)
# data collected:
# timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available
# TODO pre-requisites: Linux "tools" default on Centos 7 or compatible;  add details about libs
# TODO embed key/certificate to communicate securely with db
# TODO verify db connectivity, otherwise, cache info locally, temporarily and encrypted and...
# TODO include expiration time
# TODO add missing/NIL values in case not all resources could have been collected at once

# global declarations
debug=1
validate_args () {
  if [ $# -ne 5 ]; then
    echo "Validation: wrong number of arguments"
    exit 1
  fi
}

# definitions
validate_args "$1" "$2" "$3" "$4" "$5"
validation_status=$?
if [ $validation_status -ne  0 ]; then
  echo "Error: validation failed."
  echo "Usage: host_usage.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
psql_table_name='hosts_usage'
vmstat_in_mb=$(echo "$(vmstat -t --unit M)" | tail -1)

host_id=$(hostid)               #host id from `hosts` table "(SELECT id FROM host_info WHERE hostname='$hostname')";
memory_free=$(echo "$vmstat_in_mb" | awk --field-separator " " '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_in_mb" | awk --field-separator " " '{print $15}' | xargs)                   #in percentage
cpu_kernel=$(echo "$vmstat_in_mb" | awk --field-separator " " '{print $14}' | xargs)                  #in percentage
disk_io=$(vmstat -d | egrep "^sd[a-z]" | awk --field-separator " " '{print $10}' | xargs)   #number of disk I/O
disk_available=$(df -BM / | egrep "^\/" | awk --field-separator " " '{print $4}' | sed 's/M//' | xargs) #in MB from root
timestamp=$(echo "$vmstat_in_mb" | awk --field-separator " " '{print  $18 " " $19}' | xargs) #UTC time zone

# build SQL command
sql_cmd="INSERT INTO $psql_table_name" # TODO verify if table name should be quoted instead
sql_cmd="$sql_cmd (host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available, timestamp)"
sql_cmd="$sql_cmd VALUES ($host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available, $timestamp);"
psql_invoke="PGPASSWORD=$psql_password psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -w  -c \"$sql_cmd\" "
# NOTE alternative  postgresql://[user[:password]@][host][:port][/dbname][?param1=value1&...]

if [ $debug -eq 1 ]; then
  echo "$sql_cmd"
  echo
  echo "$psql_invoke"
  echo
fi

eval "$psql_invoke"
# TODO add caching, alert or retries on exit code != 0
exit $?
