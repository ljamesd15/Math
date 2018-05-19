package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * A private key object for RSA encryption.
 * @author LJamesD
 *
 */
public class PrivateKey {

	private final BigInteger p;
	private final BigInteger q;
	private final BigInteger n;
	private final BigInteger encodeNum;
	private final BigInteger decodeNum;
	private static final int MAX_ENCODE_LEN = KeyPair.MAX_ENCODE_LEN;
	
	PrivateKey() {
		Random secRan = new SecureRandom();
		this.p = new BigInteger(2048, 4, secRan);
		this.q = new BigInteger(1024, 4, secRan);
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
	 * Decodes cipherText which was encoded with the corresponding public key.
	 * @param cipherText
	 * @return The decrypted message.
	 */
	public String decode(String[] cipherTexts) {
		String result = "";
		
		for (int i = 0; i < cipherTexts.length; i++) {
			BigInteger encryptedNum = new BigInteger(cipherTexts[i]);
			BigInteger decryptedNum = this.decodeNum(encryptedNum);
			String currStr = "";
			
			// Now that its decrypted return from base 256 to characters.
			for (int j = MAX_ENCODE_LEN - 1; j >= 0; j--) {
				// Find the digit and coefficient for the character.
				BigInteger coeff = KeyPair.CHAR_BASE.pow(j);
				long digit = decryptedNum.divide(coeff).longValue();
				
				if (digit != 0) {
					// Mod decryptedNum by digit*coeff so we can access the next character
					decryptedNum = decryptedNum.mod(BigInteger.valueOf(digit).multiply(coeff));
					
					// Prepend the character as we are decreasing in indices.
					currStr = ((char) digit) + currStr;
				}
			}
			result += currStr;
		}
		return result;
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public BigInteger decodeNum(BigInteger x) {
		return x.modPow(this.decodeNum, this.n);
	}
	
	/**
	 * Generates and returns a public key based off of this private key's information.
	 */
	public PublicKey genPubKey() {
		return new PublicKey(this.n, this.encodeNum);
	}
	
	/**
	 * Finds the variables 's' and 't' so that the equation ax + by = gcd(a, b) is satisfied.
	 * @param a
	 * @param b
	 * @return The array containing arr[0] = gcd(a, b), arr[1] = x, and arr[2] = y
	 */
	public static BigInteger[] extEucAlgo(BigInteger a, BigInteger b) {
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
