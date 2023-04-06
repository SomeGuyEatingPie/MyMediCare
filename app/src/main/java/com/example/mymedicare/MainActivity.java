package com.example.mymedicare;

import androidx.appcompat.app.AppCompatActivity;

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
        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        btnNew.setOnClickListener(new OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(MainActivity.this, NewReading.class)); }
        });

        btnSettings.setOnClickListener(new OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(MainActivity.this, Settings.class)); }
        });

    }
}