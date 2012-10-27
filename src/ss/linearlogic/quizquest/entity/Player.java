package ss.linearlogic.quizquest.entity;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Map;
import ss.linearlogic.quizquest.Sprite;

public class Player {
	private static double speed_x;
	private static double speed_y;
	
	private final static double speed_constant = 5.0;
	
	private static Sprite sprite;

	public static void Initialize(int start_x, int start_y, String player_image) {
		sprite = new Sprite(player_image, start_x, start_y);
	}
	
	public static void Update() {
		//Reset after each frame
		speed_x = 0;
		speed_y = 0;
		
		//Check the keys that are being pressed which determine the player movement
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) speed_y = -speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) speed_y = speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) speed_x = -speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) speed_x = speed_constant;
		
		//Handle if the player goes to another quadrant
		handleOffscreen();
		
		//Set the position of the sprite(Player)
		sprite.setPosition((int)(sprite.getX() + speed_x), (int)(sprite.getY() + speed_y));
	}
	
	private static void handleOffscreen() {
		if (sprite.getX() > 480 + (Map.getCurrentQuadrant() * 20 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() + 1);
		if (sprite.getY() > 480 + (Map.getCurrentQuadrant() * 20 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() + Map.getQuadrantWidth());
		if (sprite.getX() < 0 + (Map.getCurrentQuadrant() * 20 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() - 1);
		if (sprite.getY() < 0 + (Map.getCurrentQuadrant() * 20 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() - Map.getQuadrantWidth());
	}
	
	public static int getX() {
		return sprite.getX();
	}
	
	public static int getY() {
		return sprite.getY();
	}
	
	public static int getWidth() {
		return sprite.getTextureWidth();
	}
	
	public static int getHeight() {
		return sprite.getTextureHeight();
	}
	
	public static void Render() {
		sprite.Draw();
	}
}