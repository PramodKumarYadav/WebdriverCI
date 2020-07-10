package com.epo.webdriver.manager;

public class Properties {

    private static String host;
    private static String browser;

    public static String getHost() {
        //  Set 'host' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "local".
        host = System.getProperty("host","local");
        System.out.println("System property key:'host'; value: " + host);

        return host;
    }

    public static String getBrowser() {
        //  Set 'browser' property from CI (or command line parameter) and get the property here.
        // If you want to test with a default value for this property, give it here, say "chrome".
        browser = System.getProperty("browser","chrome");
        System.out.println("System property key:'browser'; value: " + browser);

        return browser;
    }

}
