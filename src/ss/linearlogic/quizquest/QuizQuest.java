package ss.linearlogic.quizquest;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ss.linearlogic.quizquest.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class QuizQuest {
	
	private static boolean running = true;
	
	private final int screen_width = 480;
	private final int screen_height = 480;
		
	//Constructor for game object
	public QuizQuest() {
		initializeOpenGL(screen_width, screen_height);
		mainLoop();
		destroyOpenGL();
	}
	
	//Initialize the window along with the opengl aspects
	public void initializeOpenGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glDisable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		glClearDepth(1);
	}
	
	//Create a mainloop to handle rendering and logic
	public void mainLoop() {
	
		Map.initialize("map.txt");
		Map.addTexture("Grass.png", 0);
		Map.addTexture("Floor.png", 1);
		Map.addTexture("Wall.png", 2);
		Map.addTexture("Door.png", 3);

		Player.initialize(100, 100, "Door.png");
		
		Textbox.initializeWithSystemFont();
		
		Textbox.setQuestion("What is the square root of 64,\nyou must answer this question to start the game!");
		Textbox.addAnswer("2");
		Textbox.addAnswer("3");
		Textbox.addAnswer("4");
		Textbox.addAnswer("8");
		Textbox.setCorrectIndex(3);
		
		while (running) {
			//Close application when close is requested or escape key is pressed
			running = !Display.isCloseRequested();
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				break;
							
			//Game rendering/logic area
			glClear(GL_COLOR_BUFFER_BIT);
			
			Map.render();
			Player.update();
			Player.render();
			
			Textbox.update();
			
			switch(Textbox.isAnswerCorrect()) {
				case 0: // incorrect answer has been provided
					Textbox.setAnswerCorrect(-1); // reset the question's status to unanswered so the player can try again. Will be removed if player is only permitted one answer.
					System.out.println("WRONG!");
					// additional handling here
					break;
				case 1: // correct answer has been provided
					Textbox.toggleActive();
					Textbox.reset();
					System.out.println("Answer is correct!!");
					// additional handling here	
				default: // no answer has been provided
					break;
				
			}
			
			Textbox.render();
			
			//Update the display
			Display.update();
			Display.sync(60);
		}
	}
	
	//Destroy the opengl context
	public void destroyOpenGL() {
		Display.destroy();
	}
	
	//Main static method, entry point
	public static void main(String[] args) {
		new QuizQuest();
	}
}