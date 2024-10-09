package com.cherry.blossom.burst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView BackBtn;

        Switch musicSwitch;
        Switch soundEffectsSwitch;
        SharedPreferences preferences;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        BackBtn = findViewById(R.id.BackBtn);


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        preferences = getSharedPreferences("settings", MODE_PRIVATE);

        // Initialize switches
        musicSwitch = findViewById(R.id.musicSwitch);
        soundEffectsSwitch = findViewById(R.id.soundEffectsSwitch);

        // Set initial switch states based on preferences
        musicSwitch.setChecked(preferences.getBoolean("musicOn", true));
        soundEffectsSwitch.setChecked(preferences.getBoolean("soundEffectsOn", true));

        // Music switch listener
        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("musicOn", isChecked).apply();
            Intent intent = new Intent("UPDATE_MUSIC_PREF");
            sendBroadcast(intent);
        });

        // Sound effects switch listener
        soundEffectsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("soundEffectsOn", isChecked).apply();
            Intent intent = new Intent("UPDATE_SOUND_EFFECTS_PREF");
            sendBroadcast(intent);
        });
    }
}
