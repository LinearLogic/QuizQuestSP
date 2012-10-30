package ss.linearlogic.quizquest.entity;

import ss.linearlogic.quizquest.entity.Entity;
import ss.linearlogic.quizquest.item.Item;
import ss.linearlogic.quizquest.textbox.Textbox;
import ss.linearlogic.quizquest.textbox.question.Question;
import ss.linearlogic.quizquest.textbox.question.QuestionManager;

/**
 * Represents an enemy entity. The player engages in question-based combat with the enemy upon coming into contact with the enemy.
 */
public class Enemy extends Entity {

	/**
	 * The amount to decrease the player's health by when attacked by the enemy (i.e. when a question is answered incorrectly)
	 */
	private int damage;
	
	/**
	 * The enemy's current health (the enemy is defeated when this value reaches 0)
	 */
	private int health;
	
	/**
	 * The enemy's health cap (and its starting health)
	 */
	private final int maxHealth;
	
	/**
	 * The item the enemy drops (adds to the player's inventory) on death
	 */
	private Item itemToDrop;
	
	/**
	 * The identitfication number which corresponds for the question which the enemy is set to ask
	 */
	private int questionID;
	
	/**
	 * Simple constructor: calls the complete constructor using the supplied damage and maxHealth, and with a null Item subclass.
	 * @param damage The enemy's {@link #damage} value
	 * @param maxHealth The enemy's {@link #maxHealth} value
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Enemy(int damage, int maxHealth, int x, int y) {
		this(damage, maxHealth, null, x, y, 0);
	}
	
	/**
	 * Complete constructor: constructs the Entity superclass with the Enemy's typeID (4),
	 * and sets the enemy's damage, maxHealth and itemDrop values to the provided values.
	 * 
	 * @param damage The enemy's {@link #damage} value
	 * @param maxHealth The enemy's {@link #maxHealth} value
	 * @param itemToDrop The item the enemy will drop on death (see {@link #itemToDrop}
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Enemy(int damage, int maxHealth, Item itemToDrop, int x, int y, int qID) {
		super(4, x, y);
		this.damage = damage;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.itemToDrop = itemToDrop;
		this.questionID = qID;
	}
		
	/**
	 * Triggers the enemy to ask a question
	 */
	public void triggerQuestion() {
		Question question = QuestionManager.getQuestionForQID(this.questionID);
		Textbox.loadQuestion(question);
	}
	
	/**
	 * Update method which updates the logic of the enemy
	 */
	
	//Getters and setters
	/**
	 * @return The enemy's {@link #damage} value
	 */
	public int getDamage() { return this.damage; }
	
	/**
	 * Sets the enemy's {@link #damage} to the supplied integer value
	 * @param damage
	 */
	public void setDamage(int damage) {
		if (damage < 0) return;
		this.damage = damage;
	}
	
	/**
	 * @return The enemy's current {@link #health} value
	 */
	public int getHealth() { return this.health; }
	
	/**
	 * Sets the enemy's {@link #health} to the supplied integer value
	 * @param health
	 */
	public void setHealth(int health) { this.health = health; }
	
	/**
	 * @return The enemy's {@link #maxHealth} value
	 */
	public int getMaxHealth() { return this.maxHealth; }
	
	/**
	 * @return The item the enemy drops on death
	 */
	public Item getItemToDrop() { return this.itemToDrop; }
	
	/**
	 * Sets the item the enemy drops on death ({@link #itemToDrop}) to the provided Item subclass
	 * @param item
	 */
	public void setItemToDrop(Item item) { this.itemToDrop = item; }
}
