// Details on how to run test scripts with different browsers from maven command line (and thus from CI)
// https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
// Driver should work with whatever browser passed to them (either via CI, CMD line or in rare cases - from tests(not recommended way)
// Thus browser is abstracted outside driver class.
// Driver constructor makes sure that it can work with both cases (both with default browser or when browser is passed to it)

package com.powertester.webdriver.manager;

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
    String server;
    String client;
    String browser;
    URL gridURL;

    public Driver() {
        this(getServer(), getClient(), getBrowser());
    }

    public Driver(String server,  String client, String browser) {

        this.server = server;
        System.out.println("server property passed to driver: " + server);
        
        this.client = client;
        System.out.println("client property passed to driver: " + client);

        this.browser = browser;
        System.out.println("browser property passed to driver: " + browser);

        switch (server.toLowerCase()) {
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
                // This variable holds the value of grid url. 
                // From local (as client) grid is accessible on localhost:4444. From container (as client), you can access grid by container name:4444.
                String gridAddress = "";  
                
                // When calling tests on docker container hub (server) from local machine (as client)
                if (client.equalsIgnoreCase("local"))  {
                    gridAddress = "http://localhost:4444/wd/hub"; 
                }

                // When calling tests on docker container hub (server) from docker container (as client)
                if (client.equalsIgnoreCase("docker"))  {
                    gridAddress = "http://hub:4444/wd/hub"; 
                }                       
                
                try {
                    this.gridURL = new URL(gridAddress);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    this.driver = new RemoteWebDriver(gridURL, options);
                }

                if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    this.driver = new RemoteWebDriver(gridURL, options);
                }

                if (browser.equalsIgnoreCase("opera")) {
                    OperaOptions options = new OperaOptions();
                    this.driver = new RemoteWebDriver(gridURL, options);
                }
                // Note: Even though the line to setup remotewebdriver is same in all above cases, I cannot 
                // take it out. This is due to the reason that options are set differently for all browsers
                // and there is no common options, to scope it out. (options not visible outside brackets {} )
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
