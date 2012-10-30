package ss.linearlogic.quizquest.question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionManager {
	/**
	 * Holds the array of questions which are to be asked
	 */
	private static ArrayList<Question> questions = new ArrayList<Question>();

	/**
	 * Static method which loads a questions file (.txt) file and parses it into multiple questions
	 * @param filename
	 */
	public static void LoadQuestionsFile(String filename) {
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/*
		 * File format reading:
		 * questionCount -> integer
		 * Question #1
		 * 		questionID -> integer
		 * 		correctIndex -> integer
		 * 		question -> String
		 * 
		 * 		Answer [array of four]
		 * 			answerString -> String
		 */
		
		int questionCount = scanner.nextInt();
				
		for (int i = 0; i < questionCount; ++i) {
			Question question = new Question(scanner.nextInt(), scanner.nextInt());

			scanner.nextLine();
			
			String questionString = scanner.nextLine();			
			question.setQuestionString(questionString);
			
			for (int j = 0; j < 4; ++j) {
				String answer = scanner.nextLine();
				question.addAnswer(answer);
			}
			
			questions.add(question);
		}
		
		scanner.close();
	}
	
	/**
	 * Checks whether the index is correct
	 * @param qid
	 * @param answerIndx
	 * @return boolean
	 */
	public static boolean isIndexCorrect(int qid, int answerIndx) {
		return questions.get(qid).isAnswerCorrect(answerIndx);
	}
	
	/**
	 * Getter for a question at an ID
	 * @param qid
	 * @return question Object
	 */
	public static Question getQuestionForQID(int qid) {
		for (int i = 0; i < questions.size(); ++i) {
			Question question = questions.get(i);
			if (question.getQuestionID() == qid) return question;
		}
		
		return null;
	}
}
