package edu.utsa.cs3443.silvesbro.models;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import edu.utsa.cs3443.silvesbro.R;

/**
 * Represents the persistent user data for the SilvesBro app.
 * This class handles saving and loading user settings such as:
 * - Name
 * - Study time
 * - Mountain Dew count
 * - Happiness level
 * - Music and sound toggle
 * - Swagger mode toggle
 * - Character selection and customization
 */
public class UserProfile {
    private String name;
    private long totalStudyTime;
    private int drinkCount;
    private int happinessLevel;
    private boolean isMusicOn;
    private boolean isSoundOn;
    private boolean isSwaggerMode;
    private int selectedHatResId;
    private String selectedCharacter;

    /**
     * Initializes a new UserProfile with default values.
     */
    public UserProfile() {
        name = "Sam";
        totalStudyTime = 0;
        drinkCount = 0;
        happinessLevel = 100;
        isMusicOn = true;
        isSoundOn = true;
        isSwaggerMode = false;
        selectedHatResId = R.drawable.blue_party_hat;
        selectedCharacter = "Sam Silvestro";
    }

    /**
     * Loads the user profile from a CSV file. If not found, loads from assets.
     * @param context Android context used to access internal storage and assets.
     */
    public void loadProfile(Context context) {
        try {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(
                        context.openFileInput("user_profile.csv")));
            } catch (Exception e) {
                reader = new BufferedReader(new InputStreamReader(
                        context.getAssets().open("user_profile.csv")));
            }

            // skip header
            reader.readLine();
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                name = data[0];
                totalStudyTime  = Long.parseLong(data[1]);
                drinkCount      = Integer.parseInt(data[2]);
                happinessLevel  = Integer.parseInt(data[3]);
                Log.d("PROFILE", "Loaded happinessLevel = " + happinessLevel);
                isMusicOn       = Boolean.parseBoolean(data[4]);
                isSoundOn       = Boolean.parseBoolean(data[5]);
                isSwaggerMode   = Boolean.parseBoolean(data[6]);
                selectedHatResId = data.length > 7 ? Integer.parseInt(data[7]) : R.drawable.blue_party_hat;
                selectedCharacter = data.length > 8 ? data[8] : "Sam Silvestro";
            }
            reader.close();
        } catch (Exception e) {
            Log.e("UserProfile", "Failed to load profile: " + e.getMessage());
        }
    }

    /**
     * Saves the current user profile to a CSV file.
     * @param context Android context used to access internal storage.
     */
    public void saveProfile(Context context) {
        try {
            OutputStream os = context.openFileOutput("user_profile.csv", Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

            writer.write("name,total_study_time_minutes,mountain_dew_count,happiness_level,is_music_on,is_sound_on,is_swagger_mode,selected_hat_res,selected_character\n");
            writer.write(String.format("%s,%d,%d,%d,%b,%b,%b,%d,%s\n",
                    name,
                    totalStudyTime,
                    drinkCount,
                    happinessLevel,
                    isMusicOn,
                    isSoundOn,
                    isSwaggerMode,
                    selectedHatResId,
                    selectedCharacter
            ));

            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            Log.e("UserProfile", "Failed to save profile: " + e.getMessage());
        }
    }

    // === Getters and Setters ===

    /** @return User's saved name. */
    public String getName() { return name; }

    /** @param name Sets the user's name. */
    public void setName(String name) { this.name = name; }

    /** @return Total study time in minutes. */
    public long getTotalStudyTime() { return totalStudyTime; }

    /** @param minutes Adds study time to the total. */
    public void addStudyTime(int minutes) { this.totalStudyTime += minutes; }

    /** @return Number of Fountain Dudes collected. */
    public int getDrinkCount() { return drinkCount; }

    /** @param count Adds to the drink count. */
    public void addMountainDew(int count) { this.drinkCount += count; }

    /** @param count Subtracts from the drink count. */
    public void subtractMountainDew(int count) { this.drinkCount -= count; }

    /** @param drinkCount Sets the drink count directly. */
    public void setDrinkCount(int drinkCount) { this.drinkCount = drinkCount; }

    /** @return The user's happiness level. */
    public int getHappinessLvl() { return happinessLevel; }

    /** @param lvl Sets the happiness level. */
    public void setHappinessLvl(int lvl) { this.happinessLevel = lvl; }

    /** @return True if music is enabled. */
    public boolean isMusicOn() { return isMusicOn; }

    /** @param musicOn Sets music on/off. */
    public void setMusicOn(boolean musicOn) { this.isMusicOn = musicOn; }

    /** @return True if sound effects are enabled. */
    public boolean isSoundOn() { return isSoundOn; }

    /** @param soundOn Sets sound effects on/off. */
    public void setSoundOn(boolean soundOn) { this.isSoundOn = soundOn; }

    /** @return True if Swagger Mode is enabled. */
    public boolean isSwaggerModeOn() { return isSwaggerMode; }

    /** @param swaggerMode Sets Swagger Mode on/off. */
    public void setSwaggerMode(boolean swaggerMode) { this.isSwaggerMode = swaggerMode; }

    /** @return Resource ID of the selected hat image. */
    public int getSelectedHatResId() { return selectedHatResId; }

    /** @param resId Sets the selected hat image resource ID. */
    public void setSelectedHatResId(int resId) { this.selectedHatResId = resId; }

    /** @return Name of the selected character. */
    public String getSelectedCharacter() { return selectedCharacter; }

    /** @param character Sets the selected character's name. */
    public void setSelectedCharacter(String character) { this.selectedCharacter = character; }
}
