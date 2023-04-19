package com.example.mymedicare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);
        int defaultThemeValue = getResources().getInteger(R.integer.saved_theme_default_key);
        int themeVal = sharedPref.getInt(getString(R.string.saved_theme_key), defaultThemeValue);

        if (themeVal == getResources().getInteger(R.integer.saved_theme_blue_key)) {
            setTheme(R.style.AppBlueTheme);
        }
        else if (themeVal == getResources().getInteger(R.integer.saved_theme_green_key)) {
            setTheme(R.style.AppGreenTheme);
        }
        else if (themeVal == getResources().getInteger(R.integer.saved_theme_yellow_key)) {
            setTheme(R.style.AppYellowTheme);
        }
        else if (themeVal == getResources().getInteger(R.integer.saved_theme_orange_key)) {
            setTheme(R.style.AppOrangeTheme);
        }
        else if (themeVal == getResources().getInteger(R.integer.saved_theme_purple_key)) {
            setTheme(R.style.AppPurpleTheme_);
        }
        else if (themeVal == getResources().getInteger(R.integer.saved_theme_red_key)) {
            setTheme(R.style.AppRedTheme);
        }

        Button btnBlue = (Button)findViewById(R.id.btnStyleBlue);
        btnBlue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_theme_key), getResources().getInteger(R.integer.saved_theme_blue_key));
                editor.apply();

                recreate();

                ; }
        });

        Button btnDelAcc = (Button) findViewById(R.id.btnDeleteAccount);

        btnDelAcc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();

            }
        });
    }
}