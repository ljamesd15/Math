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
		
		// Make a powers of 2 array of length count and fill in values
		int[] powerOf2 = new int[count];
		powerOf2[0] = 1;
		for (int i = 1; i < count; i++) {
			powerOf2[i] = powerOf2[i - 1] * 2;
		}
		
		// Inv: n < powersOf2[i] * 2, n + base10(result) = n
		int[] result = new int[count];
		for (int i = 0; i < result.length; i++) {
			int powOf2Index = count - 1 - i;
			if (n >= powerOf2[powOf2Index]) {
				n = n - powerOf2[powOf2Index];
				result[i] = 1;
			}
		}
		
		assert(n == 0);
		return result;
		
		
	}
}
