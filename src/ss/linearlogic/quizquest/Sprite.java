package ss.linearlogic.quizquest;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	private Texture texture;
	
	private double x;
	private double y;
	private double w;
	private double h;
	
	//Texture coordinates for opengl, they must be <= 1.0
	private double texX;
	private double texY;
	private double texW;
	private double texH;
	
	//Constructor which loads the texture and sets the positions
	public Sprite(String filename, int x_pos, int y_pos) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
			
			x = x_pos;
			y = y_pos;
			w = texture.getImageWidth();
			h = texture.getImageHeight();
			
			texX = 0.0;
			texY = 0.0;
			texW = 1.0;
			texH = 1.0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Render the sprite
	public void draw() {		
		Renderer.renderTexturedRectangle(x, y, w, h, texture, texX, texY, texW, texH);
	}
	
	//Collision checking with another sprite
	public boolean isCollision(Sprite otherSprite) {
		if (getX() + getTextureWidth() >= otherSprite.getX() &&
			getX() <= otherSprite.getX() + otherSprite.getTextureWidth() &&
			getY() + getTextureHeight() >= otherSprite.getY() &&
			getY() <= otherSprite.getY() + otherSprite.getTextureHeight()) 
			return true;
		return false;
	}
	
	/*
	 * Getters and setter for the following class members
	 * x -> x position
	 * y -> y position
	 * w -> width
	 * h -> height
	 * texX -> start of texture frame X
	 * texY -> start of texture frame Y
	 * texW -> width of texture frame
	 * texH -> height of texture frame
	 */
	
	public double getTexX() {
		return texX;
	}
	
	public double getTexY() {
		return texY;
	}
	
	public double getTexW() {
		return texW;
	}
	
	public double getTexH() {
		return texH;
	}
	
	public void setTextureCoordinates(double x, double y, double w, double h) {
		texX = x;
		texY = y;
		texW = w;
		texH = h;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void setPosition(int x_pos, int y_pos) {
		x = x_pos;
		y = y_pos;
	}
	
	public void setSize(int width, int height) {
		w = width;
		h = height;
	}
	
	public int getTextureWidth() {
		return (int) texture.getImageWidth();
	}
	
	public int getTextureHeight() {
		return (int) texture.getImageHeight();
	}
}
