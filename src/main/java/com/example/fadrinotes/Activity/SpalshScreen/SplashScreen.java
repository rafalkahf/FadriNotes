package com.example.fadrinotes.Activity.SpalshScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fadrinotes.Activity.ActivityMain.MainActivity;
import com.example.fadrinotes.Activity.AuthentUser.AuthAct.AuthActivity;
import com.example.fadrinotes.R;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("LocalData", MODE_PRIVATE);

        new Handler().postDelayed(() -> {
            String username = sharedPreferences.getString("userName", "");

            Intent intent;
            if (!username.isEmpty()) {
                intent = new Intent(SplashScreen.this, MainActivity.class);
            } else {
                intent = new Intent(SplashScreen.this, AuthActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

}