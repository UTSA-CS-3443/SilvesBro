package edu.utsa.cs3443.silvesbro;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_SETTINGS  = 1;
    private static final int REQ_WARDROBE = 2;

    private Character character;
    private UserProfile userProfile;

    private TextView nameDisplay;
    private TextView countdownTimer;
    private LinearLayout timerButtons;
    private Button pauseTimerButton;
    private Button cancelTimerButton;
    private ImageView hatOverlay;
    private CountDownTimer timer;
    private TextView bottleCount;
    private boolean timerRunning  = false;
    private boolean dewOnCooldown = false;
    private long timeLeft;
    private ProgressBar happinessBar;

    private Handler happinessHandler;
    private Runnable happinessDecay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProfile = new UserProfile();
        userProfile.loadProfile(this);

        String selectedCharacter = userProfile.getSelectedCharacter();

        ImageView headTop = findViewById(R.id.sam_head_top);
        ImageView headBottom = findViewById(R.id.sam_head_bottom);
        ImageView body = findViewById(R.id.sam_body);

        updateCharacterSprites(userProfile.getSelectedCharacter());

        ConstraintLayout rootLayout = findViewById(R.id.main);
        if (userProfile.isSwaggerModeOn()) {
            rootLayout.setBackgroundResource(R.drawable.background_1);
        } else {
            rootLayout.setBackgroundResource(R.drawable.background);
        }

        int savedHap = userProfile.getHappinessLvl();
        int savedHatResId = userProfile.getSelectedHatResId();

        Log.d("MAIN", "onCreate() sees savedHap = " + savedHap);
        WardrobeItem outfit = new WardrobeItem(1, "restored", savedHatResId );

        character = new Character(savedHap, outfit, userProfile.isSwaggerModeOn());

        nameDisplay     = findViewById(R.id.name_display);
        countdownTimer  = findViewById(R.id.countdown_timer);
        timerButtons    = findViewById(R.id.timerButtons);
        pauseTimerButton= findViewById(R.id.pauseTimerButton);
        cancelTimerButton= findViewById(R.id.cancelTimerButton);
        hatOverlay      = findViewById(R.id.hat_overlay);
        bottleCount     = findViewById(R.id.bottle_count);

        updateDisplayName(userProfile.getName());
        bottleCount.setText(String.valueOf(userProfile.getDrinkCount()));
        hatOverlay.setImageResource(savedHatResId);
        hatOverlay.setVisibility(View.VISIBLE);

        ImageButton dewButton = findViewById(R.id.bt_dew);
        dewButton.setOnClickListener(v -> handleFeedButton());

        ImageButton settingsButton  = findViewById(R.id.bt_settings);
        ImageButton homeworkButton  = findViewById(R.id.bt_homework);
        ImageButton timerButton     = findViewById(R.id.bt_timer);
        ImageButton wardrobeButton  = findViewById(R.id.bt_wardrobe);

        settingsButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, REQ_SETTINGS);
        });
        homeworkButton.setOnClickListener(v -> launchActivity("homework"));
        timerButton.setOnClickListener(v -> launchActivity("timer"));
        wardrobeButton.setOnClickListener(v -> {
            Intent i = new Intent(this, WardrobeActivity.class);
            startActivityForResult(i, REQ_WARDROBE);
        });

        if (userProfile.isMusicOn()) {
            int musicRes = userProfile.isSwaggerModeOn() ? R.raw.bg_music_2 : R.raw.bg_music_1;
            SoundManager.getInstance().playMusic(this, musicRes, true);
        }

        if (getIntent().hasExtra("TIMER_DURATION_MILLIS")) {
            long dur = getIntent().getLongExtra("TIMER_DURATION_MILLIS", 0);
            startTime(dur);
        }

        ImageView samHead = findViewById(R.id.sam_head_top);

        ObjectAnimator headBob = ObjectAnimator.ofFloat(samHead, "translationY", 0f, -25f, 0f);
        headBob.setDuration(450);
        headBob.setRepeatCount(ObjectAnimator.INFINITE);
        headBob.setRepeatMode(ObjectAnimator.REVERSE);
        headBob.start();

        ObjectAnimator hatBob = ObjectAnimator.ofFloat(hatOverlay, "translationY", 0f, -25f, 0f);
        hatBob.setDuration(450);
        hatBob.setRepeatCount(ObjectAnimator.INFINITE);
        hatBob.setRepeatMode(ObjectAnimator.REVERSE);
        hatBob.start();

        ObjectAnimator headTilt = ObjectAnimator.ofFloat(samHead, "rotation", 0f, 4f, 0f);
        headTilt.setDuration(450);
        headTilt.setRepeatCount(ObjectAnimator.INFINITE);
        headTilt.setRepeatMode(ObjectAnimator.REVERSE);
        headTilt.start();

        ObjectAnimator hatTilt = ObjectAnimator.ofFloat(hatOverlay, "rotation", 0f, 4f, 0f);
        hatTilt.setDuration(450);
        hatTilt.setRepeatCount(ObjectAnimator.INFINITE);
        hatTilt.setRepeatMode(ObjectAnimator.REVERSE);
        hatTilt.start();

        happinessBar = findViewById(R.id.happiness_bar);
        happinessBar.setMax(100);
        happinessBar.setProgress(savedHap);
        Log.d("MAIN", "After setProgress, bar.getProgress() = " + happinessBar.getProgress());

        happinessHandler = new Handler(Looper.getMainLooper());

        happinessDecay = new Runnable() {
            @Override
            public void run() {
                // subtract 10 happiness
                updateHappiness(-10);
                // schedule again in 5 seconds
                happinessHandler.postDelayed(this, 5_000);
            }
        };

        happinessHandler.postDelayed(happinessDecay, 5_000);

        // pause/cancel timer
        cancelTimerButton.setOnClickListener(v -> cancelTimer());
        pauseTimerButton.setOnClickListener(v -> {
            if (timerRunning) pauseTimer();
            else startTime(timeLeft);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // avoid leaks: stop the periodic decay when the Activity is gone
        happinessHandler.removeCallbacks(happinessDecay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SETTINGS && resultCode == RESULT_OK) {
            boolean previousSwaggerMode = userProfile.isSwaggerModeOn();

            userProfile.loadProfile(this);

            updateDisplayName(userProfile.getName());

            if (userProfile.isMusicOn()) {
                boolean currentSwaggerMode = userProfile.isSwaggerModeOn();

                if (previousSwaggerMode != currentSwaggerMode) {
                    SoundManager.getInstance().stopMusic();
                    int newTrack = currentSwaggerMode ? R.raw.bg_music_2 : R.raw.bg_music_1;
                    SoundManager.getInstance().playMusic(this, newTrack, true);
                }
            } else {
                SoundManager.getInstance().stopMusic();
            }

            ConstraintLayout rootLayout = findViewById(R.id.main);
            rootLayout.setBackgroundResource(userProfile.isSwaggerModeOn()
                    ? R.drawable.background_1
                    : R.drawable.background);

            if (data != null && data.hasExtra("selectedCharacter")) {
                String selectedChar = data.getStringExtra("selectedCharacter");
                userProfile.setSelectedCharacter(selectedChar);
                updateCharacterSprites(selectedChar);
            }

        } else if (requestCode == REQ_WARDROBE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("selectedHat")) {
                int hatResId = data.getIntExtra("selectedHat", R.drawable.blue_party_hat);

                hatOverlay.setImageResource(hatResId);
                hatOverlay.setVisibility(View.VISIBLE);

                character.changeOutfit(new WardrobeItem(-1, "selected", hatResId));

                userProfile.setSelectedHatResId(hatResId);
                userProfile.saveProfile(this);
            }
        }
    }

    public void updateDrinkDisplay() {
        bottleCount.setText(String.valueOf(userProfile.getDrinkCount()));
    }

    // update necessary data after feeding/attempting to feed character
    public void handleFeedButton() {
        if (userProfile.getDrinkCount() > 0 || dewOnCooldown) {
            character.feed();
            updateHappiness(10);
            userProfile.subtractMountainDew(1);
            animateDewIntoMouth();
            updateDrinkDisplay();
        } else {
            Toast.makeText(this, "Need more Fountain Dude...", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchActivity(String screen) {
        Class<?> target = null;
        switch (screen) {
            case "settings": target = SettingsActivity.class; break;
            case "homework": target = TaskListActivity.class; break;
            case "timer":    target = TimerActivity.class;    break;
            case "wardrobe": target = WardrobeActivity.class; break;
        }
        if (target != null) startActivity(new Intent(this, target));
    }

    public void updateDisplayName(String name) {
        nameDisplay.setText(name);
    }

    // method to start the timer
    private void startTime(long duration) {
        countdownTimer.setVisibility(View.VISIBLE);
        timerButtons.setVisibility(View.VISIBLE);
        timerRunning = true;
        pauseTimerButton.setText("Pause");
        timer = new CountDownTimer(duration, 1000) {
            // update the timer every second
            @Override public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                long hrs = (millisUntilFinished / 1000) / 3600;
                long mins = ((millisUntilFinished / 1000) % 3600) / 60;
                long secs = (millisUntilFinished / 1000) % 60;
                String fmt = String.format(Locale.getDefault(), "%02d:%02d:%02d", hrs, mins, secs);
                countdownTimer.setText(fmt);
            }
            // handles the timer finishing
            @Override public void onFinish() {
                countdownTimer.setText("00:00:00");
                timerRunning = false;
                Toast.makeText(MainActivity.this, "Time's up!\n+1 Fountain Dude", Toast.LENGTH_SHORT).show();
                userProfile.addMountainDew(1);
                updateDrinkDisplay();
                // leaves the timer up for 5 seconds after completion
                new Handler().postDelayed(() -> {
                    countdownTimer.setVisibility(View.INVISIBLE);
                    timerButtons.setVisibility(View.INVISIBLE);
                }, 5000);
            }
        }.start();
    }
    // cancel timer method
    private void cancelTimer() {
        if (timer != null) timer.cancel();
        countdownTimer.setVisibility(View.INVISIBLE);
        timerButtons.setVisibility(View.INVISIBLE);
    }
    // pause timer method
    private void pauseTimer() {
        if (timer != null) timer.cancel();
        timerRunning = false;
        pauseTimerButton.setText("Start");
    }

    private void animateDewIntoMouth() {
        ImageView dewBottle   = findViewById(R.id.dew_btl);
        ImageView characterIV = findViewById(R.id.sam_head_bottom);
        ImageButton dewButton = findViewById(R.id.bt_dew);

        if (dewOnCooldown) {
            Toast.makeText(this, "Let Silvesbro catch up...", Toast.LENGTH_SHORT).show();
            return;
        }
        dewOnCooldown = true;
        dewBottle.setVisibility(View.VISIBLE);

        int[] btnLoc = new int[2];
        dewButton.getLocationOnScreen(btnLoc);
        float startX = btnLoc[0] + dewButton.getWidth()  / 2f;
        float startY = btnLoc[1] + dewButton.getHeight() / 2f;

        int[] charLoc = new int[2];
        characterIV.getLocationOnScreen(charLoc);
        float targetX = charLoc[0] + characterIV.getWidth()  / 2f;
        float targetY = charLoc[1] + characterIV.getHeight() / 3f;

        float dx = targetX - startX, dy = targetY - startY;

        dewBottle.animate()
                .translationXBy(dx)
                .translationYBy(dy)
                .setDuration(1000)
                .withEndAction(() -> {
                    dewBottle.setVisibility(View.INVISIBLE);
                    dewBottle.animate()
                            .translationXBy(-dx)
                            .translationYBy(-dy)
                            .setDuration(500)
                            .withEndAction(() -> dewBottle.setVisibility(View.INVISIBLE))
                            .start();
                    new Handler().postDelayed(() -> dewOnCooldown = false, 500);
                })
                .start();
    }

    private void updateHappiness(int delta) {
        int before = character.getHappinessLvl();
        Log.d("HAPPY", "Before delta=" + delta + ", happiness = " + before);

        if (delta > 0)
            character.increaseHappiness(delta);
        else
            character.decreaseHappiness(-delta);

        int lvl = character.getHappinessLvl();
        Log.d("HAPPY", "After change, happiness = " + lvl);

        happinessBar.setProgress(lvl);
        Log.d("HAPPY", "After bar.setProgress, bar.getProgress() = " + happinessBar.getProgress());

        userProfile.setHappinessLvl(lvl);
        userProfile.saveProfile(this);
    }

    private void updateCharacterSprites(String character) {
        ImageView headTop = findViewById(R.id.sam_head_top);
        ImageView headBottom = findViewById(R.id.sam_head_bottom);
        ImageView body = findViewById(R.id.sam_body);

        boolean isSwag = userProfile.isSwaggerModeOn();

        switch (character) {
            case "Hend Alkitawi":
                headTop.setImageResource(isSwag ? R.drawable.hend_head_top_swag : R.drawable.hend_head_top);
                headBottom.setImageResource(R.drawable.hend_head_bottom);
                body.setImageResource(R.drawable.hend_body);
                break;
            case "Samuel Ang":
                headTop.setImageResource(isSwag ? R.drawable.ang_head_top_swag : R.drawable.ang_head_top);
                headBottom.setImageResource(R.drawable.ang_head_bottom);
                body.setImageResource(R.drawable.sam_body);
                break;
            default: // Sam Silvestro
                headTop.setImageResource(isSwag ? R.drawable.sam_head_top_swag : R.drawable.sam_head_top);
                headBottom.setImageResource(R.drawable.sam_head_bottom);
                body.setImageResource(R.drawable.sam_body);
                break;
        }
    }


}
