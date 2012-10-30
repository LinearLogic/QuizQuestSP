package ss.linearlogic.quizquest.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Map;
import ss.linearlogic.quizquest.Renderer;
import ss.linearlogic.quizquest.Sprite;
import ss.linearlogic.quizquest.Textbox;
import ss.linearlogic.quizquest.entity.Door;
import ss.linearlogic.quizquest.entity.Enemy;
import ss.linearlogic.quizquest.entity.Entity;
import ss.linearlogic.quizquest.entity.Grass;

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
	
	/**
	 * The current enemy the player is fighting or null representing that the player is not battling at the moment
	 */
	private static Enemy battleFoe;
	
	/**
	 * A flag which holds whether the HUD is visible or not
	 */
	private static boolean HUDActive;
	
	// Graphical
	private static double speed_x;
	private static double speed_y;
	
	private final static double speed_constant = 5.0;
	
	//Coordinates of the player in the world, not for rendering
	private static int world_coordinates_x = 0;
	private static int world_coordinates_y = 0;
	
	private static Sprite sprite;
	
	private static ArrayList<Sprite> hearts = new ArrayList<Sprite>();
	
	/**
	 * Whether or not a key is depressed (used to prevent repeat event spam)
	 */
	public static boolean keyLifted = true;

	public static void initialize(int start_x, int start_y, String player_image) {
		sprite = new Sprite(player_image, start_x, start_y);
		
		world_coordinates_x = start_x;
		world_coordinates_y = start_y;
		
		HUDActive = true;
	}
	
	public static void update() {		
		if (battleFoe != null) {
			switch (Textbox.isAnswerCorrect()) {
			case 0: //Not correct
				//Respond appropriately
				setHealth(getHealth() - battleFoe.getDamage());
				Textbox.setAnswerCorrect(-1);
				
				if (getHealth() <= 0)
					respawn();
				
				break;
			case 1:
				Map.removeEnemy(battleFoe);
				Map.addEntity(new Grass(battleFoe.getX(), battleFoe.getY()));
				
				Inventory.addItem(Inventory.getItemCount(), battleFoe.getItemToDrop());
				Textbox.reset();
			default: // No answer yet
				break;
			}
		}
		
		if (Textbox.isActive() || Inventory.isActive()) return;
		
		//Reset after each frame
		speed_x = 0;
		speed_y = 0;
		
		/*
		 * Check the keys that are being pressed which determine the player movement. Toggle the inventory window if 'I' is pressed.
		 * 
		 * NOTE: the new system operates by adding to or subtracting from the current x or y speed,
		 * and then evaluating the resulting speeds. If there is a speed in both the x and y direction,
		 * the speeds are divided by the square root of 2, to prevent a speed increase when moving diagonally.
		 */
		if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_I))
			keyLifted = true;
		if (keyLifted && Keyboard.isKeyDown(Keyboard.KEY_I)) { //Toggles the inventory window
			Inventory.toggleActive();
			keyLifted = false;
		}
		
		if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_H)) 
			keyLifted = true;
		if (keyLifted && Keyboard.isKeyDown(Keyboard.KEY_H)) 
			HUDActive = !HUDActive;
			
		
		if (!Textbox.isActive() && !Inventory.isActive()) {
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
		
		//Handle collisions with objects through which the player cannot pass
		handleCollision();
		
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
	
	private static void fightFoe(Enemy foe) {
		battleFoe = foe;
		foe.triggerQuestion();
	}
	
	/**
	 * Makes sure the player cannot move into barrier entities (walls, doors, enemies).
	 * To sum up what's going on in this method, a virtual rectangle is constructed around the player and the vertices of that
	 * rectangle are checked for collisions. This fixes a critical movement glitch caused by the previous collision handling system.
	 */
	private static void handleCollision() {
		int mapX = getMapX();
		int mapY = getMapY();
		//Check the top lefthand corner of the virtual rectangle around the player sprite
		if ((mapX > 0) && (mapY > 0)) {
			int newWorldX = world_coordinates_x - 5;
			int newWorldY = world_coordinates_y - 5;
			//Handle the entity to the left
			Entity e = Map.getEntity(newWorldX/Map.getTileSize(), world_coordinates_y/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_x < 0)
					speed_x = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
			//Handle the entity above
			e = Map.getEntity(world_coordinates_x/Map.getTileSize(), newWorldY/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_y < 0)
					speed_y = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
		}
		//Check the top righthand corner of the virtual rectangle
		if ((mapX < Map.getWidth() - 2) && (mapY > 0)) {
			int newWorldX = world_coordinates_x + sprite.getTextureWidth() + 5;
			int newWorldY = world_coordinates_y - 5;
			//Handle the entity to the right
			Entity e = Map.getEntity(newWorldX/Map.getTileSize(), world_coordinates_y/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_x > 0)
					speed_x = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
			//Handle the entity above
			e = Map.getEntity((world_coordinates_x + sprite.getTextureWidth())/Map.getTileSize(), newWorldY/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_y < 0)
					speed_y = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
		}
		//Check the bottom lefthand corner of the virtual rectangle
		if ((mapX > 0) && (mapY < Map.getHeight() - 2)) {
			int newWorldX = world_coordinates_x - 5;
			int newWorldY = world_coordinates_y + sprite.getTextureHeight() + 5;
			//Handle the entity to the right
			Entity e = Map.getEntity(newWorldX/Map.getTileSize(), (world_coordinates_y + sprite.getTextureHeight())/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_x < 0)
					speed_x = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
			//Handle the entity above
			e = Map.getEntity(world_coordinates_x/Map.getTileSize(), newWorldY/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_y > 0)
					speed_y = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
		}
		//Check the bottom righthand corner of the virtual rectangle
		if ((mapX < Map.getWidth() - 2) && (mapY < Map.getHeight() - 2)) {
			int newWorldX = world_coordinates_x + sprite.getTextureWidth() + 5;
			int newWorldY = world_coordinates_y + sprite.getTextureHeight() + 5;
			//Handle the entity to the right
			Entity e = Map.getEntity(newWorldX/Map.getTileSize(), (world_coordinates_y + sprite.getTextureHeight())/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_x > 0)
					speed_x = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
			//Handle the entity above
			e = Map.getEntity((world_coordinates_x + sprite.getTextureWidth())/Map.getTileSize(), newWorldY/Map.getTileSize());
			if (e.getTypeID() > 1) { //Entity is a barrier entity
				if (speed_y > 0)
					speed_y = 0;
				if (e.getTypeID() == 3) { //Door
					((Door) e).forceOpen();
					System.out.println("Door unlocked!");
				}
				else if (e.getTypeID() == 4) { //Enemy
					//Handle combat
					Enemy enemy = (Enemy)e;
					fightFoe(enemy);
					
					return;
				}
			}
		}
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
	public static void render() { 
		if (HUDActive) {
			Renderer.renderColoredRectangle(0, 0, 480, 20, 0.7, 0.7, 0.7);
			double r = 1.0;
			double g = 1.0;
		
			if ((double)getHealth()/(double)getMaxHealth() > 0.5)
				r = 0.0;
			else
				g = 0.0;
						
			Renderer.renderColoredRectangle(20, 5, 100 * ((double)getHealth()/(double)getMaxHealth()), 10, r, g, 0.0);
			Renderer.renderLinedRectangle(20, 5, 100, 10, 0.0, 0.0, 0.0);
			
			for (int i = 0; i < hearts.size(); ++i) 
				hearts.get(i).draw();
		}
		
		sprite.draw(); 
	}
	
	// --- // Non-graphical methods // --- //
	/**
	 * Respawns the player, decreasing their life count and handling it appropriately
	 */
	public static void respawn() {
		// Dispatch message to player's HUD
		if (getLives() > 1) setLives(getLives() - 1);
		else if (lives == 1) {
			// Game Over sequence here...
			System.exit(0);
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
	public static void setLives(int newLives) {
		if (newLives < 1) {
			System.err.println("Error while setting player's remaining life count: count must be greater than zero.");
			return;
		} else if (newLives > 10) {
			System.err.println("Error while setting player's remainging life count: count must be less than 11.");
			return;
		}
		
		hearts.clear();
				
		for (int i = 0; i < newLives; ++i) {
			Sprite sprite = new Sprite("heart.png", (i * (25)) + 240, 2);
			hearts.add(sprite);
		}
		
		lives = newLives;
	}
}
