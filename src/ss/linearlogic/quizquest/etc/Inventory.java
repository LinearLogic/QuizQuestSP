package ss.linearlogic.quizquest.etc;

import ss.linearlogic.quizquest.item.Item;

public class Inventory {

	/**
	 * The contents of the inventory (an Array of Items). Each element of the inventory is an Item subclass.
	 */
	private Item[] contents;
	
	/**
	 * Retrieves the item at the provided index in the inventory.
	 * 
	 * @param index The index of the item being retrieved from the inventory array
	 * @return The item present at the index provided
	 */
	public Item getItem(int index) {
		if (index >= this.contents.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return null;
		}
		return this.contents[index];
	}
	
	/**
	 * Removes the Item at the given inventory index.
	 * 
	 * @param index The index of the item being removed from the inventory array
	 */
	public void removeItem(int index) {
		if (index >= this.contents.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return;
		}
		this.contents[index] = null;
	}
	
	/**
	 * Adds the specified item to the inventory array at the specified index.
	 * 
	 * @param index The indix at which to put the Item
	 * @param item The Item itself (an Item subclass, really, as Item is abstract)
	 */
	public void addItem(int index, Item item) {
		if (index >= this.contents.length) {
			System.err.println("Error retrieving inventory item - invalid slot index provided.");
			return;
		}
		this.contents[index] = item;
	}
	
	/**
	 * Returns the contents of the inventory as an array of Items
	 * 
	 * @return inventory contents as an array of Items
	 */
	public Item[] getContents() {
		return this.contents;
	}
	
	/**
	 * Clears the entire inventory by setting all Items in the inventory array to null.
	 */
	public void clearContents() {
		for (int i = 0; i < this.contents.length; i++) {
			this.contents[i] = null;
		}
	}
}
