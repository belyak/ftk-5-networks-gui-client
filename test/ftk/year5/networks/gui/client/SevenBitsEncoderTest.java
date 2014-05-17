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
        messageToEncode = new int[]{200, 200, 200, 200, 200, 200, 200};
        encoded7Bits = new int[]{100, 50, 25, 12, 70, 35, 17, 72};
        
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
     * проверка кодирования сообщения не кратного по длине 7 байтам.
     */
    @Test
    public void testEncodeNonDivisibleCase() {
        int [] originalMessage = {
            Character.getNumericValue('Y'),
            Character.getNumericValue('E'),
            Character.getNumericValue('S'),
            Character.getNumericValue('\r'),
            Character.getNumericValue('\n')
        };
        int [] encodedMessage = {44, 81, 42, 50, 1, 0, 26, 10};
        int [] result = converter.encode(originalMessage);
        
        assertArrayEquals(encodedMessage, result);
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
