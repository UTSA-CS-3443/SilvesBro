package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private boolean isSoundOn;
    private boolean isMusicOn;
    private boolean isSwaggerMode;

    private SwitchCompat musicButton;
    private SwitchCompat soundButton;
    private SwitchCompat swaggerButton;

    private Button creditsButton;
    private Button saveButton;

    private EditText nameEdit;

    UserProfile profile = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameEdit = findViewById(R.id.edittext_name);
        musicButton = findViewById(R.id.switch_music);
        soundButton = findViewById(R.id.switch_sfx);
        swaggerButton = findViewById(R.id.switch_swagger);
        creditsButton = findViewById(R.id.button_credits);
        saveButton = findViewById(R.id.button_save);

        profile.loadProfile(this);

        musicButton.setChecked(profile.isMusicOn());
        soundButton.setChecked(profile.isSoundOn());
        nameEdit.setText(profile.getName());

        musicButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleMusic());
        soundButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleSound());
        swaggerButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleSwaggerMode());

        saveButton.setOnClickListener(v -> saveSettings());
        creditsButton.setOnClickListener(v -> navigateCredits());
    }

    public void toggleSound() {
        isSoundOn = soundButton.isChecked();
    }

    public void toggleMusic() {
        isMusicOn = musicButton.isChecked();
    }

    public void toggleSwaggerMode() {
        isSwaggerMode = swaggerButton.isChecked();
    }

    public void saveSettings() {
        String name = nameEdit.getText().toString().trim();
        Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        profile.setMusicOn(musicButton.isChecked());
        profile.setSoundOn(soundButton.isChecked());
        profile.setSwaggerMode(swaggerButton.isChecked());
        profile.addName(name);
        profile.saveProfile(this);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("music", musicButton.isChecked());
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void navigateCredits() {
        Intent intent = new Intent(SettingsActivity.this, CreditsActivity.class);
        startActivity(intent);
    }
}
