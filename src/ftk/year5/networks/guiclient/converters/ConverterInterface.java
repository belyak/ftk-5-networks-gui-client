package ftk.year5.networks.guiclient.converters;

/**
 * Интерфейс который реализуют все конвертеры входного/выходного потока.
 */
public interface ConverterInterface {

    /**
     * режимы кодирования/декодирования
     */
    enum MODE {MODE_PLAIN, MODE_7BITS, MODE_BASE64}; 
    
    int get_chunk_size();
    
    int[] decode(int[] in_bytes);

    int[] encode(int[] message);
    
}
