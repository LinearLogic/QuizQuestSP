package ss.linearlogic.quizquest.item;

import ss.linearlogic.quizquest.player.Player;

/**
 * Represents a potion item. When used, potions restore a set amount of health to the player.
 */
public class Potion extends Item {

	/**
	 * The amount of health the potion restores to the player on use
	 */
	private int healthToRestore;
	
	/**
	 * Simple constructor
	 * <p />
	 * Item count (the second param in the complete constructor) defaults to 1,
	 * and is passed into the complete constructor along with the supplied healthToRestore value.
	 * 
	 * @param healthToRestore see {@link #healthToRestore}
	 */
	public Potion(int healthToRestore) {
		this(healthToRestore, 1);
	}
	
	/**
	 * Complete constructor
	 * <p />
	 * Constructs the Item superclass with the typeID of the Potion item (2) and the supplied item count value.
	 * Also sets healthToRestore to the supplied value.
	 * 
	 * @param healthToRestore See {@link #healthToRestore}
	 * @param count
	 */
	public Potion(int healthToRestore, int count) {
		super(2, count);
		this.healthToRestore = healthToRestore;
	}

	/**
	 * Increases the player's health by the healthToRestore value ({@link #healthToRestore}) of the potion, and decrements the potion item count.
	 */
	public void use() {
		Player.setHealth(Player.getHealth() + this.healthToRestore);
		setCount(getCount() -1);
	}
	
	/**
	 * Increases the player's health by the healthToRestore value ({@link #healthToRestore}) of the potion, and decrements the potion item count.
	 * This constructor version will enable multiplayer handling. Currently written to work with single-player only.
	 * 
	 * @param player The player whose health to restore using the potion
	 */
	public void use(Player player) {
		player.setHealth(Player.getHealth() + this.healthToRestore);
		setCount(getCount() - 1);
	}
	
	/**
	 * Returns the amount of health the potion restores on use.
	 * 
	 * @return The healthToRestore value ({@link #healthToRestore}) of the potion
	 */
	public int getHealthToRestore() {
		return this.healthToRestore;
	}
	
	/**
	 * Sets the amount of health the potion restores on use ({@link #healthToRestore})
	 * 
	 * @param healthToRestore
	 */
	public void setHealthToRestore(int healthToRestore) {
		this.healthToRestore = healthToRestore;
	}
}