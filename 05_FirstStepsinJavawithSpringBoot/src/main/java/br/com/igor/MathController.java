package br.com.igor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ControllerMathOperations.Operations;

@RestController
public class MathController {
	
	private static Operations operations = new Operations();

	@GetMapping(value = "/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
			
		return operations.sum(numberOne, numberTwo);
	}

	@GetMapping(value = "/sub/{numberOne}/{numberTwo}")
	public Double sub(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		return operations.subtraction(numberOne, numberTwo);
		
	}

	@GetMapping(value = "/mult/{numberOne}/{numberTwo}")
	public Double mult(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		
		return operations.multiplication(numberOne, numberTwo);
	}

	@GetMapping(value = "/div/{numberOne}/{numberTwo}")
	public Double div(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
			return operations.division(numberOne, numberTwo);
	}

	@GetMapping(value = "/mean/{numberOne}/{numberTwo}")
	public Double mean(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		return operations.mean(numberOne, numberTwo);
	}

	@GetMapping(value = "/squareRoot/{numberOne}")
	public Double squareRoot(@PathVariable("numberOne") String numberOne) {

		return operations.squareRoot(numberOne);
	}

	
}
