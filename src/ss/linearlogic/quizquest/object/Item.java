package ss.linearlogic.quizquest.item;

/**
 * Item superclass
 * <p />
 * Represents an item that can be stored in the player's inventory and then used
 */
public abstract class Item {

	/**
	 * Name of the type of item (eg. "Key", "Spell")
	 */
	private String type;
	
	/**
	 * Item ID value (0 for null, 1 for Key, 2 for Potion, 3 for Spell)
	 */
	private int typeID;
	
	/**
	 * Number of instances of this item. Used for 
	 */
	private int count;
	
	/**
	 * Constructs an Item object with the supplied typeID and count values.
	 * 
	 * @param typeID Item ID value
	 * @param count Number of times the item can be used (NOT the number of Item objects with a certain ID value)
	 */
	public Item(int typeID, int count) {
		switch(typeID)
		{
			case 1:
				this.type = "Key";
				break;
			case 2:
				this.type = "Potion";
				break;
			case 3:
				this.type = "Spell";
				break;
			default: // invalid typeID
				System.err.println("Error constructing Item - invalid typeID value supplied (only 1-3 are acceptable).");
				this.type = "";
				this.typeID = 0;
				this.count = 0;
				return;
		}
		// typeID is valid, proceed with object construction
		this.typeID = typeID;
		this.count = count;
		return;
	}
	
	/**
	 * Executes the run method defined in each Item subclass.
	 * <p />
	 * This abstract method is defined in full in each Item subclass, as each operates in a unique manner.
	 * All forms of the use() method will, however, decrease the item's count by 1.
	 */
	public abstract void use();
	
	/**
	 * Returns the item type
	 * 
	 * @return Item type (a string)
	 */
	public String getType() { return this.type; }
	
	/**
	 * Returns the item typeID
	 * 
	 * @return Item typeID (an integer, 1-3)
	 */
	public int getTypeID() { return this.typeID; }
	
	/**
	 * Returns the number of times the specific item can be used
	 * 
	 * @return Item count (an integer)
	 */
	public int getCount() { return this.count; }
	
	/**
	 * Sets the item count to the supplied value
	 * 
	 * @param count
	 */
	public void setCount(int count) { this.count = count; }
}