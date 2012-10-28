package ss.linearlogic.quizquest.player;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Map;
import ss.linearlogic.quizquest.Sprite;
import ss.linearlogic.quizquest.Textbox;

public class Player {
	
	// Non-graphical
	/**
	 * The player's health value
	 * <p />
	 * Health is increased by using health potions and decreased by being damaged by enemies.
	 * If health reaches 0, the player is respawned and loses a life.
	 */
	private static int health;
	
	/**
	 * The player's health cap value.
	 * <p />
	 * Health can never be greater than this value,
	 * and health is set to this value when the player respawns.
	 */
	private static int maxHealth;
	
	/**
	 * The number of lives the player has left until Game Over.
	 */
	private static int lives;
	
	// Graphical
	private static double speed_x;
	private static double speed_y;
	
	private final static double speed_constant = 5.0;
	
	//Coordinates of the player in the world, not for rendering
	private static int world_coordinates_x = 0;
	private static int world_coordinates_y = 0;
	
	private static Sprite sprite;

	public static void initialize(int start_x, int start_y, String player_image) {
		sprite = new Sprite(player_image, start_x, start_y);
		
		world_coordinates_x = start_x;
		world_coordinates_y = start_y;
	}
	
	public static void update() {
		//Reset after each frame
		speed_x = 0;
		speed_y = 0;
		
		/*
		 * Check the keys that are being pressed which determine the player movement
		 * 
		 * NOTE: the new system operates by adding to or subtracting from the current x or y speed,
		 * and then evaluating the resulting speeds. If there is a speed in both the x and y direction,
		 * the speeds are divided by the square root of 2, to prevent a speed increase when moving diagonally.
		 */
		if (!Textbox.isActive()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				speed_y -= speed_constant;
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				speed_y += speed_constant;
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				speed_x -= speed_constant;
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				speed_x += speed_constant;
			if ((speed_x != 0) && (speed_y != 0)) { // player is moving too quickly - adjust speeds
				speed_x *= (1/Math.sqrt(2.0));
				speed_y *= (1/Math.sqrt(2.0));
			}
		}
				
		//Handle if the player goes to another quadrant
		handleOffScreen();
		
		//Set the position of the sprite(Player)
		setPosition((int)(world_coordinates_x + speed_x), (int)(world_coordinates_y + speed_y));
		sprite.setPosition(world_coordinates_x - (Map.getCoordinateShiftX()), world_coordinates_y - (Map.getCoordinateShiftY()));
	}
	
	//Set the world coordinates
	public static void setPosition(int x, int y) {
		world_coordinates_x = x;
		world_coordinates_y = y;
	}
	
	//Get the world coordinates
	public static int getWorldX() { return world_coordinates_x; }
	
	//Get the world coordinates
	public static int getWorldY() { return world_coordinates_y; }
	
	private static void handleOffScreen() {	
		//Check if player is out of the world on the left
		if (world_coordinates_x - 5 < 0)
			if (speed_x < 0)
				speed_x = 0;
		if (world_coordinates_y - 5 < 0)
			if (speed_y < 0)
				speed_y = 0;
		
		//Check if player is out of the world on the right
		if (world_coordinates_x + getSpriteWidth() > (480 * Map.getQuadrantWidth()))
			if (speed_x > 0)
				speed_x = 0;
		if (world_coordinates_y + getSpriteHeight() > (480 * Map.getQuadrantWidth()))
			if (speed_y > 0)
				speed_y = 0;
		
		//Check if the player can move to another quadrant
		if (world_coordinates_x > (480 * (Map.getCurrentQuadrantX() + 1))) Map.setCurrentQuadrantX(Map.getCurrentQuadrantX() + 1);
		if (world_coordinates_y > (480 * (Map.getCurrentQuadrantY() + 1))) Map.setCurrentQuadrantY(Map.getCurrentQuadrantY() + 1);
		if (world_coordinates_x < (480 * Map.getCurrentQuadrantX())) Map.setCurrentQuadrantX(Map.getCurrentQuadrantX() - 1);
		if (world_coordinates_y < (480 * Map.getCurrentQuadrantY())) Map.setCurrentQuadrantY(Map.getCurrentQuadrantY() - 1);
	}
	
	//Get the player's rendering position X
	public static int getSpriteX() { return sprite.getX(); }
	
	//Get the player's rendering position Y
	public static int getSpriteY() { return sprite.getY(); }
	
	//Get the player's width
	public static int getSpriteWidth() { return sprite.getTextureWidth(); }
	
	//Get the players height
	public static int getSpriteHeight() { return sprite.getTextureHeight(); }
	
	//Returns the entityMap x-coord of the top lefthand corner of the player's sprite
	public static int getMapX() { return world_coordinates_x/Map.getTileSize(); }
	
	//Returns the entityMap y-coord of the top lefthand corner of the player's sprite
	public static int getMapY() { return world_coordinates_y/Map.getTileSize(); }
	
	//Draw the player
	public static void render() { sprite.draw(); }
	
	// --- // Non-graphical methods // --- //
	/**
	 * Respawns the player, decreasing their life count and handling it appropriately
	 */
	public static void respawn() {
		// Dispatch message to player's HUD
		lives--;
		if (lives == 0) {
			// Game Over sequence here...
		}
		setHealth(maxHealth);
	}
	
	/**
	 * Returns the player's health (an integer value)
	 * 
	 * @return health of the player ({@link #health})
	 */
	public static int getHealth() {
		return health;
	}
	
	/**
	 * Sets the player's health to the supplied value
	 * 
	 * @param newHealth The value to set the player's health to
	 */
	public static void setHealth(int newHealth) {
		health = newHealth;
		if (health > maxHealth)
			health = maxHealth;
		if (health < 0)
			health = 0;
	}
	
	/**
	 * Returns the player's health cap (an integer value)
	 * 
	 * @return The maximum health a player can have ({@link #maxHealth})
	 */
	public static int getMaxHealth() { return maxHealth; }
	
	/**
	 * Sets te player's health cap to the supplied value
	 * 
	 * @param newMaxHealth The value to set the player's maxHealth value to
	 */
	public static void setMaxHealth(int newMaxHealth) {
		if (newMaxHealth <= 0) {
			System.err.println("Failed to change maxHealth of the Player - new maxHealth value must be greater than zero.");
			return;
		}
		maxHealth = newMaxHealth;
	}
	
	/**
	 * Returns the player's life count (an integer value)
	 * 
	 * @return the number of lives the Player has remaining ({@link #lives})
	 */
	public static int getLives() { return lives; }
	
	/**
	 * Sets the player's life count to the supplied value
	 * 
	 * @param newHealth The value to set the player's life count to
	 */
	public static void setLives(int newLives) { lives = newLives; }
}