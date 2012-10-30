package ss.linearlogic.quizquest.textbox;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

import ss.linearlogic.quizquest.Renderer;
import ss.linearlogic.quizquest.player.Inventory;
import ss.linearlogic.quizquest.textbox.question.Question;

public class Textbox {
	
	/**
	 * ArrayList containing the lines of the question to be displayed
	 */
	private static ArrayList<String>questionLines = new ArrayList<String>();
	
	/**
	 * The maximum number of lines a question can have
	 */
	private static final int max_line_count = 4;
	
	/**
	 * The font of the strings in the textbox
	 */
	private static TrueTypeFont font;
	
	/**
	 * The x-coordinate of the textbox, in pixels
	 */
	private static int x;
	
	/**
	 * The y-coordinates of the textbox, in pixels
	 */
	private static int y;
	
	/**
	 * The width, in pixels, of the textbox
	 */
	private static int width;
	
	/**
	 * The height, in pixels, of the textbox
	 */
	private static int height;
	
	/**
	 * Whether this textbox window is active
	 */
	private static boolean active = true;
	
	/**
	 * ArrayList containing the possible answers to the question
	 */
	private static ArrayList<String> answers = new ArrayList<String>();
	
	/**
	 * The letters to use as the multiple choice options
	 */
	private final static String letters = "ABCD";
	
	/**
	 * The integer ID of the answer that is currently selected (0-3)
	 */
	private static int current_selection = 0;
	
	/**
	 * The index in the {@link #answers} ArrayList of the correct answer
	 */
	private static int correctIndex = -1;
	
	/**
	 * Represents the question's state: -1 if unanswer, 0 if answered incorrectly, 1 if answered correctly.
	 */
	private static int answerCorrect = -1;
	
	/**
	 * Used to prevent repeat key events if a key is down
	 */
	private static boolean keyLifted = true;
	
	/**
	 * Constructor. Not much happening here at the moment...
	 */
	public Textbox() {}
	
	/**
	 * Initializes the textbox with the supplied custom font
	 * @param fontFile The file from which to load the font
	 */
	public static void initialize(String fontFile) {
		font = Renderer.loadFont(fontFile, 24);
		
		x = 0;
		y = 380;
		
		width = 480;
		height = 100;
	}
	
	/**
	 * Initializes the textbox without a custom font
	 */
	public static void initializeWithSystemFont() {
		font = Renderer.loadSystemFont("SansSerif", 10);
		
		if (font == null) System.out.println("Font is null");
		
		x = 0; // x-coord for the top righthand corner of the text box
		y = 380; // y-coord for the top righthand corner of the text box
		
		width = 480; // dimensions of the textbox
		height = 100;
		
		active = false;
	}
	
	/**
	 * Loads the question from the parameters and creates a question box
	 * @param Question
	 */
	public static void loadQuestion(Question question) {
		//Get the question attributes and add them to the textbox
		setCorrectIndex(question.getCorrectAnswerIndex());
		setQuestion(question.getQuestionString());

		for (int i = 0; i < 4; ++i) {
			addAnswer(question.getAnswerString(i));
		}
		
		current_selection = 0;
		active = true;
	}
	
	/**
	 * Adds the supplied answer to the {@link #answers} ArrayList at the next available index
	 * @param answer
	 */
	public static void addAnswer(String answer) {
		if (answers.size() >= 4)
			return;
		
		answers.add(answer);
	}
	
	/**
	 * Removes the answer at the provided index from the {@link #answers} ArrayList
	 * @param indx
	 */
	public static void removeAnswer(int indx) {
		answers.remove(indx);
	}
	
	/**
	 * Removes provided answer string from the {@link #answers} ArrayList
	 * @param ans
	 */
	public static void removeAnswer(String ans) {
		answers.remove(ans);
	}
	
	/**
	 * Sets the index of the correct answer in the {@link #answers} ArrayList to the supplied value
	 * @param indx
	 */
	public static void setCorrectIndex(int indx) {
		correctIndex = indx;
	}
	
	/**
	 * Toggles whether the textbox window is open
	 */
	public static void toggleActive() {
		active = !active;
		
		//Make the keys disable repeat if the window is active
		Keyboard.enableRepeatEvents(!active);
	}
	
	/**
	 * Checks if the textbox window is in use
	 * @return Whether the textbox window is in use
	 */
	public static boolean isActive() {
		return active;
	}
	
	/**
	 * Returns the value of the {@link #answerCorrect} flag. See the {@link #answerCorrect} Javadoc
	 * for information about its possible states and what they mean
	 * @return The value of {@link #answerCorrect}
	 */
	public static int isAnswerCorrect() {
		return answerCorrect;
	}
	
	/**
	 * Sets the value of the {@link #answerCorrect} flag to the supplied value
	 * Used to reset the question's status to unanswered (by setting the answerCorrect flag to a value other than 0 or 1)
	 * @param correct
	 */
	public static void setAnswerCorrect(int correct) {
		answerCorrect = correct;
	}
	
	/**
	 * Set the current question to the provided question string
	 * @param question
	 */
	public static void setQuestion(String question) {
		StringTokenizer tokenizer = new StringTokenizer(question, "\n");
		int counter = 0;
		
		while (tokenizer.hasMoreTokens()) {
			if (counter > max_line_count) 
				break;
			
			questionLines.add(tokenizer.nextToken());
			
			counter++;
		}
	}
	
	/**
	 * @return The answer that is currently selected by the cursor
	 */
	public static int getCurrentSelection() {
		return current_selection;
	}
	
	/**
	 * Set the current selection (and cursor) to the answer whose index is provided
	 * @param sel The index of the answer to put the cursor on
	 */
	public static void setCurrentSelection(int sel) {
		current_selection = sel;
	}
	
	/**
	 * Update the textbox window and its contents
	 */
	public static void update() {
		if (Keyboard.areRepeatEventsEnabled()) Keyboard.enableRepeatEvents(false);
		
		if (active && !Inventory.isActive()) {
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)) keyLifted = true;
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && keyLifted) { current_selection -= 1; keyLifted = false; }
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && keyLifted) { current_selection += 1; keyLifted = false; }
			
			// if all keys have been released, set keyLifted to true to re-enable scrolling through selections
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)) keyLifted = true;
			
			//Use the numbers to correspond for answers
			if (Keyboard.isKeyDown(Keyboard.KEY_1)) current_selection = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_2)) current_selection = 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_3)) current_selection = 2;
			if (Keyboard.isKeyDown(Keyboard.KEY_4)) current_selection = 3;
			
			//Use the letters to correspond for answers
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) current_selection = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_B)) current_selection = 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) current_selection = 2;
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) current_selection = 3;
			
			//When user pressed the enter key, checks to see if the user is correct
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && keyLifted) {
				keyLifted = false;
				if (checkIfCorrect()) answerCorrect = 1;
				else answerCorrect = 0;
			}
					
			//Provide wrap around
			if (current_selection > 3) current_selection = 0;
			if (current_selection < 0) current_selection = 3;
		}
	}
	
	/**
	 * @return Whether the currently selected answer is the correct one (by comparing answer indexes)
	 */
	private static boolean checkIfCorrect() {
		return current_selection == correctIndex;
	}
	
	/**
	 * Resets all of the variables back to their original states
	 */
	public static void reset() {
		current_selection = 0;
		correctIndex = -1;
		answerCorrect = -1;
		
		active = false;
		answers.clear();
		questionLines.clear();
	}
	
	/**
	 * Render the textbox (if it is active)
	 */
	public static void render() {
		if (!active) return;
			
		//Render the rectangle with the question
		Renderer.renderColoredRectangle(x, y, width, height, 0.0f, 0.0f, 0.7f);		
		
		for (int i = 0; i < questionLines.size(); ++i) {
			Renderer.renderString(questionLines.get(i), x + 10, y + (font.getHeight() * i), font);
		}
				
		//Render the selection rectangle
		Renderer.renderTransparentRectangle((x + 20 + (current_selection * 100)) - 5, y + 65, (font.getWidth(letters.charAt(current_selection) + ": " + answers.get(current_selection))) + 10, font.getHeight() + 10);
		
		//Render all of the answers
		for (int i = 0; i < answers.size(); ++i) {
			Renderer.renderString(letters.charAt(i) + ": " + answers.get(i), x + 20 + (i * 100), y + 70, font);
		}
	}
}
