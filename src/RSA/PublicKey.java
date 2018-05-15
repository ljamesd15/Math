package RSA;

/**
 * As of right now only creates the same key pairs every time. DO NOT USE
 * @author LJamesD
 *
 */
public class PublicKey {

	private final long n;
	private final long e;
	
	/**
	 * Creates a public key object from two large primes.
	 * @param p A large prime which is vastly different from q.
	 * @param q A large prime which is vastly different from p
	 */
	PublicKey(long n, long e) {
		this.n = n;
		this.e = e;
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
	public long encodeNum(long x) {
		return RSA.modPow(x, e, n);
	}
}
