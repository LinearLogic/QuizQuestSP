package ss.linearlogic.quizquest.entity;

import ss.linearlogic.quizquest.Map;

/**
 * Represents an entity in the world
 */
public class Entity {

	/**
	 * Entity type name ("Grass", "Door", "Enemy", etc.)
	 */
	private String type;
	
	/**
	 * Entity type ID (0 -> Grass, 1 -> Floor, 2 -> Wall, 3 -> Door, 4 -> Enemy)
	 */
	private int typeID;
	
	/**
	 * X-coord of entity location in the entityMap 2D array
	 */
	private int x;
	
	/**
	 * Y-coord of entity location in the entityMap 2D array
	 */
	private int y;
	
	/**
	 * Constructs the Entity object with the given subclass typeID and x and y world coordinates
	 * @param typeID
	 * @param x
	 * @param y
	 */
	public Entity(int typeID, int x, int y) {
		this.typeID = typeID;
		switch(typeID) {
		default:
			System.err.println("Error constructing Entity object - invalid typeID supplied (value must be 0-4). Setting Entity to grass...");
		case 0:
			this.type = "Grass";
			break;
		case 1:
			this.type = "Floor";
			break;
		case 2:
			this.type = "Wall";
			break;
		case 3:
			this.type = "Door";
			break;
		case 4:
			this.type = "Enemy";
			break;
		}
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Removes the entity from the Map, setting the entity at its location to grass
	 */
	public void removeEntity() {
		Map.removeEntity(this.x, this.y);
	}
	
	//--- Getters and setters ---//
	/**
	 * @return The {@link #type} name of the entity
	 */
	public String getType() { return type; }
	
	/**
	 * @return The {@link #typeID}
	 */
	public int getTypeID() { return typeID; }
	
	/**
	 * @return The {@link #x}-coordinate of the entity in the entityMap 2D array
	 */
	public int getX() { return this.x; }
	
	/**
	 * Sets the entity's {@link #x}-coordinate in the entityMap 2D array to the supplied value
	 * @param x
	 */
	public void setX(int x) { this.x = x; }
	
	/**
	 * @return The {@link #y}-coordinate of the entity in the entityMap 2D array
	 */
	public int getY() { return this.y; }
	
	/**
	 * Sets the entity's {@link #y}-coordinate in the entityMap 2D array to the supplied value
	 * @param y
	 */
	public void setY(int y) { this.y = y; }
	
	/**
	 * @return The x-coordinate of the pixel location of the entity in the world as a whole
	 */
	public int getWorldX() { return this.x * Map.getTileSize(); }
	
	/**
	 * @return The y-coordinate of the pixel location of the entity in the world as a whole
	 */
	public int getWorldY() { return this.y * Map.getTileSize(); }
	
	/**
	 * @return The x-coordinate of the top lefthand corner of the entity within the rendering window
	 */
	public int getRenderingX() { return (int) (getWorldX() - (Math.floor(getWorldX()/480) * 480)); }
	
	/**
	 * @return 
	 */
	public int getRenderingY() { return (int) (getWorldY() - (Math.floor(getWorldY()/480) * 480)); }
}
