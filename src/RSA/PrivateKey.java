package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * As of right now only creates the same key pairs every time. DO NOT USE
 * @author LJamesD
 *
 */
public class PrivateKey {

	public final BigInteger p;
	public final BigInteger q;
	public final BigInteger n;
	public final BigInteger encodeNum;
	public final BigInteger decodeNum;
	
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
	public String decode(String cipherText) {
		return null;
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public long decodeNum(BigInteger x) {
		return x.modPow(this.decodeNum, this.n).longValue();
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
