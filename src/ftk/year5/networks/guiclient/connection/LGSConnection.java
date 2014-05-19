package ftk.year5.networks.guiclient.connection;

import java.io.IOException;

public class LGSConnection extends LGSBaseConnection {

    public LGSConnection(String host, int port) {
        super(host, port);
    }
    
    public ServerResponse versionCommand() throws IOException {
        sendCommand("ver");
        return getResponse();
    }
    
    public ServerResponse exitCommand() throws IOException {
        sendCommand("exit");
        return getResponse();
    }
    
    public ServerResponse putLineCommand(String line) throws IOException {
        sendCommand("pl", line);
        return getResponse();
    }
    
    public void clearBufferCommand() throws IOException {
        sendCommand("cb");
    }
    
    public ServerResponse calculateStatisticsCommand() throws IOException {
        sendCommand("calc");
        return getResponse();
    }
    
    public ServerResponse printStatisticsCommand() throws IOException {
        sendCommand("ps");
        return getResponse();
    }
    
    public ServerResponse loadStatisticsCommand(String name) throws IOException {
        sendCommand("ld", name);
        return getResponse();
    }
    
    public ServerResponse saveStatisticsCommand(String name) throws IOException {
        sendCommand("st", name);
        return getResponse();
    }
    
    public ServerResponse mergeStatisticsCommand(String name) throws IOException {
        sendCommand("mrg", name);
        return getResponse();
    }
}
