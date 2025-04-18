package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;

    private SoundManager() {}

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playMusic(Context context, int musicResId, boolean loop) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, musicResId);
            mediaPlayer.setLooping(loop);
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isMusicPlaying = true;
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isMusicPlaying = false;
        }
    }

    public void toggleMusic(Context context, int musicResId, boolean shouldPlay) {
        if (shouldPlay) {
            playMusic(context, musicResId, true);
        } else {
            stopMusic();
        }
    }

    public boolean isPlaying() {
        return isMusicPlaying;
    }
}
