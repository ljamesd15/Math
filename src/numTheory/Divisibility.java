package numTheory;

public class Divisibility {

	public static void main(String[] args) {
		
	}
	
	/**
	 * Determines if a number is divisible by 3
	 * @param x
	 * @return True if divisible by 3
	 */
	public static boolean divisibleBy3(int x) {
		int nextDigit;
		int digitSum = 0;
		
		do {
			nextDigit = x % 10;
			digitSum += nextDigit;
			
			x = x / 10;
			
		} while (x != 0);
		
		return digitSum % 3 == 0;
	}
	
	/**
	 * Determines if the number is divisible by 9
	 * @param x
	 * @return True if the number is divisible by 9
	 */
	public static boolean divisibleBy9(int x) {
		int nextDigit;
		int digitSum = 0;
		
		do {
			nextDigit = x % 10;
			digitSum += nextDigit;
			
			x = x / 10;
			
		} while (x != 0);
		
		return digitSum % 9 == 0; 
	}
	
	/**
	 * Determines if the number is divisible by 11
	 * @param x
	 * @return True if the number is divisible by 11;
	 */
	public static boolean divisibleBy11(int x) {
		int oddPowerDigit;
		int evenPowerDigit;
		int oddPowerDigitSum = 0;
		int evenPowerDigitSum = 0;
		
		do {
			oddPowerDigit = x % 10;
			oddPowerDigitSum += oddPowerDigit;
			
			evenPowerDigit = (x /10) % 10;
			evenPowerDigitSum += evenPowerDigit;
			
			x = x / 100;
		} while (x != 0);
		
		return (evenPowerDigitSum % 11) == (oddPowerDigitSum % 11);
	}
}
