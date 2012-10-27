package ss.linearlogic.quizquest.item;

import ss.linearlogic.quizquest.entity.Player;

public class Potion extends Item {

	/**
	 * The amount of health the potion restores to the player on use
	 */
	private int hpToRestore;
	
	/**
	 * Simple constructor
	 * <p />
	 * Item count (the second param in the complete constructor) defaults to 1,
	 * and is passed into the complete constructor along with the supplied hpToRestore value.
	 * 
	 * @param hpToRestore see {@link #hpToRestore}
	 */
	public Potion(int hpToRestore) {
		this(hpToRestore, 1);
	}
	
	/**
	 * Complete constructor
	 * <p />
	 * Constructs the Item superclass with the typeID of the Potion item (2) and the supplied item count value.
	 * Also sets hpToRestore to the supplied value.
	 * 
	 * @param hpToRestore see {@link #hpToRestore}
	 * @para count
	 */
	public Potion(int hpToRestore, int count) {
		super(2, count);
		this.hpToRestore = hpToRestore;
	}

	public void use() {
		Player.setHealth(this.hpToRestore);
	}

}