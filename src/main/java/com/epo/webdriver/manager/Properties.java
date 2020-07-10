package com.epo.webdriver.manager;

public class Properties {

    private static String hostName;
    private static String browserName;

    public static String getHostName() {
        //  Set 'host' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "local".
        hostName = System.getProperty("host","local");
        System.out.println("System property key:'host'; value: " + hostName);

        return hostName;
    }

    public static String getBrowserName() {
        //  Set 'browser' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "chrome".
        browserName = System.getProperty("browser","chrome");
        System.out.println("System property key:'browser'; value: " + browserName);

        return browserName;
    }

}
