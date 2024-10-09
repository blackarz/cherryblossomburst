package com.cherry.blossom.burst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelSelectScreen extends AppCompatActivity {

    ImageView BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select_screen);

        BackBtn = findViewById(R.id.BackBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectScreen.this, Menu.class);
                startActivity(intent);
            }
        });

// Set up click listeners for each level ImageView
        setupLevelClick(R.id.level1, 1);
        setupLevelClick(R.id.level2, 2);
        setupLevelClick(R.id.level3, 3);
        setupLevelClick(R.id.level4, 4);
        setupLevelClick(R.id.level5, 5);
        setupLevelClick(R.id.level6, 6);
        setupLevelClick(R.id.level7, 7);
        setupLevelClick(R.id.level8, 8);
        setupLevelClick(R.id.level9, 9);
        setupLevelClick(R.id.level10, 10);
        setupLevelClick(R.id.level11, 11);
        setupLevelClick(R.id.level12, 12);
        // Add similar setup for other levels (5 to 12)
    }

    private void setupLevelClick(int imageViewId, final int level) {
        ImageView levelImageView = findViewById(imageViewId);
        levelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game activity and pass the selected level
                Intent intent = new Intent(LevelSelectScreen.this, MainActivity.class);
                intent.putExtra("LEVEL", level); // Pass the selected level to GameActivity
                startActivity(intent);
            }
        });
    }
}