package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    TextView timerSliderVal;
    Slider timerSlider;
    Button startTimerButton;
    CountDownTimer timer;
    private int timeInMinutes;
    private int timeInMilliseconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timerSliderVal = findViewById(R.id.timer_slider_val);
        timerSlider = findViewById(R.id.timer_slider);
        startTimerButton = findViewById(R.id.timer_start_button);

        timerSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                value = Math.round(value);
                int intVal = (int)value; // pass this value to create the timer
                timeInMinutes = intVal;
                timerSliderVal.setText(Integer.toString(intVal));
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //set timer and switch screen to main
                timeInMilliseconds = timeInMinutes * 60000;
                startTime();
            }
        });

    }

    private void startTime() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                //countdownTimer.setText(timeFormatted); // do this in main somehow

            }

            @Override
            public void onFinish() {
                //countdownTimer.setText("00:00:00");
                Toast.makeText(TimerActivity.this, "Time's up!", Toast.LENGTH_SHORT).show(); //change to main

            }
        };
    }


}