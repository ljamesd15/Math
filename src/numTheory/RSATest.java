package numTheory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class RSATest {

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
}
