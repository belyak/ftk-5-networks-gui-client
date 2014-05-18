package ftk.year5.networks.guiclient.converters;

import java.util.Iterator;


public class MessageBitIterator implements Iterator<Boolean>, Iterable<Boolean> {    
    
    private final int intSequence[];
    private final int bitSequenceLength;
    private int currentIntOffset;
    private byte currentBitOffset;
    
    protected byte BITS_TO_ITERATE;
    
    public MessageBitIterator(int [] sequence, int bits_to_iterate) {
        intSequence = sequence;
        currentIntOffset = 0;
        BITS_TO_ITERATE = (byte) bits_to_iterate;
        bitSequenceLength = sequence.length * BITS_TO_ITERATE;
        currentBitOffset = (byte) (BITS_TO_ITERATE - 1);
    }
    
    @Override
    public boolean hasNext() {
        int absoluteBitOffset = (currentIntOffset*BITS_TO_ITERATE + currentBitOffset);
        return absoluteBitOffset < bitSequenceLength;
    }

    @Override
    public Boolean next() {
        int currentInt = intSequence[currentIntOffset];
        boolean bitIsSet = ((currentInt >> currentBitOffset) & 1) != 0;
        currentBitOffset--;
        if (currentBitOffset < 0) {
            currentBitOffset = (byte) (BITS_TO_ITERATE - 1);
            currentIntOffset++;
        }
        return bitIsSet;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Boolean> iterator() {
        return this;
    }
}