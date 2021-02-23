# shortener application

Algorithm:

Each long URL written into DB and Id (number) of this saved instance will be converted to Base62 to make it look shorter.

Service is using PostgreSQL in docker container.

If a URL has already been shortened by the system, and it is entered a second time, the first shortened URL should be given back to the user.


You can start application using docker:

 1 - just run terminal inside package with docker-compose file
 
 2 - run "docker-compose up" in terminal
 
 3 - application will be available on http://localhost:8080


