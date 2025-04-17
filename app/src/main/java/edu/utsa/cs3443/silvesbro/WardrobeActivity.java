package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WardrobeActivity extends AppCompatActivity {

    private ArrayList<WardrobeItem> availableItems;
    WardrobeItem currentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
    }

    public void loadWardrobeItems() {
        //read from csv
    }

    public void equipItem(String itemID){
        //search from wardrobe items
    }

    public ArrayList<WardrobeItem> getAvailableItems () {
        return this.availableItems;
    }

}