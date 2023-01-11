package com.james.android.surveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyActi extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;

    //Number 1
    RadioButton radio1yes;
    //Number 2
    RadioButton radio2one;
    //Number 3
    CheckBox checkbox1one;
    CheckBox checkbox1two;
    CheckBox checkbox1three;
    CheckBox checkbox1four;
    CheckBox checkbox1five;
    //Number 4
    RadioButton radio4yes;
    //Number 5
    CheckBox checkbox2one;
    CheckBox checkbox2two;
    CheckBox checkbox2three;
    CheckBox checkbox2four;
    CheckBox checkbox2five;
    CheckBox checkbox2six;
    CheckBox checkbox2seven;
    CheckBox checkbox2eight;
    CheckBox checkbox2nine;
    //Number 6
    RadioButton radio6yes;
    //Number 7
    RadioButton radio7yes;
    //Number 8
    CheckBox checkbox6one;
    CheckBox checkbox6two;
    CheckBox checkbox6three;
    CheckBox checkbox6four;
    CheckBox checkbox6five;
    CheckBox checkbox6six;
    //Number 9
    RadioButton radio9yes;
    //Number 10
    RadioButton radio10yes;
    //Number 11
    RadioButton radio11yes;
    //Number 12
    RadioButton radio12one;
    //Number 13
    EditText thirText;



    //RadioGroup
    RadioGroup firstG;
    RadioGroup secondG;
    RadioGroup thirdG;
    RadioGroup fourthG;
    RadioGroup fifthG;
    RadioGroup sixG;
    RadioGroup sevenG;
    RadioGroup eightG;
    RadioGroup nineG;
    RadioGroup tenG;
    RadioGroup elevG;
    RadioGroup twelveG;

    Button nextBtn;
    Button backBtn;

    String surveycbk = "";
    String surveycbk5 = "";
    String surveycbk8 = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey1);


        //RadioButtons (26)
        radio1yes = findViewById(R.id.radioYes);
        //Number 2
        radio2one = findViewById(R.id.radioYes);
        //Number 4
        radio4yes = findViewById(R.id.radioNo);
        //Number 6
        radio6yes = findViewById(R.id.radioNo);
        //Number 7
        radio7yes = findViewById(R.id.radioNo);
        //Number 9
        radio9yes = findViewById(R.id.radioNo);
        //Number 10
        radio10yes = findViewById(R.id.radioNo);
        //Number 11
        radio11yes = findViewById(R.id.radioNo);
        //Number 12
        radio12one = findViewById(R.id.radioNo);


        //RadioGroups
        firstG = findViewById(R.id.radioFirst);
        secondG = findViewById(R.id.radioSec);
        thirdG = findViewById(R.id.radioThird);
        fourthG = findViewById(R.id.radioFourth);
        fifthG = findViewById(R.id.radioFifth);
        sixG = findViewById(R.id.radioSix);
        sevenG = findViewById(R.id.radio7);
        eightG = findViewById(R.id.radioEight);
        nineG = findViewById(R.id.radioNine);
        tenG = findViewById(R.id.radioTen);
        elevG = findViewById(R.id.radioEleven);
        twelveG = findViewById(R.id.radioTwelve);

        //Checkboxes
        checkbox1one = findViewById(R.id.radioYes12);
        checkbox1two = findViewById(R.id.radioNo123);
        checkbox1three = findViewById(R.id.radioNo133);
        checkbox1four = findViewById(R.id.radioNo44);
        checkbox1five = findViewById(R.id.radioNo155);
        //Number 5
        checkbox2one = findViewById(R.id.radioqxe);
        checkbox2two = findViewById(R.id.radiohrt);
        checkbox2three = findViewById(R.id.radiofsr);
        checkbox2four = findViewById(R.id.radiofbr);
        checkbox2five = findViewById(R.id.radiofdr);
        checkbox2six = findViewById(R.id.radiofyr);
        checkbox2seven = findViewById(R.id.radiofrer);
        checkbox2eight = findViewById(R.id.radiofqr);
        checkbox2nine = findViewById(R.id.radiofr4);
        //Number 8
        checkbox6one = findViewById(R.id.radio8qxe);
        checkbox6two = findViewById(R.id.radio8hrt);
        checkbox6three = findViewById(R.id.radio8fsr);
        checkbox6four = findViewById(R.id.radio8fbr);
        checkbox6five = findViewById(R.id.radio8fdr);
        checkbox6six = findViewById(R.id.radio8fyr);

        //Number 13
        thirText = findViewById(R.id.thirSurveyText);




        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Survey Form");

        Map<String, String> userData = new HashMap<String, String>();

        DatabaseReference rootRef = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
           public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Survey Form")) {
                    exist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surveycbk = "";
                surveycbk5 = "";
                surveycbk8 = "";


                String get1 = checkbox1one.getText().toString();
                String get2 = checkbox1two.getText().toString();
                String get3 =  checkbox1three.getText().toString();
                String get4 = checkbox1four.getText().toString();
                String get5 =  checkbox1five.getText().toString();

                String get51 = checkbox2one.getText().toString();
                String get52 = checkbox2two.getText().toString();
                String get53 =  checkbox2three.getText().toString();
                String get54 = checkbox2four.getText().toString();
                String get55 =  checkbox2five.getText().toString();
                String get56 = checkbox2six.getText().toString();
                String get57 = checkbox2seven.getText().toString();
                String get58 =  checkbox2eight.getText().toString();
                String get59 = checkbox2nine.getText().toString();

                String get61 = checkbox6one.getText().toString();
                String get62 = checkbox6two.getText().toString();
                String get63 =  checkbox6three.getText().toString();
                String get64 = checkbox6four.getText().toString();
                String get65 =  checkbox6five.getText().toString();
                String get66 = checkbox6six.getText().toString();

                String thirteen = thirText.getText().toString();



                //For checkboxes
//Number 3 5 checked
                if (checkbox1one.isChecked()){
                    surveycbk +=", " + get1;

                }
                if (checkbox1two.isChecked()){
                    surveycbk +=", " + get2;

                }
                if (checkbox1three.isChecked()){
                    surveycbk +=", " + get3;

                }
                if (checkbox1four.isChecked()){
                    surveycbk +=", " + get4;

                }
                if (checkbox1five.isChecked()){
                    surveycbk +=", " + get5;

                }

                //Number 5 9 checked
                if (checkbox2one.isChecked()){
                    surveycbk5 +=", " + get51;

                }
                if (checkbox2two.isChecked()){
                    surveycbk5 +=", " + get52;

                }
                if (checkbox2three.isChecked()){
                    surveycbk5 +=", " + get53;

                }
                if (checkbox2four.isChecked()){
                    surveycbk5 +=", " + get54;

                }
                if (checkbox2five.isChecked()){
                    surveycbk5 +=", " + get55;

                }
                if (checkbox2six.isChecked()){
                    surveycbk5 +=", " + get56;

                }
                if (checkbox2seven.isChecked()){
                    surveycbk5 +=", " + get57;

                }
                if (checkbox2eight.isChecked()){
                    surveycbk5 +=", " + get58;

                }
                if (checkbox2nine.isChecked()){
                    surveycbk5 +=", " + get59;
                }

                //Number 8 6 checked
                if (checkbox6one.isChecked()){
                    surveycbk8 +=", " + get61;

                }
                if (checkbox6two.isChecked()){
                    surveycbk8 +=", " + get62;

                }
                if (checkbox6three.isChecked()){
                    surveycbk8 +=", " + get63;

                }
                if (checkbox6four.isChecked()){
                    surveycbk8 +=", " + get64;

                }
                if (checkbox6five.isChecked()){
                    surveycbk8 +=", " + get65;

                }
                if (checkbox6six.isChecked()){
                    surveycbk8 +=", " + get66;

                }

                //For RadioButttons
                if (firstG.getCheckedRadioButtonId() == -1 || secondG.getCheckedRadioButtonId() == -1
                || fourthG.getCheckedRadioButtonId() == -1 || sixG.getCheckedRadioButtonId() == -1 ||
                        sevenG.getCheckedRadioButtonId() == -1 || nineG.getCheckedRadioButtonId() == -1 ||
                        tenG.getCheckedRadioButtonId() == -1 ||
                        elevG.getCheckedRadioButtonId() == -1 ||
                        twelveG.getCheckedRadioButtonId() == -1 || thirteen.isEmpty())
                {
                    Toast.makeText(SurveyActi.this, "Some fields can't be empty",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                int selectedId = firstG.getCheckedRadioButtonId();
                int selectedId2 = secondG.getCheckedRadioButtonId();
                int selectedId3 = fourthG.getCheckedRadioButtonId();
                int selectedId4 = sixG.getCheckedRadioButtonId();
                int selectedId5 = sevenG.getCheckedRadioButtonId();
                int selectedId6 = nineG.getCheckedRadioButtonId();
                int selectedId7 = tenG.getCheckedRadioButtonId();
                int selectedId8 = elevG.getCheckedRadioButtonId();
                int selectedId9 = twelveG.getCheckedRadioButtonId();



                radio1yes = (RadioButton) findViewById(selectedId);
                radio2one = (RadioButton) findViewById(selectedId2);
                radio4yes = (RadioButton) findViewById(selectedId3);
                radio6yes = (RadioButton) findViewById(selectedId4);
                radio7yes = (RadioButton) findViewById(selectedId5);
                radio9yes = (RadioButton) findViewById(selectedId6);
                radio10yes = (RadioButton) findViewById(selectedId7);
                radio11yes = (RadioButton) findViewById(selectedId8);
                radio12one = (RadioButton) findViewById(selectedId9);



                userData.put("Number 1", (String) radio1yes.getText());
                userData.put("Number 2", (String) radio2one.getText());
                userData.put("Number 4", (String) radio4yes.getText());
                userData.put("Number 6", (String) radio6yes.getText());
                userData.put("Number 7", (String) radio7yes.getText());
                userData.put("Number 9", (String) radio9yes.getText());
                userData.put("Number 10", (String) radio10yes.getText());
                userData.put("Number 11", (String) radio11yes.getText());
                userData.put("Number 12", (String) radio12one.getText());

                userData.put("Number 3", surveycbk);
                userData.put("Number 5", surveycbk5);
                userData.put("Number 8", surveycbk8);
                userData.put("Number 13", thirteen);



                mDatabase.setValue(userData);
                thankyou();


                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit the survey?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    //Dialog for survey exist
    public void exist()
    {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Survey")
                .setMessage("Are you sure you want to re-attempt this survey?")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).create().show();
    }

    //Thank you dialog after completing the survey
    public void thankyou()
    {
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You have successfully completed the survey. Thank you for your participation!")
                .setCancelable(false)

                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }
}