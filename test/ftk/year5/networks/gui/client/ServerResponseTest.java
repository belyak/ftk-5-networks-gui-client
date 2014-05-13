package ftk.year5.networks.gui.client;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ServerResponseTest {
    
    @Test
    public void testServerResponseSingleLine() {
        String message = "200 Test single-line\r\n";
        ServerResponse sr = new ServerResponse(message);
        assertEquals(sr.code, 200);
        assertEquals(sr.is_multiline, false);
        assertEquals(sr.line, "Test single-line");
    }
    
    @Test
    public void testServerResponseMultiLine() {
        String message = "220-Test\nmulti\n220 lines\r\n";
        ServerResponse sr = new ServerResponse(message);
        assertEquals(sr.code, 220);
        assertEquals(sr.is_multiline, true);
        assertEquals(sr.lines.get(0), "Test");
        assertEquals(sr.lines.get(1), "multi");
        assertEquals(sr.lines.get(2), "lines");
    }
    
}
