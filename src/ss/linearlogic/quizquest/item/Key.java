package ss.linearlogic.quizquest.item;

import ss.linearlogic.quizquest.entity.Door;

/**
 * Represents a Key item. When used, the key attempts to unlock the supplied Door entity.
 * It only succeeds if the key and door share the same lockID.
 */
public class Key extends Item {
	
	/**
	 * The value shared by a corresponding key and door.  Any key can open any door that shares its lockID value.
	 */
	private int lockID;
	
	/**
	 * Simple constructor
	 * <p />
	 * Item count (the second param in the complete constructor) defaults to 1,
	 * and is passed into the complete constructor along with the supplied lockID value.
	 * 
	 * @param lockID The key's lockID ({@link #lockID})
	 */
	public Key(int lockID) {
		this(lockID, 1);
	}
	
	/**
	 * Complete constructor
	 * <p />
	 * Constructs the Item superclass with the typeID of the Key item (1) and the supplied item count value.
	 * Also sets the lockID to the supplied value.
	 * 
	 * @param lockID The key's lockID ({@link #lockID})
	 * @param count
	 */
	public Key(int lockID, int count) {
		super(1, count);
		this.lockID = lockID;
	}
	
	/**
	 * Attempts to unlock the specified door and reduces the key count by one if successful.
	 * <p />
	 * Attempts to open the door the supplied Door entity by calling the {@link Door#open(int)} method
	 * with the key's lockID as input. If the method returns true, they key count is reduced by 1.
	 * 
	 * @param door The door to attempt to open using the key
	 */
	public void use(Door door) {
		if (door.open(this.lockID)) { // door was successfully unlocked and opened, decrease the key count
			setCount(getCount() - 1);
			// inform player that door was successfully opened
			return;
		}
		// inform player that the door could not be opened
	}
	
	/**
	 * Returns the lockID, which is shared by the door the key unlocks when used.
	 * 
	 * @return lockID the key's lockID value ({@link #lockID})
	 */
	public int getlockID() { return this.lockID; }
	
	public void setLockID(int id) { lockID = id; }
}
