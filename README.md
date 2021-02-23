# shortener application

Algorithm:

Each long URL written into DB and Id (number) of this saved instance will be converted to Base62 to make it look shorter.

Service using PostgreSQL in docker container.

Using already created short URl in case if already exist.


You can start application using docker:

 1 - just run terminal inside package with docker-compose file
 
 2 - run "docker-compose up" in terminal
 
 3 - application will be available on http://localhost:8080


