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
	private static int[][] itemIDs;
	
	/**
	 * The items of the inventory (an Array of Items) - each element of the inventory is an Item subclass.
	 */
	private static Item[][] items;
	
	/**
	 * The width of the inventory 2D array in slots (tiles)
	 */
	private static int slotDimensionX;
	
	/**
	 * The height of the 2inventory 2D array in slots (tiles)
	 */
	private static int slotDimensionY;
	
	/**
	 * The number of items the inventory can hold (10 by default)
	 */
	private static int capacity = slotDimensionX * slotDimensionY;
	
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
	 * The x-coordinate of the item tile currently selected within the {@link #items} 2D array (corresponds to the index of the item object itself)
	 */
	private static int currentSelectionX;
	
	/**
	 * The y-coordinate of the item tile currently selected within the {@link #items} 2D array (corresponds to the index of the item object itself)
	 */
	private static int currentSelectionY;
	
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
	 * @param bufferWidth The {@link #pixelBufferWidth} of the inventory window
	 * @param bufferHeight The {@link #pixelBufferHeight} of the inventory window
	 * @param tileWidth The {@link #itemTileWidth}, in pixels, of item tiles in the inventory window
	 * @param tileSpacing The {@link #itemTileSpacing} of the inventory window
	 * @param widthInSlots The {@link #slotDimensionX} of the inventory 2D array
	 * @param  heightInSlots The {@link #slotDimensionY} of the inventory 2D array
	 */
	public static void initialize(int x, int y, int width, int height, int bufferWidth, int bufferHeight, int tileWidth, int tileSpacing, int widthInSlots, int heightInSlots)
	{
		pixelX = x;
		pixelY = y;
		pixelWidth = width;
		pixelHeight = height;
		pixelBufferWidth = bufferWidth;
		pixelBufferHeight = bufferHeight;
		itemTileWidth = tileWidth;
		itemTileSpacing = tileSpacing;
		slotDimensionX = widthInSlots;
		slotDimensionY = heightInSlots;
		capacity = widthInSlots * heightInSlots;
		currentSelectionX = 0;
		currentSelectionY = 0;
		itemIDs = new int[widthInSlots][heightInSlots];
		items = new Item[widthInSlots][heightInSlots];
		if (active)
			active = false;
	}
	
	public static void update() {
		if (Keyboard.areRepeatEventsEnabled()) Keyboard.enableRepeatEvents(false);
		
		if (active) { // make sure the inventory menu is currently in use
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
				keyLifted = true;
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && keyLifted) {
				currentSelectionX -= 1;
				keyLifted = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && keyLifted) {
				currentSelectionX += 1;
				keyLifted = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && keyLifted) {
				currentSelectionY -= 1;
				keyLifted = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && keyLifted) {
				currentSelectionY += 1;
				keyLifted = false;
			}
			//If all keys have been released, set keyLifted to true to re-enable scrolling through selections
			if (!keyLifted && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN))
				keyLifted = true;
			
			//Provide wrap-around scrolling through the inventory slots
			if (currentSelectionX >= slotDimensionX)
				currentSelectionX = 0;
			if (currentSelectionX < 0)
				currentSelectionX = slotDimensionX - 1;
			if (currentSelectionY >= slotDimensionY)
				currentSelectionY = 0;
			if (currentSelectionY < 0)
				currentSelectionY = slotDimensionY - 1;
			
			//Checks to see if there is a valid item in the selected inventory slot, and if so, uses the item
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && keyLifted) {
				keyLifted = false;
				System.out.println(itemIDs[currentSelectionX][currentSelectionY]);
				if (itemIDs[currentSelectionX][currentSelectionY] < 0) { //Current slot contains a valid item
					switch(items[currentSelectionX][currentSelectionY].getTypeID()) {
					case 1: //Key
						//Use key on the door the player is currently trying to unlock
						break;
					case 2: //Potion
						((Potion) items[currentSelectionX][currentSelectionY]).use();
						break;
					case 3: //Spell
						//Use spell on the enemy the player is currently in combat with
						break;
					default:
						break;
					}
				}
			}
		}
		render();
	}
	
	public static void render() {
		if (active) { //Double check whether the inventory menu is in use
			//Render the inventory window
			Renderer.renderColoredRectangle(pixelX, pixelY, pixelWidth, pixelHeight, 0.4, 0.4, 0.4);
			
			//Render the box around the corrently selected slot (must be rendered before the slot rectangles or it cover them)
			int x = pixelX + (pixelBufferWidth - 5) + currentSelectionX * (itemTileWidth + itemTileSpacing);
			int y = pixelY + (pixelBufferHeight - 5) + currentSelectionY * (itemTileWidth + itemTileSpacing);
			int w = itemTileWidth + 10;
			int h = itemTileWidth + 10;
			Renderer.renderColoredRectangle(x, y, w, h, 1.0, 0.0, 0.0);
			
			//Render the empty item slots, one by one, starting at the top inside the buffer
			int startingX = pixelX + pixelBufferWidth;
			int startingY = pixelY + pixelBufferHeight;
			for (int i = 0; i < slotDimensionX; i++) {
				startingY = pixelY + pixelBufferHeight; //reset the vertical displacement
				for (int j = 0; j < slotDimensionY; j++) {
					Renderer.renderColoredRectangle(startingX, startingY, itemTileWidth, itemTileWidth, 0.7, 0.7, 0.7);
					startingY += itemTileWidth + itemTileSpacing;
				}
				startingX += itemTileWidth + itemTileSpacing;
			}
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
		return items[index/slotDimensionX][index%slotDimensionY];
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
		return itemIDs[index/slotDimensionX][index%slotDimensionY];
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
		items[index/slotDimensionX][index%slotDimensionY] = null;
		itemIDs[index/slotDimensionX][index%slotDimensionY] = 0;
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
		items[index/slotDimensionX][index%slotDimensionY] = item;
		itemIDs[index/slotDimensionX][index%slotDimensionY] = item.getTypeID();
	}
	
	/**
	 * @return The contents of the inventory as an array of Items
	 */
	public static Item[] getitems() {
		Item[] output = new Item[capacity];
		int index = 0;
		for (int i = 0; i < slotDimensionX; i++)
			for (int j = 0; j < slotDimensionY; j++) {
				output[index] = items[i][j];
				index++;
			}
		return output;
	}
	
	/**
	 * @return The typeIDs of the items in the inventory array
	 */
	public static int[] getItemIDs() {
		int[] output = new int[capacity];
		int index = 0;
		for (int i = 0; i < slotDimensionX; i++)
			for (int j = 0; j < slotDimensionY; j++) {
				output[index] = itemIDs[i][j];
				index++;
			}
		return output;
	}
	
	/**
	 * Clears the entire inventory by setting all Items in the inventory array to null.
	 */
	public static void clearitems() {
		for (int i = 0; i < slotDimensionX; i++)
			for (int j = 0; i < slotDimensionY; i++) {
				items[i][j] = null;
				itemIDs[i][j] = 0;
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
