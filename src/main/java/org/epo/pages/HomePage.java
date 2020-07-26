// https://github.com/SeleniumHQ/selenium/wiki/PageFactory
// https://www.toptal.com/selenium/test-automation-in-selenium-using-page-object-model-and-page-factory

package org.epo.pages;

import org.epo.webdriver.Driver;
import org.epo.webdriver.HomeURL;
import org.openqa.selenium.By;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class HomePage extends HomeURL {
    private Driver driver;

    //Page URL 
    private static String PAGE_URL= getHomePageURL();

    //Locators
    @FindBy(xpath = "//div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-3']//div[1]//div[1]//div[1]//a[1]//span[1]")
    WebElement shoppingPage;

    //Constructor
    public HomePage(){
        driver = new Driver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

        driver.get(PAGE_URL);

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpen(){
        return driver.getTitle().toString().contains("React App");
    }

    public String getTitle(){
        return driver.getTitle().toString();
    }

    public void openshoppingPage(){
        shoppingPage.click();
    }
    // To close the home page (at the end of tests if required)
    public void close() {
        driver.close();
        driver.quit();
    }
}
