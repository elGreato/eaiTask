package eaitask;

public class Utils {
	public static String trim(String input) {
		input = input.trim();
		input = input.replaceAll("[,]", "");
		return input;
	}
	public static String completeAccountNumber(String accountNumber)
	{
		return new String("0")+accountNumber;
		
	}
}
