package ftk.year5.networks.guiclient.converters;

/**
 * класс предоставляющий экземпляр конвертера в зависимости от режима передачи
 */
public class ConverterProvider {
    static ConverterInterface plainConverter = new PlainConverter();
    static ConverterInterface sevenBitsConverter = new SevenBitsConverter();
    
    public static ConverterInterface provide(ConverterInterface.MODE mode) {
        switch (mode) {
            case MODE_PLAIN: 
                return plainConverter;
            case MODE_7BITS:
                return sevenBitsConverter;
        }
        return null;
    }
}
