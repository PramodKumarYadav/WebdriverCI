import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.epo.webdriver.manager.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MavenTest {

    Driver driver;

    @Before
    public void setUp() {
        driver = new Driver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test1() {
        driver.get("http://www.vpl.ca");
        assertThat(driver.getTitle(), is("Vancouver Public Library |"));
    }

}