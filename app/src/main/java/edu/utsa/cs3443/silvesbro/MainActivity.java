package edu.utsa.cs3443.silvesbro;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
        ImageView hatOverlay = findViewById(R.id.hat_overlay); // optional: move hat too

        ObjectAnimator headBob = ObjectAnimator.ofFloat(samHead, "translationY", 0f, -25f, 0f);
        headBob.setDuration(450);
        headBob.setRepeatCount(ObjectAnimator.INFINITE);
        headBob.setRepeatMode(ObjectAnimator.REVERSE);
        headBob.start();

// OPTIONAL: move hat with head
        ObjectAnimator hatBob = ObjectAnimator.ofFloat(hatOverlay, "translationY", 0f, -25f, 0f);
        hatBob.setDuration(450);
        hatBob.setRepeatCount(ObjectAnimator.INFINITE);
        hatBob.setRepeatMode(ObjectAnimator.REVERSE);
        hatBob.start();

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

    public void handleFeedButton() {
        character.feed();
        userProfile.addMountainDew(1);
        Toast.makeText(this, "SilvesBro chugged that Fountain Dude", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}
