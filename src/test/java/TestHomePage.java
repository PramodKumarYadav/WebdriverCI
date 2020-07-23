import org.epo.pages.HomePage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestHomePage {
    HomePage homePage = new HomePage();

    @Test
    public void checkIfPageIsOpened() {
        Assert.assertTrue(homePage.isPageOpen());
    }
    @Test
    public void checkIfPageTitleIsCorrect() {
        assertThat(homePage.getTitle(), is("React App 123"));
    }

    @After
    public void tearDown() {
        homePage.close();
    }
} 