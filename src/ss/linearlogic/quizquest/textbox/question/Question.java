package ss.linearlogic.quizquest.textbox.question;

/**
 * Represents a multiple choice trivia question to present to the player in a Textbox.
 */
public class Question {
	
	/**
	 * The integer ID of the question, used to identify and call it
	 */
	private int questionID;
	
	/**
	 * The index, within the array of answers, of the right answer to the question
	 */
	private int correctIndex;
	
	/**
	 * The multiple choice question being asked
	 */
	private String questionString;
	
	/**
	 * A string array containing the answer choices to the question
	 */
	private String[] answers = new String[4];

	/**
	 * Constructs the question object with the supplied {@link #questionID} and {@link #correctIndex} values
	 * @param qID
	 * @param correctIndx
	 */
	public Question(int qID, int correctIndx) {
		questionID = qID;
		correctIndex = correctIndx;
	}
	
	/**
	 * Appends an answer string to the array of answers
	 * @param answer
	 */
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
	/**
	 * Removes the supplied answer string from the {@link #answers} array, if it is present in the array
	 * @param answer
	 */
	public void removeAnswer(String answer) {
		for (int i = 0; i < answers.length; ++i) {
			if (answers[i].equalsIgnoreCase(answer)) {
				answers[i] = null;
				break;
			}
		}
	}
	
	//Remove the answer from the answers array using an index
	/**
	 * Removes the answer string at the current index in the {@link #answers} array
	 * @param index
	 */
	public void removeAnswer(int index) {
		answers[index] = null;
	}
	
	/**
	 * @param index
	 * @return The answer located at the supplied index within the {@link #answers} array
	 */
	public String getAnswerString(int index) {
		return answers[index];
	}
	
	/**
	 * Set the {@link #questionID} of the Question object
	 * 
	 * @param id
	 */
	public void setQuestionID(int id) {
		questionID = id;
	}
	
	/**
	 * @return The {@link #questionID} of the Question object
	 */
	public int getQuestionID() {
		return questionID;
	}
	
	/**
	 * Sets the {@link #correctIndex} to the supplied value
	 * 
	 * @param indx
	 */
	public void setCorrectAnswerIndex(int indx) {
		correctIndex = indx;
	}
	
	/**
	 * @return The current {@link #correctIndex} of the Question object
	 */
	public int getCorrectAnswerIndex() {
		return correctIndex;
	}
	
	/**
	 * Checks if the provided index is the index of the correct answers
	 * 
	 * @param index The integer index value to check against the {@link #correctIndex}
	 * @return True if the provided index matches the {@link #correctIndex}
	 */
	public boolean isAnswerCorrect(int index) {
		return index == correctIndex;
	}
	
	/**
	 * Sets the {@link #questionString} to the supplied string
	 * @param str
	 */
	public void setQuestionString(String str) {
		questionString = str;
	}
	
	/**
	 * @return The {@link #questionString} of the Question object
	 */
	public String getQuestionString() {
		return questionString;
	}
}
