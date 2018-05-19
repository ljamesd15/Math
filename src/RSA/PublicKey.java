package RSA;

import java.math.BigInteger;

/**
 * A public key object for RSA encryption. Used to encrypt messages which can be decrypted by using
 * the paired private key.
 * @author LJamesD
 *
 */
public class PublicKey {

	private final BigInteger n;
	private final BigInteger encodeNum;
	private int maxEncodeLen;
	
	/**
	 * Creates a public key object from two large primes.
	 * @param p A large prime which is vastly different from q.
	 * @param q A large prime which is vastly different from p.
	 * @param maxEncodeLen is the maximum number of characters this key can encrypt at one time.
	 */
	protected PublicKey(BigInteger n, BigInteger e, int maxEncodeLen) {
		this.n = n;
		this.encodeNum = e;
		this.maxEncodeLen = maxEncodeLen;
	}
	
	/**
	 * Encodes a message using RSA protocols. If the parameter string is longer than
	 * {@value #maxEncodeLen} then the returned strings will be split into chunks of up to 
	 * {@value #maxEncodeLen} characters.
	 * @param message String which you want to encode using this.
	 * @return The cipher text of message after encoding by this.
	 */
	public String[] encode(String message) {
		char[] arr = message.toCharArray();

		String[] result = new String[(arr.length / maxEncodeLen) + 1];
		int startIndex = 0;
		
		// Separate the string in sections of MAX_ENCODE_LEN characters.
		for (int i = 0; i < result.length; i++) {
			int endIndex = Math.min(startIndex + maxEncodeLen, arr.length);
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
			startIndex += maxEncodeLen;
		}
		
		return result;
	}
	
	/**
	 * Sets the new encoding length to 'length'. Only to be used for testing purposes. There are
	 * no guarantees about the functionality of this PublicKey after this method is called.
	 * @param length The new encoding length for this PublicKey.
	 * @throws IllegalArgumentException if length is less than 1.
	 */
	protected void setEncodeLength(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("length must be greater than 0.");
		}
		this.maxEncodeLen = length;
	}
	
	/**
	 * Encodes a number using RSA protocols.
	 * @param x The number to encode.
	 * @return The encrypted version of the parameter.
	 */
	public BigInteger encodeNum(BigInteger message) {
		return message.modPow(this.encodeNum, this.n);
	}
}
