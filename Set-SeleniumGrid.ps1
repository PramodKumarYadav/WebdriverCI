# Set selenium grid using docker swarm
function Set-DockerSeleniumGrid {
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

function Remove-DockerSeleniumGrid {
    [CmdletBinding()]
	param(
            [String]$Path 
        )
    
    # remove selenium grid
    docker stack ls
    docker stack rm grid
    docker stack ls
}

function Set-DockerSeleniumGridAndTests {
    [CmdletBinding()]
	param(
        )
    
    # list of all steps to run while setting up grid and tests
    docker swarm init
    docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml --log-level ERROR config > stack.yml
    docker stack deploy -c stack.yml grid
    docker stack ls
    docker service ls
    docker service logs --follow grid_test-chrome
}

function Start-TestsUsingDockerCompose {
    [CmdletBinding()]
	param(
        )
    
    # override docker compose grid with test details.
    docker-compose -f .\docker-compose-grid.yml -f .\docker-compose-test.yml up
}

function Remove-AllContainers {
    [CmdletBinding()]
	param(
        )
    
    # Stop and remove all containers (running and exited)
    docker container stop $(docker container ls -aq)
    docker container rm $(docker container ls -aq)
}