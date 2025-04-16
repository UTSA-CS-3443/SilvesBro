package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class TimerActivity extends AppCompatActivity {

    TextView timerSliderVal;
    Slider timerSlider;
    Button startTimerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer); // make sure this matches your XML file
        timerSliderVal = findViewById(R.id.timer_slider_val);
        timerSlider = findViewById(R.id.timer_slider);

        timerSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                value = Math.round(value);
                int intVal = (int)value; // pass this value to create the timer
                timerSliderVal.setText(Integer.toString(intVal));
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //set timer and switch screen to main
            }
        });

    }
}