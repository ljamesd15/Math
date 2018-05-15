package RSA;

import java.util.Random;

/**
 * As of right now only creates the same key pairs every time. DO NOT USE
 * @author LJamesD
 *
 */
public class PrivateKey {

	private final int p;
	private final int q;
	private final long n;
	private final int e;
	private final int d;
	
	// Instead of generating the same private key find a way to securely pick two primes
	PrivateKey() {
		//this.p = 104851;
		//this.q = 221807;

		this.p = 17;
		this.q = 23;
		this.n = p * q;
		
		long euler_phi = (p - 1) * (q - 1);
		Random r = new Random();
		int e = r.nextInt();
		while (e < 2 || e > euler_phi || RSA.gcd(euler_phi, e) != 1) {
			e = r.nextInt();
		}
		this.e = e;
		System.out.println(RSA.gcd(euler_phi, e));
		long[] arr = RSA.extEucAlgo(this.e, euler_phi);
		
		this.d = (int) RSA.extEucAlgo(this.e, euler_phi)[1];
		
		assert((e * d) % euler_phi == 1);
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
	public long decodeNum(long x) {
		return RSA.modPow(x, d, n);
	}
	
	/**
	 * Generates and returns a public key based off of this private key's information.
	 */
	public PublicKey genPubKey() {
		return new PublicKey(this.n, this.e);
	}
}
