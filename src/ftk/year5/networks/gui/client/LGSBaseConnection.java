/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftk.year5.networks.gui.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Соединение с сервером с возможностью отправки комманд и получения ответа.
 * 
 * @author andy
 */
public class LGSBaseConnection {
    protected String host;
    protected int port;
    
    protected Socket s;
    
    protected DataInputStream in;
    protected DataOutputStream out;
    
    /**
     * Конструктор соединения
     * 
     * @param host хост
     * @param port порт
     */
    public LGSBaseConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Установка соединения
     * 
     * @throws IOException 
     */
    public void connect() throws IOException {
        s = new Socket(host, port);
        InputStream sin = s.getInputStream();
        OutputStream sout = s.getOutputStream();
        in = new DataInputStream(sin);
        out = new DataOutputStream(sout);
    }
    
    /**
     * отправка команды без аргумента
     * 
     * @param cmd мнемоника команды
     * @throws IOException 
     */
    public void sendCommand(String cmd) throws IOException {
        sendLine(cmd);
    }
    
    /**
     * Отправка команды с аргументом
     * 
     * @param cmd мнемоника команды
     * @param rest остаток строки - аргумент
     * @throws IOException 
     */
    public void sendCommand(String cmd, String rest) throws IOException {
        String line = cmd + ' ' + rest;
        sendLine(line);
    }
    
    /**
     * блокирующее чтение до получения сообщения от сервера.
     * 
     * @return ответ сервера запакованный в объект ServerResponse
     * @throws IOException 
     */
    public ServerResponse getResponse() throws IOException {
        return new ServerResponse(readMessage());
    }
    
    private String readLine() throws IOException {
        StringBuilder buffer = new StringBuilder();
        
        byte response;
        
        while (true) {
            response = in.readByte();
            buffer.append((char) response);
            if (buffer.length() > 2) {
                if (buffer.charAt(buffer.length() - 2) == '\r' && buffer.charAt(buffer.length() - 1) == '\n') {
                    return new String(buffer);
                }
            }
        }
    }
    
    private String readMessage() throws IOException {
        StringBuilder buffer = new StringBuilder();
        boolean is_multiline;
        List<String> lines = new ArrayList<>();
        
        String line = readLine();
        lines.add(line);
        
        is_multiline = line.charAt(3) == '-';
        
        if (is_multiline) {
            String final_str_start = line.substring(0, 4);
            while(true) {
                line = readLine();
                lines.add(line);
                if (line.substring(0, 4).equals(final_str_start)) {
                    break;
                }
            }
        } else {
            return line;
        }
        StringBuilder result = new StringBuilder();
        for (String result_line: lines) {
            result.append(result_line);
        }
        return new String(result);
    }
    
    private void sendLine(String line) throws IOException {
        StringBuilder sb = new StringBuilder(line);
        sb.append(ServerResponse.LINES_DELIMITER);
        String msg = new String(sb);
        out.writeBytes(msg);
        out.flush();
    }
}
