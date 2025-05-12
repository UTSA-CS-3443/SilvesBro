package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
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
    private int timeInMinutes;
    private long timeInMilliseconds;


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
                // pass this value to create the timer
                int intVal = (int)value;
                timeInMinutes = intVal;
                timerSliderVal.setText(Integer.toString(intVal));
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass time in milliseconds to main to start timer
                timeInMilliseconds = timeInMinutes * 60000;
                if (timeInMilliseconds == 0) {
                    Toast.makeText(TimerActivity.this, "Must enter a time!", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println(timeInMilliseconds);
                    Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                    intent.putExtra("TIMER_DURATION_MILLIS", timeInMilliseconds);
                    startActivity(intent);
                }
            }
        });

    }

}