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

> Dependencies (Once we start running with Docker, these dependencies will be removed and reduced only to having Docker desktop installed on your machine) 
> - GIT installed
> - JDK installed 
> - Maven installed 

# Tools (recommended)
- IntelliJ (for building tests)
- VSCode (for GitHub integration and also for running tests in containers - when we get there)
    - Powershell plugin

# Project Goals
- [ ] Clean design
    - [X] Tests should be agnostic of browser. i.e. Tests should only have context about tseting the app (no reference to browser).
    - [X] Driver should be agnostic of browser choice. i.e. the choice of browser should be outside driver class. It should just provide a driver that it asked. 
    - [X] Choice of browser is to be made outside tests (either via CI or command line). In case if you dont want to do that, there should be a option for user to define a default driver with which all tests could be run. 
    - [ ] Tests should be atomic and independent of each other (to allow running in parallel)
    - [ ] Tests should run in parallel (to keep test feedback fast and to encourage more tests)
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
[Download chrome driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)

# Step2: Design decisions:
- Tests should be atomic (not dependent on each other)
- Tests should run in parrallel.
    - [Some best practices recommendations by Nikolay](https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd)
- Tests should be browser agnostic and should only care about testing the application.
- Driver should work with whatever browser is passed to it from outside. 
- User should be able to set the browser and tests to run, from outside tests; i.e. via command line, CI. In rare cases, if there is a need it should be possible to set the browser from tests however this is a bad practise and must always be avoided. 

# Step3: To run the tests from command line (for any browser)
- [how-to-run-scripts-in-a-specific-browser-with-maven](https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/)
- mvn test -Dbrowser=chrome (To run tests in chrome)
- mvn test -Dbrowser=firefox (To run tests in firefox)
- mvn test -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome only for test class MavenTest)

# References
- [Download chrome driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
- [how-to-run-scripts-in-a-specific-browser-with-maven](https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/)
- [Some best practices recommendations by Nikolay](https://ultimateqa.com/automation-patterns-antipatterns/?utm_sq=g6eq8wpdyo&utm_source=LinkedIn&utm_medium=social&utm_campaign=NikolayAdvolodkin&utm_content=OwnBlogPosts#bdd)
- [Official selenium docs](https://www.selenium.dev/documentation/en/)
- [generating-junit-html-reports](https://www.eviltester.com/post/junit/generating-junit-html-reports/)