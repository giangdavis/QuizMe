/** Definition & implementation of QuestionTF class. (extends Question)
*Version: 1.0
*Author: Davis Giang
*/

public class QuestionTF extends Question {

	QuestionTF(int level, String question, String answer) {
		super(level, question, answer); 
	}
	/*-------------------------------------------------------------------------*/
	public void showQuestion() {
		System.out.println(question + "(True/False)");
	}
	/*-------------------------------------------------------------------------*/
	public void showAnswer() {
		System.out.println(answer);
	}
	/*-------------------------------------------------------------------------*/
	public boolean checkAnswer(String givenAnswer) {
		if(answer.equalsIgnoreCase("t") || answer.equalsIgnoreCase("True")) {
			if(givenAnswer.equalsIgnoreCase("T") || givenAnswer.equalsIgnoreCase("true"))
				return true;
			else
				return false;
		}
		else if(answer.equalsIgnoreCase("F") || answer.equalsIgnoreCase("false")) {
			if(givenAnswer.equalsIgnoreCase("f") || givenAnswer.equalsIgnoreCase("false")) 
				return true;
			else
				return false;
		}
		else
			return false;
	}

}
