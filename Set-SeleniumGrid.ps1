# To work with below scripts, first dot import them and then call any fn. Ex:
    #  . .\Set-SeleniumGrid.ps1
    # Start-ComposeGridAndTests
    
# Option 02: This will run the container and next step will run the tests on container
function Start-TestContainerOnly{
    docker-compose -f .\docker-compose-test.yml up
}
function Start-TestsOnTestContainerItself{
    # At this moment, I have only installed chrome driver for local execution
    docker container exec -it grid_test mvn clean test -Dhost=container -Dbrowser=chrome
}

# Option 03: Set selenium grid using docker swarm (and then you can run tests from local)
function Set-SwarmGrid {
    [CmdletBinding()]
	param(
            [String]$Path = "./docker-compose-grid.yml"
        )
    
    # list of all steps to run while setting up grid (only without tests)
    docker swarm init
    docker stack deploy -c $Path grid
    docker stack ls
    docker service ls
}

# Option 04: Set selenium grid using docker swarm Or compose and run tests from test container on grid 
function Set-GridAndTestContainersViaDockerSwarm {
    [CmdletBinding()]
	param(
        )
    
    # list of all steps to run while setting up grid and tests
    docker swarm init
    docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml --log-level ERROR config > stack.yml
    docker stack deploy -c stack.yml grid
    docker stack ls
    docker service ls
    docker service logs --follow grid_test
}

function Set-GridAndTestContainersViaDockerCompose{
    [CmdletBinding()]
	param(
        )
    
    # override docker compose grid with test details.
    docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml up
}

# This will run tests on grid (no matter if they were started from swarm or from compose)
function Start-TestsOnGridFromContainer{
    $testContainerId = docker container ls -f name=grid_test -q
    docker container exec -it $testContainerId mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=firefox

    # Grid can be accessed here: http://localhost:4444/grid/console
}

# House keeping functions
function Remove-SwarmGrid {
    [CmdletBinding()]
	param(
            [String]$Path 
        )
    
    # remove  grid
    docker stack ls
    docker stack rm grid
    docker stack ls
}

function Remove-AllDockerContainers {
    [CmdletBinding()]
	param(
        )
    
    # Stop and remove all containers (running and exited)
    docker container stop $(docker container ls -aq)
    docker container rm $(docker container ls -aq)
}
