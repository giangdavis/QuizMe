import java.io.*;
import java.util.Scanner;

public class Validate {
	/* public static void main(String[] args) {
		// Command line arg is not provided, prompt user for one
		if (args.length == 0) {
			System.out.println("Please enter a filename");
			// don't forget to close scanner scannerName.close();
			Scanner reader = new Scanner(System.in);
			// do i need to store this?
			String fileName = reader.next();
			// printing it just to check filename
			System.out.println(fileName);
			reader.close();
		}
		try {
			String fileName = args[2];

			File file = new File(fileName);
			validate(file);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Array index out of bounds error, check arguments");
		}
	}
*/
	
	public static void validate(File file) {
		try {
			// Scan the file
			Scanner reader = new Scanner(file);
			int lineNum = 0;
			while (reader.hasNextLine()) {
				lineNum++;

				String line = reader.nextLine();

				// If the string is not blank
				if (line.length() != 0) {
					// Split the String into tokens
					String delim = "\\|";
					String[] tokens = line.split(delim);

					// Check fields
					// This will split tokens for short answers & true and false
					if (tokens.length == 4) {
						String qType = tokens[0];
						if (checkQType(qType, lineNum)) {
							String lvl = tokens[1];
							if (checkLvl(lvl, lineNum)) {
								String q = tokens[2];
								String answer = tokens[3];
								System.out.println("Valid question: " + qType + " " + lvl + " " + q + " " + answer);
							}
						}
						// Test
					} else if (tokens.length == 5) {
						String qType = tokens[0];
						if (checkQType(qType, lineNum)) {
							String lvl = tokens[1];
							if (checkLvl(lvl, lineNum)) {
								String q = tokens[2];
								String answers = tokens[3];
								int ansFields = checkAnsFields(answers, lineNum);
								if (ansFields >= 2 & ansFields <= 4) {
									String letterAns = tokens[4];
									if (checkLetterAns(letterAns, lineNum, ansFields)) {
										System.out.println("Valid question: " + qType + " " + lvl + " " + q + " "
												+ answers + " " + letterAns);
									}
								}
							}
						}
					} else {
						System.err.print("Invalid fields on line number: ");
						System.err.println(lineNum);
						System.out.println();
					}
				}
			}
			reader.close();

		} catch (FileNotFoundException e) {

			System.err.print("Error could not open file.");
		}
	}

	public static boolean checkQType(String q, int lineNum) {
		if (q.equalsIgnoreCase("sa") || q.equalsIgnoreCase("tf") || q.equalsIgnoreCase("mc")
				)
			return true;
		else {
			System.err.println("Invalid question type on line number: " + lineNum);
			return false;
		}
	}

	public static boolean checkLvl(String lvl, int lineNum) {
		int intLvl = Integer.parseInt(lvl);
		if (intLvl >= 1 && intLvl <= 9) {
			return true;
		} else {
			System.err.println("Invalid question level on line number: " + lineNum);
			return false;
		}
	}

	public static boolean checkTFAnswer(String ans, int lineNum) {
		String temp = ans.toLowerCase();
		if (ans.equalsIgnoreCase("true") || ans.equalsIgnoreCase("false") || ans.equalsIgnoreCase("t")
				|| ans.equalsIgnoreCase("f")) {
			return true;
		} else {
			System.err.println("Invalid T/F answer on line number " + lineNum);
			return false;
		}
	}

	public static int checkAnsFields(String ans, int lineNum) {
		String delim = ":";
		String tokens[] = ans.split(delim);
		return tokens.length;
	}

	public static boolean checkLetterAns(String ans, int lineNum, int fields) {
		if (fields == 2) {
			if (ans.equalsIgnoreCase("a") || ans.equalsIgnoreCase("b"))
				return true;
			else
				return false;
		} else if (fields == 3) {
			if (ans.equalsIgnoreCase("a") || ans.equalsIgnoreCase("b") || ans.equalsIgnoreCase("c"))
				return true;
			else
				return false;
		} else {
			if (ans.equalsIgnoreCase("a") || ans.equalsIgnoreCase("b") || ans.equalsIgnoreCase("c")
					|| ans.equalsIgnoreCase("d"))
				return true;
			else {
				System.err.println("Invalid MA/MC answer on line number: " + lineNum);
				return false;
			}
		}
	
	}
}
