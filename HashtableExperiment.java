
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Driver class that runs the experiment to study how
 *  the load factor affects the average number of probes
 *  required for linear probing and double hashing
 *  for various types of inputs.
 *
 * @author Jonathan Dunn
 * @date October 12, 2026
 */
public class HashtableExperiment {
    private int dataSource;
    private double loadFactor;
    private int debugLevel = 0;
    private int numberOfObjects;
    private int tableSize;
    private LinearProbing linear;
    private DoubleHashing doubleHash;

    /**
     * Prints use message
     */
    public static void printUsageMessage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println("  <dataSource>: 1 ==> random numbers");
        System.out.println("                2 ==> date value as a long");
        System.out.println("                3 ==> word list");
        System.out.println("  <loadFactor>: The ratio of objects to table size, ");
        System.out.println("                denoted by alpha = n/m");
        System.out.println("  <debugLevel>: 0 ==> print summary of experiment");
        System.out.println("                1 ==> save the two hash tables to a file at the end");
        System.out.println("                2 ==> print debugging output for each insert");
    }

    /**
     * Get table size using generateTwinPrime method.
     *  Should be the higher number of the smallest
     *  twin prime pair within given range.
     */
    public void getTableSize() {
        this.tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
    }

    /**
     * Get number of objects that the table should hold
     *  to get desired work factor.
     */
    public void getNumberOfObjects() {
        numberOfObjects = (int) Math.ceil(this.loadFactor * this.tableSize);
    }

    /**
     * Takes user input, checks for correct usage and
     *  assigns them to the appropriate variables.
     *
     * @param args string of user input passed from
     *  main
     */
    public void processArgs(String[] args) {
        try {
            dataSource = Integer.parseInt(args[0]);
            loadFactor = Double.parseDouble(args[1]);

            if (args.length == 3) {
                debugLevel = Integer.parseInt(args[2]);
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Error: Unable to parse arguments.");
        }

        if (dataSource < 1 || dataSource > 3) {
            throw new IllegalArgumentException(
                    "Error: datasource must be between 1, 2, or 3.");
        }

        if (loadFactor > 1 || loadFactor < 0
                || Double.isNaN(loadFactor)) {
            throw new IllegalArgumentException(
                    "Error: loadfactor must a be a value between 0 and 1.");
        }

        if (args.length == 3 && (debugLevel < 0 || debugLevel > 2)) {
            throw new IllegalArgumentException(
                    "Error: debuglevel must be a 0, 1, or 2.");
        }
    }

    /**
     * Checks whether either hash table still needs objects.
     *
     * @return true if either table has not reached numberOfObjects
     */
    private boolean needsMoreObjects() {
        return linear.getSize() < numberOfObjects
                || doubleHash.getSize() < numberOfObjects;
    }

    /**
     * Inserts an object into a hash table and prints
     *  insertion status if debug level is 2.
     *
     * @param table hash table receiving the object
     * @param tableName name of the hashing strategy
     * @param key object to insert
     * @throws HashTableOverflowException if the table overflows
     */
    private void insertIntoTable(Hashtable table, String tableName, Object key) throws HashTableOverflowException {

        if (table.getSize() < numberOfObjects) {
            HashObject newHash = new HashObject(key);
            int currentSize = table.getSize();
            int returnedIndex = table.insert(table.hashTable, newHash);

            if (this.debugLevel == 2) {
                if (table.getSize() > currentSize) {
                    System.out.println(tableName + " - Table("
                            + returnedIndex + "): Inserted Key "
                            + newHash.getKey() + " succesfully.");
                }
                else {
                    System.out.println(tableName + " - Table("
                            + returnedIndex + "): Key "
                            + newHash.getKey() + " was a duplicate.");
                }
            }
        }
    }

    /**
     * Inserts a key into whichever hash tables still need objects.
     *
     * @param key object to insert into the hash tables
     * @throws HashTableOverflowException if a table overflows
     */
    private void insertIntoTables(Object key) throws HashTableOverflowException {
        insertIntoTable(linear, "Linear", key);
        insertIntoTable(doubleHash, "Double", key);
    }

    /**
     * Populates hash tables with random integer keys.
     *
     * @throws HashTableOverflowException if a table overflows
     */
    private void populateRandomNumbers() throws HashTableOverflowException {
        Random rand = new Random();

        while (needsMoreObjects()) {
            int key = rand.nextInt();
            insertIntoTables(key);
        }
    }

    /**
     * Populates hash tables with Date objects.
     *  Each successive date is one second later.
     *
     * @throws HashTableOverflowException if a table overflows
     */
    private void populateDates() throws HashTableOverflowException {
        long current = new Date().getTime();

        while (needsMoreObjects()) {
            Date currentDate = new Date(current);
            insertIntoTables(currentDate);
            current += 1000;
        }
    }

    /**
     * Populates hash tables with strings from word-list.txt.
     *
     * @throws FileNotFoundException if word-list.txt cannot be opened
     * @throws HashTableOverflowException if a table overflows
     */
    private void populateWords() throws FileNotFoundException, HashTableOverflowException {

        File file = new File("word-list.txt");

        try (Scanner fileScanner = new Scanner(file)) {
            while (needsMoreObjects() && fileScanner.hasNextLine()) {
                String currentString = fileScanner.nextLine();
                insertIntoTables(currentString);
            }

            if (needsMoreObjects()) {
                System.out.println(
                        "Error: word-list.txt does not contain enough words.");
            }
        }
    }

    /**
     * Populates hash tables with objects matching the selected data source.
     *
     * @param dataSource integer representing the type of data
     *      1 - random numbers
     *      2 - Date objects
     *      3 - Strings from word-list.txt
     */
    public void populateTable(int dataSource) {
        try {
            if (dataSource == 1) {
                populateRandomNumbers();
            }
            else if (dataSource == 2) {
                populateDates();
            }
            else {
                populateWords();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(
                    "Error: Could not open file: \"word-list.txt\"");
        }
        catch (HashTableOverflowException e) {
            System.out.println("Error: Hash table overflow.");
        }
    }

    /**
     * Helper method to translate dataSource
     *  variable to its string equivalent for
     *  display purposes.
     *
     * @return String representation of dataSource
     */
    private String translateDataSource() {
        if (this.dataSource == 1) {
            return "Random Numbers";
        }
        else if (this.dataSource == 2) {
            return "Dates";
        }
        else if (this.dataSource == 3) {
            return "Word-List";
        }
        else {
            return "Unknown Source";
        }
    }

    /**
     * Prints the results for one hash table.
     *
     * @param table hash table whose results are printed
     * @param tableName name of the hashing strategy
     * @param dumpFile filename used when debug level is 1
     */
    private void printTableResults(Hashtable table, String tableName, String dumpFile) {

        System.out.println("\tUsing " + tableName);
        System.out.println("HashtableExperiment: size of hash table is "
                + table.getSize());
        System.out.println("\tInserted " + table.getTotalInserts()
                + " elements, of which "
                + table.getTotalDuplicates() + " were duplicates");
        System.out.printf("\tAvg. no. of probes = %.2f%n",
                table.calcAverageProbe());

        if (debugLevel == 1) {
            try {
                table.dumpToFile(dumpFile);
                System.out.println(
                        "HashtableExperiment: Saved dump of hash table");
            }
            catch (FileNotFoundException e) {
                System.out.println("Error: Could not write to file.");
            }
        }
    }

    /**
     * Prints results of experiment to user.
     *  Prints the table capacity, data source, load factor,
     *  and statistics for both hash tables.
     */
    public void printResults() {
        System.out.println(
                "HashtableExperiment: Found a twin prime for table capacity: "
                        + this.tableSize);
        System.out.println("HashtableExperiment: Input: "
                + this.translateDataSource()
                + "\tLoadfactor: " + this.loadFactor);
        System.out.println("");

        printTableResults(linear, "Linear Probing", "linear-dump.txt");

        System.out.println("");

        printTableResults(doubleHash, "Double Hashing", "double-dump.txt");
    }

    public static void main(String[] args) {
        HashtableExperiment experiment = new HashtableExperiment();

        if (args.length < 2 || args.length > 3) {
            printUsageMessage();
            return;
        }

        try {
            experiment.processArgs(args);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            printUsageMessage();
            return;
        }

        experiment.getTableSize();
        experiment.getNumberOfObjects();

        experiment.linear = new LinearProbing(experiment.tableSize);
        experiment.doubleHash = new DoubleHashing(experiment.tableSize);

        experiment.populateTable(experiment.dataSource);

        experiment.printResults();
    }
}
