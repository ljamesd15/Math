package numTheory;

public class ModularArithmetic {

	public static void main(String[] args) {
		//int[] nums = new int[] {3, 5, 7};
		
		//System.out.println(lowestCommonMultiple(nums));
		System.out.println(gcd(51, 141));
		
		System.out.println(Math.pow(3, 70));
		
		/*
		int a = 101;
		int b = 37;
		int[] answer = extEucAlgo(a, b);
		
		System.out.println("ax + by = gcd(a, b)");
		System.out.println(a + "*(" + answer[1] + ") + " + b + "*(" + answer[2] + ") = " + answer[0]);
		System.out.println();
		*/
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
	 * Finds the variables 's' and 't' so that the equation ax + by = gcd(a, b) is satisfied.
	 * @param a
	 * @param b
	 * @return The array containing arr[0] = gcd(a, b), arr[1] = x, and arr[2] = y
	 */
	public static int[] extEucAlgo(int a, int b) {
		boolean swappedVals = false;
		if (b > a) {
			int tmp = a;
			a = b;
			b = tmp;
			swappedVals = true;
		}
		
		int[] vars = new int[] {a, b, 0, 1, 1, 0}; // {a, b, r, s, x, y}
		int q;
		int c;
		int tmpR;
		int tmpS;
		
		while (vars[1] != 0) {
			for (int i = 0; i < 6; i++) {
				System.out.print(vars[i] + ", ");
			}
			System.out.println();
			
			q = vars[0] / vars[1];
			c = vars[0] % vars[1];
			tmpR = vars[2];
			tmpS = vars[3];
			
			// {a, b, r, s, x, y} = {b, c, x - qr, y - qs, r, s}
			vars[0] = vars[1];
			vars[1] = c;
			vars[2] = vars[4] - q * tmpR;
			vars[3] = vars[5] - q * tmpS;
			vars[4] = tmpR;
			vars[5] = tmpS;
			

		}		
		
		if (swappedVals) {
			return new int[] {vars[0], vars[5], vars[4]}; // a, y, x
		} else {
			return new int[] {vars[0], vars[4], vars[5]}; // a, x, y
		}
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param m
	 * @return
	 */
	public static int solveModLinearEq(int a, int b, int m) {
		return -1;
	}
}
