package ftk.year5.networks.guiclient.connection;

import ftk.year5.networks.guiclient.converters.ConverterInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Класс для хранения свойств сервера - поддерживаемых комманд и режимов 
 *  передачи извлекаемых из BM (BannerMessage) - первого ответа сервера.
 */
public class ServerProperties {
    private List<String> supportedCommands;
    private List<ConverterInterface.MODE> supportedModes;
    
    public ServerProperties(ServerResponse banner_message) {
    }
    
    /**
     * Проверка на то, что соединение установлено с сервером LGS
     * 
     * @param response первый ответ сервера
     * @return сервер является сервером LGS
     */
    public boolean verificateServer(ServerResponse response) {
        boolean verification_result = verificateBannerMessage(response);
        String [] extractedCommands = extractAvailableCommandsMnemonics(response);
        ConverterInterface.MODE [] extractedModes = extractAvailableTransferModes(response);
        verification_result &= (extractedCommands != null);
        if (verification_result) {
            supportedCommands = new ArrayList<>();
            supportedCommands.addAll(Arrays.asList(extractedCommands));
            supportedModes = new ArrayList<>();
            supportedModes.addAll(Arrays.asList(extractedModes));
        }
        return verification_result;
    }
    
        
    /**
     * Минимальная проверка на то, что соединение установлено с сервером LGS
     * 
     * @param response первое сообщение полученное от сервера
     * @return присоединились к тому серверу
     */
    boolean verificateBannerMessage(ServerResponse response) {
        if (!response.is_multiline && response.code == 200) {
            if (response.line.contains("Text frequency analysis server")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Проверяет, поддерживается ли комманда сервером, с которым установлено
     * подключение на основании информации, извлеченной из BM
     * @param cmd мнемоника команды
     * @return команда подддерживается/не поддерживается
     */
    public boolean commandIsSupported(String cmd) {
        return supportedCommands.contains(cmd);
    }
    
    /**
     * Проверяет, поддерживается ли режим передачи сервером с которым 
     * установлено соединение на основании информации, извлеченной из BM
     * @param mode возможный режим передачи
     * @return режим передачи поддерживается/не поддерживается
     */
    public boolean modeIsSupported(ConverterInterface.MODE mode) {
        return supportedModes.contains(mode);
    }
    
    /**
     * Извлечение списка поддерживаемых команд из BannerMessage
     * 
     * @param response первое сообщение полученное от сервера
     * @return список мнемоник поддерживаемых комманд
     */
    private String [] extractAvailableCommandsMnemonics(ServerResponse response) {
        return extractServiceInfo(response, "SC:");
    }
    
    /**
     * Извлечение списка поддерживаемых режимов из BannerMessage
     * 
     * @param response первое сообщение полученное от сервера
     * @return список поддерживаемых режимов
     */
    private ConverterInterface.MODE [] extractAvailableTransferModes(ServerResponse response) {
        String [] modesMnemonics = extractServiceInfo(response, "SM:");
        ConverterInterface.MODE [] modes = new ConverterInterface.MODE[modesMnemonics.length];
        for (int i = 0; i < modesMnemonics.length; i++) {
            switch(modesMnemonics[i]) {
                case "plain": modes[i] = ConverterInterface.MODE.MODE_PLAIN;
                    break;
                case "7bit": modes[i] = ConverterInterface.MODE.MODE_7BITS;
                    break;
                case "base64": modes[i] = ConverterInterface.MODE.MODE_BASE64;
                    break;
            }
        }
        return modes;
    }
    
    /**
     * Общая логика для извлечения списков мнемоник из BM
     * 
     * @param response первое сообщение полученное от сервера
     * @param startSequence последовательность, предшевствующая извлекаемым 
     * подстрокам
     * @return 
     */
    private String [] extractServiceInfo(ServerResponse response, String startSequence) {
        if (!response.is_multiline && response.code == 200) {
            String line = response.line;
            int startIx = line.indexOf(startSequence);
            if (startIx == -1) {
                return null;
            }
            int endIx = line.indexOf(".", startIx);
            if (endIx == -1) {
                return null;
            }
            String joined_data = line.substring(startIx + startSequence.length(), endIx);
            return joined_data.split(",");
        }
        return null;
    }
}
