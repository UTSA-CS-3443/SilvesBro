package edu.utsa.cs3443.silvesbro;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView samHeadTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton settingsButton = findViewById(R.id.bt_settings);
        ImageButton dewButton = findViewById(R.id.bt_dew);
        ImageButton homeworkButton = findViewById(R.id.bt_homework);
        ImageButton timerButton = findViewById(R.id.bt_timer);
        ImageButton wardrobeButton = findViewById(R.id.bt_wardrobe);


        samHeadTop = findViewById(R.id.sam_head_top);

        // up and down motion placeholder
        ObjectAnimator talkAnim = ObjectAnimator.ofFloat(samHeadTop, "translationY", 0f, -40f, 0f);
        talkAnim.setDuration(300);
        talkAnim.setRepeatMode(ValueAnimator.RESTART);
        talkAnim.setRepeatCount(ValueAnimator.INFINITE);

        talkAnim.start();
    }
}