package com.cherry.blossom.burst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    ImageView PlayBtn,SettingsBtn,ExtBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        PlayBtn = findViewById(R.id.PlayBtn);
        SettingsBtn = findViewById(R.id.SettingsBtn);
        ExtBtn = findViewById(R.id.ExtBtn);

        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, LevelSelectScreen.class);
                startActivity(intent);
            }
        });

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, SettingsScreen.class);
                startActivity(intent);
            }
        });

        ExtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom layout
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_custom, null);

                // Create the dialog
                AlertDialog dialog = new AlertDialog.Builder(Menu.this) // Replace with your activity name
                        .setView(dialogView)
                        .setCancelable(false) // Optional: prevents dismissal on outside touch
                        .create();

                // Set button actions
                ImageView btnYes = dialogView.findViewById(R.id.btn_yes);
                ImageView btnNo = dialogView.findViewById(R.id.btn_no);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);

                        intent.addCategory(Intent.CATEGORY_HOME);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // Show the dialog
                dialog.show();
            }
        });


    }
}