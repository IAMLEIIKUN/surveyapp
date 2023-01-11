package com.james.android.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Number21 extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;


    //RadioGroup
    RadioGroup SleepFirstrbsecsec;

    //RadioButtons
    RadioButton secondsleep1;


    //Button
    Button nextBtn1st;
    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number21);

        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sleep Assessment");

        Map<String, Object> userData = new HashMap<String,Object>();


        SleepFirstrbsecsec = findViewById(R.id.Slfgsacgz);

        secondsleep1 = findViewById(R.id.sed6g4c2hx);


        backbtn = findViewById(R.id.backBtnsleep);
        nextBtn1st = findViewById(R.id.nextBtn1st);

        nextBtn1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SleepFirstrbsecsec.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Number21.this, "Please choose at least one!",
                            Toast.LENGTH_SHORT).show();
                } else {


                int selectedId = SleepFirstrbsecsec.getCheckedRadioButtonId();

                secondsleep1 = (RadioButton) findViewById(selectedId);
                userData.put("Number 21", (String) secondsleep1.getText());
                mDatabase.updateChildren(userData);

                Intent intent = new Intent(getApplicationContext(), Number22.class);
                startActivity(intent);
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}