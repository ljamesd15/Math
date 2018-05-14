package numTheory;

public class RSA {

	public static void main(String[] args) {
		int[] arr = binaryOf(8);
		for (int i = arr.length - 1; i >= 0; i--) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}
	
	/**
	 * Creates and returns a binary representation of the integer n.
	 * @returns An array of {0, 1} whose LSB is the zeroth index.
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
		
		// If n is bigger than our next biggest power of 2 then subtract that from n and 
		// set the bit in the bitset
		int[] result = new int[count];
		for (int i = powerOf2.length - 1; i > -1; i--) {
			if (n >= powerOf2[i]) {
				n = n - powerOf2[i];
				result[i] = 1;
			}
		}
		
		assert(n == 0);
		return result;
		
		
	}
}
