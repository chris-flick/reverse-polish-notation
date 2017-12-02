import java.io.*;

public class REPL{

	private String [] KEYWORDS = {"QUIT", "LET", "PRINT"};
	private char [] OPERATORS = {'+', '-', '/', '*'};

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
				String keyword = "";

				int result = checkValidKeyword(line);
			}
			catch (IOException ioe){

			}

		}
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