package com.example.mymedicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNew = (Button)findViewById(R.id.btnNewReading);
        Button btnAccount = (Button)findViewById(R.id.btnAccount);
        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        Button btnView = (Button)findViewById(R.id.btnViewReading);
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
}