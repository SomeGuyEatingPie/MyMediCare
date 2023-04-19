package com.example.mymedicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewReading extends AppCompatActivity {

    //The Metric variable tracks how temp is recorded
    //celsius (true) or fahrenheit (false)
    private Boolean metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reading);

        Button btnChange = (Button)findViewById(R.id.btnChangeUnit);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        EditText extTemp = (EditText) findViewById(R.id.txtTemp);
        EditText extBPHigh = (EditText) findViewById(R.id.txtBPHigh);
        EditText extBPLow = (EditText) findViewById(R.id.txtBPLow);
        EditText extHR = (EditText) findViewById(R.id.txtHR);

        TextView txtUnit = (TextView) findViewById(R.id.txtUnit);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);
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

        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                float temp = Float.parseFloat(extTemp.getText().toString());
                float finalTemp = temp;
                int bpHigh = Integer.parseInt(extBPHigh.getText().toString());
                int bpLow = Integer.parseInt(extBPLow.getText().toString());
                int hr = Integer.parseInt(extHR.getText().toString());

                if (metric = false) { temp = (temp - 32) * 5/9;}

                if (temp >= 38
                        || hr > 160
                        || (bpLow > 110 && bpHigh > 180) ) {

                    AlertDialog.Builder highRisk =
                            new AlertDialog.Builder(NewReading.this)
                                    .setTitle("High Risk Reading Detected")
                                    .setMessage("One or more of your readings have been identified as 'high risk'." +
                                            " Would you like to notify your GP?")
                                    .setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    sendSMS(new String[] {Float.toString(finalTemp), Integer.toString(bpLow), Integer.toString(bpHigh), Integer.toString(hr)});
                                                    dialog.cancel();

                                                }

                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    });

                    highRisk.show();

                }

            }
        });
    }

    private void sendSMS(String[] readings){

        ActivityCompat.requestPermissions(NewReading.this,new String[]{android.Manifest.permission.SEND_SMS},1);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);
        String message = getString(R.string.saved_sms_default_message);
        //String phoneNumber = sharedPref.getString(getString(R.string.saved_dr_number_key), null);
        String phoneNumber = "5556";

        message = message.replace("[drName]", sharedPref.getString(getString(R.string.saved_dr_name_key), null));
        message = message.replace("[userFirstName]", sharedPref.getString(getString(R.string.saved_user_firstname_key), null));
        message = message.replace("[userLastName]", sharedPref.getString(getString(R.string.saved_user_lastname_key), null));
        message = message.replace("[temp]", readings[0]);
        message = message.replace("[bpLow]", readings[1]);
        message = message.replace("[bpHigh]", readings[2]);
        message = message.replace("[hr]", readings[3]);

        try {

            SmsManager sms= this.getSystemService(SmsManager.class);
            sms.sendTextMessage(phoneNumber, null, message, null, null);

            AlertDialog.Builder sendSuccess =
                    new AlertDialog.Builder(this)
                            .setTitle("Message Sent")
                            .setMessage("The message was sent to your GP successfully")
                            .setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

            sendSuccess.show();

        }
        catch (Exception e) {

            MainActivity.errorMessage(this, e);

        }
    }

}