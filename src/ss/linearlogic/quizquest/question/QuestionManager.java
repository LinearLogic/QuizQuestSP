package ss.linearlogic.quizquest.question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionManager {
	private static ArrayList<Question> questions = new ArrayList<Question>();

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
			String questionString = scanner.nextLine();
			
			question.setQuestionString(questionString);
			
			for (int j = 0; j < 4; ++j) {
				String answer = scanner.nextLine();
				question.addAnswer(answer);
			}
			
			questions.add(question.getQuestionID(), question);
		}
		
		scanner.close();
	}
	
	public static boolean isIndexCorrect(int qid, int answerIndx) {
		return questions.get(qid).isAnswerCorrect(answerIndx);
	}
	
	public Question getQuestionForQID(int qid) {
		return questions.get(qid);
	}
}
