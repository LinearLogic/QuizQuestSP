package ss.linearlogic.quizquest;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

public class Renderer {
	public static void RenderRectangle(double x, double y, 
			double w, double h) {
		glColor3f(1.0f, 1.0f, 1.0f);
		
		glBegin(GL_TRIANGLE_FAN);
			glVertex2d(x, y);
			glVertex2d(x + w, y);
			glVertex2d(x + w, y + h);
			glVertex2d(x, y + h);
		glEnd();
	}
	
	public static void RenderColoredRectangle(double x, double y, 
			double w, double h, 
			double r, double g, 
			double b) {
		glColor3d(r, g, b);
		
		glBegin(GL_TRIANGLE_FAN);
			glVertex2d(x, y);
			glVertex2d(x + w, y);
			glVertex2d(x + w, y + h);
			glVertex2d(x, y + h);
		glEnd();
	}
	
	public static void RenderTexturedRectangle(double x, double y,
			double w, double h, 
			Texture texture) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		
		RenderRectangle(x, y, w, h);
	}
}
