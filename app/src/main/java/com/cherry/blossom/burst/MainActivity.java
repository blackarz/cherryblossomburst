package com.cherry.blossom.burst;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView SettingsBtn;

    private SoundPool soundPool;
    private int clickSound;
    private boolean isSoundEffectsOn;

    private MediaPlayer backgroundMusic;
    private boolean isMusicOn;

    private SharedPreferences preferences;

    private ImageView BackBtn;
    private RelativeLayout gameLayout;
    private TextView scoreTextView;
    private TextView levelTextView;
    private int score = 0;
    private int level = 1;
    private Random random = new Random();
    private Handler handler = new Handler();
    private int targetScore = 50;  // Score required to complete the level
    private int lives = 3;         // Number of lives (could be reduced by bombs)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackBtn = findViewById(R.id.BackBtn);
        SettingsBtn = findViewById(R.id.SettingsBtn);

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsScreen.class);
                startActivity(intent);
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        // Load settings from SharedPreferences
        preferences = getSharedPreferences("settings", MODE_PRIVATE);
        isMusicOn = preferences.getBoolean("musicOn", true);
        isSoundEffectsOn = preferences.getBoolean("soundEffectsOn", true);

        // Initialize background music and sound effects
        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusic.setLooping(true);  // Loop the music
        if (isMusicOn) {
            backgroundMusic.start();  // Start music
        }

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        clickSound = soundPool.load(this, R.raw.click_sound, 1);

        // Register broadcast receivers
        registerReceiver(musicPrefReceiver, new IntentFilter("UPDATE_MUSIC_PREF"), null, null);
        registerReceiver(soundEffectsPrefReceiver, new IntentFilter("UPDATE_SOUND_EFFECTS_PREF"), null, null);





        // Initialize views
        gameLayout = findViewById(R.id.gameLayout);
        scoreTextView = findViewById(R.id.scoreTextView);
        levelTextView = findViewById(R.id.levelTextView);

        // Get level from intent
        level = getIntent().getIntExtra("LEVEL", 1);
        updateLevel();

        // Start the game by generating elements
        startGame();
    }

    private void startGame() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateElement();
                handler.postDelayed(this, 1000);  // Continue generating elements with a delay
            }
        }, 1000);
    }

    private void generateElement() {
        boolean isBomb = random.nextBoolean();

        // Create a new ImageView for the element
        final ImageView element = new ImageView(this);
        element.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));

        if (isBomb) {
            element.setImageResource(R.drawable.bomb);  // Bomb image
        } else {
            int[] gems = {R.drawable.gem_red, R.drawable.gem_blue, R.drawable.gem_green, R.drawable.gem_yellow, R.drawable.gem_pink};
            element.setImageResource(gems[random.nextInt(gems.length)]);  // Random gem image
        }

        // Set random position at the top
        int randomX = random.nextInt(gameLayout.getWidth() - 100);
        element.setX(randomX);
        element.setY(0);

        gameLayout.addView(element);

        // Animate the element falling down
        ObjectAnimator fallAnimator = ObjectAnimator.ofFloat(element, "translationY", gameLayout.getHeight());
        fallAnimator.setDuration(3000);
        fallAnimator.setInterpolator(new LinearInterpolator());
        fallAnimator.start();

        // Handle click events to destroy the element
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play click sound if sound effects are enabled
                if (isSoundEffectsOn) {
                    soundPool.play(clickSound, 1, 1, 0, 0, 1);
                }

                gameLayout.removeView(element);  // Remove element when clicked
                if (isBomb) {
                    // Bomb clicked, go to fail screen
                    handleFail();
                } else {
                    // Gem clicked, increase score
                    score += 10;
                    updateScore();
                    if (score >= targetScore) {
                        handleWin();
                    }
                }
            }
        });

        // Remove the element if it reaches the bottom
        fallAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gameLayout.removeView(element);
            }
        });
    }

    private void handleWin() {
        // Go to win screen
        Intent intent = new Intent(MainActivity.this, WinScreen.class);
        intent.putExtra("LEVEL", level);
        intent.putExtra("SCORE", score);  // Pass the score to the win screen
        startActivity(intent);
        finish();  // Close the game activity
    }


    private void handleFail() {
        // Go to fail screen
        Intent intent = new Intent(MainActivity.this, LevelFailedScreen.class);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
        finish();  // Close the game activity
    }

    private void updateScore() {
        // Update the score and level display
        scoreTextView.setText("Score: " + score);
        levelTextView.setText("Level: " + level);
    }

    private void updateLevel() {
        // Update level text view with current level
        levelTextView.setText("Level: " + level);
    }



    // BroadcastReceiver to update music preferences dynamically
    private BroadcastReceiver musicPrefReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("UPDATE_MUSIC_PREF")) {
                isMusicOn = preferences.getBoolean("musicOn", true);
                if (isMusicOn) {
                    backgroundMusic.start();
                } else {
                    backgroundMusic.pause();
                }
            }
        }
    };

    // BroadcastReceiver to update sound effects preferences dynamically
    private final BroadcastReceiver soundEffectsPrefReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "UPDATE_SOUND_EFFECTS_PREF")) {
                isSoundEffectsOn = preferences.getBoolean("soundEffectsOn", true);
                if (isSoundEffectsOn) {
                    soundPool.autoResume();
                } else {
                    soundPool.autoPause();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMusicOn && backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (backgroundMusic != null) {
            backgroundMusic.release();  // Release media player resources
        }

        if (soundPool != null) {
            soundPool.release();  // Release SoundPool resources
        }

        unregisterReceiver(musicPrefReceiver);
        unregisterReceiver(soundEffectsPrefReceiver);
    }




}
