package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.utsa.cs3443.silvesbro.models.WardrobeItem;

/**
 * WardrobeActivity displays a view of selectable wardrobe items.
 * Users can pick a hat to equip, which sends the selection back to MainActivity.
 */

public class WardrobeActivity extends AppCompatActivity {

    private ArrayList<WardrobeItem> availableItems = new ArrayList<>();
    private WardrobeItem currentItem;

    /**
     * Initializes the activity and loads wardrobe items
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        loadWardrobeItems();
        displayItems();
    }

    /**
     * Reads wardrobe data from CSV and populates availableItems list
     */
    public void loadWardrobeItems() {
        try {
            InputStream is = getResources().openRawResource(R.raw.wardrobe_items);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) {
                    int itemId = Integer.parseInt(tokens[0].trim());
                    String name = tokens[1].trim();
                    String resName = tokens[2].trim().substring(tokens[2].trim().lastIndexOf("/") + 1).replace(".png", "");
                    int resId = getResources().getIdentifier(resName, "drawable", getPackageName());


                    Log.d("WardrobeDebug", "Loaded: " + name + " - ResID: " + resId);

                    if (resId != 0) {
                        WardrobeItem item = new WardrobeItem(itemId, name, resId);
                        availableItems.add(item);
                    } else {
                        Log.w("WardrobeDebug", "Resource not found for: " + tokens[2].trim());
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load wardrobe items", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays wardrobe items in rows of two using ImageButtons
     */
    public void displayItems() {
        LinearLayout hatList = findViewById(R.id.hat_list);
        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout rowLayout = null;
        for (int i = 0; i < availableItems.size(); i++) {
            if (i % 2 == 0) {
                rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                rowLayout.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
                hatList.addView(rowLayout);
            }

            WardrobeItem item = availableItems.get(i);
            ImageButton hatButton = new ImageButton(this);
            hatButton.setImageResource(item.getImageResource());
            hatButton.setBackground(null);
            hatButton.setAdjustViewBounds(true);
            hatButton.setScaleType(ImageButton.ScaleType.FIT_CENTER);
            hatButton.setLayoutParams(new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT));
            hatButton.setPadding(16, 16, 16, 16);

            hatButton.setOnClickListener(v -> equipItem(item.getItemId()));

            if (rowLayout != null) {
                rowLayout.addView(hatButton);
            }
        }
    }

    /**
     * Sets the selected item and returns result to MainActivity
     *
     * @param itemID The selected item’s ID
     */
    public void equipItem(int itemID) {
        for (WardrobeItem item : availableItems) {
            if (item.getItemId() == itemID) {
                currentItem = item;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedHatId", item.getItemId());
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
            }
        }
    }

    /**
     * Returns list of loaded item
     *
     * @return ArrayList of WardrobeItem objects
     */
    public ArrayList<WardrobeItem> getAvailableItems () {
        return this.availableItems;
    }
}
