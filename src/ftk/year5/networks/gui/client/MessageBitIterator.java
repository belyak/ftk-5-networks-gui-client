package ftk.year5.networks.gui.client;

import java.util.Iterator;


public class MessageBitIterator implements Iterator<Boolean>, Iterable<Boolean> {    
    
    private final int intSequence[];
    private final int bitSequenceLength;
    private int currentIntOffset = 0;
    private byte currentBitOffset = 7;
    
    public MessageBitIterator(int [] sequence) {
        intSequence = sequence;
        bitSequenceLength = sequence.length * 8;
    }
    
    @Override
    public boolean hasNext() {
        return (currentIntOffset*8 + currentBitOffset) < bitSequenceLength;
    }

    @Override
    public Boolean next() {
        int currentInt = intSequence[currentIntOffset];
        boolean bitIsSet = ((currentInt >> currentBitOffset) & 1) != 0;
        currentBitOffset--;
        if (currentBitOffset < 0) {
            currentBitOffset = 7;
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