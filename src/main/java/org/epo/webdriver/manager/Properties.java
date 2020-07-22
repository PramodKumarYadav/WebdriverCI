/*
NOTE: Properites (this class) is the interface between User input from command line/CI to our test framework (mainly Driver class).
Driver class then uses these properties to create the driver and browser based on chosen host. 

Set these properties from CI (or command line parameter) and get the properties here.
-> The defaults (when parameter not passed) for each property is specified in second argument of command 
    ex: host = System.getProperty("host","local"); 
-> Defaults for host=local and browser=chrome and accessGridFrom=local(ignored if not running tests in grid mode)

Note: Refer Readme.md -> Step4: Test Execution -> [A quick summary of execution Modes] to see how we use these properties
 for different execution Modes. 
*/

package org.epo.webdriver.manager;

public class Properties {
    private static String host;
    private static String browser;
    private static String accessGridFrom;

    public static String getHost() {
        host = System.getProperty("host","local");
        printSystemProperty("host", host);
        return host;
    }

    public static String getBrowser() {
        browser = System.getProperty("browser","chrome");
        printSystemProperty("browser", browser);
        return browser;
    }

    public static String getAccessGridFrom() {       
        accessGridFrom = System.getProperty("accessGridFrom","local");
        printSystemProperty("accessGridFrom", accessGridFrom);
        return accessGridFrom;
    }

    private static void printSystemProperty(String key, String value) {
        System.out.println("System property key:" + key + "; value: " + value);
    }
}
