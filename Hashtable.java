import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Abstract class representing a hash table.
 *  Hash table is represented by an array of HashObjects.
 *  Keeps track of it's capacity, current size,
 *  load factor, total amount of inserts attempted,
 *  number of duplicates found and total probes.
 * 
 * @author: Jonathan Dunn
 * @date: October 12, 2026
 */
public abstract class Hashtable {
    protected HashObject[] hashTable;
    protected int size;
    protected int capacity;
    protected double loadFactor;
    protected int totalInserts = 0;
    protected int totalDuplicates = 0;
    protected int totalProbes = 0;

    /**
     * Constructor for a hashtable of given size.
     * @param arraySize integer for size of array
     *  that represents the hash table.
     */
    public Hashtable (int arraySize) {
        this.capacity = arraySize;
        this.size = 0;
        hashTable = new HashObject[this.capacity];
    }

    /**
     * Used to determine the probing sequence that the
     *  table will use to check where an object with the
     *  given key will be inserted into the table.
     * 
     * @param key new key used to calculate probe point
     * @param index index to be used to calculate prob point
     * @return integer representing index that object should be
     *  insert into
     */
    public abstract int probeSequence(int key, int index);

    /**
     * Getter method for total inserts.
     * 
     * @return integer representing total amount
     *  of insert attempts done by hash table.
     */
    public int getTotalInserts() {
        return totalInserts;
    }

    /**
     * Getter method for current table size.
     * 
     * @return integer representing the total number
     *  of elements within the table.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets total number of duplicate insert attempts.
     * 
     * @return integer representing totalled frequency count
     *   for each element.
     */
    public int getTotalDuplicates() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.hashTable[i] != null) {
                totalDuplicates += (this.hashTable[i].getFrequencyCount() - 1);
            }
        }

        return this.totalDuplicates;
    }

    /**
     * Gets total number of probes done by each element
     *  before successful insertion.
     * 
     * @return integer representing summed total of probe
     *  attempts by all elements.
     */
    public int getTotalProbes() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.hashTable[i] != null) {
                totalProbes += this.hashTable[i].getProbeCount();
            }
        }

        return this.totalProbes;
    }

    /**
     * Takes total probes and gives average total of probes
     *  for the experiment.
     * 
     * @return double number representing the average probing
     *  attempts made by each element before a successful
     *  insertion was found.
     */
    public double calcAverageProbe() {
        double average;

        if (this.size == 0) {
            return 0.0;
        }

        average = (double) getTotalProbes() / this.size;

        return average;
    }

    /**
     * Dumps the tables contents to a file. Should include
     *  the index with an element and the element itself.
     * 
     * @param fileName string representing file that the
     *  dump should be written to.
     * @throws FileNotFoundException if file is not found or
     *  unable to be written to.
     */
    public void dumpToFile(String fileName) throws FileNotFoundException {
        PrintWriter outPut = new PrintWriter(fileName);
        for (int i = 0; i < this.capacity; i++) {
            if (hashTable[i] != null) {
                outPut.print("table[" + i + "]: " + hashTable[i].toString() + "\n");
            }
        }
        outPut.close();
    }

    /**
     * Inserts a HashObject into the specified table. Insertion index
     *  specified by the probeSequence method.
     * 
     * @param table array of HashObjects that we want to insert the
     *  new HashObject
     * @param toInsert HashObject to be inserted
     * @return index where object was inserted or where copy of
     *  HashObject was already found within the table.
     * @throws HashTableOverflowException thrown if table is full
     */
    public int insert(HashObject[] table, HashObject toInsert) throws HashTableOverflowException {
        int currentIndex = 0;
        int insertIndex = 0;
        int k = toInsert.getKey().hashCode();
        totalInserts++;

        while (currentIndex < this.capacity) {
            toInsert.incrementProbe();
            insertIndex = probeSequence(k, currentIndex);
            if (table[insertIndex] == null) {
                table[insertIndex] = toInsert;
                this.size++;
                
                return insertIndex;
            }
            else if(table[insertIndex].equals(toInsert)) {
                table[insertIndex].incrementFrequency();
                return insertIndex;
            }

            currentIndex++;
        }

        throw new HashTableOverflowException("Error: Hash table overflow");
    }

    /**
     * Checks to see if the passed HashObject is already within the table.
     * 
     * @param table HashObject array to be checked
     * @param search HashObject being searched for.
     * @return int representing index of element if found, -1 if not.
     */
    public int search(HashObject[] table, HashObject search) {
        int currentIndex = 0;
        int searchIndex = 0;
        int k = search.getKey().hashCode();

        searchIndex = probeSequence(k, currentIndex);

        while (currentIndex < this.capacity && table[searchIndex] != null) {
            if (table[searchIndex].equals(search)) {
                return searchIndex;
            }

            currentIndex++;
            searchIndex = probeSequence(k, currentIndex);
        }

        return -1;

    }
    
    /**
     * Method used to make sure that the probing calculations returns
     *  a positive integer.
     * 
     * @param dividend integer representing the dividend
     * @param divisor integer representing the divisor
     * @return returns a positive integer for the probe calculations.
     */
    protected int positiveMod (int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }

        return quotient;
    }
}
