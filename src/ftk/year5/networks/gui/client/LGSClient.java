package ftk.year5.networks.gui.client;

import java.io.IOException;

public class LGSClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        LGSConnection conn = new LGSConnection("andrey-linux", 8020);
        conn.connect();
        ServerResponse bannerResponse = conn.getResponse();
        System.out.println(bannerResponse);
        
        conn.versionCommand();
        ServerResponse response = conn.getResponse();
        System.out.println(response);
        
        conn.putLineCommand("oop oop oop abba");
        response = conn.getResponse();
        System.out.println(response);
        
        conn.calculateStatisticsCommand();
        response = conn.getResponse();
        System.out.println(response);
        
        conn.printStatisticsCommand();
        response = conn.getResponse();
        System.out.println(response);
    }
}
