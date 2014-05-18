package ftk.year5.networks.guiclient.converters;

import ftk.year5.networks.guiclient.ServerResponse;

public class SevenBitsConverter implements ConverterInterface {
    public SevenBitsConverter() {
    }
    
    @Override
    public int get_chunk_size() {
        return 8;
    }
    @Override
    public int[] encode(int [] message) {        
        // вставим пробелы перед последовательностью конца строки если длина
        // сообщения не кратна 7
        int [] in_message;
        if (message.length % 7 == 0) {
            in_message = message;
        } else {
            int requiredMessageLength = message.length + (7 - message.length % 7);
            in_message = new int[requiredMessageLength];
            int linesDelimiterLength = ServerResponse.LINES_DELIMITER.length();
            int linesDelimiterOffset = message.length - linesDelimiterLength;
            System.arraycopy(message, 0, in_message, 0, linesDelimiterOffset);
            for (int i = linesDelimiterOffset; i < requiredMessageLength - linesDelimiterLength; i ++) {
                in_message[i] = 32; // код символа пробел
            }
            int delimiterI = 0;
            for (int i = requiredMessageLength - linesDelimiterLength; i < requiredMessageLength; i++) {
                in_message[i] = (int)ServerResponse.LINES_DELIMITER.charAt(delimiterI);
                delimiterI++;
            }
        }
        
        // определим длину кодированного сообщения как округленное вверх
        // значение частное от деления длины некодированного сообщения на 7
        // и умноженное на 8 (каждые 7 байт кодируются в 8 байт).
        int encodedLength = in_message.length / 7 * 8;
        int [] result = new int[encodedLength];
        
        int result_offset = 0;
        int bit_position = 6;
        int out_byte = 0;
        for (boolean bitIsSet: new MessageBitIterator(in_message, 8)) {
            if (bitIsSet) {
                out_byte += (1 << bit_position);
            }
            bit_position--;
            if (bit_position < 0) {
                bit_position = 6;
                result[result_offset] = out_byte;
                result_offset++;
                out_byte = 0;
            }
        }
        return result;
    }
    @Override
    public int [] decode(int [] in_bytes) {
        int encodedLength = in_bytes.length / 8 * 7;
        int [] result = new int[encodedLength];
        int result_offset = 0;
        int bit_position = 7;
        int out_byte = 0;
        for (boolean bitIsSet: new MessageBitIterator(in_bytes, 7)) {
            if (bitIsSet) {
                out_byte += (1 << bit_position);
            }
            bit_position--;
            if (bit_position < 0) {
                bit_position = 7;
                result[result_offset] = out_byte;
                result_offset++;
                out_byte = 0;
            }
        }
        return result;
    }
}
