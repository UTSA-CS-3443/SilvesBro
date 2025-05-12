package edu.utsa.cs3443.silvesbro.models;

/**
 * Represents an item in the wardrobe that a character can wear.
 * Each WardrobeItem contains:
 * - ID
 * - name
 * - image location reference
 */
public class WardrobeItem {
    private final int itemId;
    private final String itemName;
    private final int imageResource;

    /**
     * Class constructor
     *
     * @param itemId Numeric ID associated with the item
     * @param itemName Display name of the item
     * @param imageResource Resource ID for the item's image
     */
    public WardrobeItem(int itemId, String itemName, int imageResource) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageResource = imageResource;
    }

    /**
     * Gets the image resources
     *
     * @return Resource ID for the image
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Gets the Image ID
     *
     * @return Item ID
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets the hat name
     *
     * @return Name of the hat
     */
    public String getItemName() {
        return itemName;
    }

}
