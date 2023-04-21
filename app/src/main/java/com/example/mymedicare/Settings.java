package com.example.mymedicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

        //get shared preferences
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);
        int defaultThemeValue = getResources().getInteger(R.integer.saved_theme_default_key);
        int themeVal = sharedPref.getInt(getString(R.string.saved_theme_key), defaultThemeValue);

        //get UI elements
        Button btnBlue = (Button)findViewById(R.id.btnStyleBlue);
        Button btnDelAcc = (Button) findViewById(R.id.btnDeleteAccount);
        Button delReadings = (Button) findViewById(R.id.btnDeleteReadings);

        // Intended to be used to set an activities theme on creation, not working
        /*
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
         */

        //set theme to blue, not working
        btnBlue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_theme_key), getResources().getInteger(R.integer.saved_theme_blue_key));
                editor.apply();

                recreate();

                ; }
        });

        //delete all data from the shared preferences file
        //confirm user's intention
        //clear file
        //inform user of success
        btnDelAcc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            public void onClick(View v) {

                try {

                    AlertDialog.Builder delete =
                            new AlertDialog.Builder(Settings.this)
                                    .setTitle("Delete User Details")
                                    .setMessage("Are you sure you would like to delete your details from the app?")
                                    .setPositiveButton(
                                            "YES",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.clear();
                                                    editor.commit();

                                                    AlertDialog.Builder delete =
                                                            new AlertDialog.Builder(Settings.this)
                                                                    .setTitle("User Details Deleted")
                                                                    .setMessage("Your details were successfully deleted.")
                                                                    .setPositiveButton(
                                                                            "OK",
                                                                            new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int id) {
                                                                                    dialog.cancel();
                                                                                }
                                                                            });

                                                    delete.show();

                                                    dialog.cancel();
                                                }
                                            })
                                    .setNegativeButton(
                                            "NO",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                    delete.show();

                }
                catch (Exception e){

                    MainActivity.errorMessage(Settings.this, e);

                }

            }
        });

        //delete all saved readings, not implemented
        delReadings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                AlertDialog.Builder NotImplemented =
                        new AlertDialog.Builder(Settings.this)
                                .setTitle("Feature Not Implemented")
                                .setMessage("This feature has not been implemented yet. Check back later")
                                .setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                NotImplemented.show();

            }
        });
    }
}