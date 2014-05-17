package ftk.year5.networks.gui.client;

import static java.lang.Math.floor;

public class SevenBitsEncoder {
    public SevenBitsEncoder() {
    }
    public int[] encode(int [] message) {
        // определим длину кодированного сообщения как округленное вверх
        // значение частное от деления длины некодированного сообщения на 7
        // и умноженное на 8 (каждые 7 байт кодируются в 8 байт).
        int encodedLength = ((int)floor(((double)message.length)/7))*8;
        int [] result = new int[encodedLength];
        int result_offset = 0;
        int bit_position = 6;
        int out_byte = 0;
        for (boolean bitIsSet: new MessageBitIterator(message, 8)) {
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
    public int [] decode(int [] in_bytes) {
        int encodedLength = ((int)floor(((double)in_bytes.length)/8))*7;
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
