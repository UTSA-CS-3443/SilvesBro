package edu.utsa.cs3443.silvesbro.models;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
/**
 * Represents the virtual character (e.g. Mr. Silvestro) in the SilvesBro app.
 * Responsible for managing happiness, outfit, and swagger mode behavior.
 */
public class Character {
    private int happinessLvl;
    private WardrobeItem currentOutfit;
    private boolean isSwaggerMode;
    private View samHeadTop;

    /**
     * Constructs a Character instance.
     *
     * @param happinessLvl    Initial happiness level (0-100).
     * @param currentOutfit   Wardrobe item currently worn by the character.
     * @param isSwaggerMode   Whether Swagger Mode is active.
     */
    public Character(int happinessLvl, WardrobeItem currentOutfit, boolean isSwaggerMode) {
        this.happinessLvl = happinessLvl;
        this.currentOutfit = currentOutfit;
        this.isSwaggerMode = isSwaggerMode;
    }

    /**
     * Decreases happiness by the specified amount.
     *
     * @param amount Amount to subtract from happiness (minimum 0).
     */
    public void decreaseHappiness(int amount) {
        happinessLvl = Math.max(0, happinessLvl - amount);
    }

    /**
     * Increases happiness by the specified amount.
     *
     * @param amount Amount to add to happiness (maximum 100).
     */
    public void increaseHappiness(int amount) { happinessLvl = Math.min(100, happinessLvl + amount);}

    /**
     * Simulates feeding the character, which increases happiness.
     */
    public void feed() { increaseHappiness(10); }

    public void changeOutfit(WardrobeItem item) { this.currentOutfit = item; }

    public int getCurrentOutfitResId() { return (currentOutfit != null) ? currentOutfit.getImageResource() : -1; }

    public void toggleSwaggerMode() { isSwaggerMode = !isSwaggerMode; }

    public void speak(View samHeadTop) {
        this.samHeadTop = samHeadTop;
        ObjectAnimator talkAnim = ObjectAnimator.ofFloat(samHeadTop, "translationY", 0f, -40f, 0f);
        talkAnim.setDuration(300);
        talkAnim.setRepeatMode(ValueAnimator.RESTART);
        talkAnim.setRepeatCount(ValueAnimator.INFINITE);
        talkAnim.start();
    }

    public int getHappinessLvl() { return happinessLvl; }

    public WardrobeItem getCurrentOutfit() { return currentOutfit; }

    public boolean isSwaggerMode() { return isSwaggerMode; }

    public void setSwaggerMode(boolean swag) {
        this.isSwaggerMode = swag;
    }

}
