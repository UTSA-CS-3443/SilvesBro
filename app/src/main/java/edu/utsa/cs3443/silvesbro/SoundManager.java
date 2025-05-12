package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Singleton class that handles background music playback for the SilvesBro app.
 * Supports playing, stopping, toggling, and checking music playback status.
 */
public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private SoundManager() {}

    /**
     * Retrieves the single instance of SoundManager.
     *
     * @return The SoundManager instance.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Plays music using the specified resource ID.
     *
     * @param context     The application context.
     * @param musicResId  The resource ID of the music file to play.
     * @param loop        Whether the music should loop continuously.
     */
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

    /**
     * Stops the currently playing music and releases resources.
     */
    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isMusicPlaying = false;
        }
    }

    /**
     * Toggles music playback based on the provided flag.
     *
     * @param context     The application context.
     * @param musicResId  The resource ID of the music file to play.
     * @param shouldPlay  If true, music will play; otherwise, it will stop.
     */
    public void toggleMusic(Context context, int musicResId, boolean shouldPlay) {
        if (shouldPlay) {
            playMusic(context, musicResId, true);
        } else {
            stopMusic();
        }
    }

    /**
     * Checks if music is currently playing.
     *
     * @return True if music is playing, false otherwise.
     */
    public boolean isPlaying() {
        return isMusicPlaying;
    }
}
