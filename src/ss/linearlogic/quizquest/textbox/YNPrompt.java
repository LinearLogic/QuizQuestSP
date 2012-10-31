package ss.linearlogic.quizquest.textbox;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

import ss.linearlogic.quizquest.Renderer;
import ss.linearlogic.quizquest.player.Inventory;
import ss.linearlogic.quizquest.player.Player;

/**
 * A window containing a yes/no prompt and the two choices (which can be scrolled between)
 */
public class YNPrompt {
	
	/**
	 * X-coordinate of the starting location (in pixels) of the prompt window within the current quadrant/camera window
	 */
	private int pixelX;
	
	/**
	 * Default {@link #pixelX} value
	 */
	private static int defaultPixelX;
	
	/**
	 * X-coordinate of the starting location (in pixels) of the prompt window within the current quadrant/camera window
	 */
	private int pixelY;
	
	/**
	 * Default {@link #pixelY} value
	 */
	private static int defaultPixelY;
	
	/**
	 * Width, in pixels, of the prompt window
	 */
	private int pixelWidth;
	
	/**
	 * Default {@link #pixelWidth} value
	 */
	private static int defaultPixelWidth;
	
	/**
	 * Height, in pixels, of the prompt window
	 */
	private int pixelHeight;
	
	/**
	 * Default {@link #pixelHeight} value
	 */
	private static int defaultPixelHeight;
	
	/**
	 * The question to be displayed in the prompt window
	 */
	private String question = "";
	
	/**
	 * The integer ID of the prompt type (0 for exit, 1 for door)
	 */
	private int typeID;
	
	/**
	 * The font of the question string in the prompt window
	 */
	private TrueTypeFont font;
	
	/**
	 * Currently selected answer choice (0 --> Yes, 1 --> No. This seems counterintuitive, but the selections
	 * are indexes in an array, left to right, and the 'Yes' option is to be displayed to the left of the 'No' option).
	 */
	private int currentSelection;
	
	/**
	 * Whether the prompt window is currently in use
	 */
	private boolean active;
	
	/**
	 * Represents the status of the question (-1 if unanswered, 0 if answered "no", 1 if answered "yes")
	 */
	private int answerStatus = -1;
	
	/**
	 * Whether a relevant key is depressed (used to prevent repeat key event spam)
	 */
	private boolean keyLifted = false;
	
	/**
	 * Static ArrayList containing all the active YNPrompt objects
	 */
	private static ArrayList<YNPrompt> prompts = new ArrayList<YNPrompt>();
	
	/**
	 * Simple constructor - calls the complete constructor with the default window dimension values.
	 * 
	 * @param typeID The {@link #typeID} of the prompt, used to identify which type of prompt it is
	 */
	public YNPrompt(int typeID) {
		this(defaultPixelX, defaultPixelY, defaultPixelWidth, defaultPixelHeight, typeID);
	}
	
	/**
	 * Complete constructor. Loads (but does not display) the prompt window with the supplied location, width, and height
	 * @param x The {@link #pixelX} coordinate of the prompt window
	 * @param y The {@link #pixelY} coordinate of the prompt window
	 * @param width The width of the prompt window, in pixels
	 * @param height The height of the prompt window, in pixels
	 */
	public YNPrompt(int x, int y, int width, int height, int typeID) {
		this.pixelX = x;
		this.pixelY = y;
		this.pixelWidth = width;
		this.pixelHeight = height;
		this.currentSelection = 1; //1 is the index of the 'NO' option
		this.answerStatus = -1;
		//Attempt to load the system font and dispatch an error on failure
		this.font = Renderer.loadSystemFont("SansSerif", 10);
		if (this.font == null)
			System.err.println("Font is null");
		this.typeID = typeID;
		if (typeID == 0) //Quit-game prompt
			this.question = "Would you like to quit the current game session?";
		else if (typeID == 1) //Door prompt
			this.question = "Would you like to use your key to open this door?";
		prompts.add(this);
		this.setActive(true);
	}
	
	/**
	 * Sets the default YNPrompt window dimensions to the supplied values
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void setDefaultValues(int x, int y, int width, int height) {
		defaultPixelX = x;
		defaultPixelY = y;
		defaultPixelWidth = width;
		defaultPixelHeight = height;
	}
	/**
	 * Sets the {@link #question} of the prompt window to the provided string
	 * @param q The question in question ;)
	 */
	public void setQuestion(String q) {
		this.question = q;
	}
	
	/**
	 * @return Whether the prompt window is currently {@link #active}
	 */
	public boolean isActive() { return this.active; }
	
	/**
	 * Toggles whether the prompt window is open and in use (activating the window minimizes all other prompt windows)
	 * 
	 * @param active The boolean value representing the desired state of the prompt window (true for active, otherwise false)
	 */
	public void setActive(boolean active) {
		if (active)
			for (YNPrompt prompt : prompts)
				if (prompt.isActive())
					prompt.setActive(false);
		this.active = active;
	}
	
	/**
	 * Clears the YNPrompt specifications (such as {@link #answerStatus} and {@link #question} , closes the prompt window,
	 * and removes the YNPrompt object from the {@link #prompts} ArrayList.
	 */
	public void close() {
		if (this.active) //Make sure the window is closed
			this.active = false;
		this.answerStatus = -1;
		this.currentSelection = 1;
		this.question = "";
		prompts.remove(this);
		Textbox.setKeyLifted(false);
		Inventory.setKeyLifted(false);
	}
	
	/**
	 * @return The status of the question (see {@link #answerValue}
	 */
	public int getAnswerStatus() { return this.answerStatus; }
	
	/**
	 * Sets the value of the {@link #answerCorrect} flag to the supplied value
	 * Used to reset the question's status to unanswered (by setting the answerCorrect flag to a value other than 0 or 1)
	 * @param correct
	 */
	public void setAnswerStatus(int answerStatus) { this.answerStatus = answerStatus; }
	
	/**
	 * @return The {@link #typeID} of the YNPrompt object
	 */
	public int getTypeID() { return this.typeID; }
	
	/**
	 * Update the prompt window and its contents, and then render them
	 */
	public void update() {
		if (!this.active) //Make sure the prompt window is actually in use
			return;
		if (!this.keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			this.keyLifted = true;
		
		//Handle keyboard input
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && this.keyLifted) {
			this.currentSelection -= 1;
			this.keyLifted = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && this.keyLifted) {
			this.currentSelection += 1;
			this.keyLifted = false;
		}
		
		//Provide wrap-around scrolling
		if (this.currentSelection > 1)
			this.currentSelection = 0;
		if (this.currentSelection < 0)
			this.currentSelection = 1;
		
		//Handle answer selection
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && this.keyLifted) {
			this.keyLifted = false;
			this.answerStatus = (this.currentSelection+1)%2;
		}

		render();
	}
	
	/**
	 * Renders the prompt window and its contents
	 */
	public void render() {
		if (!this.active) //Double check that the prompt window is in use
			return;
		//Rectangle rendering
		Renderer.renderColoredRectangle(this.pixelX, this.pixelY, this.pixelWidth, this.pixelHeight, Renderer.default_window_r, Renderer.default_window_g, Renderer.default_window_b); //Render the main window 
		Renderer.renderLinedRectangle(this.pixelX, this.pixelY, this.pixelWidth, this.pixelHeight, 1.0, 1.0, 1.0); //Render the outline

		//Render the rectangle highlighting the currently selected option
		if (this.currentSelection == 0) //"Yes" option is selected
			Renderer.renderTransparentRectangle(this.pixelX + 37, this.pixelY + 40, this.font.getWidth("Yes") + 6, this.font.getHeight() + 2);
		else //"No" option is selected
			Renderer.renderTransparentRectangle(this.pixelX + 197, this.pixelY + 40, this.font.getWidth("No") + 6, this.font.getHeight() + 2);
		
		//String rendering
		Renderer.renderString(this.question, this.pixelX + 10, this.pixelY + 10, this.font); //Render the question
		Renderer.renderString("Yes", this.pixelX + 40, this.pixelY + 40, this.font); //Render the "Yes" option
		Renderer.renderString("No", this.pixelX + 200, this.pixelY + 40, this.font); //Render the "No" option
	}
		
	/**
	 * Update all active YNPrompt objects/windows
	 */
	public static void updateAll() {
		for (YNPrompt prompt : prompts)
			prompt.update();
	}
	
	/**
	 * Close all active YNPrompt objects/windows
	 */
	public static void closeAll() {
		ArrayList<YNPrompt> temp = new ArrayList<YNPrompt>();
		for (YNPrompt prompt : prompts) //This process involving a temporary ArrayList is necessary to prevent CMEs
			temp.add(prompt);
		for (YNPrompt prompt : temp)
			prompts.remove(prompt);
		prompts.clear();
	}
	
	/**
	 * @return Whether there are any active prompt windows
	 */
	public static boolean arePromptsActive() {
		for (YNPrompt prompt : prompts)
			if (prompt.isActive())
				return true;
		return false;
	}
	
	/**
	 * @return The list of loaded (but not necessarily active) {@link #prompts}
	 */
	public static ArrayList<YNPrompt> getPrompts() { return prompts; }
	
	/**
	 * Activates the next prompt window in the {@link #prompts} queue
	 */
	public static void loadNextPrompt() {
		if (prompts.size() > 0) {
			prompts.get(0).setActive(true);
			Player.setCurrentYNPrompt(prompts.get(0));
		}
	}
}
