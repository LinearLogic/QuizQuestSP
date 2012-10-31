package ss.linearlogic.quizquest.entity;

import ss.linearlogic.quizquest.Map;
import ss.linearlogic.quizquest.entity.Entity;

/**
 * Represents a door entity in the world
 */
public class Door extends Entity {

	/**
	 * The value shared by a corresponding key and door.  Any key can open any door that shares its lockID value.
	 */
	private int lockID;
	
	/**
	 * Constructs the Entity superclass using the Door typeID (3), and sets the door's lockID to the supplied value.
	 * @param lockID The door's lockID (see {@link #lockID})
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Door(int lockID, int x, int y) {
		super(3, x, y);
		this.lockID = lockID;
	}

	/**
	 * Attempts to open the door given a lockID. The door (and all those adjacent to it) will be opened if the given lockID matches that of the door.
	 * 
	 * @param lockID The lockID of the key with which the Player is attempting to open the door
	 * @return The result of the attempt - true if successful, otherwise false.
	 */
	public boolean open(int lockID) {
		if (this.lockID == lockID) {
			this.forceOpen();
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the state of the door to open or closed depending on the supplied boolean value.
	 * 
	 * @param isOpen The boolean state to set {@link #isOpen} to.
	 */
	public void forceOpen() {
		Map.addEntity(new Floor(this.getX(), this.getY()));
		//Handle adjacent door entities
		if (getX() < Map.getWidth() - 1)
			if (Map.getEntity(getX() + 1, getY()).getTypeID() == 3) //Entity to the right is a door
				((Door) Map.getEntity(getX() + 1, getY())).forceOpen();
		if (getX() > 0)
			if (Map.getEntity(getX() - 1, getY()).getTypeID() == 3) //Entity to the left is a door
				((Door) Map.getEntity(getX() - 1, getY())).forceOpen();
		if (getY() < Map.getHeight() - 1)
			if ((Map.getEntity(getX(), getY() + 1)).getTypeID() == 3) // Entity above is a door
				((Door) Map.getEntity(getX(), getY() + 1)).forceOpen();
		if (getY() > 0)
			if ((Map.getEntity(getX(), getY() - 1)).getTypeID() == 3) // Entity below is a door
				((Door) Map.getEntity(getX(), getY() - 1)).forceOpen();
	}
	
	/**
	 * @return The door's {@link #lockID} value
	 */
	public int getLockID() { return this.lockID; }
	
	/**
	 * Set the door's {@link #lockID} to the supplied value 
	 * @param lockID
	 */
	public void setLockID(int lockID) { this.lockID = lockID; }
}
