
public class QuestionSA extends Question {
	QuestionSA(int level, String question, String answer) {}
	
	@Override
	public void showQuestion() {
		System.out.println(question); 
		
	}

	@Override
	public void showAnswer() {
		System.out.println(answer);
		
	}

	@Override
	public boolean checkAnswer(String givenAnswer) {
		return givenAnswer.equalsIgnoreCase(answer);
	}

}
