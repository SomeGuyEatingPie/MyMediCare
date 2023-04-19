package com.example.mymedicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class Account extends AppCompatActivity {

    private int birthYear;
    private int birthMonth;
    private int birthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.medicare_prefs), Context.MODE_PRIVATE);

        EditText extFirstName = (EditText)  findViewById(R.id.txtFirstName);
        extFirstName.setText( sharedPref.getString( getString( R.string.saved_user_firstname_key), ""));
        EditText extLastName = (EditText)  findViewById(R.id.txtLastName);
        extLastName.setText( sharedPref.getString( getString( R.string.saved_user_lastname_key), ""));
        EditText extDoB = (EditText) findViewById(R.id.txtDOB);
        extDoB.setText( sharedPref.getString( getString( R.string.saved_user_dob_key), ""));

        Spinner spinGender = (Spinner) findViewById(R.id.spinGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(adapter);
        String gender = (sharedPref.getString( getString( R.string.saved_user_gender_key), null));
        if (gender != null) {

            String[] genderArray = getResources().getStringArray(R.array.gender_array);

            for (int i = 0; i < genderArray.length; i++ ) {

                if (gender.equals(genderArray[i])){
                    spinGender.setSelection(i);
                }

            }
        }

        EditText extDrName = (EditText)  findViewById(R.id.txtDrName);
        extDrName.setText( sharedPref.getString( getString( R.string.saved_dr_name_key), ""));
        EditText extDrNumber = (EditText)  findViewById(R.id.txtDrNumber);
        extDrNumber.setText( sharedPref.getString(getString( R.string.saved_dr_number_key), "" ));
        Button btnSave = (Button) findViewById(R.id.btnSave);


        extDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();

                int y = cal.get(Calendar.YEAR);
                int m = cal.get(Calendar.MONTH);
                int d = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DoBPicker = new DatePickerDialog(Account.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int setYear,
                                                  int setMonth, int setDay) {

                                birthYear = setYear;
                                birthMonth = setMonth + 1;
                                birthDay = setDay;
                                extDoB.setText(birthDay + "/" + birthMonth + "/" + birthYear);

                            }
                        },

                        y, m, d);

                DoBPicker.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String drNumber = extDrNumber.getText().toString();

                try {
                    if (extFirstName.getText().toString().equals("")
                            || extLastName.getText().toString().equals("")
                            || extDoB.getText().toString().equals("")) {


                        AlertDialog.Builder emptyField =
                                new AlertDialog.Builder(Account.this)
                                        .setTitle("Missing Information")
                                        .setMessage("Please Input Details for First Name, Last Name and Date of Birth.")
                                        .setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                        emptyField.show();

                    } else {

                        if (!phoneCheck(drNumber).equals("")) {


                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.saved_user_firstname_key), extFirstName.getText().toString());
                            editor.putString(getString(R.string.saved_user_lastname_key), extLastName.getText().toString());
                            editor.putString(getString(R.string.saved_user_dob_key), extDoB.getText().toString());
                            editor.putString(getString(R.string.saved_user_gender_key), spinGender.getSelectedItem().toString());
                            editor.putString(getString(R.string.saved_dr_name_key), extDrName.getText().toString());
                            editor.putString(getString(R.string.saved_dr_number_key), drNumber);
                            editor.apply();

                            AlertDialog.Builder success =
                                    new AlertDialog.Builder(Account.this)
                                            .setTitle("Save Successful")
                                            .setMessage("User data was successfully saved")
                                            .setPositiveButton(
                                                    "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });

                            success.show();
                        }

                    }
                }
                catch(Exception e){

                    MainActivity.errorMessage(Account.this, e);


                }
            }

        });
    }

    private String phoneCheck(String phoneNumber) {


        if (!phoneNumber.substring(0, 3).contains("+44")) {

            if (Character.compare(phoneNumber.charAt(0), '0') == 0) {

                phoneNumber =  new StringBuilder(phoneNumber).deleteCharAt(0).toString();

            }

            phoneNumber = "+44" + phoneNumber;

        }

        if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {

            AlertDialog.Builder badNumber =
                    new AlertDialog.Builder(Account.this)
                            .setTitle("Invalid Doctor Phone Number")
                            .setMessage("Check you have input your doctors phone number correctly and try again.")
                            .setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

            badNumber.show();
            return "";
        }
        else {
            return phoneNumber;
        }
    }

}