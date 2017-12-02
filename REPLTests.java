import static org.junit.Assert.*;

import org.junit.*;

public class REPLTests {

	REPL repl;

	@Before
	public void setup(){
		repl = new REPL();
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
}