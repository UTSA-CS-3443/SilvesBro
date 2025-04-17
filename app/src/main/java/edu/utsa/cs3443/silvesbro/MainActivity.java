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

    private TextView happinessDisplay;
    private TextView nameDisplay;
    private TextView timerDisplay;
    private CountDownTimer timer;
    private TextView countdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProfile = new UserProfile();
        userProfile.loadProfile(this);

        WardrobeItem defaultOutfit = new WardrobeItem(1, "Blue Party Hat", R.drawable.blue_party_hat);
        character = new Character(userProfile.getHappinessLvl(), defaultOutfit, userProfile.isSwaggerModeOn());

        //happinessDisplay = findViewById(R.id.happiness_display);   // Add these later
        nameDisplay = findViewById(R.id.name_display);
        //timerDisplay = findViewById(R.id.timer_display);           // Add these later

        updateDisplayName(userProfile.getName());
        //updateHappinessMeter();
        //updateDisplayTimer();

        ImageButton dewButton = findViewById(R.id.bt_dew);
        dewButton.setOnClickListener(v -> handleFeedButton());

        ImageButton settingsButton = findViewById(R.id.bt_settings);
        ImageButton homeworkButton = findViewById(R.id.bt_homework);
        ImageButton timerButton = findViewById(R.id.bt_timer);
        ImageButton wardrobeButton = findViewById(R.id.bt_wardrobe);
        // below views are for the timer
        countdownTimer = findViewById(R.id.countdown_timer);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 1);
        });        homeworkButton.setOnClickListener(v -> launchActivity("homework"));
        timerButton.setOnClickListener(v -> launchActivity("timer"));
        wardrobeButton.setOnClickListener(v -> launchActivity("wardrobe"));

        SoundManager soundManager = SoundManager.getInstance();
        if (userProfile.isMusicOn()) {
            soundManager.playMusic(this, R.raw.bg_music_1, true);
        }

        //check if timer returned a duration
        if(getIntent().hasExtra("TIMER_DURATION_MILLIS")) {
            long durationMillis = getIntent().getLongExtra("TIMER_DURATION_MILLIS", 0);
            System.out.println("duration: " + durationMillis);
            startTime(durationMillis);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Reload profile to update all fields
            userProfile.loadProfile(this);

            updateDisplayName(userProfile.getName());

            if (userProfile.isMusicOn()) {
                SoundManager.getInstance().playMusic(this, R.raw.bg_music_1, true);
            } else {
                SoundManager.getInstance().stopMusic();
            }
        }
    }
    public void handleFeedButton() {
        character.feed();
        userProfile.addMountainDew(1);
        //updateHappinessMeter();
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


    /*
    public void updateHappinessMeter() {
        int happiness = character.getHappinessLvl();
        happinessDisplay.setText("Happiness: " + happiness);
    }

    public void updateDisplayTimer() {
        timerDisplay.setText("Study Time: " + userProfile.getTotalStudyTime() + " min");
    }
    */

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
                countdownTimer.setText(timeFormatted); // do this in main somehow

            }

            @Override
            public void onFinish() {
                countdownTimer.setText("00:00:00");
                Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();

            }
        }.start();
    }

}
