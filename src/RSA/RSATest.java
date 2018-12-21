package RSA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class RSATest {

	@Test
	public void testGCD() {
		assertEquals(1, RSA.gcd(13, 24));
		assertEquals(4, RSA.gcd(4, 8));
		assertEquals(2, RSA.gcd(152, 6));
		assertEquals(13, RSA.gcd(13, 26));
		assertEquals(1, RSA.gcd(10, 21));
		assertEquals(1, RSA.gcd(71, 57));
	}
	
	@Test
	public void testBinaryOf() {		
		int[] tests = new int[] {8, 5, 379797810, 27039271};
		int[][] solutions = new int[4][];
		solutions[0] = new int[] {1, 0, 0, 0};
		solutions[1] = new int[] {1, 0, 1};
		solutions[2] = new int[] {1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 
				1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0};
		solutions[3] = new int[] {1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1,
				1, 0, 0, 0, 1, 0, 0, 1, 1, 1};
		
		
		for (int i = 0; i < tests.length; i++) {
			int[] binary = RSA.binaryOf(tests[i]);
			for (int j = 0; j < binary.length; j++) {
				assertEquals(solutions[i][j], binary[j]);
			}
		}
	}
	
	@Test
	public void testModPow() {
		// 6^1 mod 7
		assertEquals(6, RSA.modPow(6, 1, 7));
		
		// 4^2 mod 5
		assertEquals(1, RSA.modPow(4, 2, 5));
		
		// 9^5 mod 13
		assertEquals(3, RSA.modPow(9, 5, 13));
		
		// 19^{28} mod 32
		assertEquals(17, RSA.modPow(19, 28, 32));
		
		// 7^{37979797810} mod 17
		assertEquals(15, RSA.modPow(7, 379797810, 17));
	}
	
	@Test
	public void testExtEucAlgo() {
		// g = 27s + 12t
		int[] sol = new int[] {3, 1, -2};
		long[] returned = RSA.extEucAlgo(27, 12);
		assertEquals(sol.length, returned.length);
		for (int i = 0; i < sol.length; i++) {
			assertEquals(sol[i], returned[i]);
		}

		// g = 9s + 13t
		sol = new int[] {1, 3, -2};
		returned = RSA.extEucAlgo(9, 13);
		assertEquals(sol.length, returned.length);
		for (int i = 0; i < sol.length; i++) {
			assertEquals(sol[i], returned[i]);
		}

		// g = 314s + 71t
		sol = new int[] {1, -26, 115};
		returned = RSA.extEucAlgo(314, 71);
		assertEquals(sol.length, returned.length);
		for (int i = 0; i < sol.length; i++) {
			assertEquals(sol[i], returned[i]);
		}

		// g = 29s + 23t
		sol = new int[] {1, 4, -5};
		returned = RSA.extEucAlgo(29, 23);
		assertEquals(sol.length, returned.length);
		for (int i = 0; i < sol.length; i++) {
			assertEquals(sol[i], returned[i]);
		}
	}
	
	@Test
	public void encodeDecodeKeyPairInts() {
		KeyPair pair = new KeyPair(1024);
		BigInteger cipherText;
		int[] messages = new int[] {17, 0, 1, 27, 300, 4782172, 4782169, 560212};
		
		for (int i = 0; i < messages.length; i++) {
			cipherText = pair.encodeNum(messages[i]);
			assertEquals(messages[i], pair.decodeNum(cipherText));
		}
	}
	
	@Test
	public void encodeDecodeKeyPairStrings() {
		KeyPair pair = new KeyPair(2048);
		
		String[] messages = {"Hello World!", "This is a test.", "RSA is really cool.",
				"This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message.",
				""};
		
		for (int i = 0; i < messages.length - 1; i++) {
			String[] ints = pair.encode(messages[i]);
			assertEquals(messages[i], pair.decode(ints));
		}
		
		for (int i = 0; i < 410; i++) {
			messages[messages.length - 1] = messages[messages.length - 1] + (char) 255;
		}
		
		assertEquals(messages[messages.length - 1], 
				pair.decode(pair.encode(messages[messages.length - 1])));
	}
	
	@Test
	public void testStrToNumAndBack() {
		String[] messages = {"Hello World!", "This is a test.", "RSA is really cool.",
				"This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message."};

		for (int i = 0; i < messages.length; i++) {
			String[] ints = RSA.strToNum(messages[i]);
			assertEquals(messages[i], RSA.numToStr(ints));
		}
	}
	
	@Test
	public void testMaxEncodingLengths() {
		// Supported key sizes and previously known maximum encoding lengths for each size.
		int[] keySizes = new int[] {1024, 2048, 4096};
		int[] maxEncodeLengths = new int[] {250, 400, 400};
		
		// Create a message of the highest integer value characters. Make the message length 
		// just larger than the maximum possible encoding length so that it will spill to another
		// encoding block.
		String message = "";
		for (int j = 0; j < maxEncodeLengths[maxEncodeLengths.length - 1] + 10; j++) {
			message = message + (char) 255;
		}
		
		// Test each keySize
		for (int i = 0; i < keySizes.length; i++) {
			
			// Test multiple times for various key pairs of same size.
			for (int j = 0; j < 20; j++) {
				KeyPair pair = new KeyPair(keySizes[i], maxEncodeLengths[i]);
				
				assert(message.equals(pair.decode(pair.encode(message))));
			}
		}
	}
	
	@Test
	public void testTimeOfDecode() {
		KeyPair pair = new KeyPair(2048);
		long start, end, elapsedTimes100Norm;
		int timesToDecode = 20;
		
		// Create the messages
		String[] messages = {"Hello World!", "This is a test.", "RSA is really cool.",
				"This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message. "
				+ "This will be a super long message. This will be a super long message. This will be a super long message.",
				""};
		for (int i = 0; i < 410; i++) {
			messages[messages.length - 1] = messages[messages.length - 1] + (char) 255;
		}
		
		// Encode each of the messages.
		String[][] encodings = new String[messages.length][];
		for (int i = 0; i < messages.length - 1; i++) {
			encodings[i] = pair.encode(messages[i]);
		}
		
		// Decode the messages method
		start = System.nanoTime();
		for (int i = 0; i < timesToDecode; i++) {
			for (int j = 0; j < messages.length - 1; j++) {
				assertEquals(messages[j], pair.decode(encodings[j]));
			}
		}
		end = System.nanoTime();
		elapsedTimes100Norm = (end - start) / 10000000;
		
		// Output results
		System.out.println("It took " + elapsedTimes100Norm / 100.0 + " seconds with decode.");
	}
}
