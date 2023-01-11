package com.james.android.surveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Number1 extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;


    //RadioGroup
    RadioGroup firstsleeprg;

    //RadioButtons
    RadioButton SleepFirstrb;


    //Button
    Button nextBtn1st;
    Button backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number1);

        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sleep Assessment");

        Map<String, Object> userData = new HashMap<String,Object>();

        firstsleeprg = findViewById(R.id.firstsleeprg);

        SleepFirstrb = findViewById(R.id.SleepFirstrb);

        backbtn = findViewById(R.id.backBtnsleep);


        DatabaseReference rootRef = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Sleep Assessment")) {
                    exist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        nextBtn1st = findViewById(R.id.nextBtn1st);

        nextBtn1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstsleeprg.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Number1.this, "Please choose at least one!",
                            Toast.LENGTH_SHORT).show();
                } else {



                int selectedId = firstsleeprg.getCheckedRadioButtonId();

                SleepFirstrb = (RadioButton) findViewById(selectedId);

                userData.put("Number 1", (String) SleepFirstrb.getText());
                mDatabase.updateChildren(userData);
                Intent intent = new Intent(getApplicationContext(), Number2.class);
                startActivity(intent);
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
}