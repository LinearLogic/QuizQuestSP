package ss.linearlogic.quizquest.entity;

import ss.linearlogic.quizquest.Map;

/**
 * Represents an entity in the world
 */
public class Entity {

	/*
	 * NOTE: completion of this class is postponed until the location management system has been fleshed out to the extent necessary.
	 */
	private String type; // "Player"; "Enemy"; "Wall"; "Door"
	private int typeID; // 1 --> Door; 2 --> Wall; 3 --> Roof; 4 --> Enemy
	private int x; // x-coord of entity location
	private int y; // y-coord of entity location
	
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
	
	//-------[getters and setters]-------//
	public String getType() { return type; }
	
	public int getTypeID() { return typeID; }
	
	public int getX() { return this.x; }
	
	public void setX(int x) { this.x = x; }
	
	public int getY() { return this.y; }
	
	public void setY(int y) { this.y = y; }
}
