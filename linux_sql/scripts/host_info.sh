#! /bin/sh
# monitoring agent script
# collects hardware information about host on deployment;
# then updates database (psql)
# data collected:
# id,hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,timestamp
# TODO pre-requisites: Linux "tools" default on Centos 7 or compatible;  add details about libs
# TODO embed key/certificate to communicate securely with db
# TODO verify db connectivity, otherwise, cachge info lcoally, temporarily and encrypted and...
# TODO upload at first opportunity;
# TODO include local hardware refresh timer, or possibly, detect hardware changes (hash change?)
# TODO include expiration time

# lscpu fields
cpu_number_key="^CPU\(s\):"
cpu_architecture_key="^Architecture:"
cpu_model_key="^Model\ name:"
cpu_mhz_key="^CPU MHz:"
L2_cache_key="^L2\ cache:"
# free
total_mem_key="^Mem:"

# id=1      #psql db auto-increment
hostname=$(hostname -f) #fully qualified hostname
cpu_number=$(echo "$(lscpu)" | egrep "$cpu_number_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_architecture=$(echo "$(lscpu)" | egrep "$cpu_architecture_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_model=$(echo "$(lscpu)" | egrep "$cpu_model_key" | awk --field-separator ":" '{print $2}' | xargs)
cpu_mhz=$(echo "$(lscpu)" | egrep "$cpu_mhz_key" | awk  --field-separator ":" '{print $2}' | xargs)
L2_cache=$(echo "$(lscpu)" | egrep "$L2_cache_key" | awk  --field-separator ":" '{print $2}' | sed 's/K//' | xargs)     #in kB
total_mem=$(echo "$(free --kilo)" | egrep "$total_mem_key" | awk  --field-separator " " '{print $2}' | xargs)
timestamp=$(TZ="UTC0" date '+%F %T')  #Current time in UTC time zone TODO read from vmstat instead

echo $hostname
echo $cpu_number
echo $cpu_architecture
echo $cpu_model
echo $cpu_mhz
echo $L2_cache
echo $total_mem
echo $timestamp
