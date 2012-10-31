package ss.linearlogic.quizquest.textbox;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Renderer;

/**
 * A window containing a yes/no prompt and the two choices (which can be scrolled between)
 */
public class YNPrompt {
	
	/**
	 * X-coordinate of the starting location (in pixels) of the prompt window within the current quadrant/camera window
	 */
	private static int pixelX;
	
	/**
	 * X-coordinate of the starting location (in pixels) of the prompt window within the current quadrant/camera window
	 */
	private static int pixelY;
	
	/**
	 * Width, in pixels, of the prompt window
	 */
	private static int pixelWidth;
	
	/**
	 * Height, in pixels, of the prompt window
	 */
	private static int pixelHeight;
	
	/**
	 * The question to be displayed in the prompt window
	 */
	private static String question = "";
	
	/**
	 * Currently selected answer choice (0 --> Yes, 1 --> No. This seems counterintuitive, but the selections
	 * are indexes in an array, left to right, and the 'Yes' option is to be displayed to the left of the 'No' option).
	 */
	private static int currentSelection;
	
	/**
	 * Whether the prompt window is currently in use
	 */
	private static boolean active;
	
	/**
	 * Represents the status of the question (-1 if unanswered, 0 if answered "no", 1 if answered "yes")
	 */
	private static int answerStatus = -1;
	
	/**
	 * Whether a relevant key is depressed (used to prevent repeat key event spam)
	 */
	private static boolean keyLifted = true;
	
	/**
	 * Sets the {@link #question} of the prompt window to the provided string
	 * @param q The question in question ;)
	 */
	public static void setQuestion(String q) {
		question = q;
	}
	
	/**
	 * @return Whether the prompt window is currently {@link #active}
	 */
	public static boolean isActive() { return active; } 
	
	/**
	 * Toggle whether the prompt window is open and in use
	 */
	public static void toggleActive() {
		active = !active;
	}
	
	/**
	 * @return The status of the question (see {@link #answerValue}
	 */
	public static int getAnswerStatus() { return answerStatus; }
	
	/**
	 * Sets the value of the {@link #answerCorrect} flag to the supplied value
	 * Used to reset the question's status to unanswered (by setting the answerCorrect flag to a value other than 0 or 1)
	 * @param correct
	 */
	public static void setAnswerStatus(int correct) { answerStatus = correct; }
	
	/**
	 * Load (but do not display) the prompt window with the supplied location, width, and height
	 * @param x The {@link #pixelX} coordinate of the prompt window
	 * @param y The {@link #pixelY} coordinate of the prompt window
	 * @param width The width of the prompt window, in pixels
	 * @param height The height of the prompt window, in pixels
	 */
	public static void initialize(int x, int y, int width, int height) {
		pixelX = x;
		pixelY = y;
		pixelWidth = width;
		pixelHeight = height;
		currentSelection = 1; //1 is the index of the 'NO' option
		answerStatus = -1;
	}
	
	/**
	 * Update the prompt window and its contents, and then render them
	 */
	public static void update() {
		if (!active) //Make sure the prompt window is actually in use
			return;
		if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			keyLifted = true;
		
		//Handle keyboard input
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && keyLifted) {
			currentSelection -= 1;
			keyLifted = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && keyLifted) {
			currentSelection += 1;
			keyLifted = false;
		}
		
		//Provide wrap-around scrolling
		if (currentSelection > 1)
			currentSelection = 0;
		if (currentSelection < 0)
			currentSelection = 1;
		
		//Handle answer selection
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && keyLifted) {
			keyLifted = false;
			answerStatus = currentSelection%1;
		}

		
		// ...
		render();
	}
	
	/**
	 * Renders the prompt window and its contents
	 */
	public static void render() {
		if (!active) //Double check that the prompt window is in use
			return;
		
		Renderer.renderColoredRectangle(pixelX, pixelY, pixelWidth, pixelHeight, 0.0, 0.0, 1.0);
	}
	
	public static void reset() {
		if (active) //Make sure the window is closed
			active = false;
		answerStatus = -1;
		currentSelection = 1;
		question = "";
	}
}
