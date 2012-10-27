package ss.linearlogic.quizquest;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

public class Renderer {
	//Render a white rectangle
	public static void renderRectangle(double x, double y, 
			double w, double h) {
		renderColoredRectangle(x, y, w, h, 1.0, 1.0, 1.0);
	}
	
	public static void renderTransparentRectangle(double x, double y, 
			double w, double h) {
		glDisable(GL_TEXTURE_2D);
		
		glColor4d(1.0, 1.0, 1.0, 0.3);
		
		glBegin(GL_TRIANGLE_FAN);
			glVertex2d(x, y);
			glVertex2d(x + w, y);
			glVertex2d(x + w, y + h);
			glVertex2d(x, y + h);
		glEnd();
		
		glEnable(GL_TEXTURE_2D);
	}
	
	//Render a colored rectangle
	public static void renderColoredRectangle(double x, double y, 
			double w, double h, 
			double r, double g, 
			double b) {
		glDisable(GL_TEXTURE_2D);
		
		glColor3d(r, g, b);
		
		glBegin(GL_TRIANGLE_FAN);
			glVertex2d(x, y);
			glVertex2d(x + w, y);
			glVertex2d(x + w, y + h);
			glVertex2d(x, y + h);
		glEnd();
		
		glEnable(GL_TEXTURE_2D);
	}
	
	//Render a rectangle with a texture object
	public static void renderTexturedRectangle(double x, double y,
			double w, double h, 
			Texture texture) {
		renderTexturedRectangle(x, y, w, h, texture, 0.0, 0.0, 1.0, 1.0);
	}
	
	//Draw a textured rectangle with a texture object
	//along with texture coordinates on the texture.
	public static void renderTexturedRectangle(double x, double y,
			double w, double h,
			Texture texture,
			double texX, double texY, 
			double texW, double texH) {
		glEnable(GL_TEXTURE_2D);
		
		glColor3d(1.0, 1.0, 1.0);
		
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
	
	//Render a string in white
	public static void renderString(String string, double x, double y, TrueTypeFont font) {		
		renderString(string, x, y, font, Color.black);
	}
	
	//Render a string with color
	public static void renderString(String string, double x, double y, TrueTypeFont font, Color colr) {
		TextureImpl.bindNone();
		font.drawString((int)x, (int)y, string, Color.yellow);
	}
	
	
	public static TrueTypeFont loadSystemFont(String systemFont, int fontSize) {
		Font awtFont = new Font(systemFont, Font.BOLD, fontSize);
		return new TrueTypeFont(awtFont, true);
	}
	
	//Load a ttf font file
	public static TrueTypeFont loadFont(String fontFile, int fontSize) { 
		Font fnt = null;
		
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(fontFile);
			fnt = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			fnt.deriveFont((float)fontSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (fnt == null) System.out.println("Unable to load font");
		
		return new TrueTypeFont(fnt, false);
	}
}
