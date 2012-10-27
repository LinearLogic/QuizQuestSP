package ss.linearlogic.quizquest;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class QuizQuest {
	
	private static boolean running = true;
	
	private final int screen_width = 640;
	private final int screen_height = 480;
	
	private long startTime = 0;
	private long endTime = 0;
	private long deltaTime = 0;
	
	public QuizQuest() {
		InitializeOpenGL(screen_width, screen_height);
		MainLoop();
		DestroyOpenGL();
	}
	
	public void InitializeOpenGL(int width, int height) {
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
	}
	
	public void MainLoop() {
		while (running) {
			running = !Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
				
			startTime = getTime();
			deltaTime = startTime - endTime;
			
			//Game rendering/logic area
			glClear(GL_COLOR_BUFFER_BIT);
			Renderer.RenderRectangle(10, 10, 20, 20);
			
			Display.update();
			Display.sync(60);
			
			endTime = getTime();
		}
	}
	
	public void DestroyOpenGL() {
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new QuizQuest();
	}

	private long getTime() {
		return (long)(1000 * Sys.getTime() / Sys.getTimerResolution());
	}
}