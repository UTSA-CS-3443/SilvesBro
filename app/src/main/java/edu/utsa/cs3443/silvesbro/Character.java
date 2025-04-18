package edu.utsa.cs3443.silvesbro;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

public class Character {
    private int happinessLvl;
    private WardrobeItem currentOutfit;
    private boolean isSwaggerMode;
    private View samHeadTop;

    public Character(int happinessLvl, WardrobeItem currentOutfit, boolean isSwaggerMode) {
        this.happinessLvl = happinessLvl;
        this.currentOutfit = currentOutfit;
        this.isSwaggerMode = isSwaggerMode;
    }

    public void decreaseHappiness(int amount) {
        happinessLvl = Math.max(0, happinessLvl - amount);
    }

    public void increaseHappiness(int amount) {
        happinessLvl = Math.min(100, happinessLvl + amount);
    }

    public void feed() {
        increaseHappiness(10);
    }

    public void changeOutfit(WardrobeItem item) {
        this.currentOutfit = item;
    }

    public void toggleSwaggerMode() {
        this.isSwaggerMode = !this.isSwaggerMode;
    }

    public void speak(View samHeadTop) {
        this.samHeadTop = samHeadTop;

        ObjectAnimator talkAnim = ObjectAnimator.ofFloat(samHeadTop, "translationY", 0f, -40f, 0f);
        talkAnim.setDuration(300);
        talkAnim.setRepeatMode(ValueAnimator.RESTART);
        talkAnim.setRepeatCount(ValueAnimator.INFINITE);
        talkAnim.start();
    }

    public int getHappinessLvl() {
        return happinessLvl;
    }

    public WardrobeItem getCurrentOutfit() {
        return currentOutfit;
    }

    public boolean isSwaggerMode() {
        return isSwaggerMode;
    }
}

