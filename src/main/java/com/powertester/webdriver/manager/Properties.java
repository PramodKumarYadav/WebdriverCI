/*
Set these properties from CI (or command line parameter) and get the properties here.
The defaults (when parameter not passed) for each property is specified in second argument; 
so for server=local and browser=chrome.

Usage of properties from command line
mvn clean test (defaults to -Dserver=local -Dbrowser=chrome )
mvn clean test -Dbrowser=firefox (To run tests in firefox; defaults to -Dserver=local)
mvn clean test -Dserver=local -Dbrowser=chrome (same as default)
mvn clean test -Dserver=local -Dbrowser=chrome -Dtest=MavenTest (To run tests in chrome only for test class MavenTest)

To run tests on grid from local client
mvn clean test -Dserver=grid -Dbrowser=chrome (-Dclient=local is default)
mvn clean test -Dbrowser=firefox -Dserver=grid -Dclient=local 

To run tests on grid from docker container
mvn clean test -Dserver=grid -Dclient=docker -Dbrowser=chrome
mvn clean test -Dbrowser=firefox -Dserver=grid -Dclient=docker 
*/

package com.powertester.webdriver.manager;

public class Properties {
    private static String server;
    private static String client;
    private static String browser;

    public static String getServer() {
        server = System.getProperty("server","local");
        printSystemProperty("server","local");
        return server;
    }

    public static String getClient() {
        client = System.getProperty("client","local");
        printSystemProperty("client","local");
        return client;
    }

    public static String getBrowser() {
        browser = System.getProperty("browser","chrome");
        printSystemProperty("browser","chrome");
        return browser;
    }

    private static void printSystemProperty(String key, String value) {
        System.out.println("System property key:" + key + "; value: " + value);
    }
}
