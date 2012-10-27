package ss.linearlogic.quizquest.entity;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Sprite;

public class Player {
	private double speed_x;
	private double speed_y;
	
	private final double speed_constant = 5.0;
	
	private Sprite sprite;
	
	public Player(int start_x, int start_y, String player_image) {
		sprite = new Sprite(player_image, start_x, start_y);
	}
	
	public void Update() {
		//Reset after each frame
		speed_x = 0;
		speed_y = 0;
		
		//Check the keys that are being pressed which determine the player movement
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) speed_y = -speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) speed_y = speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) speed_x = -speed_constant;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) speed_x = speed_constant;
		
		//Set the position of the sprite(Player)
		sprite.setPosition((int)(sprite.getX() + speed_x), (int)(sprite.getY() + speed_y));
	}
	
	public void Render() {
		sprite.Draw();
	}
}