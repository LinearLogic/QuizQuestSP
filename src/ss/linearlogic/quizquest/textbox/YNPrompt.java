package ss.linearlogic.quizquest.textbox;

import ss.linearlogic.quizquest.Renderer;

/**
 * A window containing a yes/no prompt and the two choices (which can be scrolled between)
 */
public class YNPrompt {
	
	private static int pixelX;
	
	private static int pixelY;
	
	private static int pixelWidth;
	
	private static int pixelHeight;
	
	private static String question = "";
	
	private static int currentSelection;
	
	private static boolean active;
	
	public static void initialize(int x, int y, int w, int h) { //TODO: decide whether to hardcode the dimensions and/or location of the prompt window
		pixelX = x;
		pixelY = y;
		pixelWidth = w;
		pixelHeight = h;
		currentSelection = 1; //1 is the index of the 'NO' option
	}
	
	public static void setQuestion(String q) {
		question = q;
	}
	
	public static void update() {
		// ...
		render();
	}
	
	private static void render() {
		if (!active) return;
		
		Renderer.renderColoredRectangle(pixelX, pixelY, pixelWidth, pixelHeight, 0.0, 0.0, 1.0);
	}
}
