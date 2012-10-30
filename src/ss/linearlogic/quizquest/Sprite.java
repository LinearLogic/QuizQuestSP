package ss.linearlogic.quizquest;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	
	/**
	 * Texture object of the sprites (provides its appearance and dimensions)
	 */
	private Texture texture;
	
	/**
	 * X distance of the sprite, in pixels, from the top lefthand corner of the current quadrant window (NOT the full map)
	 */
	private double x;
	
	/**
	 * Y distance of the sprite, in pixels, from the top lefthand corner of the current quadrant window (NOT the full map)
	 */
	private double y;
	
	/**
	 * Width, in pixels, of the sprite
	 */
	private double w;
	
	/**
	 * Height, in pixels, of the sprite
	 */
	private double h;
	
	/**
	 * Texture x-coordinate for use by OpenGL; must be <= 1.0
	 */
	private double texX;
	
	/**
	 * Texture x-coordinate for use by OpenGL; must be <= 1.0
	 */
	private double texY;
	
	/**
	 * Texture width for use by OpenGL; must be <= 1.0
	 */
	private double texW;
	
	/**
	 * Texture height for use by OpenGL; must be <= 1.0
	 */
	private double texH;
	
	/**
	 * Constructs the Sprite object with the supplied image file for the Texture,
	 * and the supplied image file for the {@link #x}- and {@link #y}-coordinates
	 * 
	 * @param filename File from which to load the texture object for the sprite
	 * @param x {@link #x}-coordinate
	 * @param y {@link #y}-coordinate
	 */
	public Sprite(String filename, int x, int y) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
			
			this.x = x;
			this.y = y;
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
	
	/**
	 * Renders the sprite
	 */
	public void draw() {		
		Renderer.renderTexturedRectangle(x, y, w, h, texture, texX, texY, texW, texH);
	}
	
	/**
	 * Checks for a collision with the supplied sprite
	 * @param otherSprite
	 * @return Whether or not the two sprites have collided
	 */
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
	
	/**
	 * @return The sprite's {@link #texX} value
	 */
	public double getTexX() {
		return texX;
	}
	
	/**
	 * @return The sprite's {@link #texY} value
	 */
	public double getTexY() {
		return texY;
	}
	
	/**
	 * @return The sprite's {@link #texW} value
	 */
	public double getTexW() {
		return texW;
	}
	
	/**
	 * @return The sprite's {@link #texH} value
	 */
	public double getTexH() {
		return texH;
	}
	
	/**
	 * Set the texture's coordinates and dimensions to the provided values
	 * @param x The texture's new {@link #texX} value
	 * @param y The texture's new {@link #texY} value
	 * @param w The texture's new {@link #texW} value
	 * @param h The texture's new {@link #texH} value
	 */
	public void setTextureCoordinates(double x, double y, double w, double h) {
		texX = x;
		texY = y;
		texW = w;
		texH = h;
	}
	
	/**
	 * The sprite's {@link #x}-coordinate
	 */
	public int getX() {
		return (int) x;
	}
	
	/**
	 * @return The sprite's {@link #y}-coordinate
	 */
	public int getY() {
		return (int) y;
	}
	
	/**
	 * Sets the sprite's {@link #x}- and {@link #y}-coordinates to the supplied values
	 * @param x_pos
	 * @param y_pos
	 */
	public void setPosition(int x_pos, int y_pos) {
		x = x_pos;
		y = y_pos;
	}
	
	/**
	 * Sets the sprite's {@link #w}idth and {@link #h}eight to the supplied values
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		w = width;
		h = height;
	}
	
	/**
	 * @return The sprite's texture width, in pixels (NOT the OpenGL width, {@link #texW})
	 */
	public int getTextureWidth() {
		return (int) texture.getImageWidth();
	}
	
	/**
	 * @return The sprite's texture height, in pixels (NOT the OpenGL height, {@link #texH})
	 */
	public int getTextureHeight() {
		return (int) texture.getImageHeight();
	}
}
