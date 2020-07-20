REM Every part of this pipeline ran using Docker containers, taking advantage of a neat trick: 
REM containers running in Docker can connect to the Docker API and start new containers on the same Docker Engine they’re running on. 
REM The Jenkins image has the Docker CLI installed, and the configuration in the Compose file sets up Jenkins so when it runs Docker commands
REM they get sent to the Docker Engine on your machine. It sounds odd, but it’s really just taking advantage of the fact that 
REM the Docker CLI calls into the Docker API, so CLIs from different places can connect to the same Docker Engine 

REM Setup grid containers and test container (named test)
REM Then enter into the test container and execute using test container name (test):

docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml up -d 
&& docker-compose ps 
&& docker container exec -it test mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=chrome
&& docker-compose down