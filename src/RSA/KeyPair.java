package RSA;

import java.math.BigInteger;

/**
 * A KeyPair object which contains a private and public key for RSA encryption.
 * @author LJamesD
 *
 */
public class KeyPair {

	private final PublicKey pubK;
	private final PrivateKey privK;
	/** At around 380-390 we lose precision in BigIntegers. If edited make sure it still works. **/
	protected static final int MAX_ENCODE_LEN = 350; 
	protected static final BigInteger CHAR_BASE = BigInteger.valueOf(256);
	
	KeyPair() {
		this.privK = new PrivateKey();
		this.pubK = this.privK.genPubKey();
	}
	
	public PublicKey getPublicKey() {
		return this.privK.genPubKey();
	}
	
	/**
	 * Encodes a message using the public key of this key pair.
	 * @param message
	 * @return The cipher text of the message.
	 */
	public String[] encode(String message) {
		return this.pubK.encode(message);
	}
	
	/**
	 * Decodes a message using the private key of this pair.
	 * @param cipherText
	 * @return The decrypted message.
	 */
	public String decode(String[] cipherTexts) {
		return this.privK.decode(cipherTexts);
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public BigInteger encodeNum(long x) {
		return this.pubK.encodeNum(BigInteger.valueOf(x));
	}
	
	/**
	 * Decodes a number which was encrypted using this' public key.
	 * @param x
	 * @return The number which 
	 */
	public long decodeNum(BigInteger x) {
		return this.privK.decodeNum(x).longValue();
	}
}
