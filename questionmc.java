
public class QuestionMC extends Question {
	private String correctAns; 
	
	QuestionMC(int level, String question, String answer, String correctAns) {
		this.correctAns = correctAns; 
	}
	
	public void showQuestion() {
		String delim = ":";
		char letterOption = 'A'; 
		String[] tokens = answer.split(delim); 
		
		for(String word: tokens) {
			System.out.println(letterOption + ": " + word); 
			letterOption++; 
		}
		System.out.println(); 
	}
	
	public boolean checkAnswer(String givenAnswer) {
		if(givenAnswer.length() == 1) {
			return givenAnswer.equalsIgnoreCase(correctAns); 
		}
		else
			return false;
	}
	
	public void showAnswer() {
		System.out.println(correctAns); 
	}
}
