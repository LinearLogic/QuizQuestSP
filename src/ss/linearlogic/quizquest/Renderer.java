package ss.linearlogic.quizquest;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Gateway to OpenGL - provides various rendering utilities
 */
public class Renderer {
	
	/**
	 * Red component of window color
	 */
	public static double default_window_r = 0.4;
	
	/**
	 * Green component of window color
	 */
	public static double default_window_g = 0.4;
	
	/**
	 * Blue component of window color
	 */
	public static double default_window_b = 0.4;
	
	/**
	 * Render a white rectangle of the supplied dimensions at the supplied location
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 */
	public static void renderRectangle(double x, double y, 
			double w, double h) {
		renderColoredRectangle(x, y, w, h, 1.0, 1.0, 1.0);
	}
	
	/**
	 * Render a rectangular outline with the provided specifications
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 * @param r Red intensity (must be between 0.0 and 1.0, inclusive)
	 * @param g Green intensity (must be between 0.0 and 1.0, inclusive)
	 * @param b Blue intensity (must be between 0.0 and 1.0, inclusive)
	 */
	public static void renderLinedRectangle(double x, double y, double w, double h, double r, double g, double b) {
		glDisable(GL_TEXTURE_2D);
		
		glColor4d(r, g, b, 1.0);
		
		glBegin(GL_LINE_LOOP);
			glVertex2d(x, y);
			glVertex2d(x + w, y);
			glVertex2d(x + w, y + h);
			glVertex2d(x, y + h);
		glEnd();
		
		glEnable(GL_TEXTURE_2D);
	}
	
	/**
	 * Render a transparent rectangle of the supplied dimensions at the supplied location
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 */
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
	
	/**
	 * Render a colored rectangle of the supplied dimensions and color at the supplied location
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 * @param r Red intensity (must be between 0.0 and 1.0, inclusive)
	 * @param g Green intensity (must be between 0.0 and 1.0, inclusive)
	 * @param b Blue intensity (must be between 0.0 and 1.0, inclusive)
	 */
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
	
	/**
	 * Render a rectangle with a texture object
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 * @param texture The texture object with which to fill the rectangle
	 */
	public static void renderTexturedRectangle(double x, double y,
			double w, double h, 
			Texture texture) {
		renderTexturedRectangle(x, y, w, h, texture, 0.0, 0.0, 1.0, 1.0);
	}
	
	/**
	 * Render a rectangle with a texture object and the location and dimensions of the texture
	 * @param x X-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the rectangle (pixel location within the rendering window)
	 * @param w Width of the rectangle, in pixels
	 * @param h Height of the rectangle, in pixels
	 * @param texture The texture object with which to fill the rectangle
	 * @param texX X-coordinate of the top lefthand corner of the texture (pixel location within the rendering window)
	 * @param texY Y-coordinate of the top lefthand corner of the texture (pixel location within the rendering window)
	 * @param texW Width of the texture, in pixels
	 * @param texH Height of the texture, in pixels
	 */
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
	
	/**
	 * Render a string in black
	 * @param string The contents of the string
	 * @param x X-coordinate of the top lefthand corner of the string (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the string (pixel location within the rendering window)
	 * @param font Font type of the string
	 */
	public static void renderString(String string, double x, double y, TrueTypeFont font) {		
		renderString(string, x, y, font, Color.white);
	}
	
	/**
	 * Render a string in the color supplied
	 * @param string The contents of the string
	 * @param x X-coordinate of the top lefthand corner of the string (pixel location within the rendering window)
	 * @param y Y-coordinate of the top lefthand corner of the string (pixel location within the rendering window)
	 * @param font Font type of the string
	 * @param color The color in which to display the string
	 */
	public static void renderString(String string, double x, double y, TrueTypeFont font, Color color) {
		TextureImpl.bindNone();
		font.drawString((int)x, (int)y, string, color);
	}
	
	/**
	 * Load a TrueType system font with provided font type and size
	 * @param systemFont
	 * @param fontSize
	 * @return The TrueTypeFont loaded
	 */
	public static TrueTypeFont loadSystemFont(String systemFont, int fontSize) {
		Font awtFont = new Font(systemFont, Font.BOLD, fontSize);
		return new TrueTypeFont(awtFont, true);
	}
	
	/**
	 * Load a TrueType font from a .ttf file
	 * @param fontFile The name of the .ttf file
	 * @param fontSize The size of the font
	 * @return The TrueTypeFont loaded
	 */
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
