package com.cherry.blossom.burst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class WinScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        // Play again button to go to the next level
        ImageView nextLevelButton = findViewById(R.id.nextLevelButton);
        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase level and restart the game with the new level
                Intent intent = new Intent(WinScreen.this, MainActivity.class);
                intent.putExtra("LEVEL", getIntent().getIntExtra("LEVEL", 1) + 1);  // Go to next level
                startActivity(intent);
                finish();  // Close the win screen
            }
        });

        // Retrieve the score from the Intent
        int score = getIntent().getIntExtra("SCORE", 0);

        // Find the TextView and set the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

    }
}
