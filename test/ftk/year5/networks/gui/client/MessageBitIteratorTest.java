/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ftk.year5.networks.gui.client;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andy
 */
public class MessageBitIteratorTest {
    
    public MessageBitIteratorTest() {
    }
    
    /**
     * проверка работы итератора на простой последовательности интеджеров.
     * ( итерируются только 8 младших бит интеджера)
     */
    @Test
    public void testRatherComplexTest() {
        System.out.println("rather complex iterator test...");
        int [] inSequence = {16, 240, 1};
        boolean [] outSequence = {
            false, false, false, true, false, false, false, false, // 16
            true,  true,  true,  true, false, false, false, false, // 240
            false, false, false, false, false, false, false, true  // 1
        };
        int count = 0;
        for (boolean bitIsSet: new MessageBitIterator(inSequence)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed at count ");
            sb.append(count);
            assertEquals(new String(sb), outSequence[count], bitIsSet);
            count++;
        }
    }
}
