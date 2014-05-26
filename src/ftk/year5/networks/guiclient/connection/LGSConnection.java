package ftk.year5.networks.guiclient.connection;

import ftk.year5.networks.guiclient.converters.ConverterInterface;
import java.io.IOException;

public class LGSConnection extends LGSBaseConnection {

    public LGSConnection(String host, int port) {
        super(host, port);
    }
    
    public ServerResponse versionCommand() throws IOException {
        sendCommand(CmdMnemonics.VERSION);
        return getResponse();
    }
    
    public ServerResponse exitCommand() throws IOException {
        sendCommand(CmdMnemonics.EXIT);
        return getResponse();
    }
    
    public ServerResponse setModeCommand(ConverterInterface.MODE mode) throws IOException {
        String mode_str = "plain";
        switch (mode) {
            case MODE_PLAIN: mode_str = "plain"; break;
            case MODE_7BITS: mode_str = "7bit"; break;
            case MODE_BASE64: mode_str = "base64"; break;
        }
        sendCommand(CmdMnemonics.MODE, mode_str);
        ServerResponse response = getResponse();
        if (response.code == 200) {
            this.setConvertMode(mode);
        }
        return response;
    }
    
    public ServerResponse putLineCommand(String line) throws IOException {
        sendCommand(CmdMnemonics.PUT_LINE, line);
        return getResponse();
    }
    
    public ServerResponse clearBufferCommand() throws IOException {
        sendCommand(CmdMnemonics.CLEAR_BUFFER);
        return getResponse();
    }
    
    public ServerResponse calculateStatisticsCommand() throws IOException {
        sendCommand(CmdMnemonics.CALCULATE);
        return getResponse();
    }
    
    public ServerResponse printStatisticsCommand() throws IOException {
        sendCommand(CmdMnemonics.PRINT_STATISTICS);
        return getResponse();
    }
    
    public ServerResponse loadStatisticsCommand(String name) throws IOException {
        sendCommand(CmdMnemonics.LOAD_STATISTICS, name);
        return getResponse();
    }
    
    public ServerResponse saveStatisticsCommand(String name) throws IOException {
        sendCommand(CmdMnemonics.SAVE_STATISTICS, name);
        return getResponse();
    }
    
    public ServerResponse mergeStatisticsCommand(String name) throws IOException {
        sendCommand(CmdMnemonics.MERGE_STATISTICS, name);
        return getResponse();
    }
}
