package ss.linearlogic.quizquest.entity;

/**
 * Represents a wall entity, through which the player cannot pass while moving about the map.
 */
public class Grass extends Entity{

	/**
	 * Constructs the Entity superclass using the Grass typeID (0) and x and y coordinate values.
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Grass(int x, int y) {
		super(0, x, y);
	}

}
