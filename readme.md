# To run this project.
Go to the drive location from where you want to run this project. 
> Run below commands from command line (needs git to be installed).Say

- cd c:\
- Git clone https://github.com/PramodKumarYadav/WebdriverCI.git
- cd WebdriverCI
> Open in say vscode powershell (or from command line itself) run a test and see if all works okay. Run from command line below command.
- mvn clean test
> This should run the tests and you should now see a target repository created in root. This should now contain reports. 
in path 'Your root directory'\WebdriverCI\target\surefire-reports

> Dependencies (Once we start running with Docker, these dependencies will be removed) 
> - JDK installed 
> - Maven installed 

# Project Goals
- [ ] Clean design
    - [ ] Tests should be atomic and independent of each other (to allow running in parallel)
    - [ ] Tests should run in parallel
    - [X] Tests should be agnostic of browser. Should only bother about testing the app.
    - [X] Driver should be agnostic of browser choice. Choice should be made outside driver.
    - [X] User should be able to define a default browser or a browser of his choice from command line or from CI/hub/Saucelabs.
- [ ] Use of docker files to setup test environment
- [ ] Use of CI to run tests with each merge in master
- [ ] Use of reports (html) & CI parceable


# Design components
- Step1: Test Environment setup (using dockers)
- Step2: Design should be clean and should allow for easy CI/manual switch.
- Step3: Run tests from CI (automated scheduled or triggered). Or manually: from command line.
- Step4: Version control tests in Git (any server such as Github).
- Step5: Reports (For both CI-parceable and humans - html)

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
    - 
- Tests should be quick to run.
    - This should allow quick feedback to dev/test teams.
- Tests should be scaleable without impacting overall execution time.
    - One way is to run tests in parrallel to achieve this.
    - This should encourage users to add more tests and not discourage. 
- 

# Step1: Test Environment setup 
## Manual setup (Without dockers)
Download latest chrome driver:
https://sites.google.com/a/chromium.org/chromedriver/downloads

# Step2: Design decisions:
- Tests should be atomic (not dependent on each other)
- Tests should run in parrallel.
    - https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd
- Tests should be browser agnostic and should only care about testing the application.
- Driver should work with whatever browser is passed to it from outside. 
- User should be able to set the browser and tests to run, from outside tests; i.e. via command line, CI. In rare cases, if there is a need it should be possible to set the browser from tests however this is a bad practise and must always be avoided. 

# Step3: To run the tests from command line (for any browser)
- https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
- mvn test -Dbrowser=chrome (To run tests in chrome)
- mvn test -Dbrowser=firefox (To run tests in firefox)
- mvn test -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome only for test class MavenTest)

