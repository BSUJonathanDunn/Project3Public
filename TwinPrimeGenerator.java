/**
 * Class that helps to determine the smallest set of
 *  twin prime numbers and get the biggest of the set.
 * 
 * @author: Jonathan Dunn
 * @date: Ocotober 12, 2026
 */
public class TwinPrimeGenerator {

    /**
     * Checks whether the specified integer is a prime number
     * 
     * @param num integer number to check
     * @return true if num is prime, false if not.
     */
    static boolean twinPrimeHelper (int num) {
        boolean isPrime = true;
        int squareRoot = (int) Math.sqrt(num);

        if (num < 2) {
            return false;
        }

        for(int i = 2; i <= squareRoot; i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }

    /**
     * Checks all numbers within a given range and tries to find 
     *  a set of twin primes. Twin prime defined as a number 
     *  m and number m - 2 are both prime.
     * 
     * @param min integer number that is minimum number for the range.
     * @param max integer number representing maximum number for the range.
     * @return bigger integer of the twin prime set, 0 if not found.
     */
    public static int generateTwinPrime(int min, int max) {
        boolean firstPrime = false;
        boolean secondPrime = false;
        int returnPrime = 0;

        for (int i = min + 2; i <= max; i++) {
            firstPrime = twinPrimeHelper(i);
            secondPrime = twinPrimeHelper(i - 2);

            if (firstPrime && secondPrime) {
                returnPrime = i;
                break;
            }
            else {
                firstPrime = false;
                secondPrime = false;
            }
        }

        return returnPrime;
    }
}
