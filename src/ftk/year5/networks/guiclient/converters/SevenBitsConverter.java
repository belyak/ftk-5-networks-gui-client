package ftk.year5.networks.guiclient.converters;

import ftk.year5.networks.guiclient.connection.ServerResponse;

public class SevenBitsConverter implements ConverterInterface {
    public SevenBitsConverter() {
    }
    
    @Override
    public int getChunkSize() {
        return 8;
    }
    @Override
    public int[] encode(int [] message) {        
        // вставим пробелы перед последовательностью конца строки если длина
        // сообщения не кратна 7
        int [] inMessage;
        if (message.length % 7 == 0) {
            inMessage = message;
        } else {
            int requiredMessageLength = message.length + (7 - message.length % 7);
            inMessage = new int[requiredMessageLength];
            int linesDelimiterLength = ServerResponse.LINES_DELIMITER.length();
            int linesDelimiterOffset = message.length - linesDelimiterLength;
            System.arraycopy(message, 0, inMessage, 0, linesDelimiterOffset);
            for (int i = linesDelimiterOffset; i < requiredMessageLength - linesDelimiterLength; i ++) {
                inMessage[i] = 32; // код символа пробел
            }
            int delimiterI = 0;
            for (int i = requiredMessageLength - linesDelimiterLength; i < requiredMessageLength; i++) {
                inMessage[i] = (int)ServerResponse.LINES_DELIMITER.charAt(delimiterI);
                delimiterI++;
            }
        }
        
        int encodedLength = inMessage.length / 7 * 8;
        int [] result = new int[encodedLength];
        
        int resultOffset = 0;
        int bitPosition = 6;
        int outByte = 0;
        for (boolean bitIsSet: new MessageBitIterator(inMessage, 8)) {
            if (bitIsSet) {
                outByte += (1 << bitPosition);
            }
            bitPosition--;
            if (bitPosition < 0) {
                bitPosition = 6;
                result[resultOffset] = outByte;
                resultOffset++;
                outByte = 0;
            }
        }
        return result;
    }
    @Override
    public int [] decode(int [] inBytes) {
        int encodedLength = inBytes.length / 8 * 7;
        int [] result = new int[encodedLength];
        int resultOffset = 0;
        int bitPosition = 7;
        int outByte = 0;
        for (boolean bitIsSet: new MessageBitIterator(inBytes, 7)) {
            if (bitIsSet) {
                outByte += (1 << bitPosition);
            }
            bitPosition--;
            if (bitPosition < 0) {
                bitPosition = 7;
                result[resultOffset] = outByte;
                resultOffset++;
                outByte = 0;
            }
        }
        return result;
    }
}
