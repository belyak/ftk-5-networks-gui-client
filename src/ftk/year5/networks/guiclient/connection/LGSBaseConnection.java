/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftk.year5.networks.guiclient.connection;

import ftk.year5.networks.guiclient.converters.ConverterInterface;
import ftk.year5.networks.guiclient.converters.ConverterProvider;
import ftk.year5.networks.guiclient.converters.SevenBitsConverter;
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
    
    ConverterInterface.MODE convertingMode;
    ConverterInterface currentConverter;
    
    protected SevenBitsConverter sbits_encoder;
    
    /**
     * Конструктор соединения
     * 
     * @param host хост
     * @param port порт
     */
    public LGSBaseConnection(String host, int port) {
        this.host = host;
        this.port = port;
        this.sbits_encoder = new SevenBitsConverter();
        convertingMode = ConverterInterface.MODE.MODE_PLAIN;
        currentConverter = ConverterProvider.provide(convertingMode);
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
    
    public ConverterInterface.MODE getConvertingMode() {
        return convertingMode;
    }
    
    public void disconnect() throws IOException {
        s.close();
    }
    /**
     * Устанавливает режим передачи и выставляет требуемый конвертер.
     * 
     * @param mode режим передачи
     */
    protected void setConvertMode(ConverterInterface.MODE mode) {
        convertingMode = mode;
        currentConverter = ConverterProvider.provide(mode);
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
        List<Byte> collectedBytes = new ArrayList<>();
       
        int chunkSize = currentConverter.getChunkSize();
        
        byte in_byte;
        int [] encoded_message_part = new int[chunkSize];
        int [] decoded_message_part;
        
        while (true) {
            
            for (int i = 0; i < chunkSize; i++) {
                in_byte = in.readByte();
                encoded_message_part[i] = (int) in_byte;
            }
            decoded_message_part = currentConverter.decode(encoded_message_part);
            for (int in_int: decoded_message_part) {
                in_byte = (byte) in_int;
                collectedBytes.add(in_byte);
            }
            
            //buffer.append((char) in_byte);
            if (collectedBytes.size() > 2) {
                if (collectedBytes.get(collectedBytes.size() - 2) == '\r' 
                        && collectedBytes.get(collectedBytes.size() - 1) == '\n') {
                    byte [] result = new byte[collectedBytes.size()];
                    for (int i = 0; i < collectedBytes.size(); i++) {
                        result[i] = collectedBytes.get(i);
                    }
                    return new String(result, "UTF-8");
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
            String final_str_start = line.substring(0, 3) + " ";
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
        String str_msg = new String(sb);
        
        byte [] str_bytes = str_msg.getBytes();
        byte [] encodedString = new String(str_bytes, "UTF-8").getBytes();
        
        int [] message = new int[encodedString.length];
        for (int i = 0; i < message.length; i++) {
            message[i] = encodedString[i];
        }
        
        int [] encoded_message = currentConverter.encode(message);
        byte [] msg = new byte[encoded_message.length];
        for (int i = 0; i < encoded_message.length; i++) {
            msg[i] = (byte) encoded_message[i];
        }
        out.write(msg);
        out.flush();
    }
}
