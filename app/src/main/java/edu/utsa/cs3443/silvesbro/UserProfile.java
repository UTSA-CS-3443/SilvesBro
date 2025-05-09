package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getTotalStudyTime() { return totalStudyTime; }
    public void addStudyTime(int minutes) { this.totalStudyTime += minutes; }

    public int getDrinkCount() { return drinkCount; }
    public void addMountainDew(int count) { this.drinkCount += count; }
    public void subtractMountainDew(int count) { this.drinkCount -= count; }
    public void setDrinkCount(int drinkCount) { this.drinkCount = drinkCount; }

    public int getHappinessLvl() { return happinessLevel; }
    public void setHappinessLvl(int lvl) { this.happinessLevel = lvl; }

    public boolean isMusicOn() { return isMusicOn; }
    public void setMusicOn(boolean musicOn) { this.isMusicOn = musicOn; }

    public boolean isSoundOn() { return isSoundOn; }
    public void setSoundOn(boolean soundOn) { this.isSoundOn = soundOn; }

    public boolean isSwaggerModeOn() { return isSwaggerMode; }
    public void setSwaggerMode(boolean swaggerMode) { this.isSwaggerMode = swaggerMode; }
    public int getSelectedHatResId() { return selectedHatResId; }
    public void setSelectedHatResId(int resId) { this.selectedHatResId = resId; }
    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(String character) {
        this.selectedCharacter = character;
    }
}
