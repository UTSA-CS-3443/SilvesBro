package edu.utsa.cs3443.silvesbro.models;

public class WardrobeItem {
    private final int itemId;
    private final String itemName;
    private final int imageResource;

    public WardrobeItem(int itemId, String itemName, int imageResource) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "WardrobeItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
