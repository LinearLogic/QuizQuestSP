package ss.linearlogic.quizquest.player;

import ss.linearlogic.quizquest.item.Item;

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
	public static void initialize(int x, int y, int width, int height, int bufferWidth, int bufferHeight, int tileWidth, int tileSpacing)
	{
		pixelX = x;
		pixelY = y;
		pixelWidth = width;
		pixelHeight = height;
		pixelBufferWidth = bufferWidth;
		pixelBufferHeight = bufferHeight;
		itemTileWidth = tileWidth;
		itemTileSpacing = tileSpacing;
		currentSelectionIndex = 0;
		if (active)
			active = false;
	}
	
	//---// Inventory contents handling //---//
	/**
	 * Retrieves the item at the provided index in the inventory.
	 * 
	 * @param index The index of the item being retrieved from the inventory array
	 * @return The item present at the provided index
	 */
	public static Item getItem(int index) {
		if (index >= items.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return null;
		}
		return items[index];
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
	}
	
	/**
	 * Returns the contents of the inventory as an array of Items
	 * 
	 * @return inventory items as an array of Items
	 */
	public static Item[] getitems() {
		return items;
	}
	
	/**
	 * Clears the entire inventory by setting all Items in the inventory array to null.
	 */
	public static void clearitems() {
		for (int i = 0; i < items.length; i++) {
			items[i] = null;
		}
	}
}
