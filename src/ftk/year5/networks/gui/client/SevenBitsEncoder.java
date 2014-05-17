package ftk.year5.networks.gui.client;

/**
 *
 * @author andy
 */
public class SevenBitsEncoder {
    public SevenBitsEncoder() {
    }
    public byte[] encode(byte [] message) {
        byte [] result = new byte[2];
        result[0] = 21;
        result[1] = 22;
        return result;
    }
    public byte [] decode(byte [] in_bytes) {
        return new byte[22];
    }
}
