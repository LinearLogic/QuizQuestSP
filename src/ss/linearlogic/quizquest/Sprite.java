package ss.linearlogic.quizquest;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	private Texture texture;
	
	public double x;
	public double y;
	public double w;
	public double h;
	
	public Sprite(String filename) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Draw() {
		Renderer.RenderTexturedRectangle(x, y, w, h, texture);
	}
}
