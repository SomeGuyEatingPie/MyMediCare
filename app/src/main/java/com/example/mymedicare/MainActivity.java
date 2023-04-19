package com.example.mymedicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String userName;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        Button btnNew = (Button)findViewById(R.id.btnNewReading);
        Button btnAccount = (Button)findViewById(R.id.btnAccount);
        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        Button btnView = (Button)findViewById(R.id.btnViewReading);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);
        this.userName = sharedPref.getString( getString( R.string.saved_user_firstname_key), "");

        if ( !userName.equals("") ) {

            txtWelcome.setText(getResources().getString(R.string.WelcomeMessage) + " " + userName);

        }
        else {

            if (sharedPref.getString(getString(R.string.saved_user_firstname_key), null)
                    == null) {

                AlertDialog.Builder NoUser =
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("New User Detected")
                                .setMessage("User details could not be found. Please enter your details now.")
                                .setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                startActivity(new Intent(MainActivity.this, Account.class));
                                                dialog.cancel();
                                            }
                                        });

                NoUser.show();

            }

        }


        btnNew.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){

                startActivity(new Intent(MainActivity.this, NewReading.class));

            }
        });

        btnView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {


                AlertDialog.Builder NotImplemented =
                        new AlertDialog.Builder(MainActivity.this)
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

        btnAccount.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){

                startActivity(new Intent(MainActivity.this, Account.class)); }

        });

        btnSettings.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){

                startActivity(new Intent(MainActivity.this, Settings.class)); }
        });



    }

    public static void errorMessage(Context context, Exception handledException) {

        AlertDialog.Builder error =
                new AlertDialog.Builder(context)
                        .setTitle("An error occurred")
                        .setMessage("An error occurred while saving. Please try again. Error message: " + handledException)
                        .setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

        error.show();

    }

}