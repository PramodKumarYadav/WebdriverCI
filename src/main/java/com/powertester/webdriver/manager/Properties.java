/*
Usage of properties from command line
mvn clean test (defaults to -Dhost=local -Dbrowser=chrome )
mvn clean test -Dbrowser=firefox (To run tests in firefox; defaults to -Dhost=local)
mvn clean test -Dhost=local -Dbrowser=chrome (same as default)
mvn clean test -Dhost=local -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome only for test class MavenTest)

To run tests on grid.
mvn clean test -Dhost=grid -Dbrowser=chrome
mvn clean test -Dbrowser=firefox -Dhost=grid
*/

// Set these properties from CI (or command line parameter) and get the properties here.
// The defaults (when parameter not passed) for each property is specified in second argument; 
// so for host=local and browser=chrome

package com.powertester.webdriver.manager;

public class Properties {
    private static String host;
    private static String browser;

    public static String getHost() {
        host = System.getProperty("host","local");
        System.out.println("System property key:'host'; value: " + host);
        return host;
    }

    public static String getBrowser() {
        browser = System.getProperty("browser","chrome");
        System.out.println("System property key:'browser'; value: " + browser);
        return browser;
    }
}
