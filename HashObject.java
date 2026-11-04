/**
 * Class representing a HashObject that contains a
 *  key, the frequency of it appearing in a hash table,
 *  and probeCount.
 * 
 * @author: Jonathan Dunn
 */
public class HashObject {
    private Object key;
    private int frequencyCount;
    private int probeCount;
    
    public HashObject(Object input) {
        key = input;
        frequencyCount = 1;
        probeCount = 0;
    }

    /**
     * Helper method to increase frequency count.
     */
    public void incrementFrequency() {
        this.frequencyCount++;
    }

    /**
     * Helper method in increment probe count.
     */
    public void incrementProbe() {
        this.probeCount++;
    }

    /**
     * Returns HashObject key value.
     * 
     * @return Object representing the object's
     *  key.
     */
    public Object getKey() {
        return key;
    }

    /**
     * Returns probe count.
     * 
     * @return integer representing number
     *  of probes done by HashObject.
     */
    public int getProbeCount() {
        return this.probeCount;
    }

    /**
     * Returns frequency count.
     * 
     * @return integer representing number of times
     *  a probe with a duplicate HashObject was inserted
     *  at it's current location.
     */
    public int getFrequencyCount() {
        return this.frequencyCount;
    }

    @Override 
    public boolean equals(Object input) {
        if (input instanceof HashObject) {
            HashObject temp = (HashObject) input;
            return this.key.equals(temp.getKey());
        }
        else {
            return false;
        }
        
    }

    @Override 
    public String toString() {
        String output;
        StringBuilder outString = new StringBuilder();

        outString.append(this.key);
        outString.append(" ");
        outString.append(this.frequencyCount);
        outString.append(" ");
        outString.append(this.probeCount);
        output = outString.toString();

        return output;
    }
}
