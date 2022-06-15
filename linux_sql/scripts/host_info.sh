#! /bin/sh
# monitoring agent script
# collects hardware information about host on deployment;
# then updates database (psql)
# data collected:
# id,hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,timestamp
# TODO pre-requisites: Linux "tools" default on Centos 7 or compatible;  add details about libs
# TODO embed key/certificate to communicate securely with db
# TODO verify db connectivity, otherwise, cache info locally, temporarily and encrypted and...
# TODO upload at first opportunity;
# TODO include local hardware refresh timer, or possibly, detect hardware changes (hash change?)
# TODO include expiration time

debug=1
# ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
  echo "Error: wrong number of arguments"
  echo "Usage: host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

# lscpu fields
cpu_number_key="^CPU\(s\):"
cpu_architecture_key="^Architecture:"
cpu_model_key="^Model\ name:"
cpu_mhz_key="^CPU MHz:"
L2_cache_key="^L2\ cache:"
# free
total_mem_key="^Mem:"

hostname=$(hostname -f) #fully qualified hostname
cpu_number=$(echo "$(lscpu)" | egrep "$cpu_number_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_architecture=$(echo "$(lscpu)" | egrep "$cpu_architecture_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_model=$(echo "$(lscpu)" | egrep "$cpu_model_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_mhz=$(echo "$(lscpu)" | egrep "$cpu_mhz_key" | awk  --field-separator ":" '{print $2}' | xargs)
L2_cache=$(echo "$(lscpu)" | egrep "$L2_cache_key" | awk  --field-separator ":" '{print $2}' | sed 's/K//' | xargs)     #in kB
total_mem=$(echo "$(free --kilo)" | egrep "$total_mem_key" | awk  --field-separator " " '{print $2}' | xargs)
timestamp=$(TZ="UTC0" date '+%F %T')  #Current time in UTC time zone TODO read from vmstat instead

if [ $debug -eq 1 ]; then
  echo $hostname
  echo $cpu_number
  echo $cpu_architecture
  echo $cpu_model
  echo $cpu_mhz
  echo $L2_cache
  echo $total_mem
  echo $timestamp
fi

exit 0