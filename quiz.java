/**Definition and implementation of Quiz class 
*@Version: 1.0
*@Author: Davis Giang
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
	private String fileName;
	@SuppressWarnings("unused")
	private boolean fileNameIsValid;
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	Quiz(String dataFile) {
		this.fileName = dataFile;
		// @SuppressWarnings("unused")
		// questions = new ArrayList<Question>();
		if (fileName != null) {
			fileNameIsValid = true;
			File file = new File(fileName);
			if (!loadQuestions(file))
				fileNameIsValid = false;
		}
	}
	
	private boolean loadQuestions(File file) {
		try {
			Scanner reader = new Scanner(file);
			int lineNum = 0;
			while (reader.hasNextLine()) {
				lineNum++;
				String line = reader.nextLine();
				String delim = "\\|";
				String[] tokens = line.split(delim);
				if (validate(tokens, lineNum)) {
					String qType = tokens[0];
					int level = Integer.parseInt(tokens[1]);
					String question = tokens[2];
					String answer = tokens[3];
					if (qType.equalsIgnoreCase("sa")) {
						// create QuestionSA
						// questions.add(new QuestionSA(level, question, answer));
						Question newQuestion = new QuestionSA(level, question, answer);
						questions.add(newQuestion);
					} else if (qType.equalsIgnoreCase("tf")) {
						// create QuestionTF
						questions.add(new QuestionTF(level, question, answer));
					} else {
						String correctAns = tokens[4];
						questions.add(new QuestionMC(level, question, answer, correctAns));
					}
				}
			}
			return true;
		} catch (FileNotFoundException e) {
			System.err.print("Error, could not open file.");
		}
		return false;
	}
	/*-------------------------------------------------------------------------*/
	private boolean validate(String[] tokens, int lineNum) {
		try {
			String qType = tokens[0];
			// Check for comments, bad comments
			if (tokens.length == 1) {
				if (qType.startsWith("#") || qType.startsWith("//")) {
					return false;
				}
				if(qType.isEmpty()) 
					return false;
			}
			if (tokens.length == 4) {
				// Choice question type to match sa or tf
				if (!checkQTypeSATF(qType))
					throw new InvalidDataException("Invalid question type on line number: " + lineNum);

				String lvl = tokens[1];
				if (!checkLvl(lvl))
					throw new InvalidDataException("Invalid level on line number: " + lineNum);

				String ans = tokens[3];

				if (qType.equalsIgnoreCase("tf")) {
					if (!checkTFAnswer(ans))
						throw new InvalidDataException(
								"Invalid answers for true/false question on line number: " + lineNum);
				}

			} else if (tokens.length == 5) {
				// Check question type to match multiple choice
				if (!qType.equalsIgnoreCase("mc"))
					throw new InvalidDataException("Invalid question type on line number: " + lineNum);

				// Check level
				String lvl = tokens[1];
				if (!checkLvl(lvl))
					throw new InvalidDataException("Invalid level on line number: " + lineNum);

				// Check ans fields
				String answers = tokens[3];
				int ansFields = countAnsFields(answers);
				if (ansFields >= 2 & ansFields <= 4) {
					String letterAns = tokens[4];
					if (!checkLetterAns(letterAns, ansFields))
						throw new InvalidDataException("Invalid letter answer on line number: " + lineNum);
				} else
					throw new InvalidDataException("Invalid number of answers on line number: " + lineNum);
				// Check letter ans
			} else if (tokens.length < 4 || tokens.length > 5) {
				throw new InvalidDataException(
						"Invalid fields, you have " + tokens.length + " on line number: " + lineNum);
			} else
				return false;
		} catch (InvalidDataException e) {
			System.err.println(e);
		}
		return true;
	}
	/*-------------------------------------------------------------------------*/
	private int totalQuestions() {
		return questions.size();
	}
	/*-------------------------------------------------------------------------*/
	private void deliverQuiz() {
		String answer;
		Scanner sc = new Scanner(System.in);

		System.out.println("It's quiz time: ");

		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).showQuestion();

			answer = sc.nextLine();

			if (questions.get(i).checkAnswer(answer)) {
				System.out.println("Correct! Great job.");
				questions.get(i).markCorrect();
			} else {
				System.out.println("Sorry, the answer is: ");
				questions.get(i).showAnswer();
			}
		}
		//sc.close();
	}
	/*-------------------------------------------------------------------------*/
	public void redeliverQuiz() {
		String answer; 
		Scanner sc = new Scanner(System.in);
		for(int i=0; i<questions.size(); i++) {
			if(!questions.get(i).isCorrect()) {
				questions.get(i).showQuestion();
				
				answer=sc.nextLine(); 
				
				if (questions.get(i).checkAnswer(answer)) {
					System.out.println("Correct! Great job.");
					questions.get(i).markCorrect();
				} else {
					System.out.println("Sorry, the answer is: ");
					questions.get(i).showAnswer();
				}
			}
		}
		//sc.close();
	}
	/*-------------------------------------------------------------------------*/
	private int getCorrectCount() {
		int count = 0;

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).isCorrect())
				count++;
		}
		return count;
	}
	/*-------------------------------------------------------------------------*/
	private int getIncorrectCount() {
		int count = 0;

		for (int i = 0; i < questions.size(); i++) {
			if (!questions.get(i).isCorrect())
				count++;
		}
		return count;
	}
	/*-------------------------------------------------------------------------*/
	/* Checks the Question type if it is a short answer question or True false question
	 * @param String q is the Question type (String) to be checked
	 * @return true if it is a SA question or TF question
	 */ 
	public static boolean checkQTypeSATF(String q) {
		if (q.equalsIgnoreCase("sa") || q.equalsIgnoreCase("tf"))
			return true;
		else
			return false;
	}
	/*-------------------------------------------------------------------------*/
	/* Checks the validity of questions level
	 * @param String lvl is level of the question
	 * @return True if its a valid level
	 */ 
	public static boolean checkLvl(String lvl) {
		int intLvl = Integer.parseInt(lvl);

		if (intLvl >= 1 && intLvl <= 9)
			return true;
		else
			return false;

	}
	/*-------------------------------------------------------------------------*/
	/* Checks if the answer is T/F true/false
	 * @param String ans is answer to be checked
	 * @return True if answer matches t/f or true/false
	 */ 

	public static boolean checkTFAnswer(String ans) {
		if (ans.equalsIgnoreCase("true") || ans.equalsIgnoreCase("false") || ans.equalsIgnoreCase("t")
				|| ans.equalsIgnoreCase("f"))
			return true;
		else
			return false;

	}
	/*-------------------------------------------------------------------------*/
	/* Splits the String ans and counts the number of answer fields
	 * @param String ans contains all the answers pre-split
	 * @return the number of answer fields
	 */ 

	public static int countAnsFields(String ans) {
		String delim = ":";
		String tokens[] = ans.split(delim);
		return tokens.length;
	}
	/*-------------------------------------------------------------------------*/
	/* Checks the validity of the letter answer for QuestionMC type, it must match
	 * the answers given
	 * @param String ans is the answer to be checked and fields is number of ans
	 * fields 
	 * @return true if the letter answer is valid
	 * @throws
	 */ 

	public static boolean checkLetterAns(String ans, int fields) {
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
			else
				return false;

		}
	}
	/*-------------------------------------------------------------------------*/
	
	/* Creates a quiz from arguments passed by console, delivers the quiz and 
	 * redelivers until user stops or all the questions are answered correctly
	 * @param String[] args is the file name to be accessed
	 * @throws arrayIndexOutOfBounds if there is no argument
	 */ 
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			String retake = "y"; 
			String fileName = args[0];
			System.out.println("Your file name input into arg[0] is: " + fileName);
			Quiz quiz = new Quiz(fileName);
			quiz.deliverQuiz();
			while(quiz.getCorrectCount() != quiz.totalQuestions() && retake.equalsIgnoreCase("y")) {
				System.out.println("Would you like to retry the questions you missed?(Y/any other key to exit"); 
				retake = sc.nextLine(); 
				if(retake.equalsIgnoreCase("y")) 
				quiz.redeliverQuiz();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.print("Error, array out of bounds exception (no argument provided)");
		}
	}
}
