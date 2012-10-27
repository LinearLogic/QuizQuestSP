package ss.linearlogic.quizquest.entity;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Map;
import ss.linearlogic.quizquest.Sprite;

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
		if (sprite.getX() < 0 + (Map.getCurrentQuadrant() * 19 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() - 1);
		if (sprite.getY() < 0 + (Map.getCurrentQuadrant() * 19 * 32)) Map.setCurrentQuadrant(Map.getCurrentQuadrant() - Map.getQuadrantWidth());
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
	}
	
	/**
	 * Returns the player's health cap (an integer value)
	 * 
	 * @return The maximum health a player can have ({@link #maxHealth})
	 */
	public static int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Sets te player's health cap to the supplied value
	 * 
	 * @param newMaxHealth The value to set the player's maxHealth value to
	 */
	public static void setMaxHealth(int newMaxHealth) {
		maxHealth = newMaxHealth;
	}
	
	/**
	 * Returns the player's life count (an integer value)
	 * 
	 * @return the number of lives the Player has remaining ({@link #lives})
	 */
	public static int getLives() {
		return lives;
	}
	
	/**
	 * Sets the player's life count to the supplied value
	 * 
	 * @param newHealth The value to set the player's life count to
	 */
	public static void setLives(int newLives) {
		lives = newLives;
	}
}