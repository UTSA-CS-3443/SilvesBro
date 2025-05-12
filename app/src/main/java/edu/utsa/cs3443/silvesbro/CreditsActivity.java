package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * CreditsActivity displays a static screen showing the app's creators,
 * special thanks, and music attributions. This activity is purely UI-based
 * and serves as a fun tribute screen for the SilvesBro app.
 */
public class CreditsActivity extends AppCompatActivity {

    /**
     * Called when the CreditsActivity is first created.
     * Loads the layout defined in activity_credits.xml.
     *
     * @param savedInstanceState The previously saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }
}
