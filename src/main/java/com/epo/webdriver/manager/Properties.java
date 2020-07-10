package com.epo.webdriver.manager;

public class Properties {

    private static String hostName;
    private static String browserName;

    public static String getHostName() {
        //  Set 'hostName' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "local".
        hostName = System.getProperty("hostName","local");
        System.out.println("System property key:'hostName'; value: " + hostName);

        return hostName;
    }

    public static String getBrowserName() {
        //  Set 'browserName' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "chrome".
        browserName = System.getProperty("browserName","chrome");
        System.out.println("System property key:'browserName'; value: " + browserName);

        return browserName;
    }

}
