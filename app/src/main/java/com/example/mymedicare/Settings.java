package com.example.mymedicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnBlue = (Button)findViewById(R.id.btnStyleBlue);
        btnBlue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                setTheme(R.style.AppBlueTheme);

                ; }
        });

    }
}