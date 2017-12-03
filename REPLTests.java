import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.*;

public class REPLTests {

	REPL repl;

	@Before
	public void setup(){
		repl = new REPL();
	}

	/*
	*	Testing that evaluateRPM returns NULL when passed in C
	*/
	@Test
	public void testREPLNULL(){
		String testString = "C";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		assertNull(repl.evaluateRPN(splitTestString));
	}

	/*
	* Test division by zero
	*/
	@Test
	public void divisionByZero(){
		String testString = "3 0 /";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		assertNull(repl.evaluateRPN(splitTestString));
	}

	/*
	* Test if variable is properly set
	*/
	@Test
	public void declareVariable(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("10",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if variable holds value
	*/
	@Test
	public void checkVariable(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);
		testString = "C";
		splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("10",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if variable holds value and we can do addition with it
	*/
	@Test
	public void checkVariableAddition(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);
		testString = "C 5 +";
		splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("15",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if variable holds value and we can do subtraction with it
	*/
	@Test
	public void checkVariableSubtraction(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);
		testString = "C 5 -";
		splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("5",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if variable holds value and we can do multiplication with it
	*/
	@Test
	public void checkVariableMultiplication(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);
		testString = "C 5 *";
		splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("50",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if variable holds value and we can do division with it
	*/
	@Test
	public void checkVariableDivision(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);
		testString = "C 5 /";
		splitTestString = testString.toUpperCase().split("\\s+");
		assertEquals("2",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test if we can set a variable to another variable
	*/
	@Test
	public void checkVariableSetting(){
		String testString = "LET C 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);

		testString = "lEt D C C +";
		splitTestString = testString.toUpperCase().split("\\s+");
		repl.evaluateRPN(splitTestString);

		testString = "D";
		splitTestString = testString.toUpperCase().split("\\s+");

		assertEquals("20",repl.evaluateRPN(splitTestString).toString());
	}

	/*
	* Test to see if improper variable will return null
	*/

	@Test
	public void checkImproperVariableName(){
		String testString = "LET CATS 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		assertNull(repl.evaluateRPN(splitTestString));
	}

	/*
	* Test to see if improper variable will return null
	*/
	@Test
	public void checkImproperVariableName2(){
		String testString = "LET 5 5 5 +";
		String[] splitTestString = testString.toUpperCase().split("\\s+");
		assertNull(repl.evaluateRPN(splitTestString));
	}


	/*
	*	Testing that checkKeywordsArray() returns the correct int for PRINT keyword
	*/
	@Test
	public void PRINTKeyword(){
		String testString = "PRINT";

		int result = repl.checkKeyWordsArray(testString);

		assertEquals(3, result);
	}

	/*
	*	Testing that checkKeywordsArray() returns the correct int for lowercase print keyword
	*/
	@Test
	public void printKeyword(){
		String testString = "print";

		int result = repl.checkKeyWordsArray(testString);

		assertEquals(3, result);
	}

	/*
	*	Testing that checkKeywordsArray() returns the correct int for LET keyword
	*/
	@Test
	public void LETKeyword(){
		String testString = "LET";

		int result = repl.checkKeyWordsArray(testString);

		assertEquals(2, result);
	}

	/*
	*	Testing that checkKeywordsArray() returns the correct int for QUIT keyword
	*/
	@Test
	public void QUITKeyword(){
		String testString = "QUIT";

		int result = repl.checkKeyWordsArray(testString);

		assertEquals(1, result);
	}

	/*
	*	Testing that correct return value for keyword in right position
	*/
	@Test
	public void keywordRightPosition(){
		String [] line = {"print", "1", "2", "+"};

		int result = repl.checkValidKeyword(line);

		assertEquals(3, result);
	}

	/*
	*	Testing that correct return value for keyword in wrong position
	*/
	@Test
	public void keywordWrongPosition(){
		String [] line = {"1", "2", "+", "print"};

		int result = repl.checkValidKeyword(line);

		assertEquals(-1, result);
	}

	/*
	*	Testing that correct return value for no keyword
	*/
	@Test
	public void noKeyword(){
		String [] line = {"1", "2", "+"};

		int result = repl.checkValidKeyword(line);

		assertEquals(0, result);
	}

	/*
	*	Test that + is returned true
	*/
	@Test
	public void checkOperatorTest1(){
		assertTrue(repl.checkOperator("+"));
	}

	/*
	*	Test that - is returned true
	*/
	@Test
	public void checkOperatorTest2(){
		assertTrue(repl.checkOperator("-"));
	}

	/*
	*	Test that * is returned true
	*/
	@Test
	public void checkOperatorTest3(){
		assertTrue(repl.checkOperator("*"));
	}

	/*
	*	Test that / is returned true
	*/
	@Test
	public void checkOperatorTest4(){
		assertTrue(repl.checkOperator("/"));
	}

	/*
	*	Test that a long passed through is returned false
	*/
	@Test
	public void checkOperatorTest5(){
		assertFalse(repl.checkOperator("125"));
	}

	/*
	*	Test that passing + to evaluate returns addition
	*/
	@Test
	public void evaluateTest1(){
		BigInteger result = repl.evaluate(new BigInteger("2"), new BigInteger("3"), "+");

		assertEquals(0, result.compareTo(new BigInteger("5")));
	}

	/*
	*	Test that passing - to evaluate returns subtraction
	*/
	@Test
	public void evaluateTest2(){
		BigInteger result = repl.evaluate(new BigInteger("2"), new BigInteger("3"), "-");

		assertEquals(0, result.compareTo(new BigInteger("-1")));
	}

	/*
	*	Test that passing * to evaluate returns multiplication
	*/
	@Test
	public void evaluateTest3(){
		BigInteger result = repl.evaluate(new BigInteger("2"), new BigInteger("3"), "*");

		assertEquals(0, result.compareTo(new BigInteger("6")));
	}

	/*
	*	Test that passing / to evaluate returns division
	*/
	@Test
	public void evaluateTest4(){
		BigInteger result = repl.evaluate(new BigInteger("6"), new BigInteger("3"), "/");

		assertEquals(0, result.compareTo(new BigInteger("2")));
	}

	/*
	*	Test that evaluating addition RPN returns correct answer
	*/
	@Test
	public void evaluateRPNTest1(){
		String [] line = {"6", "3", "+"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("9")));
	}

	/*
	*	Test that evaluating subtraction RPN returns correct answer
	*/
	@Test
	public void evaluateRPNTest2(){
		String [] line = {"6", "3", "-"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("3")));
	}

	/*
	*	Test that evaluating multiplication RPN returns correct answer
	*/
	@Test
	public void evaluateRPNTest3(){
		String [] line = {"6", "3", "*"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("18")));
	}

	/*
	*	Test that evaluating division RPN returns correct answer
	*/
	@Test
	public void evaluateRPNTest4(){
		String [] line = {"6", "3", "/"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("2")));
	}

	/*
	*	Testing that longer RPN sequence produces correct answer
	*/
	@Test
	public void evaluateRPNTest5(){
		String [] line = {"10", "10", "*", "5", "5", "*", "0", "0", "*", "+", "+"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("125")));
	}

	/*
	*	Testing that large number RPN sequence produces correct answer
	*/
	@Test
	public void evaluateRPNTest6(){
		String [] line = {"999999999999999999", "999999999999999999", "*"};

		BigInteger result = repl.evaluateRPN(line);

		assertEquals(0, result.compareTo(new BigInteger("999999999999999998000000000000000001")));
	}
}
