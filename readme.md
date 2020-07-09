
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

