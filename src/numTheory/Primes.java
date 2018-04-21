package numTheory;
import java.util.ArrayList;
import java.util.List;

public class Primes {
	
	public static void main(String[] args) {
		int[] testPoints = new int[] {50, 100, 500, 1000, 10000, 100000};
		
		System.out.printf("%-10s%-10s%-10s%-10s%-10s", "x", "4k - 1", "4k + 1", "other", "pi(x)");
		System.out.println();
		
		for (int i = 0; i < testPoints.length; i++) {
			printRatio(testPoints[i]);
		}
	}

	/**
	 * Prints the ratio of primes of the form 4k - 1, 4k + 1, and other from 2 up to and 
	 * including x.
	 * @param x the maximum number which the primes will be counted till.
	 */
	public static void printRatio(int max) {
		int kplus1 = 0;
		int kminus1 = 0;
	    int other = 0;
		
		int[] primes = generatePrimes(max);
		
		for (int i = 0; i < primes.length; i++) {
			int prime = primes[i];
			
			if ((prime + 1) % 4 == 0) {
				kminus1++;
			} else if ((prime - 1) % 4 == 0){
				kplus1++;
			} else {
				other++;
			}
		}
		int totalPrimes = primes.length;
		System.out.printf("%-10s%-10s%-10s%-10s%-10s", max, kminus1, kplus1, other, totalPrimes);
		System.out.println();
	}
	
	/**
	 * Determines if a number is prime.
	 * @param n is the number determined to be a prime or composite.
	 * @return True if n is a prime, false if n is composite.
	 */
	public static boolean isPrime(int n) {
		if (n > 2) {
		    // Check if n is a multiple of 2
		    if (n % 2 == 0) {
		    	if (n == 2) {
		    		return true;
		    	}
		    	return false;
		    }
		    
		    // If not, then just check the odds
		    for (int i = 3; i * i <= n; i += 2) {
		        if(n % i == 0)
		            return false;
		    }
			return true;
			
		} else if (n == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Generates an array of all primes of all numbers less than or equal to x
	 * @param x the maximum number which could be in the array.
	 * @return The array starting at 2 which contains the prime numbers up to and possibly 
	 * including x.
	 */
	public static int[] generatePrimes(int x) {
		if (x < 2) {
			throw new IllegalArgumentException("x must be greater than or equal to 2");
		}
		
		List<Integer> primes = new ArrayList<Integer>();
		
		for (int i = 2; i <= x; i++) {
			// Add all numbers to the list.
			primes.add(i);
		} 
		
		// Discard composites
		
		// Inv: All numbers from 0 to i - 1 are prime
		for (int i = 0; i < primes.size(); i++) {
			// Check if the number can be dividing by any of the numbers in index before i
			int curr = primes.get(i);
			
			// Inv: curr is not divisible by all numbers from 0 to j - 1
			for (int j = 0; j < i; j++) {
				if (curr % primes.get(j) == 0) {
					primes.remove(i);
					i--;
					break;
				}
			}
		}
		
		int[] result = new int[primes.size()];
		for (int i = 0; i < primes.size(); i++) {
			result[i] = primes.get(i);
		}
		
		return result;
	}
}
