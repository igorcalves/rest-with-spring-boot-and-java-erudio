package ControllerMathOperations;

import br.com.igor.exceptions.UnsupportedMathOperationException;

public class CheckString {
	
	public Double convertAndCheck(String numberOne) {
		
		if(!isNumeric(numberOne)) {
			throw new UnsupportedMathOperationException("Please set  numeric value");
		}
		
		return convertToDouble(numberOne);
		
	}
	
	
	public static Double convertToDouble(String strNumber) {
		if (strNumber == null)
			return 0d;
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number))
			return Double.parseDouble(number);
		return 1.0d;
	}

	public static boolean isNumeric(String strNumber) {
		if (strNumber == null)
			return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");

	}

}
