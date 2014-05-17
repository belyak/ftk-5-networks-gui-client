package ftk.year5.networks.gui.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SevenBitsEncoderTest {
    
    SevenBitsEncoder converter;
    int [] messageToEncode;
    int [] encoded7Bits;
    
    public SevenBitsEncoderTest() {
    }
    
    @Before
    public void setUp() {
        converter = new SevenBitsEncoder();
        // определим последовательность байт для кодирования и 
        // последовательность декодированных байт для использования в тестовых
        // методах:
        messageToEncode = new int [7];
        for (int i = 0; i < 7; i ++) {
            messageToEncode[i] = 200; // байт в Java имеет знак
        }
        encoded7Bits = new int[8];
        encoded7Bits[0] = 100;
        encoded7Bits[1] = 50;
        encoded7Bits[2] = 25;
        encoded7Bits[3] = 12;
        encoded7Bits[4] = 70;
        encoded7Bits[5] = 35;
        encoded7Bits[6] = 17;
        encoded7Bits[7] = 72;
        
    }

    /**
     * проверка кодирования сообщения, кратный случай:
     * текстовое сообщение 7 байт по 8 бит --> 8 байт по 7 бит
     */
    @Test
    public void testEncodeDivisibleCase() {
        System.out.println("encode");
        int [] result = converter.encode(messageToEncode);
        assertArrayEquals(encoded7Bits, result);
    }

    /**
     * Проверка декодирования сообщения
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        int [] result = converter.decode(encoded7Bits);
        assertArrayEquals(messageToEncode, result);
    }
    
}
