package RSA;

import java.math.BigInteger;

/**
 * As of right now only creates the same key pairs every time. DO NOT USE
 * @author LJamesD
 *
 */
public class PublicKey {

	private final BigInteger n;
	private final BigInteger encodeNum;
	
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
	public String encode(String message) {
		return null;
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public BigInteger encodeNum(long x) {
		BigInteger message = BigInteger.valueOf(x);
		return message.modPow(this.encodeNum, this.n);
	}
}
