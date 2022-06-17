#! /bin/sh
# monitoring agent script:
# `host_info.sh psql_host psql_port db_name psql_user psql_password`
# collects hardware specification data about host on deployment;
# then updates database (psql)
# data collected:
# id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, timestamp
# current version is not signed and communicates in clear text

# TODO pre-requisites: Linux "tools" default on Centos 7 or compatible;  add details about libs
# TODO embed key/certificate to communicate securely with db
# TODO verify db connectivity, otherwise, cache info locally, temporarily and encrypted and...
# TODO upload at first opportunity;
# TODO include local hardware refresh timer, or possibly, detect hardware changes (hash change?)
# TODO include expiration time

validate_args () {
  if [ $# -ne 5 ]; then
      echo "Error: validation failed."
      echo "Usage: host_info.sh psql_host psql_port db_name psql_user psql_password"
    exit 1
  fi
}
# global constants
debug=1
validate_args "$@"
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5 #TODO #SECURITY research and use alternative safer way for the password value
psql_table_name='host_info'

# lscpu fields
lscpu_info=$(lscpu) # lscpu is part of util-linux package; ftp://ftp.kernel.org/pub/linux/utils/util-linux/
cpu_number_key="^CPU\(s\):"
cpu_architecture_key="^Architecture:"
cpu_model_key="^Model\ name:"
cpu_mhz_key="^CPU MHz:"
L2_cache_key="^L2\ cache:"

lscpu_value () {
  echo "$lscpu_info" | egrep "$1" | awk --field-separator ":" '{print $2}' | xargs
}

hostname=$(hostname -f) #fully qualified hostname
cpu_number=$(lscpu_value "$cpu_number_key")
cpu_architecture=$(lscpu_value "$cpu_architecture_key")
cpu_model=$(lscpu_value "$cpu_model_key")
cpu_mhz=$(lscpu_value "$cpu_mhz_key")
L2_cache=$( echo $(lscpu_value "$L2_cache_key") | sed 's/K//')  # in kB
total_mem=$(echo "$(free --kilo)" | egrep "^Mem:" | awk  --field-separator " " '{print $2}' | xargs)
timestamp=$(TZ="UTC0" date '+%F %T')  #Current time in UTC time zone TODO read from vmstat instead

# build SQL command
sql_cmd="INSERT INTO $psql_table_name" # TODO verify if table name should be quoted instead
sql_cmd="$sql_cmd (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)"
sql_cmd="$sql_cmd VALUES ($hostname, $cpu_number, $cpu_architecture, $cpu_model, $cpu_mhz, $L2_cache, $total_mem, $timestamp);"
psql_invoke="psql postgresql://$psql_user:$psql_password@$psql_host:$psql_port/$db_name -c \"$sql_cmd\" "

if [ $debug -eq 1 ]; then
  echo "$sql_cmd"
  echo
  echo "$psql_invoke"
  echo
fi

psql postgresql://"$psql_user":"$psql_password"@"$psql_host":$psql_port/"$db_name" -c \"$sql_cmd\"  #> /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "Error: problem updating table \"$psql_table_name\" on database \"$db_name\" on host $psql_host:$psql_port"
  echo "Error: \"$psql_invoke\" returned a non-zero exit code $? "
fi

# TODO add caching, alert or retries on exit code != 0
exit $?
