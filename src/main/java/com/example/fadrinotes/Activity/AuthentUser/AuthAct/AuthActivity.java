package com.example.fadrinotes.Activity.AuthentUser.AuthAct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fadrinotes.Activity.AuthentUser.fragment.SignInAuth;
import com.example.fadrinotes.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        SignInAuth signInAuth = new SignInAuth();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.myFrame, signInAuth).commit();
        }

}