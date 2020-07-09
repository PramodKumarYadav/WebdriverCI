package com.epo.webdriver.manager;

public class Browser {

    private static String browserName;

    public static String getBrowserName() {
        //  Set 'browser' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say chrome.
        browserName = System.getProperty("browser","chrome");
        System.out.println("System property key:'browser'; value: " + browserName);

        return browserName;
    }
}
