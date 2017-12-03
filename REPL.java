import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.util.HashMap;

public class REPL{

	private String [] KEYWORDS = {"QUIT", "LET", "PRINT"};
	private char [] OPERATORS = {'+', '-', '/', '*'};
	private String [] alphabetArray = { "A", "B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private List<String> ALPHABET = Arrays.asList(alphabetArray);
	private int lineNum = 1;
	private HashMap<String, BigInteger> hashMap = new HashMap<String, BigInteger>();
	public REPL(){

	}

	/**
	 * read-eval-print-loop method
	 */
	public void run(){
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while(true){
			try{
				System.out.print("> ");
				String in = input.readLine();
				String [] line = in.toUpperCase().split("\\s+");

				if(in.equals("")){
					System.err.println("Line " + lineNum + ": Operator " + " NULL " + " applied to empty stack.");
					continue;
				}

				String keyword = "";

				int result = checkValidKeyword(line);

				if (result == -1){
					System.err.println("Line " + lineNum + ": Keyword found in middle of expression.");
					continue;
				}

				evaluateExpression(line, result);
			}
			catch (IOException ioe){

			}

			lineNum++;
		}

	}


	/**
	* determines what to do depending on the keyword
	* 0 - evaluate
	* 1 - Quit
	* 2 - Let
	* 3 - evaluate, then Print
	* @param line String []
	* @param int keyword
	*/
	public void evaluateExpression(String [] line, int keyword){
		switch (keyword){
			case 0:
				BigInteger result = evaluateRPN(line);
				if (result != null)
					System.out.println(result.toString());

				System.out.println();
				break;
			case 1:
				System.exit(0);
				break;
			case 2:
				//Same concept as Case 3. Evaluate then print.
				BigInteger answer = evaluateRPN(line);
				if (answer != null) System.out.println(answer.toString());
				System.out.println();
				break;
			case 3:
				BigInteger ans = evaluateRPN(line);
				if (ans != null)
					System.out.println(ans.toString());

				System.out.println();
				break;
		}

	}

	/**
	*
	* @param line String []
	* @return BigInteger - result of RPN evaluation. If -1, then operater applied to empty stack.
	*/
	public BigInteger evaluateRPN(String [] line){
		Stack<BigInteger> stack = new Stack<BigInteger>();
		BigInteger result = BigInteger.ZERO;
		boolean isLet = false;
		String variable = "";

		for (int i = 0; i < line.length; i++){
			if(isLet == true && line[i].length() == 1 && ALPHABET.contains(line[i])){ //If the previous string is let, then this key must be a variable.
				variable = line[i];
				isLet = false;
				continue;
			}else if(isLet == true && line[i].length() == 1 && ALPHABET.contains(line[i]) == false){
				System.out.println("Line " + lineNum + ": Not a valid variable!");
				return null;
			}

			// ignore the print statement and evaluate
			if (line[i].toUpperCase().equals("PRINT")){
				continue;
			}else if(line[i].toUpperCase().equals("LET")){ //If it is let, we got to continue to the next iteration
				isLet = true;
				continue;
			}


			// if operator is found, need to pop two items from stack and apply operator to them
			if (checkOperator(line[i])){
				try{
					BigInteger operand2 = stack.pop();
					BigInteger operand1 = stack.pop();
					if(operand2.toString().equals("0") && line[i].equals("/")){
						return null;
					}else{
						BigInteger answer = evaluate(operand1, operand2, line[i]);
						stack.push(answer);
					}
				}
				// catches empty stack
				catch (EmptyStackException ese){
					System.err.println("Line " + lineNum + ": Operator " + line[i] + " applied to empty stack.");
					//Requirement states that if this is in REPL mode, we just need to display
					//but the line will be ignored. Meaning no exit?
					//System.exit(2);
					return null;
				}
			}
			// put number onto stack since it isn't an operator
			else{
				try{
					if(line[i].length() == 1 && hashMap.containsKey(line[i].toUpperCase())){
						stack.push(new BigInteger(hashMap.get(line[i]).toString()));
					}else stack.push(new BigInteger(line[i]));
				}
				// catches unknown keywords found in line
				catch (NumberFormatException nfe){
					if(line[i].length() == 1 && hashMap.containsKey(line[i].toUpperCase()) == false && ALPHABET.contains(line[i])){
						System.err.println("Line " + lineNum + ": " + "Variable " + line[i] + " is not initialized.");
						//Requirement states that if this is in REPL mode, we just need to display
						//but the line will be ignored. Meaning no exit?
						//System.exit(1);
						return null;
					}
					System.err.println("Line " + lineNum + ": " + "Unknown keyword " + line[i]);
					//System.exit(4);
					return null;
				}
			}
		}

		// if stack isn't 1 here, then there are elements still on stack
		if (stack.size() != 1){
			System.err.println("Line " + lineNum + ": " + stack.size() + " elements in stack after evaluation.");
			//System.exit(3);
			return null;
		}

		// pop result off stack to return
		try{
			result = stack.pop();
			if(!variable.equals("")){
					hashMap.put(variable,result);
			}
		}
		catch(EmptyStackException ese){
			System.err.println("Line " + lineNum + ": Stack is empty.");
			return null;
		}




		return result;
	}

	/**
	* performs the given operator on the numbers gived
	* @param o1 long
	* @param o2 long
	* @param operator String
	* @return long - result of operation
	*/
	public BigInteger evaluate(BigInteger o1, BigInteger o2, String operator){
		BigInteger result = BigInteger.ZERO;

		switch (operator){
			case "+":
				result = o1.add(o2);
				break;
			case "-":
				result = o1.subtract(o2);
				break;
			case "*":
				result = o1.multiply(o2);
				break;
			case "/":
				result = o1.divide(o2);
				break;
		}

		return result;
	}

	/**
	*	checks whether string is one of the valid operators
	* @param s String
	* @return boolean true if one of the valid operators
	*/
	public boolean checkOperator(String s){
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}

	/**
	 * checks for a keyword
	 * checks that keyword is at the beginnging of the line
	 * @param line String []
	 * @return int - int signifies if a keyword was found
	 * -1 - keyword found in wrong position of line
	 * 0 - no keyword
	 * 1 - 3 - keyword found and points to which index in KEYWORDS
	 */
	public int checkValidKeyword(String [] line){
		int result = 0;

		// checks to see if first string in line is a keyword
		result = checkKeyWordsArray(line[0]);

		// iterates through rest of line looking for a keyword
		// if found, return -1 to signify an error
		for (int i = 1; i < line.length; i++){
			if (checkKeyWordsArray(line[i]) != 0)
				return -1;
		}

		return result;
	}


	/**
	*
	* @param s String
	* @return int - signifies if a keyword was found
	* 0 - no keyword
	* 1 - QUIT found
	* 2 - LET found
	* 3 - PRINT found
	*/
	public int checkKeyWordsArray(String s){

		int result = 0;
		// iterates through Keywords and compares to string input in order to determine
		// if any keyword was found
		for (int i = 0; i < KEYWORDS.length; i++){
			if (s.toUpperCase().equals(KEYWORDS[i])){
				result = i + 1;
			}
		}

		return result;
	}
}
