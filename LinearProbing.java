/**
 * Subclass for the hashtable class specific
 * to linear probing. Mainly to take care of
 * how it handles the creation of it's probing
 * sequence.
 * 
 * @author Jonathan Dunn
 * @date October 12, 2026
 */
public class LinearProbing extends Hashtable {
    
    public LinearProbing(int size) {
        super(size);
    }

    @Override 
    public int probeSequence(int key, int index) {
        int hashOne;
        int hashKey;

        hashOne = positiveMod(key, this.capacity);
        hashKey = positiveMod((hashOne + index), this.capacity);

        return hashKey;
    }
}
