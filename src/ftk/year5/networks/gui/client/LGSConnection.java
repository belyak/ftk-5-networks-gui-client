package ftk.year5.networks.gui.client;

import java.io.IOException;

public class LGSConnection extends LGSBaseConnection {

    public LGSConnection(String host, int port) {
        super(host, port);
    }
    
    public void versionCommand() throws IOException {
        sendCommand("ver");
    }
    
    public void putLineCommand(String line) throws IOException {
        sendCommand("pl", line);
    }
    
    public void clearBufferCommand() throws IOException {
        sendCommand("cb");
    }
    
    public void calculateStatisticsCommand() throws IOException {
        sendCommand("calc");
    }
    
    public void printStatisticsCommand() throws IOException {
        sendCommand("ps");
    }
}
