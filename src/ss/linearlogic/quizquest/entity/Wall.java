package ss.linearlogic.quizquest.entity;

/**
 * Represents a wall entity, through which the player cannot pass while moving about the map.
 */
public class Wall extends Entity{

	/**
	 * Constructs the Entity superclass using the Wall typeID (2) and x and y coordinate values.
	 * @param x The x-coord of the entity
	 * @param y The y-coord of the entity
	 */
	public Wall(int x, int y) {
		super(2, x, y);
	}

}
