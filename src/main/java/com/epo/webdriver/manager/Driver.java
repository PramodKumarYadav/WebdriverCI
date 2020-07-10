// Details on how to run test scripts with different browsers from maven command line (and thus from CI)
// https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
// Driver should work with whatever browser passed to them (either via CI, CMD line or in rare cases - from tests(not recommended way)
// Thus browser is abstracted outside driver class.
// Driver constructor makes sure that it can work with both cases (both with default browser or when browser is passed to it)

package com.epo.webdriver.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Driver extends Properties implements WebDriver {

    WebDriver driver;
    String host;
    String browser;

    public Driver() {
        this(getHost(), getBrowser());
    }

    public Driver(String host, String browser) {

        this.host = host;
        System.out.println("host property passed to driver: " + host);

        this.browser = browser;
        System.out.println("browser property passed to driver: " + browser);

        switch (host.toLowerCase()) {
            case "local":
                if (browser.equalsIgnoreCase("chrome")) {
                    this.driver = new ChromeDriver();
                }

                if (browser.equalsIgnoreCase("firefox")) {
                    this.driver = new FirefoxDriver();
                }

                if (browser.equalsIgnoreCase("ie")) {
                    this.driver = new InternetExplorerDriver();
                }

                if (browser.equalsIgnoreCase("htmlunit")) {
                    this.driver = new HtmlUnitDriver(true);
                }
                break;
            case "grid":
                String nodeURL = "http://localhost:4444/wd/hub";
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    try {
                        this.driver = new RemoteWebDriver(new URL(nodeURL), options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    try {
                        this.driver = new RemoteWebDriver(new URL(nodeURL), options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                if (browser.equalsIgnoreCase("opera")) {
                    OperaOptions options = new OperaOptions();
                    try {
                        this.driver = new RemoteWebDriver(new URL(nodeURL), options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                this.driver = new ChromeDriver();
                break;
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
        // this.driver.quit();
        try{
            this.driver.quit();
        }catch (Exception e){
            System.out.println("Properties closed already, " +
                            "did not need to quit after all");
            // e.printStackTrace();
        }
    }

    public TargetLocator switchTo() {
        return this.driver.switchTo();
    }
}
