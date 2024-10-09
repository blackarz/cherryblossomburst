package com.cherry.blossom.burst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelFailedScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_failed_screen);

        // Retry the same level
        ImageView retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelFailedScreen.this, MainActivity.class);
                intent.putExtra("LEVEL", getIntent().getIntExtra("LEVEL", 1));  // Retry current level
                startActivity(intent);
                finish();  // Close the failed screen
            }
        });
    }
}
