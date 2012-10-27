package ss.linearlogic.quizquest.item;

import ss.linearlogic.quizquest.entity.Enemy;

/**
 * Represents a spell item.
 * When used, spells deal damage to the entity specified (typically the entity the player is in combat with).
 */
public class Spell extends Item {

	/**
	 * The amount of damage the spell deals to the enemy (i.e. the amount by which to decrement the health of the enemy)
	 */
	private int damage;
	
	/**
	 * Simple constructor
	 * <p />
	 * Item count (the second param in the complete constructor) defaults to 1,
	 * and is passed into the complete constructor along with the supplied damage value.
	 * 
	 * @param damage See {@link #damage}
	 */
	public Spell(int damage) {
		this(damage, 1);
	}
	
	/**
	 * Complete constructor
	 * <p />
	 * Constructs the Item superclass with the typeID of the spell item (2) and the supplied item count value.
	 * Also sets damage to the supplied value.
	 * 
	 * @param damage See {@link #damage}
	 * @param count
	 */
	public Spell(int damage, int count) {
		super(3, count);
		this.damage = damage;
	}

	/**
	 * Reduces the supplied enemy's health by the damage value ({@link #damage}) of the spell, and decrements the spell item count.
	 * 
	 * @param enemy The enemy to use the spell on
	 */
	public void use(Enemy enemy) {
		// damage the enemy - to be filled in once entity repackaging has been completed.
		setCount(getCount() - 1);
	}
	
	/**
	 * Returns the amount of damage the spell deals on use.
	 * 
	 * @return The damage value ({@link #damage}) of the spell
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * Sets the amount of damage the spell deals on use ({@link #damage})
	 * 
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
