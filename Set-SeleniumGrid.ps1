# To work with below scripts, first dot import them and then call any fn. Ex:
    #  . .\Set-SeleniumGrid.ps1
    # Start-ComposeGridAndTests
    
# Set selenium grid using docker swarm
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

function Set-SwarmGridAndTests {
    [CmdletBinding()]
	param(
        )
    
    # list of all steps to run while setting up grid and tests
    docker swarm init
    docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml --log-level ERROR config > stack.yml
    docker stack deploy -c stack.yml grid
    docker stack ls
    docker service ls
    docker service logs --follow grid_test-chrome
}

function Start-ComposeGridAndTests{
    [CmdletBinding()]
	param(
        )
    
    # override docker compose grid with test details.
    docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml up
}

function Remove-AllDockerContainers {
    [CmdletBinding()]
	param(
        )
    
    # Stop and remove all containers (running and exited)
    docker container stop $(docker container ls -aq)
    docker container rm $(docker container ls -aq)
}