#! /bin/sh
# resource agent script
# collects periodic resource usage  information;
# collection is based on a timer configured on cron
# then updates database (psql)
# data collected:
# timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available
# TODO pre-requisites: Linux "tools" default on Centos 7 or compatible;  add details about libs
# TODO embed key/certificate to communicate securely with db
# TODO verify db connectivity, otherwise, cache info locally, temporarily and encrypted and...
# TODO include expiration time
# TODO add missing/NIL values in case not all resources could have been collected at once


timestamp=$(TZ="UTC0" date '+%F %T') #UTC time zone
host_id=1                     #host id from `hosts` table
memory_free=$(cat /proc/meminfo | egrep "^MemFree:" | awk --field-separator " " '{print $2}' | xargs)
memory_free=$((memory_free / 1024))
cpu_idle=$(vmstat | tail -1 | awk --field-separator " " '{print $15}' | xargs)                   #in percentage
cpu_kernel=$(vmstat | tail -1 | awk --field-separator " " '{print $14}' | xargs)                  #in percentage
disk_io=$(vmstat -d | egrep "^sd[a-z]" | awk --field-separator " " '{print $10}' | xargs)   #number of disk I/O
disk_available=$(df -BM / | egrep "^\/" | awk --field-separator " " '{print $4}' | sed 's/M//' | xargs) #in MB. root directory avaiable disk

echo $timestamp
echo $host_id
echo $memory_free
echo $cpu_idle
echo $cpu_kernel
echo $disk_io
echo $disk_available