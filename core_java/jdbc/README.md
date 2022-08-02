# Introduction
This app allows accessing the RDBMS in a programmatic way instead of SQL.
This makes the data from the database accessible to end users who are not comfortable with SQL,
helps isolate the database from the end users for security purpose,
and create the building blocks for future APIs and possibly a different front-end interface instead
of a text/CLI one only.

## Technologies:
This app relies heavily on JDBC, a standard Java API.
Behind the scene, JDBC makes use of SQL-prepared statements (precompiled) for faster execution.
The underlying database is running on `Postgresql 9.6`,
containerized thanks to a `docker` container with `Alpine Linux`.
The testing framework makes use of `JUnit 5`.
Due to the simple nature of this app, no unit tests have been written (for now)
The app also uses Maven, the project management and build tool.

# Implementation
## Entity Relationship Diagram

[Entity Relationship Diagram](resources/diagrams/hplussports.png)

## Design Patterns

Data Access Object (DAO) is a design pattern creates an abstract interface or abstract class 
defining methods to access a database. These methods define at least the usual CRUD (Create, )
Read, Update, Delete) operations. The classes that are the entities representing the database
tables implement these methods through their associated Data Transfer Object (DTO) classes.
The combination of DAO/DTO pattern allows to encapsulate the details of database access and the
different CRUD operations and exposes a uniform interface for user classes.
This isolation is also an example of the single responsibility principle in OOP design, and
separates the database access domain, from the business domain represented by the entity classes.

The Repository design pattern is a simplified form of the DAO pattern, and finds usage when the DAO
represents only one table. The Repository is useful too if processing the data relationships,
especially joins, needs to be done within the application, programatiocally, instead of through SQL
database facilities.

# Test
How you test your app against the database?
The database setup makes use of psql_docker.sh script (from the Linux_SQL project),
but modified and expanded
to handle initialization using multiple DDL/.sql files and more command line options
to better define database names.
The script has been renamed to [`jdbc_docker`](tools/jdbc_docker.sh)
and is under the `tools directory`.
Running this tools checks for docker service,
pulls and deploys locally an instance of Postgresql 9.6 running on Alpine Linux,
then, if successful,
create the database, user, and executes the DDL files located under the `./sql` directory.
The order of execution of the files is dependent on their names (lexicographic order,
using also numbers, inspired by a similar technique used for Linux boot scripts under `/etc`)

The verification of query results and checking them against the test database is done manually,
by connecting to the local Postgresql docker instance:

`psql -h localhost -U hplussport_adm -d hplussport`


