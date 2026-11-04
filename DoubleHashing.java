/**
 * Subclass for the hashtable class specific
 *  to double hashing. Mainly to take care of
 *  how it handles the creation of it's probing
 *  sequence.
 * 
 * @author Jonathan Dunn
 * @date October 12, 2026
 */
public class DoubleHashing extends Hashtable {

    public DoubleHashing(int size) {
        super(size);
    }

    @Override 
    public int probeSequence(int key, int index) {
        int hashOne;
        int hashTwo;
        int hashKey;

        hashOne = positiveMod(key, this.capacity);
        hashTwo = 1 + (positiveMod(key, (this.capacity - 2)));
        hashKey = positiveMod((hashOne + (index * hashTwo)), this.capacity);

        return hashKey;
    }    
}
