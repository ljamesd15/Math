package RSA;

import java.math.BigInteger;

/**
 * A KeyPair object which contains a private and public key for RSA encryption.
 * Can be used to encrypt and decrypt messages.
 * @author LJamesD
 *
 */
public class KeyPair {

	private final PublicKey pk;
	private final PrivateKey sk;
	/** At around 380-390 we lose precision in BigIntegers. If edited make sure it still works. **/
	protected static final int[] MAX_ENCODE_LENS = {250, 400}; 
	protected static final BigInteger CHAR_BASE = BigInteger.valueOf(256);
	
	/**
	 * Creates a public-private key pair which can be used to encrypt and decrypt messages and/or
	 * numbers. Supported key size values are 1024, 2048, 4096.
	 * @param keySize The bit-size of the key pair.
	 * @throws IllegalArgumentException if key size is not a supported value.
	 */
	public KeyPair(int keySize) {
		switch(keySize) {
			case 1024:
				this.sk = new PrivateKey(keySize, MAX_ENCODE_LENS[0]);
				break;
			case 2048:
				// Fall through, use same encoding length as 4096
			case 4096:
				this.sk = new PrivateKey(keySize, MAX_ENCODE_LENS[1]);
				break;
			default:
				throw new IllegalArgumentException("Supported key sizes are: 1024, 2048, 4096.");
		}
		this.pk = this.sk.genPubKey();
	}
	
	/**
	 * Creates a key pair of size keySize with an encoding length of encodeLen. It is YOUR job to
	 * make sure an encoding length is small enough so that once encrypted strings/numbers can be
	 * decrypted properly. There are no guarantees whether a KeyPair returned from this constructor
	 * will function properly.
	 * @param keySize The number of bits which will be used for the primes to generate the keys.
	 * @param encodeLen The number of characters which can be encrypted and decrypted at one time.
	 */
	protected KeyPair(int keySize, int encodeLen) {
		this.sk = new PrivateKey(keySize, encodeLen);
		this.pk = this.sk.genPubKey();
	}
	
	/**
	 * Sets the new encoding length to 'length'. Only to be used for testing purposes. There are
	 * no guarantees about the functionality of this KeyPair after this method is called.
	 * @param length The new encoding length for this KeyPair.
	 * @throws IllegalArgumentException if length is less than 1.
	 */
	protected void setEncodeLength(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("length must be greater than 0.");
		}
		this.sk.setEncodeLength(length);
		this.pk.setEncodeLength(length);
	}
	
	/**
	 * @return The public key for this key pair.
	 */
	public PublicKey getPublicKey() {
		return this.sk.genPubKey();
	}
	
	/**
	 * Encodes a message using the public key of this key pair.
	 * @param message
	 * @return The cipher text of the message.
	 */
	public String[] encode(String message) {
		return this.pk.encode(message);
	}
	
	/**
	 * Decodes a message using the private key of this pair.
	 * @param cipherText
	 * @return The decrypted message.
	 */
	public String decode(String[] cipherTexts) {
		return this.sk.decode(cipherTexts);
	}
	
	/**
	 * Encrypts a number using this pair's public key.
	 * @param x The number which will be encrypted.
	 * @return The encrypted version of this number.
	 */
	public BigInteger encodeNum(long x) {
		return this.pk.encodeNum(BigInteger.valueOf(x));
	}
	
	/**
	 * Decodes a number which was encrypted using this' public key.
	 * @param x The encrypted number.
	 * @return The decrypted number.
	 */
	public long decodeNum(BigInteger x) {
		return this.sk.decodeNum(x).longValue();
	}
}
