package ss.linearlogic.quizquest.player;

import org.lwjgl.input.Keyboard;

import ss.linearlogic.quizquest.Renderer;
import ss.linearlogic.quizquest.item.Item;
import ss.linearlogic.quizquest.item.Key;
import ss.linearlogic.quizquest.item.Potion;

/**
 * Represents the player's inventory, an array of Item subclass objects
 */
public class Inventory {

	/**
	 * The typeIDs of the items in the inventory (an Array of ints)
	 */
	private static int[] itemIDs;
	
	/**
	 * The items of the inventory (an Array of Items) - each element of the inventory is an Item subclass.
	 */
	private static Item[] items;
	
	/**
	 * The number of items the inventory can hold (10 by default)
	 */
	private static int capacity = 10;
	
	/**
	 * X-coordinate of the pixel location of the top lefthand corner of the inventory window within the game window (NOT the world pixel location)
	 */
	private static int pixelX;
	
	/**
	 * Y-coordinate of the pixel location of the top lefthand corner of the inventory window within the game window (NOT the world pixel location)
	 */
	private static int pixelY;
	
	/**
	 * The width, in item tiles, of the inventory window
	 */
	private static int pixelWidth;
	
	/**
	 * The height, in item tiles, of the inventory window
	 */
	private static int pixelHeight;
	
	/**
	 * Distance between the left and right sides of the inventory window and the left and right sides of each item tile within
	 */
	private static int pixelBufferWidth;
	
	/**
	 * Distance between the top side of the inventory window and the top side of the top item tile within,
	 * and the bottom side of the inventory window and the bottom side of the bottom item tile within. (Horrific wording, isn't it?)
	 */
	private static int pixelBufferHeight;
	
	/**
	 * The width (and height, as tiles are square) of an item tile, in pixels
	 */
	private static int itemTileWidth;
	
	/**
	 * Spacing, in pixels, between two adjacent item tiles
	 */
	private static int itemTileSpacing;
	
	/**
	 * Whether or not the inventory window is open
	 */
	private static boolean active = false;
	
	/**
	 * The index of the item tile currently selected (corresponds to the index of the item object itself)
	 */
	private static int currentSelectionIndex;
	
	/**
	 * Whether a key is currently depressed
	 */
	private static boolean keyLifted = true;
	
	/**
	 * Loads the inventory window with the supplied specifications. Note: this method does not actually open
	 * the inventory window after initializing it - this must be done with the {@link #toggleActive(boolean)} method.
	 * 
	 * @param x The {@link #pixelX} coordinate of the inventory window
	 * @param y The {@link #pixelY} coordinate of the inventory window
	 * @param width The width, in pixels, of the inventory window
	 * @param height The height, in pixels of the inventory window
	 * @param tileWidth The {@link #itemTileWidth}, in pixels, of item tiles in the inventory window
	 * @param tileSpacing The {@link #itemTileSpacing} of the inventory window
	 */
	public static void initialize(int x, int y, int width, int height, int bufferWidth, int bufferHeight, int tileWidth, int tileSpacing, int inventoryCapacity)
	{
		pixelX = x;
		pixelY = y;
		pixelWidth = width;
		pixelHeight = height;
		pixelBufferWidth = bufferWidth;
		pixelBufferHeight = bufferHeight;
		itemTileWidth = tileWidth;
		itemTileSpacing = tileSpacing;
		capacity = inventoryCapacity;
		currentSelectionIndex = 0;
		if (active)
			active = false;
	}
	
	public static void update() {
		if (Keyboard.areRepeatEventsEnabled()) Keyboard.enableRepeatEvents(false);
		
		if (active) { // make sure the inventory menu is currently in use
			
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
				keyLifted = true;
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && keyLifted) {
				currentSelectionIndex -= 1;
				keyLifted = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && keyLifted) {
				currentSelectionIndex += 1;
				keyLifted = false;
			}
			//If all keys have been released, set keyLifted to true to re-enable scrolling through selections
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
				keyLifted = true;
			
			//Checks to see if there is a valid item in the selected inventory slot, and if so, uses the item
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && keyLifted) {
				keyLifted = false;
				if (itemIDs[currentSelectionIndex] < 0) { //Current slot contains a valid item
					switch(items[currentSelectionIndex].getTypeID()) {
					case 1: //Key
						//Use key on the door the player is currently trying to unlock
						break;
					case 2: //Potion
						((Potion) items[currentSelectionIndex]).use();
						break;
					case 3: //Spell
						//Use spell on the enemy the player is currently in combat with
						break;
					default:
						break;
					}
				}
			}
					
			//Provide wrap-around scrolling through the inventory slots
			if (currentSelectionIndex > 10)
				currentSelectionIndex = 0;
			if (currentSelectionIndex < 0)
				currentSelectionIndex = 10;
		}
		
		render();
	}
	
	public static void render() {
		if (active) { //Double check whether the inventory menu is in use
			Renderer.renderColoredRectangle(pixelX, pixelY, pixelWidth, pixelHeight, 0.7, 0.7, 0.7);
		}
	}
	
	//---// Inventory contents handling //---//
	/**
	 * Retrieves the item at the specified index in the inventory.
	 * 
	 * @param index Inventory array index
	 * @return The item present at the provided index
	 */
	public static Item getItem(int index) {
		if (index >= capacity) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return null;
		}
		return items[index];
	}
	
	/**
	 * Retrieves the typeID of the item at the specified index in the inventory.
	 * @param index Inventory array index
	 * @return Item typeID
	 */
	public static int getItemID(int index)
	{
		if (index >= capacity) {
			System.err.println("Error retrieving inventory itemID - invalid slot index provided.");
			return 0;
		}
		return itemIDs[index];
	}
	/**
	 * Removes the Item at the given inventory index.
	 * 
	 * @param index The index of the item being removed from the inventory array
	 */
	public static void removeItem(int index) {
		if (index >= items.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return;
		}
		items[index] = null;
		itemIDs[index] = 0;
	}
	
	/**
	 * Adds the specified item to the inventory array at the specified index.
	 * 
	 * @param index The indix at which to put the Item
	 * @param item The Item itself (an Item subclass, really, as Item is abstract)
	 */
	public static void addItem(int index, Item item) {
		if (index >= items.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return;
		}
		items[index] = item;
		itemIDs[index] = item.getTypeID();
	}
	
	/**
	 * @return The contents of the inventory as an array of Items
	 */
	public static Item[] getitems() { return items; }
	
	/**
	 * @return The typeIDs of the items in the inventory array
	 */
	public static int[] getItemIDs() { return itemIDs; }
	
	/**
	 * Clears the entire inventory by setting all Items in the inventory array to null.
	 */
	public static void clearitems() {
		for (int i = 0; i < items.length; i++) {
			items[i] = null;
			itemIDs[i] = 0;
		}
	}
	
	/**
	 * @return Whether the inventory window is in use
	 */
	public static boolean isActive() { return active; }
	
	/**
	 * Toggle whether the inventory window is open and in use
	 */
	public static void toggleActive() { active = !active; }
}
