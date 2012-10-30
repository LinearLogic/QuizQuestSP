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
	 * Represents distance in pixels from the x-coordinate of the top lefthand corner of the world
	 * to the x-coordinate of the top lefthand corner of the Entity tile
	 */
	private int world_coordinates_x = Map.getTileSize() * x;
	
	/**
	 * Represents distance in pixels from the y-coordinate of the top lefthand corner of the world
	 * to the y-coordinate of the top lefthand corner of the Entity tile
	 */
	private int world_coordinates_y = Map.getTileSize() * y;
	
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
	 * @return The {@link #world_coordinates_x} value of the entity
	 */
	public int getWorldX() { return this.world_coordinates_x; }
	
	/**
	 * @return The {@link #world_coordinates_y} value of the entity
	 */
	public int getWorldY() { return this.world_coordinates_y; }
}
