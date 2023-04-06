package com.example.mymedicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewReading extends AppCompatActivity {

    //The Metric variable tracks how termp is recorded
    //celcius (true) or farenheit (false)
    private Boolean metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reading);

        Button btnChange= (Button)findViewById(R.id.btnChangeUnit);
        TextView txtUnit = (TextView) findViewById(R.id.txtUnit);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean defaultValue = getResources().getBoolean(R.bool.saved_metric_default_key);
        this.metric = sharedPref.getBoolean(getString(R.string.saved_metric_key), defaultValue);

        if (!metric) {
            txtUnit.setText(getString(R.string.UnitF));
        }
        btnChange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if (metric == true){

                    metric = false;
                    txtUnit.setText(getString(R.string.UnitF));

                }
                else{

                    metric = true;
                    txtUnit.setText(getString(R.string.UnitC));

                }

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.saved_metric_key), metric);
                editor.apply();

            }
        });
    }
}