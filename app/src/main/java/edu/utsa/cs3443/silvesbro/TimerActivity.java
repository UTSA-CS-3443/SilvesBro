package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class TimerActivity extends AppCompatActivity {

    TextView timerSliderVal;
    Slider timerSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer); // make sure this matches your XML file
        timerSliderVal = findViewById(R.id.timer_slider_val);
        timerSlider = findViewById(R.id.timer_slider);

        timerSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                
            }
        });

    }
}