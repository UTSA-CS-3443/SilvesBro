package edu.utsa.cs3443.silvesbro;

public class WardrobeItem {
    private int itemId;
    private String itemName;
    private int imageResource;


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
}

