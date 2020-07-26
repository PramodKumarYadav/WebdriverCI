package org.epo.webdriver;

public class HomeURL  extends Properties {
    private static String homePageURL;

    public static String getHomePageURL() {    
        switch (getHost().toLowerCase()) {
            case "local":
                System.out.println("entered local");
                //  Say if your application can be run from localhost on port 8081
                homePageURL = "http://localhost:8081/";
                break;
            case "container":
                System.out.println("entered container");
                // I WANT TO CONNECT FROM A CONTAINER TO A SERVICE ON THE HOST
                // https://docs.docker.com/docker-for-windows/networking/ 
                homePageURL = "http://host.docker.internal:8081/";
                break;
            case "grid":   
                System.out.println("entered grid");
                homePageURL = "http://host.docker.internal:8081/";                      
                break;
            default:
                homePageURL = "http://localhost:8081/";
                break;
        }
        System.out.println("homePageURL: " + homePageURL);
        return homePageURL;
    }
}

// URL i used for initial testing. 
// driver.get("http://www.vpl.ca");
