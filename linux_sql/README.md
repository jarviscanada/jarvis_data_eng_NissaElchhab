# Linux Cluster Monitoring Agent

# Introduction
The Jarvis Linux Cluster Administration team (LCA) manages a cluster of Linux nodes at the Company (Jarvis Consulting).
The LCA needs to collect data about the Linux nodes it's using, for multiple purposes, including documenting its assets 
as automatically as possible, nodes enumeration and monitoring, and in order to generate reports for future resource
planning and provisioning.

## ## Overview and Business Needs
This project aims to provide the LCA team a set of tools (the Linux Cluster Monitoring Agent) to gather the hardware 
specifications of each node of the Linux cluster that the team manages, to gather each node's resource usage in as close
to real-time as reasonable for this purpose, and to collect all that data in a centralized RDBMS that can be easily 
set up and managed on an LCA member workstation. The Cluster Monitoring Agent (LCMA) will be composed of agents running
on the nodes themselves (host agents) and an agent running on the LCA admin workstations (LCMA DB agent).

In order to keep the project simple and cost-effective, with accessible future updates and auditing on one hand, 
and as portable as possible, it is recommended to use a FOSS (Free and Open Source) scripting language that is available
everywhere and use the platform's available tools.

The deployment itself, for now, is going to be handled by the LCA team.

## Prerequsites and Environement Survey:
The current Linux cluster is comprised of 10 nodes of Centos Linux 7, a derivative of RedHat Enterprise Linux(tm) and
a POSIX-compliant Linux distribution, or distro, which comes by default with GNU and Systemd userspace.

### Available FOSS tools:
The userspace includes:
   - scripting engine: bash as ubiquitous default shell, with sh/POSIX compatibility and with some useful but 
incompatible extensions ("bashisms")
   - The Linux Kernel exported virtual filesystems /proc and /sys which can conveniently be access by many FOSS
monitoring tools in the distro.
   - openssh client and server for remote and secure access
   - openGPG/PGP for encryption/signature verification
   - standard Linux tools for job scheduling and periodical polling, such as cron/crontab/cron jobs
   - iptables for network stack management/query
   - default Centos 7 repositories include:
     - git for Source Versionning System
     - Postgresql as the RDBMS for the LCMDB
     - psql, a cli/shell postgresql client, to write data on the remote DB instance.
     - Docker as a container technology.
     - an FOSS dockerized and verified copy of postgresql pulled from dockerhub (official docker images repository)

### Networking
These nodes are internally connected through a switch. The host agents do not need, for now, any specific configuration
The nodes are assigned ipv4 addresses and a hostname through internal DHCP and DNS servers.
The host agents will need to communicate (write-only) with the LCMA DB using its hostname or ip address.
That information will need to be updated manually on the scripts before they're redeployed manually.

### Preliminary Security Considerations:
(Security should be considered early in the design. Due to the MVP nature of this project, at this stage, these security
considerations are important to keep in mind but are to be implemented for a future release.)

Due to the need for hardware access, the tools running on the nodes will need privilegied access in read-only mode.
This still could pose a potential security risk. Some readily available solutions can minimize the risks:
- Using the correct Default Unix Discrete Access Control, by creating a dedicated user/group (TBD)
- If available, using optionally SELinux more advanced access control and auditing features
- GPG/PGP signing the scripts, and/or using sha256 checksums, as the host agent scripts are not supposed to be
  updated frequently
- Using ssh to encrypt and tunnel communications between the nodes and the main database, to prevent or limit network
  recon and/or Machine In The Middle attack (MITM)
- Configuiring local hosts auditing and alerting in case of a detected mopdification, as well as the database
  invalidation

The MVP will contain hardcoded configuration information. Extra-caution needs to be taken when deploying it on a git
repo, including if it's an internal one. If pushed to a public git hosting such as Github, the hardcoded information
*must be removed* and replaced by environement variables.

For the time being, the MVP will be deployed on a few local machines and servers within the IT network.

The MVP is configured with debug mode to diplay extra information about its execution. This can be turned off or removed
later, and has no impact on its execution.

# Quick Start
TBD

- Start a psql instance using `psql_docker.sh`
- Create tables using `ddl.sql`
- Insert hardware specs data into the DB using `host_info.sh`
- Insert hardware usage data into the DB using `host_usage.sh`
- Crontab setup

# Implemenation
The project will be implemented using the Agile methodology to be able to test frequently if each feature fits the MVP
requirements and the LCA team business needs.

The Linux Cluster Monitoring Agent (LCMA) and The Linux Monitoring Database (LCMDB) can be developed independently 
of each other.

The LCMA is sub-divided into 2 scripts:
    - host_info.sh will gather the local host hardware information, during its first startup after deployment, or on
demand. The info will be inserted into the LCMDB using psql, a local cli/shell database client.
    - host_usage.sh will be triggered each minute by cron, gather current host statistic in a reasonable time 
(miliseconds) and insert that data into the LCMDB, again using psql.
    - Note: host_info.sh must be the first script to start, in order to initialize relevant host information in the DB,
that host_usage.sh will make use of.
    - In order to create the cronjob, at this stage, a manual edit of contabs can be performed by a sysadmin.
An improvement on this design could be that host_info.sh updates the crontab itself on lauch, and host_usage.sh will
error if it's started before host_info.sh and/or a crontab entry was not present.
    - The scripts can be started on boot by adding the relevant startup scripts in /etc/rc.d or on the systemd entries.

The LCMDB has 2 components:
    - A psql_docker.sh script that serves to:
        - download and/or setup a postgresql docker container
        - create a postgresql local volume (to persist the DB independently of the docker container)
        - start that container (by default named jrvs-psql)
        - create a new DB (by default host_agent) 
        - and finally configure its schema, before starting to listen on the postgrsql port (by default 5432).
        - Note: A sysadmin needs to ensure the postgresql DB port is mapped to an ingress open port on the LCA
    workstation's firewall, or manually open it. That step can be later automated.
    - The psql_docker.sh can also start and stop the LCMDB container. This option is convenient to hide the docker usage
details for the LCA admins and make the script usage more intuitive and portable if another container technology is
chosen instead of docker at a later time.

The implementation will use bash scripting, and try to stick to the most POSIX-ly correct way for a Unix shell script,
for future portability. In case a "bashism" is needed, that choice must be documented.
For the same purpose, the script will use the most common Linux monitoring commands or utilities, such as vmstat, for
becuase these have well documented behaviour and have known equivalent in other operating systems.
The choice of the OS monitoring tools will have to go through the Tech Lead/Senior Developer for validation.

The scripts will make sure to validate:
    - The environement compatibility, including environement variables that the scripts will need
    - The availability and/or correctness of the Network time sService (NTP) as these script use timestamps
    - The availability of the tools that are used by the scripts, including docker, psql, sed, awk, vmstat, cron etc...
    - The input arguments (first checking their syntax and number, then later, their types and other constraints)
    - The availability of netwoking by ping'ing the remote host/port, and preferably the remote postgresql instance
    - The success or failure of each step by checking the last exist code for non-zero/failure/error codes

Each operation that the script is doing should be displayed on stderr for troubleshooting. However, that option can be
configured through a debug variable or parameter.

## Architecture
(A schematic diagram included)


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
The tests consist of running the agents and the database in a known environement and comparing the accuracy and timeness
of the recorded data with the information locally validated. At this stage, a visual comparison is enough.
Future tests can involved simulating different network conditions using Centos 7 test VMs, and different hardware and 
software states created by stress testing the VMs and/or modifying virtual hardware on the fly.

# Deployment
The tool deployment has 2 sides:
1. The Database and the RDBMS can be deployed manually by the LCA sysadmins on their local machines.
   (note: multiple simulataneous deplyments of the databse can exist without conflicting, considering that collected 
2. data is immutable)

3. The node's local agents can be deployed using either Kickstart unattended install for Centos 7, and/or by adding the 
4. agent source code to the team hosted Github repository and manually modifying `crontab` to periodically check for the
5. agent repository updates, pulling the latest one and running it locally. A simpler option, considering that there are
6. so far 10 machines, would be to deploy and run the agents manually on earch node.

# Improvements
- Deploy using Ansible/Puppet/Chef
- update bash script to make it POSIX compliant for portability (in case FreeBSD nodes were deployed later)
- Create a dedicated user/group and enforce Mandatory and Discretianory Access Control (MAC and DAC) using SELinux to 
- ensure the agents privilegied software and hardware access is as restricted as possible.
- Update the agents to record locally the collected data in case of network failure
- Add to the databse schema a key/token for the agent authentication and integrity checking to prevent tampering

