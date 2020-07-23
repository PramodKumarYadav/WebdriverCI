package org.epo.webdriver;

public class HomeURL  extends Properties {
    private static String homePageURL;

    public static String getHomePageURL() {    
        switch (getHost().toLowerCase()) {
            case "local":
                System.out.println("entered local");
                homePageURL = "http://localhost:8081/";
                break;
            case "container":
                System.out.println("entered container");
                homePageURL = "http://host.docker.internal:8081/";
                break;
            case "grid":   
                System.out.println("entered grid");
                String accessGridFrom = getAccessGridFrom().toLowerCase();  
                
                // When accessing grid from local machine
                if (accessGridFrom.equals("local"))  {
                    homePageURL = "http://localhost:8081/";
                }

                // When accessing grid from test container
                if (accessGridFrom.equals("container"))  {
                    homePageURL = "http://host.docker.internal:8081/" ;
                }                       
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
