package RSA;

import java.math.BigInteger;

/**
 * A public key object for RSA encryption.
 * @author LJamesD
 *
 */
public class PublicKey {

	private final BigInteger n;
	private final BigInteger encodeNum;
	private static final int MAX_ENCODE_LEN = KeyPair.MAX_ENCODE_LEN;
	
	/**
	 * Creates a public key object from two large primes.
	 * @param p A large prime which is vastly different from q.
	 * @param q A large prime which is vastly different from p
	 */
	PublicKey(BigInteger n, BigInteger e) {
		this.n = n;
		this.encodeNum = e;
	}
	
	/**
	 * Encodes a message using a publicly known n and e
	 * @param message String which you want to encode using this.
	 * @return The cipher text of message after encoding by this.
	 */
	public String[] encode(String message) {
		char[] arr = message.toCharArray();

		String[] result = new String[(arr.length / MAX_ENCODE_LEN) + 1];
		int startIndex = 0;
		
		// Separate the string in sections of MAX_ENCODE_LEN characters.
		for (int i = 0; i < result.length; i++) {
			int endIndex = Math.min(startIndex + MAX_ENCODE_LEN, arr.length);
			BigInteger numRep = BigInteger.ZERO;
			
			for (int j = startIndex; j < endIndex; j++) {
				// Get the index within this loop
				int index = j - startIndex;
				
				// Convert character to base 256 and add to previous result
				numRep = numRep.add(KeyPair.CHAR_BASE.pow(index).multiply
						(BigInteger.valueOf((int) arr[j])));
			}

			// Encrypt the integer representation
			BigInteger encryptedVal = this.encodeNum(numRep);
			result[i] = encryptedVal.toString();

			// Move the starting index MAX_ENCODE_LEN spots forward in the char array.
			startIndex += MAX_ENCODE_LEN;
		}
		
		return result;
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public BigInteger encodeNum(BigInteger message) {
		return message.modPow(this.encodeNum, this.n);
	}
}
