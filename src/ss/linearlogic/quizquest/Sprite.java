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
	
	//Constructor which loads the texture and sets the positions
	public Sprite(String filename, int x_pos, int y_pos) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
			
			x = x_pos;
			y = y_pos;
			w = texture.getImageWidth();
			h = texture.getImageHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Render the sprite
	public void Draw() {
		Renderer.RenderTexturedRectangle(x, y, w, h, texture);
	}
	
	/*
	 * Getters and setter for the following class members
	 * x -> x position
	 * y -> y position
	 * w -> width
	 * h -> height
	 */
	
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
