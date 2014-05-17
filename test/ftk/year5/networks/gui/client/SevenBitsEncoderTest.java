/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftk.year5.networks.gui.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andy
 */
public class SevenBitsEncoderTest {
    
    SevenBitsEncoder converter;
    byte [] messageToEncode;
    byte [] encoded7Bits;
    
    public SevenBitsEncoderTest() {
    }
    
    @Before
    public void setUp() {
        converter = new SevenBitsEncoder();
        messageToEncode = new byte [7];
        for (int i = 0; i < 7; i ++) {
            messageToEncode[i] = 200 - 128; // байт в Java имеет знак
        }
        encoded7Bits = new byte[8];
        encoded7Bits[0] = 100 - 128;
        encoded7Bits[1] = 50 - 128;
        encoded7Bits[2] = 25 - 128;
        encoded7Bits[3] = 12 - 128;
        encoded7Bits[4] = 70 - 128;
        encoded7Bits[5] = 35 - 128;
        encoded7Bits[6] = 17 - 128;
        encoded7Bits[7] = 72 - 128;
        
    }

    /**
     * роверка перекодирования сообщения, кратный случай:
     * текстовое сообщение 7 байт по 8 бит --> 8 байт по 7 бит
     */
    @Test
    public void testEncodeDivisibleCase() {
        System.out.println("encode");
        byte [] result = converter.encode(messageToEncode);
        assertArrayEquals(encoded7Bits, result);
    }

    /**
     * Test of decode method, of class SevenBitsEncoder.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        byte [] result = converter.decode(encoded7Bits);
        assertArrayEquals(messageToEncode, result);
    }
    
}
