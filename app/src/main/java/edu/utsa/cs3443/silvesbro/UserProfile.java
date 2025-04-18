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
    public UserProfile() {
        name = "Sam";
        totalStudyTime = 0;
        drinkCount = 0;
        happinessLevel = 100;
        isMusicOn = true;
        isSoundOn = true;
        isSwaggerMode = false;
    }

    public void loadProfile(Context context) {
        try {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(context.openFileInput("user_profile.csv")));
            } catch (Exception e) {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("user_profile.csv")));
            }
            reader.readLine();
            String line = reader.readLine();
            String[] data = line.split(",");
            name = data[0];
            totalStudyTime = Long.parseLong(data[1]);
            drinkCount = Integer.parseInt(data[2]);
            happinessLevel = Integer.parseInt(data[3]);
            isMusicOn = Boolean.parseBoolean(data[4]);
            isSoundOn = Boolean.parseBoolean(data[5]);
            isSwaggerMode = Boolean.parseBoolean(data[6]);
            reader.close();
        } catch (Exception e) {
            Log.e("UserProfile", "Failed to load profile: " + e.getMessage());
        }
    }

    public void saveProfile(Context context) throws RuntimeException  {
        try {
            OutputStream outputStream = context.openFileOutput("user_profile.csv", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write("name,total_study_time_minutes,mountain_dew_count,happiness_level,is_music_on,is_sound_on,is_swagger_mode\n");
            writer.write(String.format("%s,%d,%d,%d,%b,%b,%b\n",
                    name, totalStudyTime, drinkCount, happinessLevel, isMusicOn, isSoundOn, isSwaggerMode));

            writer.close();
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e("UserProfile", "Failed to save profile: " + e.getMessage());
        }
    }

    public void addStudyTime(int minutes) {
        totalStudyTime += minutes;
    }

    public void addMountainDew(int count) {
        drinkCount += count;
    }

    public void subtractMountainDew(int count) {
        drinkCount -= count;
    }

    public void addName(String n) {
        name = n;
    }

    public int getHappinessLvl() {
        return happinessLevel;
    }

    public long getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setSwaggerMode(boolean swagOn) { this.isSwaggerMode = swagOn; }
    public void setMusicOn(boolean musicOn) { this.isMusicOn = musicOn; }
    public void setSoundOn(boolean soundOn) { this.isSoundOn = soundOn; }
    public boolean isMusicOn() { return isMusicOn; }
    public boolean isSoundOn() { return isSoundOn; }
    public boolean isSwaggerModeOn() {
        return isSwaggerMode;
    }
    public String getName() { return name; }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(int drinkCount) {
        this.drinkCount = drinkCount;
    }
}
