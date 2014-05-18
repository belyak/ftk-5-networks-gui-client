package ftk.year5.networks.guiclient.converters;

/**
 * Конвертер не изменяющий последовательность при получении/отправке.
 */
public class PlainConverter implements ConverterInterface {

    public PlainConverter() {
    }
    
    @Override
    public int get_chunk_size() {
        return 1;
    }

    @Override
    public int[] decode(int[] in_bytes) {
        return in_bytes;
    }

    @Override
    public int[] encode(int[] message) {
        return message;
    }
}
