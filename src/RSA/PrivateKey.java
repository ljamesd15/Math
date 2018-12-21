package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * A private key object for RSA encryption. Used to decrypt messages from the paired public key.
 * @author LJamesD
 *
 */
public class PrivateKey {

	private final BigInteger p;
	private final BigInteger q;
	private final BigInteger n;
	private final BigInteger encodeNum;
	private final BigInteger decodeNum;
	private int maxEncodeLen;
	
	/**
	 * Creates a secret key which can be used to decrypt messages encrypted with the corresponding
	 * public key.
	 * @param keySize The bit-size of this key.
	 * @param maxEncodeLen The maximum number of characters which can be encrypted and decrypted at
	 * one time.
	 */
	protected PrivateKey(int keySize, int maxEncodeLen) {
		Random secRan = new SecureRandom();
		this.maxEncodeLen = maxEncodeLen;
		this.p = new BigInteger(keySize + 1, 4, secRan);
		this.q = new BigInteger(keySize - 1, 4, secRan);
		// We want to have different bit sized primes so we can assure that they are far enough
		// away from one another so that factoring stays difficult.
		this.n = this.p.multiply(this.q);
		
		BigInteger euler_phi = (this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE)));
		BigInteger possibleE = new BigInteger(1024, secRan);
		BigInteger possibleD;
		
		do {
			possibleE = new BigInteger(1024, secRan);
			// Need to be invertible so if the gcd is not 1 then try again.
		} while (possibleE.gcd(euler_phi).compareTo(BigInteger.ONE) != 0);
		
		possibleD = extEucAlgo(possibleE, euler_phi)[1];
		// Need it to be invertible so if possibleD is less than 1 add 
		// euler_phi to it until it is positive
		while(possibleD.compareTo(BigInteger.ONE) < 0) {
			possibleD = possibleD.add(euler_phi);
		}

		this.encodeNum = possibleE; 			
		this.decodeNum = possibleD;
	}
	
	/**
	 * Sets the new encoding length to 'length'. Only to be used for testing purposes. There are
	 * no guarantees about the functionality of this PrivateKey after this method is called.
	 * @param length The new encoding length for this PrivateKey.
	 * @throws IllegalArgumentException if length is less than 1.
	 */
	protected void setEncodeLength(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("length must be greater than 0.");
		}
		this.maxEncodeLen = length;
	}
	
	/**
	 * Decrypts the provided encrypted strings and concatenates the decrypted versions into one
	 * string. Must be encrypted by the paired public key for decryption to work.
	 * @param cipherText The strings which will be decrypted.
	 * @return The decrypted message concatenated into one string.
	 */
	public String decode(String[] cipherTexts) {
		String result = "";
		
		// Decrypt each cipher text then convert the number into a string.
		for (int i = 0; i < cipherTexts.length; i++) {
			BigInteger encryptedNum = new BigInteger(cipherTexts[i]);
			BigInteger decryptedNum = this.decodeNum(encryptedNum);
			String currStr = "";
			
			// Get the next character until the decrypted number is zero.
			while(decryptedNum.compareTo(BigInteger.ZERO) != 0) {
				// Pre-pend the character
				currStr = (char)(decryptedNum.mod(KeyPair.CHAR_BASE).intValue()) + currStr;
				decryptedNum = decryptedNum.divide(KeyPair.CHAR_BASE);
			}
			result = currStr + result;
		}
		return result;
	}
	
	/**
	 * Decrypts a number which was encrypted using the paired public key.
	 * @param x The number which will be decrypted.
	 * @return The decrypted version of the parameter.
	 */
	public BigInteger decodeNum(BigInteger x) {
		return x.modPow(this.decodeNum, this.n);
	}
	
	/**
	 * Generates and returns a public key based off of this private key's information.
	 */
	protected PublicKey genPubKey() {
		return new PublicKey(this.n, this.encodeNum, this.maxEncodeLen);
	}
	
	/**
	 * Finds the variables 's' and 't' so that the equation ax + by = gcd(a, b) is satisfied.
	 * @param a
	 * @param b
	 * @return The array containing arr[0] = gcd(a, b), arr[1] = x, and arr[2] = y
	 */
	private static BigInteger[] extEucAlgo(BigInteger a, BigInteger b) {
		boolean swappedVals = false;
		if (b.compareTo(a) == 1) {
			BigInteger tmp = a;
			a = b;
			b = tmp;
			swappedVals = true;
		}
		
		BigInteger[] vars = new BigInteger[] {a, b, BigInteger.ZERO, BigInteger.ONE, 
				BigInteger.ONE, BigInteger.ZERO}; // {a, b, r, s, x, y}
		BigInteger q;
		BigInteger c;
		BigInteger tmpR;
		BigInteger tmpS;
		
		while (vars[1] != BigInteger.ZERO) {			
			q = vars[0].divide(vars[1]);
			c = vars[0].mod(vars[1]);
			tmpR = vars[2];
			tmpS = vars[3];
			
			// {a, b, r, s, x, y} = {b, c, x - qr, y - qs, r, s}
			vars[0] = vars[1];
			vars[1] = c;
			vars[2] = vars[4].subtract(q.multiply(tmpR));
			vars[3] = vars[5].subtract(q.multiply(tmpS));
			vars[4] = tmpR;
			vars[5] = tmpS;
		}		
		
		if (swappedVals) {
			return new BigInteger[] {vars[0], vars[5], vars[4]}; // a, y, x
		} else {
			return new BigInteger[] {vars[0], vars[4], vars[5]}; // a, x, y
		}
	}
}
