package ss.linearlogic.quizquest.entity;

import ss.linearlogic.quizquest.entity.Entity;

public class Door extends Entity {

	/**
	 * The value shared by a corresponding key and door.  Any key can open any door that shares its lockID value.
	 */
	private int lockID;
	
	/**
	 * Whether the door is open and can be passed through
	 */
	private boolean isOpen;
	
	/**
	 * Constructs the Entity superclass using the Door typeID (1), and sets the door's lockID to the supplied value.
	 * @param lockID The door's lockID (see {@link #lockID})
	 */
	public Door(int lockID) {
		super(1);
		this.lockID = lockID;
	}

	/**
	 * Attempts to open the door given a lockID. The door will be opened if the given lockID matches that of the door.
	 * 
	 * @param lockID The lockID of the key with which the Player is attempting to open the door
	 * @return The result of the attempt - true if successful, otherwise false.
	 */
	public boolean open(int lockID) {
		if (this.lockID == lockID) {
			this.isOpen = true;
			return true;
		}
		return false;
	}
	
	/**
	 * @return Whether the door is open
	 */
	public boolean isOpen() {
		return this.isOpen;
	}
	
	/**
	 * Sets the state of the door to open or closed depending on the supplied boolean value.
	 * 
	 * @param isOpen The boolean state to set {@link #isOpen} to.
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
