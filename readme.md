# Getting started
Download project (and quick test)
From command line, run below command in the directory where you want to store this project. 
- `Git clone https://github.com/PramodKumarYadav/WebdriverCI.git`
- `cd WebdriverCI`
- `mvn clean test`
> This should run the tests and you should now see a target repository created in root. This should now contain reports in path 'Your root directory'\WebdriverCI\target\surefire-reports


# Test Areas
- Step1: Test objectives
- Step2: Test Environment setup (using dockers)
- Step3: Design should be clean and should allow for easy CI/manual switch.
- Step4: Run tests from CI (automated scheduled or triggered). Or manually: from command line.
- Step5: Version control tests in Git (any host such as Github).
- Step6: Reports (For both CI-parceable and humans - html)

# Step1: Test objectives
- Tests should be atomic and not dependent on other tests.
    - [ ] To increase speed of execution.
    - [ ] To allow parrallel execution of tests.
    - [ ] To improve test coverage (failing end to end tests, results in non-tested code).
- Test design should be clean and readable.
    - [ ] To improve readability and maintenance.
    - [ ] Should eliminate duplication.       
        - [ ] by seperating intent from implementation (intent in controller/test classes vs implementation in tools/pageobject classes).
        - [ ] by using page object model.
        - [ ] by seperating tests from general purpose code. 
- Tests should be quick to run.
    - [ ] This should allow quick feedback to dev/test teams.
- Tests should be scaleable without impacting overall execution time.
    - [ ] One way is to run tests in parrallel to achieve this.
    - [ ] This should encourage users to add more tests and not discourage. 
- Framework Goals
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

# Step2: Test Environment setup 
## Generic Tools (Recommended)
- IntelliJ 
    - For building/maintaining tests.
    - For running tests locally.
- VSCode 
    - For GitHub integration (has a smooth UI) 
    - For running testing inside containers (have good remote container plugin)
    - For Powershell ease of use. 
- Docker-Desktop

## Software dependencies 
Once we start running tests with (and in) Docker, these dependencies will be removed and reduced only to having Docker desktop installed on your machine) 
- GIT installed
- JDK installed 
- Maven installed 

```Versions used
PS D:\WebdriverCI> java -version
- java version "10.0.2" 2018-07-17
PS D:\WebdriverCI> mvn -version 
- Apache Maven 3.5.4 (1edded0938998edf8bf061f1ceb3cfdeccf443fe; 2018-06-17T20:33:14+02:00)
```

## Manual setup (Without dockers)
Download latest chrome driver:
- [Download chrome driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)


## Dockerize test framework
- [maven base image](https://hub.docker.com/_/maven)
- `Step3: Test Execution` (below), explains how to run (test) docker conatainers - in (grid) docker containers. 

# Step3: Test Design:
Jenkins(CI)- Docker(test env) - Selenium/mvn/junit (for browser automation) - reports(Html human redable/CI parseable)
- [ ] Later to also add actual test design diagram here. 
- [ ] revisit this section later.
- Tests should be atomic (not dependent on each other)
- Tests should run in parrallel.
    - [Some best practices recommendations by Nikolay](https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd)
- Tests should be browser agnostic and should only care about testing the application.
- Driver should work with whatever browser is passed to it from outside. 
- User should be able to set the browser and tests to run, from outside tests; i.e. via command line, CI. In rare cases, if there is a need it should be possible to set the browser from tests however this is a bad practise and must always be avoided. 


# Step4: Test Execution
## To run tests from command line (for any browser, local/grid)
- [how-to-run-scripts-in-a-specific-browser-with-maven](https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/)
- [pass-parameters-from-command-line-to-a-selenium-project-using-maven-command](https://www.google.com/amp/s/eltestor.wordpress.com/2015/09/13/pass-parameters-from-command-line-to-a-selenium-project-using-maven-command/amp/)

## A quick summary of execution Modes
There are in total 4 supported execution modes (at this moment, can increase in future):
- `host=local` (tests are triggered from local. Tests run on local.)
- `host=container` (tests are triggered from container. Tests run on the same container.)
- `host=grid -accessHostFrom=local` (tests are triggered from local. Tests run on containers on a grid.)
- `host=grid -accessHostFrom=container` (tests are triggered from container. Tests run on containers on a grid.)

Properties called from command line are [`host, browser, accessGridFrom(only relevant for grid mode)`]
```
Case1: To Run tests on local 
    mvn clean test (defaults to -Dhost=local -Dbrowser=chrome )
    mvn clean test -Dbrowser=firefox (To run tests in firefox; defaults to -Dhost=local)
    mvn clean test -Dhost=local -Dbrowser=chrome (same as default)
    mvn clean test -Dhost=local -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome only for test class MavenTest)

Case2: To Run tests on test container 
    mvn clean test -Dhost=container -Dbrowser=chrome 
    mvn clean test -Dhost=container -Dbrowser=firefox 

Case3: To Run tests on grid 
    CaseA: To run tests on grid from local host
    mvn clean test -Dhost=grid -Dbrowser=chrome (-DaccessGridFrom=local is default)
    mvn clean test -Dhost=grid -DaccessGridFrom=local -Dbrowser=firefox 

    CaseA: To run tests on grid from test container
    mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=chrome
    mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=firefox 
```
## Details on execution Modes
## Option1: Run tests locally [host: local]
### `Goal:`
    -  Tests are triggered and run locally
### `Benefits:`
    - Best option for easy local debugging and building scripts. 
    - Cons is everyone needs to have all the softwares, env variables setup for tests to run.
    - Can lead to - runs on my machine, not on yours kind of issues. 

### Step1: Install drivers and setup system path variables
- [] write instructions here or give link to the section

### Step2: Run Tests locally
- `mvn clean test ` (Default host is local and default browser is chrome)
- `mvn clean test -Dhost=local -Dbrowser=chrome ` (Same as default)
- `mvn clean test -Dbrowser=firefox ` (To run tests in firefox; default host is local)
- `mvn clean test -Dtest=MavenTest ` (To run tests only for test class MavenTest. Defaults are local, chrome)
- `mvn clean test -Dhost=local -Dbrowser=firefox -Dtest=MavenTest ` (To run tests in firefox for only test class MavenTest)

## Option2: Run tests on a docker test container [host: container]
### `Goal:`
    -  Tests are triggered and run in docker container
### `Benefits:`
    - Anyone can run using dockerfile(image when we have image directory).
    - You dont need local setup for this to work (except docker desktop).
    - Eliminates problem of works on my machine, not on yours.
    - Cons is, we are still not able to scale our tests (for distributed execution)

### Step1: Build an image from dockerfile and run tests on this container
- Build a image from dockerfile using docker-compose (this takes care of mapping your test framework to container)
    - `docker-compose -f .\docker-compose-test.yml up`
    - `docker container ls` (list container to see if it is up. and now you can get its id/name)
    - `docker container exec -it test bash` (enter in the container)
    - `mvn clean test -Dhost=container`
    -  `docker container exec -it test mvn clean test -Dhost=container` (or without entering into container in interactive mode)
    - This will give you successful results as below (we expect one test to fail and one to pass)
    - Note: You will still get a bind failure error but you will see that the tests have run succesfully. 
``` [INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running BadMavenTest
System property key:host; value: container
System property key:accessGridFrom; value: local
System property key:browser; value: chrome
host property passed to driver: container
accessGridFrom property passed to driver: local
browser property passed to driver: chrome
Starting ChromeDriver 84.0.4147.30 (48b3e868b4cc0aa7e8149519690b6f6949e110a8-refs/branch-heads/4147@{#310}) on port 26362
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
[1594910042.375][SEVERE]: bind() failed: Cannot assign requested address (99)
Jul 16, 2020 2:34:03 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 4.962 s <<< FAILURE! - in BadMavenTest
[ERROR] test1(BadMavenTest)  Time elapsed: 4.832 s  <<< FAILURE!
java.lang.AssertionError:

Expected: is "Vancouver Public Library 3 |"
     but: was "Vancouver Public Library |"
        at BadMavenTest.test1(BadMavenTest.java:56)

[INFO] Running MavenTest
System property key:host; value: container
System property key:accessGridFrom; value: local
System property key:browser; value: chrome
host property passed to driver: container
accessGridFrom property passed to driver: local
browser property passed to driver: chrome
Starting ChromeDriver 84.0.4147.30 (48b3e868b4cc0aa7e8149519690b6f6949e110a8-refs/branch-heads/41[1594910046.654][SEVERE]: bind() failed: Cannot assign requested address (99)
47@{#310}) on port 3767
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Jul 16, 2020 2:34:06 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.261 s - in MavenTest
[INFO] 
[INFO] Results:
[INFO]
[ERROR] Failures: 
[ERROR]   BadMavenTest.test1:56 
Expected: is "Vancouver Public Library 3 |"
     but: was "Vancouver Public Library |"
[INFO]
[ERROR] Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.945 s
[INFO] Finished at: 2020-07-16T14:34:10Z
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.22.1:test (default-test) on project WebdriverCI: There are test failures.
[ERROR]
[ERROR] Please refer to /usr/src/target/surefire-reports for the individual test results.
[ERROR] Please refer to dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```
- To build the image from dockerfile and then do the hard work seperately, use below commands
    - `docker image build -t test .   `
    - `docker run --rm -it -v ${PWD}:/usr/src -w /usr/src test mvn clean test -Dhost=container` (here we have to map the volumes, set working directory on the container for image test)
    - You will get a successful test result (expected one test to fail and one to pass). Actual logs below:
```
[INFO] Results:
[INFO]
[ERROR] Failures: 
[ERROR]   BadMavenTest.test1:56 
Expected: is "Vancouver Public Library 3 |"
     but: was "Vancouver Public Library |"
[INFO]
[ERROR] Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
[INFO]
```

### Troubleshooting (some tips)
- From the container, if you forget to give option that the host is container, So say if you run
    - `mvn clean test` (Defaults are -Dhost=local, -Dbrowser=chrome)
    - You will get errors as below. 
``` INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running BadMavenTest
System property key:host; value: local
System property key:accessGridFrom; value: local
System property key:browser; value: chrome
host property passed to driver: local
accessGridFrom property passed to driver: local
browser property passed to driver: chrome
[1594909076.152][Starting ChromeDriver 84.0.4147.30 (48b3e868b4cc0aa7e8149519690b6f6949e110a8-refs/branch-heads/4147@{#310}) on port 31549
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
SEVERE]: bind() failed: Cannot assign requested address (99)
[ERROR] Tests run: 2, Failures: 0, Errors: 2, Skipped: 0, Time elapsed: 1.412 s <<< FAILURE! - in BadMavenTest
[ERROR] test1(BadMavenTest)  Time elapsed: 1.276 s  <<< ERROR!
org.openqa.selenium.WebDriverException: 
unknown error: Chrome failed to start: exited abnormally.
  (unknown error: DevToolsActivePort file doesn't exist)
  (The process started from chrome location /usr/bin/google-chrome is no longer running, so ChromeDriver is assuming that Chrome has crashed.)       
Build info: version: '3.141.59', revision: 'e82be7d358', time: '2018-11-14T08:17:03'
System info: host: 'f997b82b2e30', ip: '172.25.0.2', os.name: 'Linux', os.arch: 'amd64', os.version: '4.19.76-linuxkit', java.version: '1.8.0_252'   
Driver info: driver.version: Driver
remote stacktrace: #0 0x55854c67bea9 <unknown>
```
> This is because, we dont have the capabilites in the container to run it with chrome launched. We handle this by running the chrome browser in a headless mode (check the Driver.java class for option host=container, line: options.setHeadless(true); ) 
- So if you run the tests with option for host as container, you will get the tests executed successfully (as we saw above):
    - `mvn clean test -Dhost=container`
    

### Step2: Run Tests locally
- `mvn clean test -Dhost=container ` (Default host is local and default browser is chrome)
- `mvn clean test -Dhost=container -Dbrowser=chrome ` (at this moment, only can run chrome tests in container(host). You can run other browsers in a grid setup. Later to add this as well)

## Option3: Run tests on Docker grid from local [host: grid ; accessGridFrom=local]
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
    - `mvn clean test -Dhost=grid -DaccessGridFrom=local -Dbrowser=chrome ` 
- And you can open another powershell window, and in parrallel, run tests on say firefox on the grid
    - `mvn clean test -Dbrowser=firefox -Dhost=grid` (note: order of parameter doesn't matter; default -DaccessGridFrom=local )
- And on the local parrallely, if you wish:
    - `mvn clean test -Dbrowser=firefox `
    - `mvn clean test ` (default is -Dhost=local -Dbrowser=chrome)
- Stop stack with
    - `docker stack rm grid`

### Step3: Run Tests on Docker Grid 
- `mvn clean test -Dhost=grid ` (Default is chrome, -DaccessGridFrom=local)
- `mvn clean test -Dhost=grid -DaccessGridFrom=local -Dbrowser=chrome` 
- `mvn clean test -Dhost=grid -DaccessGridFrom=local -Dbrowser=firefox` 

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
        - `mvn clean test -Dbrowser=chrome` (default is -Dhost=local -DaccessGridFrom=local )
        -  This should now fail saying:
        -  `Cannot find file at 'c:\tools\selenium\chromedriver.exe' (c:\tools\selenium\chromedriver.exe). This usually indicates a missing or moved file.`
    - Now, run the same test on grid, 
        - `mvn clean test -Dhost=grid -Dbrowser=chrome` (default is -DaccessGridFrom=local )
        - You will see that tests ran successfully.
        - This proves that tests are running inside container in grid and not using driver from your local machine. 

## Option4: Run tests on Docker grid from test container [host: grid; accessGridFrom: container; ]

### `Goal:`
    - Tests triggered from docker container - test.
    - Tests run in docker container - grid.
### `Benefits:`
    - No local installation needed and anyone can run it from their machine

## OptionA: Run tests using docker-compose
- To run grid and tests via docker-compose (run): 
    - `docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml up`
- Ctrl+c to exit.
- Before restart:
    - `docker container stop $(docker container ls -aq)` (stop all containers - running and exited)
    - `docker container rm $(docker container ls -aq)` (remove all containers - running and exited)  
- You can now restart again:
    - `docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml up`
- Enter into the test container and execute using:
    - `docker container exec -it grid_test mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=chrome`
- Or to keep the execution consistent from Docker swarm mode and Docker-compose mode, we can do this:
- Get the test container id (from name of container)
    -   For ex: name can be: grid_test.1.s94xza3oisshvs01s4d614unp. But for filtering; first few letters (i.e. name of service are enough)
    - `$testContainerId=docker container ls -f name=grid_test -q`
- Enter into the test container and execute using:
    - `docker container exec -it $testContainerId mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=chrome`

## OptionB: Run tests using docker swarm
### Step1: Create a combined stack.yml file to deploy both tests and grid together
> A combined file is now created using docker-compose-grid.yml and docker-compose-test.yml :
- `docker-compose -f ./docker-compose-grid.yml -f ./docker-compose-test.yml -f ./docker-compose-test-depend-on-grid.yml --log-level ERROR config > stack.yml`
 
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
- Get the test container id (from name of container)
    - For ex: name can be:  grid_test.1.s94xza3oisshvs01s4d614unp. But for filtering; first few letters (i.e. name of service are enough)
    - `$testContainerId=docker container ls -f name=grid_test -q` 
- Enter into the test container and execute using:  
    - `docker container exec -it $testContainerId mvn clean test -Dhost=grid -DaccessGridFrom=container -Dbrowser=chrome`
- To check the logs of test container
    - `docker service logs --follow grid_test`
    - You can now see the results of test and that the container is still alive. 
    - You can also see the results in IntelliJ (or vscode): target-> surefire-reports-> ...(here)
- Stop stack with
    - `docker stack rm grid`

# Step5: Version control tests in Git.
- In this case, its easy. Version control is done in git here. 

# Step6: Reports (For both CI-parceable and humans - html)
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