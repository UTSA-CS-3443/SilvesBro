package edu.utsa.cs3443.silvesbro;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Character character;
    private UserProfile userProfile;

    private TextView nameDisplay;
    private TextView countdownTimer;
    private ImageView hatOverlay;
    private CountDownTimer timer;
<<<<<<< Updated upstream
    private TextView bottleCount;
    private boolean timerPaused = false;
    private boolean timerCanceled = false;
=======
    private boolean dewOnCooldown = false;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProfile = new UserProfile();
        userProfile.loadProfile(this);

        WardrobeItem defaultOutfit = new WardrobeItem(1, "Blue Party Hat", R.drawable.blue_party_hat);
        character = new Character(userProfile.getHappinessLvl(), defaultOutfit, userProfile.isSwaggerModeOn());

        nameDisplay = findViewById(R.id.name_display);
        countdownTimer = findViewById(R.id.countdown_timer);
        hatOverlay = findViewById(R.id.hat_overlay);

        updateDisplayName(userProfile.getName());
        hatOverlay.setImageResource(defaultOutfit.getImageResource());
        hatOverlay.setVisibility(View.VISIBLE);

        ImageButton dewButton = findViewById(R.id.bt_dew);
        dewButton.setOnClickListener(v -> handleFeedButton());

        ImageButton settingsButton = findViewById(R.id.bt_settings);
        ImageButton homeworkButton = findViewById(R.id.bt_homework);
        ImageButton timerButton = findViewById(R.id.bt_timer);
        ImageButton wardrobeButton = findViewById(R.id.bt_wardrobe);

        bottleCount = findViewById(R.id.bottle_count);
        bottleCount.setText(Integer.toString(userProfile.getDrinkCount()));

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 1);
        });

        homeworkButton.setOnClickListener(v -> launchActivity("homework"));
        timerButton.setOnClickListener(v -> launchActivity("timer"));
        wardrobeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WardrobeActivity.class);
            startActivityForResult(intent, 2);
        });

        SoundManager soundManager = SoundManager.getInstance();
        if (userProfile.isMusicOn()) {
            soundManager.playMusic(this, R.raw.bg_music_1, true);
        }

        if(getIntent().hasExtra("TIMER_DURATION_MILLIS")) {
            long durationMillis = getIntent().getLongExtra("TIMER_DURATION_MILLIS", 0);
            startTime(durationMillis);
        }

        ImageView samHead = findViewById(R.id.sam_head_top);
        ImageView hatOverlay = findViewById(R.id.hat_overlay);

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



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            userProfile.loadProfile(this);
            updateDisplayName(userProfile.getName());

            if (userProfile.isMusicOn()) {
                SoundManager.getInstance().playMusic(this, R.raw.bg_music_1, true);
            } else {
                SoundManager.getInstance().stopMusic();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("selectedHat")) {
                int hatResId = data.getIntExtra("selectedHat", R.drawable.blue_party_hat);
                hatOverlay.setImageResource(hatResId);
                hatOverlay.setVisibility(View.VISIBLE);
            }
        }
    }

    public void updateDrinkDisplay() {
        bottleCount.setText(Integer.toString(userProfile.getDrinkCount()));
    }

    public void handleFeedButton() {
<<<<<<< Updated upstream
        if (userProfile.getDrinkCount() > 0) {
            character.feed();
            userProfile.subtractMountainDew(1);
            updateDrinkDisplay();
            Toast.makeText(this, "SilvesBro chugged that Fountain Dude", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Need more Fountain Dude...", Toast.LENGTH_SHORT).show();
        }
=======
        character.feed();
        userProfile.addMountainDew(1);
        animateDewIntoMouth();
        Toast.makeText(this, "SilvesBro chugged that Fountain Dude", Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes
    }

    private void launchActivity(String screen) {
        Class<?> target = null;

        switch (screen) {
            case "settings":
                target = SettingsActivity.class;
                break;
            case "homework":
                target = TaskListActivity.class;
                break;
            case "timer":
                target = TimerActivity.class;
                break;
            case "wardrobe":
                target = WardrobeActivity.class;
                break;
        }

        if (target != null) {
            Intent intent = new Intent(MainActivity.this, target);
            startActivity(intent);
        }
    }

    public void updateDisplayName(String name) {
        nameDisplay.setText(name);
    }

    // starts the timer
    private void startTime(long duration) {
        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                countdownTimer.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                countdownTimer.setText("00:00:00");
                Toast.makeText(MainActivity.this, "Time's up!\n+1 Fountain Dude", Toast.LENGTH_SHORT).show();
                userProfile.addMountainDew(1);
                updateDrinkDisplay();
                // handler allows the timer text to disappear after 5 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        countdownTimer.setText("");
                    }
                }, 5000);
            }
        }.start();
    }



    private void animateDewIntoMouth() {
        ImageView dewBottle = findViewById(R.id.dew_btl);
        ImageView character = findViewById(R.id.sam_head_bottom);
        ImageButton dewButton = findViewById(R.id.bt_dew);

        if (dewOnCooldown) {
            Toast.makeText(this, "Let Silvesbro catch up...", Toast.LENGTH_SHORT).show();
            return;
        }

        dewOnCooldown = true;

        dewBottle.setVisibility(View.VISIBLE);

        int[] buttonLocation = new int[2];
        dewButton.getLocationOnScreen(buttonLocation);
        float startX = buttonLocation[0] + dewButton.getWidth() / 2f;
        float startY = buttonLocation[1] + dewButton.getHeight() / 2f;


        int[] characterLocation = new int[2];
        character.getLocationOnScreen(characterLocation);
        float targetX = characterLocation[0] + character.getWidth() / 2f;
        float targetY = characterLocation[1] + character.getHeight() / 3f;

        float deltaX = targetX - startX;
        float deltaY = targetY - startY;

        dewBottle.animate()
                .translationXBy(deltaX)
                .translationYBy(deltaY)
                .setDuration(1000)
                .withEndAction(() -> {

                    dewBottle.setVisibility(View.INVISIBLE);

                    dewBottle.animate()
                            .translationXBy(-deltaX)
                            .translationYBy(-deltaY)
                            .setDuration(1000)
                            .withEndAction(() -> {
                                dewBottle.setVisibility(View.INVISIBLE);
                            })
                            .start();

                    dewBottle.postDelayed(() -> {
                        dewOnCooldown = false;
                    }, 3000); // cooldown duration in ms
                })
                .start();
    }
}
