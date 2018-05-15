package RSA;

/**
 * As of right now only creates the same key pairs every time. DO NOT USE
 * @author LJamesD
 *
 */
public class KeyPair {

	private final PublicKey pubK;
	private final PrivateKey privK;
	
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
	 * @return THe cipher text of the message.
	 */
	public String encode(String message) {
		return this.pubK.encode(message);
	}
	
	/**
	 * Decodes a message using the private key of this pair.
	 * @param cipherText
	 * @return The decrypted message.
	 */
	public String decode(String cipherText) {
		return this.privK.decode(cipherText);
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public long encodeNum(long x) {
		return this.pubK.encodeNum(x);
	}
	
	/**
	 * Dummy for testing
	 * @param x
	 * @return
	 */
	public long decodeNum(long x) {
		return this.privK.decodeNum(x);
	}
}
