# Download project (and quick test)
From command line, run below command in the directory where you want to store this project. 
- `Git clone https://github.com/PramodKumarYadav/WebdriverCI.git`
- `cd WebdriverCI`
- `mvn clean test`
> This should run the tests and you should now see a target repository created in root. This should now contain reports in path 'Your root directory'\WebdriverCI\target\surefire-reports

## Dependencies 
Once we start running tests with (and in) Docker, these dependencies will be removed and reduced only to having Docker desktop installed on your machine) 
- GIT installed
- JDK installed 
- Maven installed 

Versions used
> PS D:\WebdriverCI> java -version
- java version "10.0.2" 2018-07-17
> PS D:\WebdriverCI> mvn -version 
- Apache Maven 3.5.4 (1edded0938998edf8bf061f1ceb3cfdeccf443fe; 2018-06-17T20:33:14+02:00)

# Todo: combine framework goals and test objectives (I see similarites here)
# Framework Goals
- [ ] Clean design
    - [X] Tests should be agnostic of browser. i.e. Tests should only have context about tseting the app (no reference to browser).
    - [X] Driver should be agnostic of browser choice. i.e. the choice of browser should be outside driver class. It should just provide a driver that it asked. 
    - [X] Choice of browser is to be made outside tests (either via CI or command line). In case if you dont want to do that, there should be a option for user to define a default driver with which all tests could be run. 
    - [ ] Tests should be atomic and independent of each other (to allow running in parallel)
    - [ ] Tests should run in parallel (to keep test feedback fast and to encourage more tests)
- [X] Use of docker files to setup test environment
    - [X] To allow tests to run distributed on a grid (not same as parrallel)
    - [ ] To allow a consistent setup across test machines.
    - [ ] To create a test container to also run tests from container (entrypoint) -not just from localhost -GRID (which needs local setup)
- [ ] Use of CI to run tests with each merge in master
- [ ] Use of reports (html) & CI parceable

# Test objectives
- Tests should be atomic and not dependent on other tests.
    - To increase speed of execution.
    - To allow parrallel execution of tests.
    - To improve test coverage (failing end to end tests, results in non-tested code).
- Test design should be clean and readable.
    - To improve readability and maintenance.
    - Should eliminate duplication.       
        - by seperating intent from implementation (intent in controller/test classes vs implementation in tools/pageobject classes).
        - by using page object model.
        - by seperating tests from general purpose code. 
- Tests should be quick to run.
    - This should allow quick feedback to dev/test teams.
- Tests should be scaleable without impacting overall execution time.
    - One way is to run tests in parrallel to achieve this.
    - This should encourage users to add more tests and not discourage. 

# Design components
- Step1: Test Environment setup (using dockers)
- Step2: Design should be clean and should allow for easy CI/manual switch.
- Step3: Run tests from CI (automated scheduled or triggered). Or manually: from command line.
- Step4: Version control tests in Git (any server such as Github).
- Step5: Reports (For both CI-parceable and humans - html)

# Step1: Test Environment setup 
## Generic Tools (Recommended)
- IntelliJ 
    - For building/maintaining tests.
    - For running tests locally.
- VSCode 
    - For GitHub integration (has a smooth UI) 
    - For running testing inside containers (have good remote container plugin)
    - For Powershell ease of use. 
- Docker-Desktop

## Manual setup (Without dockers)
Download latest chrome driver:
- [Download chrome driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)


## Dockerize test framework
- [maven base image](https://hub.docker.com/_/maven)
- `Step3: Test Execution` (below), explains how to run (test) docker conatainers - in (grid) docker containers. 

# Step2: Test Design (decisions):
- Tests should be atomic (not dependent on each other)
- Tests should run in parrallel.
    - [Some best practices recommendations by Nikolay](https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd)
- Tests should be browser agnostic and should only care about testing the application.
- Driver should work with whatever browser is passed to it from outside. 
- User should be able to set the browser and tests to run, from outside tests; i.e. via command line, CI. In rare cases, if there is a need it should be possible to set the browser from tests however this is a bad practise and must always be avoided. 
- [ ] Later to also add actual test design diagram here. 

# Step3: Test Execution
## To run tests from command line (for any browser, local/grid)
- [how-to-run-scripts-in-a-specific-browser-with-maven](https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/)
- [pass-parameters-from-command-line-to-a-selenium-project-using-maven-command](https://www.google.com/amp/s/eltestor.wordpress.com/2015/09/13/pass-parameters-from-command-line-to-a-selenium-project-using-maven-command/amp/)

## Option1: Run tests locally
### `Goal:`
    -  Tests are triggered and run locally
### `Benefits:`
    - An option for easy local debugging and building scripts. 

### Step1: Install drivers and setup system path variables
- [] write instructions here or give link to the section

### Step2: Run Tests locally
- `mvn clean test ` (Default env is local and defaulut browser is chrome)
- `mvn clean test -Dhost=local -Dbrowser=chrome ` (Same as default)
- `mvn clean test -Dbrowser=firefox ` (To run tests in firefox; default host is local)
- `mvn clean test -Dtest=MavenTest ` (To run tests only for test class MavenTest. Defaults are local, chrome)
- `mvn clean test -Dhost=local -Dbrowser=firefox -Dtest=MavenTest ` (To run tests in firefox for only test class MavenTest)

## Option2: Run tests on Docker grid (triggered from locally)
### `Goal:`
    - Tests are triggered locally but run on a docker selenium grid
### `Benefits:`
    - With minimum local setup (just with JDK and maven on machine), anyone can run and scale tests (running on multiple nodes).
### Step1: Still need local setup 
    -[] Atleast jdk and maven installed.
### Step2: Setup GRID/nodes

- Reference: https://github.com/SeleniumHQ/docker-selenium
- To start Docker in Swarm mode
    - `docker swarm init` 
- To deploy selenium Grid
    - `docker stack deploy -c docker-compose-grid.yml grid`
- Check if grid is active or not @:
    - http://localhost:4444/grid/console 
- List stack with:
    - `docker stack ls`
- To list running services with:
    - `docker service ls` 
- To scale any service run;
    - `docker service scale grid_chrome=5` or/and 
    - `docker service scale grid_firefox=3`
- Check if grid scaled:
    - http://localhost:4444/grid/console 
    - `docker service ls` (should see it here too)
- Now to run tests:
    - `mvn clean test -Dhost=grid -Dbrowser=chrome ` 
- And you can open another powershell window, and in parrallel, run tests on say firefox on the grid
    - `mvn clean test -Dbrowser=firefox -Dhost=grid` (note: order of parameter doesn't matter)
- And on the local parrallely, if you wish:
    - `mvn clean test -Dhost=local -Dbrowser=chrome `
    - `mvn clean test ` (default is -Dhost=local -Dbrowser=chrome)
- Stop stack with
    - `docker stack rm grid`

### Step3: Run Tests on Docker Grid 
- `mvn clean test -Dhost=grid ` (Default is chrome)
- `mvn clean test -Dhost=grid -Dbrowser=chrome` 
- `mvn clean test -Dhost=grid -Dbrowser=firefox` 

### Troubleshooting (some tips)
- Troubleshooting: 
    - If you get errors in containes and you are not able to do another mvn clean test, 
    - then remove stack with
        - `docker stack rm grid` 
    - and redeploy the Grid;
        -  `docker stack deploy -c docker-compose-grid.yml grid`
- NOTE: If you want to check that, the tests are really running from containers (with containers drivers and not local driver); do this
    - Remove a driver from tools location (say remove chromedriver.exe from your drivers path (in my case this is: C:\tools\selenium)) and say put this on desktop.
    - Run a local test without using grid, so say: 
        - `mvn clean test -Dhost=local  -Dbrowser=chrome`
        -  This should now fail saying:
        -  `Cannot find file at 'c:\tools\selenium\chromedriver.exe' (c:\tools\selenium\chromedriver.exe). This usually indicates a missing or moved file.`
    - Now, run the same test on grid, 
        - `mvn clean test -Dhost=grid -Dbrowser=chrome`
        - You will see that tests ran successfully.
        - This proves that tests are running inside container in grid and not using driver from your local machine. 

## Option3: Run tests on Docker grid (triggered from test docker container)

### `Goal:`
    - Tests triggered from docker container - test.
    - Tests run in docker container - grid.
### `Benefits:`
    - No local installation needed and anyone can run it from their machine

## OptionA: Run tests using docker-compose
- To run grid and tests via docker-compose (run): 
    - `docker-compose -f .\docker-compose-grid.yml -f .\docker-compose-test.yml up`
- Ctrl+c to exit.
- Before restart:
    - `docker container stop $(docker container ls -aq)` (stop all containers - running and exited)
    - `docker container rm $(docker container ls -aq)` (remove all containers - running and exited)  
- You can now restart again:
    - `docker-compose -f .\docker-compose-grid.yml -f .\docker-compose-test.yml up`

## OptionB: Run tests using docker swarm
### Step1: Create a combined stack.yml file to deploy both tests and grid together
> A combined file is now created using docker-compose-grid.yml and docker-compose-test.yml :
- `docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml --log-level ERROR config > stack.yml`
 
### Step2: Setup GRID-nodes and run test-nodes
- Reference: https://github.com/SeleniumHQ/docker-selenium
> All the commands mentioned in Option2 for using docker swarm can be used here. 

> Only commands specific to this workflow are mentioned here. 
- To start Docker in Swarm mode
    - `docker swarm init` 
- To deploy selenium Grid
    - `docker stack deploy -c stack.yml grid`
- Check if grid is active or not @:
    - http://localhost:4444/grid/console 
- List stack with:
    - `docker stack ls`
- To list running services with:
    - `docker service ls` 
- To check the logs of test container
    - `docker service logs --follow grid_test-chrome`
    - You can now see the results of test and that the container is still alive. 
    - You can also see the results in IntelliJ (or vscode): target-> surefire-reports-> ...(here)
- Stop stack with
    - `docker stack rm grid`

# Step4: Version control tests in Git (any server such as Github).
- [] to be added
# Step5: Reports (For both CI-parceable and humans - html)
- [] to be added

# References
- [Official selenium docs](https://www.selenium.dev/documentation/en/)
- [Official github repo for docker-selenium](https://github.com/SeleniumHQ/docker-selenium)
- [docker-selenium/releases - to know driver versions](https://github.com/SeleniumHQ/docker-selenium/releases)
- [Download chrome driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
    - [Capabilities & ChromeOptions](https://chromedriver.chromium.org/capabilities)
- [how-to-run-scripts-in-a-specific-browser-with-maven](https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/)
- [Some best practices recommendations by Nikolay](https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd)
- [generating-junit-html-reports](https://www.eviltester.com/post/junit/generating-junit-html-reports/)
- [distributed-testing-with-selenium-grid](https://testdriven.io/blog/distributed-testing-with-selenium-grid/)
- [pass-parameters-from-command-line-to-a-selenium-project-using-maven-command](https://www.google.com/amp/s/eltestor.wordpress.com/2015/09/13/pass-parameters-from-command-line-to-a-selenium-project-using-maven-command/amp/)
- [article in ruby to build docker image for test project](https://www.methodsandtools.com/archive/testingdocker.php)