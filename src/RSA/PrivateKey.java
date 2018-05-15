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
	private final long e;
	private final long d;
	
	// Instead of generating the same private key find a way to securely pick two primes
	PrivateKey() {
		this.p = 945709903;
		this.q = 223683227;
		this.n = p * q;
		
		long euler_phi = (p - 1) * (q - 1);
		Random r = new Random();
		long e = r.nextLong();
		while (e < euler_phi && RSA.gcd(euler_phi, e) != 1) {
			e = r.nextLong();
		}
		this.e = e;
		
		this.d = RSA.extEucAlgo(this.e, euler_phi)[1];
		
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
	 * Generates and returns a public key based off of this private key's information.
	 */
	public PublicKey genPubKey() {
		return new PublicKey(this.n, this.e);
	}
}
