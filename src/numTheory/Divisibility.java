package numTheory;

public class Divisibility {

	public static void main(String[] args) {
		int[] nums = new int[] {3, 5, 7};
		
		System.out.println(lowestCommonMultiple(nums));
		System.out.println(gcd(5646546, 364849464));
	}
	
	/**
	 * Determines if a number is divisible by 3
	 * @param x
	 * @return True if divisible by 3
	 */
	public static boolean divisibleBy3(int x) {
		int nextDigit;
		int digitSum = 0;
		
		do {
			nextDigit = x % 10;
			digitSum += nextDigit;
			
			x = x / 10;
			
		} while (x != 0);
		
		return digitSum % 3 == 0;
	}
	
	/**
	 * Determines if the number is divisible by 9
	 * @param x
	 * @return True if the number is divisible by 9
	 */
	public static boolean divisibleBy9(int x) {
		int nextDigit;
		int digitSum = 0;
		
		do {
			nextDigit = x % 10;
			digitSum += nextDigit;
			
			x = x / 10;
			
		} while (x != 0);
		
		return digitSum % 9 == 0; 
	}
	
	/**
	 * Determines if the number is divisible by 11
	 * @param x
	 * @return True if the number is divisible by 11;
	 */
	public static boolean divisibleBy11(int x) {
		int oddPowerDigit;
		int evenPowerDigit;
		int oddPowerDigitSum = 0;
		int evenPowerDigitSum = 0;
		
		do {
			oddPowerDigit = x % 10;
			oddPowerDigitSum += oddPowerDigit;
			
			evenPowerDigit = (x /10) % 10;
			evenPowerDigitSum += evenPowerDigit;
			
			x = x / 100;
		} while (x != 0);
		
		return (evenPowerDigitSum % 11) == (oddPowerDigitSum % 11);
	}
	
	/**
	 * Finds the lowest common multiple of all the numbers in the array.
	 * @param numbers must be all positive numbers.
	 * @return If there is no number less than the max positive java long then -1 is returned.
	 */
	public static long lowestCommonMultiple(int[] numbers) {
		// Find the largest number
		int largestNum = 0;
		for (int i = 0; i < numbers.length; i++) {
			if (largestNum < numbers[i]) {
				largestNum = numbers[i];
			}
		}
		
		// Go by multiples of largestNum
		long current = largestNum;
		// While there is no overflow
		while (current > 0) {
			
			boolean dividedByAll = true;
			// Check if its divisible by all of the parameter numbers
			for (int i = 0; i < numbers.length; i++) {
				if (current % numbers[i] != 0) {
					dividedByAll = false;
					break;
				}
			}
			
			// If it was divided by all then we have found it!
			if (dividedByAll) {
				return current;
			} else {
				current += largestNum;
			}
		}
		
		return -1;
	}
	
	/**
	 * Computes and returns the gcd of a and b
	 */
	public static int gcd(int a, int b) {
		if (b > a) {
			int tmp = b;
			b = a;
			a = tmp;
		}
		
		int r = a % b;
		
		while (r != 0) {
			a = b;
			b = r;
			r = a % b;
		}		
		return b;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] extEucAlgo(int a, int b) {
		if (gcd(a, b) != 1) {
			throw new IllegalArgumentException("'a' and 'b' must be relatively prime.");
		}
		
		// private method int[] a, b, x, y, s, t, r
		return new int[2];
	}
}
