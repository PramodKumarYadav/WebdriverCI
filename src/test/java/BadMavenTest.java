import com.epo.webdriver.manager.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

    public class BadMavenTest {

        Driver driver;

        @Before
        public void setUp() {


            // (Browser must not be set in tests but from CI or command line as shown below)
            // To run the tests from command line (for any browser)
            //     - https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
            //     - mvn test -Dbrowser=chrome (To run tests in chrome)
            //     - mvn test -Dbrowser=firefox (To run tests in firefox)
            //     - mvn test -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome for only test class MavenTest)

            // Below is Just to show that if it is needed to set browser from tests; how it can be done (not recommended).
            // Idea is tests should be browser agnostic and need not worry about which browser they are running from.
            // Tests must only worry about the application, not about the browser.
            // NOTE: If you set the property as shown below in one test, it will be set for other tests too. So you may
            // expect that the other test where you are not passing the property should run with default, but it will
            // pick this property value. So another reason, why you should only set the property from outside tests
            // and not from within tests.
//        System.setProperty("browser", "firefox");
//        String browserName = System.getProperty("browser");
//        driver = new Driver(browserName);
        
        // If you have to set a seperate browser than the one you passed from CI, you must do it via specifying the name
        // not by setting the property. This way, your other tests, will use the default from propety and this test
        // will use what you specify.
        // Uncommenting this will make it a bad test since we may 'think' we are running with the browser of our choice
        // but this command will run it using another browser 
        // (which will be a problem in say GRID-Docker-setup-which will not have that browser)  
        // driver = new Driver("firefox");

           driver = new Driver();
        }

        @After
        public void tearDown() {
            driver.close();
            driver.quit();
        }

        @Test
        public void test1() {
            driver.get("http://www.vpl.ca");
            assertThat(driver.getTitle(), is("Vancouver Public Library 3 |"));
        }

    }
