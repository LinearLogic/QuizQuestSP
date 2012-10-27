package ss.linearlogic.quizquest.entity;

/**
 * Represents a wall entity, through which the player cannot pass while moving about the map.
 */
public class Floor extends Entity{

	/**
	 * Constructs the Entity superclass using the Floor typeID (1) and x and y coordinate values.
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Floor(int x, int y) {
		super(1, x, y);
	}

}
