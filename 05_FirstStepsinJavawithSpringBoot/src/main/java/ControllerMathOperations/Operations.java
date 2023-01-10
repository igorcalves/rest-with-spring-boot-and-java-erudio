package ControllerMathOperations;

public class Operations {

	private static CheckString ck = new CheckString();

	public Double sum(String numberOne, String numberTwo) {
		return ck.convertAndCheck(numberOne) + ck.convertAndCheck(numberTwo);
	}

	public Double subtraction(String numberOne, String numberTwo) {
		return ck.convertAndCheck(numberOne) * ck.convertAndCheck(numberTwo);
	}

	public Double multiplication(String numberOne, String numberTwo) {
		return ck.convertAndCheck(numberOne) * ck.convertAndCheck(numberTwo);
	}

	public Double division(String numberOne, String numberTwo) {
		return ck.convertAndCheck(numberOne) / ck.convertAndCheck(numberTwo);
	}
	
	public Double mean(String numberOne, String numberTwo) {
		return (ck.convertAndCheck(numberOne) + ck.convertAndCheck(numberTwo))/2;
	}
	
	public Double squareRoot(String numberOne) {
		return Math.sqrt(ck.convertAndCheck(numberOne));
	}
}
