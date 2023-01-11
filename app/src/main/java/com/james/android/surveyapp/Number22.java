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

public class Number22 extends AppCompatActivity {

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
        setContentView(R.layout.activity_number22);

        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = mydb.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sleep Assessment");

        Map<String, Object> userData = new HashMap<String,Object>();


        SleepFirstrbsecsec = findViewById(R.id.Slfgrqsacgz);

        secondsleep1 = findViewById(R.id.sed6grqf4c2hx);


        backbtn = findViewById(R.id.backBtnsleep);
        nextBtn1st = findViewById(R.id.nextBtn1st);

        nextBtn1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SleepFirstrbsecsec.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Number22.this, "Please choose at least one!",
                            Toast.LENGTH_SHORT).show();
                } else {


                int selectedId = SleepFirstrbsecsec.getCheckedRadioButtonId();

                secondsleep1 = (RadioButton) findViewById(selectedId);
                userData.put("Number 22", (String) secondsleep1.getText());
                mDatabase.updateChildren(userData);


                    thankyou();
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

    //Thank you dialog after completing the survey
    public void thankyou()
    {

        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You have successfully completed the Sleep Assessment survey. Thank you for your participation!\n\nYou may now see your recommendations.")
                .setCancelable(false)

                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }


}