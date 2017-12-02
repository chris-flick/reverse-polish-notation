import java.io.*;

public class RPN{

	/**
	 * Main class.
	 * This is where all the work is done
	 * @param  args the command line argument
	 */
	public static void main (String args []){
		if (args.length == 0){
			REPL repl = new REPL();
			repl.run();
		}
	}

}