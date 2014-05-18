package ftk.year5.networks.guiclient;

import java.util.ArrayList;
import java.util.List;


/**
 * Ответ сервера
 * 
 * @author andy
 */
public class ServerResponse {
    public static String LINES_DELIMITER = "\r\n";
    public int code;
    public boolean is_multiline;
    public String line;
    public List<String> lines;
    
    public ServerResponse(String message) {
        is_multiline = message.charAt(3) == '-';
        
        if (is_multiline) {
            String[] split = message.split(LINES_DELIMITER);
            int code1 = Integer.parseInt(split[0].substring(0, 3));
            int code2 = Integer.parseInt(split[split.length - 1].substring(0, 3));
            if (code1 != code2) {
                //TODO: raise exception
            }
            code = code1;
            lines = new ArrayList<>();
            lines.add(split[0].substring(4));
            for (int i = 1; i < split.length - 1; i++) {
                lines.add(split[i]);
            }
            lines.add(split[split.length - 1].substring(4).replace(LINES_DELIMITER, ""));
        } else {
            code = Integer.parseInt(message.substring(0, 3));
            line = message.substring(4).replace(LINES_DELIMITER, "");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.append(' ');
        if (is_multiline) {
            for (String current_line : lines) {
                sb.append(current_line);
                sb.append("\n");
            }
        } else {
            sb.append(line);
        }
        return new String(sb);
    } 
}
