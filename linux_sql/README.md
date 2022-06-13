# Linux Cluster Monitoring Agent

# Introduction
TBD

The Jarvis Linux Cluster Administration (LCA) manages a cluster of Linux nodes at Jarvis. The LCA needs to collect data about the Linux nodes in order to generate reports for future resource planning.
This project aims to provide the LCA team a tool to gather the hardware specifications of each node of the Linux cluster that the team manages, to gather each node's resource usage in real-time, and to collect all that data in an RDBMS.

The Linux cluster is comprised of 10 nodes of Centos Linux 7. These nodes are internally connected through a switch and are assigned internal ipv4 addresses.

Based on the requirements gathered so far, the project can use free and open source (FOSS) tools readily available on the same Centos 7 platform:
	- Bash as a scripting language
	- Linux hardware and software monitoring tols and facilities (`/proc` and `/sys` system info)
	- Openssh client and server to secure data and add network portability through ssh tunnels
	- Postgresql as a high performance FOSS RDBMS
	- Docker as a container technology for the RDBMS
	- Git as a source/version control tool
	- Cron jobs for periodic resource polling and communication with the RDBMS

# Quick Start
TBD

- Start a psql instance using `psql_docker.sh`
- Create tables using `ddl.sql`
- Insert hardware specs data into the DB using `host_info.sh`
- Insert hardware usage data into the DB using `host_usage.sh`
- Crontab setup

# Implemenation
TBD

## Architecture
TBD

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
TBD
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
The tests consist of running the agents and the database in a known environement and comparing the accuracy and timeness of the recorded data with the information locally validated. At this stage, a visual comparison is enough.
Future tests can involved simulating different network conditions using Centos 7 test VMs, and different hardware and software states created by stress testing the VMs and/or modifying virtual hardware on the fly.

# Deployment
The tool deployment has 2 sides:
1. The Database and the RDBMS can be deployed manually by the LCA sysadmins on their local machines.
(note: multiple simulataneous deplyments of the databse can exist without conflicting, considering that collected data is immutable)

2. The node's local agents can be deployed using either Kickstart unattended install for Centos 7, and/or by adding the agent source code to the team hosted Github repository and manually modifying `crontab` to periodically check for the agent repository updates, pulling the latest one and running it locally. A simpler option, considering that there are so far 10 machines, would be to deploy and run the agents manually on earch node.

# Improvements
- Deploy using Ansible/Puppet/Chef
- update bash script to make it POSIX compliant for portability (in case FreeBSD nodes were deployed later)
- Create a dedicated user/group and enforce Mandatory and Discretianory Access Control (MAC and DAC) using SELinux to ensure the agents privilegied software and hardware access is as restricted as possible.
- Update the agents to record locally the collected data in case of network failure
- Add to the databse schema a key/token for the agent authentication and integrity checking to prevent tampering

