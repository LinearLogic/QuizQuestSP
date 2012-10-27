package ss.linearlogic.quizquest.entity;

/**
 * Represents an entity in the world
 */
public class Entity {

	/*
	 * NOTE: completion of this class is postponed until the location management system has been fleshed out to the extent necessary.
	 */
	private String type; // "Player"; "Enemy"; "Wall"; "Door"
	private int typeID; // 1 --> Door; 2 --> Wall; 3 --> Roof; 4 --> Enemy
	
	public Entity(int typeID) {
		this.typeID = typeID;
		switch(typeID) {
		case 1:
			this.type = "Door";
			break;
		case 2:
			this.type = "Wall";
			break;
		case 3:
			this.type = "Floor";
			break;
		case 4:
			this.type = "Enemy";
			break;
		default:
			System.err.println("Error constructing Entity object - invalid typeID supplied (value must be 1-4)");
		}
	}
	
	//-------[getters and setters]-------//
	public String getType() { return type; }
	
	public int getTypeID() { return typeID; }
}
