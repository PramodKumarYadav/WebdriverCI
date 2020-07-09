// Details on how to run test scripts with different browsers from maven command line (and thus from CI)
// https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
// Driver should work with whatever browser passed to them (either via CI, CMD line or in rare cases - from tests(not recommended way)
// Thus browser is abstracted outside driver class.
// Driver constructor makes sure that it can work with both cases (both with default browser or when browser is passed to it)

package com.epo.webdriver.manager;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Driver extends Browser implements WebDriver{

    WebDriver driver;
    String browserName;

    public Driver() {
        this(getBrowserName());
    }

    public Driver(String browserName) {

        this.browserName = browserName;
        System.out.println("browser name passed to driver: " + browserName);

        if (browserName.equalsIgnoreCase("chrome")) {
            this.driver = new ChromeDriver();
        }

        if (browserName.equalsIgnoreCase("firefox")) {
            this.driver = new FirefoxDriver();
        }

        if (browserName.equalsIgnoreCase("ie")) {
            this.driver = new InternetExplorerDriver();
        }

        if (browserName.equalsIgnoreCase("htmlunit")) {
            this.driver = new HtmlUnitDriver(true);
        }
    }

    public void close() {
        this.driver.close();
    }

    public WebElement findElement(By arg0) {
        return this.driver.findElement(arg0);
    }

    public List<WebElement> findElements(By arg0) {
        return this.driver.findElements(arg0);
    }

    public void get(String arg0) {
        this.driver.get(arg0);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public String getPageSource() {
        return this.driver.getPageSource();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return this.driver.getWindowHandles();
    }

    public Options manage() {
        return this.driver.manage();
    }

    public Navigation navigate() {
        return this.driver.navigate();
    }

    public void quit() {
        this.driver.quit();
    }

    public TargetLocator switchTo() {
        return this.driver.switchTo();
    }
}
