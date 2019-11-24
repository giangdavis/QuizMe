/**Description: Definition & implementation of abstract class Question
*Version: 1.0
*Author: Davis Giang
*/

abstract public class Question {
	protected int level;
	protected String answer;
	protected String question;
	protected boolean correct = false;

	public Question(int level, String question, String answer) {
		this.level = level;
		this.question = question;
		this.answer = answer;
		correct = false;
	}
	/*-------------------------------------------------------------------------*/
	// @description: Marks the question correct
	public void markCorrect() {
		correct = true;
	}
	/*-------------------------------------------------------------------------*/
	/* Checks if the question is correct
	 * @return true if the question is correct
	 */ 
	public boolean isCorrect() {
		if (correct == true)
			return true;
		else
			return false;
	}
	/*-------------------------------------------------------------------------*/
	// @description: Prints out the question  
	public abstract void showQuestion();
	/*-------------------------------------------------------------------------*/
	//@description: Prints out the answer
	public abstract void showAnswer();
	/*-------------------------------------------------------------------------*/
	/* Checks the answer to the given answer
	 * @param givenAnswer is the users answer
	 * @return true the answer matches
	 */ 
	public abstract boolean checkAnswer(String givenAnswer);
}
