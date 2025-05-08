package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    private Spinner characterSpinner;

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
        characterSpinner = findViewById(R.id.character_spinner);

        profile.loadProfile(this);
        nameEdit.setText(profile.getName());
        musicButton.setChecked(profile.isMusicOn());
        soundButton.setChecked(profile.isSoundOn());
        swaggerButton.setChecked(profile.isSwaggerModeOn());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.character_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterSpinner.setAdapter(adapter);

        int index = adapter.getPosition(profile.getSelectedCharacter());
        if (index >= 0) {
            characterSpinner.setSelection(index);
        }

        saveButton.setOnClickListener(v -> saveSettings());
        creditsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CreditsActivity.class));
        });
    }

    private void saveSettings() {
        String name = nameEdit.getText().toString().trim();
        String selectedChar = characterSpinner.getSelectedItem().toString();

        profile.setName(name);
        profile.setMusicOn(musicButton.isChecked());
        profile.setSoundOn(soundButton.isChecked());
        profile.setSwaggerMode(swaggerButton.isChecked());
        profile.setSelectedCharacter(selectedChar);
        profile.saveProfile(this);

        Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();

        Intent result = new Intent();
        result.putExtra("name", name);
        result.putExtra("music", musicButton.isChecked());
        result.putExtra("selectedCharacter", selectedChar);
        setResult(RESULT_OK, result);
        finish();
    }
}
