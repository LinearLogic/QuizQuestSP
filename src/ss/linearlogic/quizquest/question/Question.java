package ss.linearlogic.quizquest.question;

public class Question {
	private int questionID;
	private int correctIndex;
	private String questionString;
	
	private String[] answers = new String[4];
	
	//Constructor for the question
	public Question(int qID, int correctIndx) {
		questionID = qID;
		correctIndex = correctIndx;
	}
	
	//Add an answer to the array of strings
	public void addAnswer(String answer) {
		if (answers[3] != null) return;
		
		for (int i = 0; i < answers.length; ++i) {
			if (answers[i] == null) {
				answers[i] = answer;
				break;
			}
		}
	}
	
	//Remove the answer from the answers array using an answer string object
	public void removeAnswer(String answer) {
		for (int i = 0; i < answers.length; ++i) {
			if (answers[i] == answer) {
				answers[i] = null;
				break;
			}
		}
	}
	
	//Remove the answer from the answers array using an index
	public void removeAnswer(int index) {
		answers[index] = null;
	}
	
	//Get the answer according to the index
	public String getAnswerString(int indx) {
		return answers[indx];
	}
	
	//Set the question ID
	public void setQuestionID(int id) {
		questionID = id;
	}
	
	//Get the question ID
	public int getQuestionID() {
		return questionID;
	}
	
	//Set the correct answer index
	public void setCorrectAnswerIndex(int indx) {
		correctIndex = indx;
	}
	
	//Get the correct answer index
	public int getCorrectAnswerIndex() {
		return correctIndex;
	}
	
	//Check if the index provided is the correct index
	public boolean isAnswerCorrect(int indx) {
		return indx == correctIndex;
	}
	
	//Set the question string
	public void setQuestionString(String str) {
		questionString = str;
	}
	
	//Get the question string
	public String getQuestionString() {
		return questionString;
	}
}
