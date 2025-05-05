package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat musicButton;
    private SwitchCompat soundButton;
    private SwitchCompat swaggerButton;
    private EditText    nameEdit;
    private Button      creditsButton;
    private Button      saveButton;

    private UserProfile profile = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameEdit       = findViewById(R.id.edittext_name);
        musicButton    = findViewById(R.id.switch_music);
        soundButton    = findViewById(R.id.switch_sfx);
        swaggerButton  = findViewById(R.id.switch_swagger);
        creditsButton  = findViewById(R.id.button_credits);
        saveButton     = findViewById(R.id.button_save);

        profile.loadProfile(this);
        nameEdit.setText(profile.getName());
        musicButton.setChecked(profile.isMusicOn());
        soundButton.setChecked(profile.isSoundOn());
        swaggerButton.setChecked(profile.isSwaggerModeOn());

        saveButton.setOnClickListener(v -> saveSettings());
        creditsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CreditsActivity.class));
        });
    }

    private void saveSettings() {
        String name = nameEdit.getText().toString().trim();
        Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();

        profile.setName(name);
        profile.setMusicOn(musicButton.isChecked());
        profile.setSoundOn(soundButton.isChecked());
        profile.setSwaggerMode(swaggerButton.isChecked());
        profile.saveProfile(this);

        Intent result = new Intent();
        result.putExtra("name", name);
        result.putExtra("music", musicButton.isChecked());
        setResult(RESULT_OK, result);
        finish();
    }
}
