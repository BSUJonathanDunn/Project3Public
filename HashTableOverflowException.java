/**
 * Custom error handler, thrown if trying to insert an object
 *  into a full hash table.
 * 
 * @author Jonathan Dunn
 * HashTableOverflowException
 */
public class HashTableOverflowException extends Exception {
    public HashTableOverflowException(String message) {
        super(message);
    }
}