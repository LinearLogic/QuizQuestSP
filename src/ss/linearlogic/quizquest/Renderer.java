package ss.linearlogic.quizquest;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

public class Renderer {
	//Render a white rectangle
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
	
	//Render a colored rectangle
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
	
	//Render a rectangle with a texture object
	public static void RenderTexturedRectangle(double x, double y,
			double w, double h, 
			Texture texture) {
		RenderTexturedRectangle(x, y, w, h, texture, 0.0, 0.0, 1.0, 1.0);
	}
	
	//Draw a textured rectangle with a texture object
	//along with texture coordinates on the texture.
	public static void RenderTexturedRectangle(double x, double y,
			double w, double h,
			Texture texture,
			double texX, double texY, 
			double texW, double texH) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		
		glBegin(GL_TRIANGLE_STRIP);
			glTexCoord2d(texX, texY);
			glVertex2d(x, y);
			glTexCoord2d(texX + texW, texY);
			glVertex2d(x + w, y);
			glTexCoord2d(texX, texY + texH);
			glVertex2d(x, y + h);
			glTexCoord2d(texX + texW, texY + texH);
			glVertex2d(x + w, y + h);
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
}
