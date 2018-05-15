package RSA;

public class RSA {

	public static void main(String[] args) {
		int[] arr = binaryOf(8);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}
	
	/**
	 * Computes and returns the gcd of a and b
	 */
	public static long gcd(long a, long b) {
		if (b > a) {
			long tmp = b;
			b = a;
			a = tmp;
		}
		
		long r = a % b;
		
		while (r != 0) {
			a = b;
			b = r;
			r = a % b;
		}		
		return b;
	}
	
	/**
	 * Creates and returns a binary representation of the integer n.
	 * @returns An array of {0, 1} whose MSB is the zeroth index.
	 */
	public static int[] binaryOf(long n) {
		int currentPowOf2 = 1; // Start with 2^0
		int count  = 1; // Binary digits required to represent 1
		
		// While n is greater than or equal to the next power of 2.
		while(n >= currentPowOf2 * 2) {
			currentPowOf2 *= 2;
			count++;
		}
		
		// Make an array of the powers of 2 with length count
		int[] powerOf2 = new int[count];
		powerOf2[count - 1] = 1;
		for (int i = count - 2; i >= 0; i--) {
			powerOf2[i] = powerOf2[i + 1] * 2;
		}
		
		// Inv: n < powersOf2[i - 1] * 2, n + base10(result) = n
		int[] result = new int[count];
		for (int i = 0; i < powerOf2.length; i++) {
			if (n >= powerOf2[i]) {
				n = n - powerOf2[i];
				result[i] = 1;
			}
		}
		
		assert(n == 0);
		return result;	
	}
	
	/**
	 * Solves the equation of a^m (mod n).
	 * @param a The base which will be exponentiated (mod n).
	 * @param m The number which the base will be exponentiated to (mod n).
	 * @param n The modulus of the equation.
	 * @throws IllegalArgumentException if base, power or modulo is negative.
	 * @return Returns a number x such that 0 <= x < modulo and x = a^m (mod n).
	 */
	public static long modPow(long a, long m, long n) {
		if (a < 0 || m < 0 || n < 0) {
			throw new IllegalArgumentException("The base, power and modulo parameters must all be "
					+ "non-negative.");
		}
		// Faster to compute outright
		if (m <= 2) {
			return (int) (Math.pow(a, m) % n);
		}
		
		int[] binOfM = binaryOf(m);
		
		// Array which will hold all the residues of the base to powers of 2 mod modulo
		int[] residues = new int[binOfM.length];
		residues[residues.length - 1] = (int) (a % n);
		residues[residues.length - 2] = (int) (Math.pow(a, 2) % n);
		for (int i = residues.length - 3; i >= 0; i--) {
			// a^i = a^{i+1} * a^2 since (i+1)*2 = i
			residues[i] = (int) (Math.pow(residues[i + 1], 2) % n);
		}
		
		int result = 1;
		// If k + l = m then a^k * a^l = a^m thus using our binary representation of m and our 
		// pre-calculated residues lets calculate a^m mod n.
		for (int i = 0; i < binOfM.length; i++) {
			if (binOfM[i] == 1) {
				result = (int) ((result * residues[i]) % n);
			}
		}
		
		return result;
	}
	
	
	/**
	 * Finds the variables 's' and 't' so that the equation ax + by = gcd(a, b) is satisfied.
	 * @param a
	 * @param b
	 * @return The array containing arr[0] = gcd(a, b), arr[1] = x, and arr[2] = y
	 */
	public static long[] extEucAlgo(long a, long b) {
		boolean swappedVals = false;
		if (b > a) {
			long tmp = a;
			a = b;
			b = tmp;
			swappedVals = true;
		}
		
		long[] vars = new long[] {a, b, 0, 1, 1, 0}; // {a, b, r, s, x, y}
		long q;
		long c;
		long tmpR;
		long tmpS;
		
		while (vars[1] != 0) {			
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
			return new long[] {vars[0], vars[5], vars[4]}; // a, y, x
		} else {
			return new long[] {vars[0], vars[4], vars[5]}; // a, x, y
		}
	}
}
