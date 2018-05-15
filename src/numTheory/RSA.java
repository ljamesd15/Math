package numTheory;

public class RSA {

	public static void main(String[] args) {
		int[] arr = binaryOf(8);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}
	
	/**
	 * Creates and returns a binary representation of the integer n.
	 * @returns An array of {0, 1} whose MSB is the zeroth index.
	 */
	public static int[] binaryOf(int n) {
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
	public static int modPow(int a, int m, int n) {
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
		residues[residues.length - 1] = a % n;
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
				result = (result * residues[i]) % n;
			}
		}
		
		return result;
	}
}
