package com.james.android.surveyapp;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton signout;
    FloatingActionButton ezSurv;
    FloatingActionButton about;
    FloatingActionButton sleep;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference reference;
    private String userID;
    private String[] recom;
    Toolbar toolbar;
    TextView reco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        recom = getResources().getStringArray(R.array.recomm);

        reco = findViewById(R.id.setReco);
        about = findViewById(R.id.about);
        sleep = findViewById(R.id.sleep);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Number1.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent);
            }
        });

        ezSurv = findViewById(R.id.surveyEz);
        ezSurv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SurveyActi.class);
                startActivity(intent);

            }
        });

        signout = findViewById(R.id.logout_c);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });

    }

    public void signout() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Sign-out")
                .setMessage("Are you sure you want to sign-out?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), login_fragment.class);
                        startActivity(intent);
                        finish();
                        FirebaseAuth.getInstance().signOut();
                    }
                }).create().show();

    }



    @Override
    public void onStart () {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = mUser.getUid();

            final TextView nameTxt = (TextView) findViewById(R.id.setName);
            final TextView ageTxt = (TextView) findViewById(R.id.setAge);
            final TextView emailTxt = (TextView) findViewById(R.id.setEmail);
            final TextView recoTxt = (TextView) findViewById(R.id.setReco);

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users userProfile = snapshot.getValue(Users.class);

                    if (userProfile !=null)
                    {

                        String name = userProfile.name;
                        String age = userProfile.age;
                        String email = userProfile.email;
                        String username = userProfile.username;


                        String nameS = "<b>Full Name:</b> " + name;
                        String ageS = "<b>Age:</b> " + age;
                        String emailS = "<b>Email:</b> " + email;
                        nameTxt.setText(Html.fromHtml(nameS));
                        ageTxt.setText(Html.fromHtml(ageS));
                        emailTxt.setText(Html.fromHtml(emailS));
                        toolbar.setSubtitle("Welcome @" + username +"!");
                        setReco1();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            startActivity(new Intent(MainActivity.this, login_fragment.class));
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void setReco1() {

        String uid1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef1 = db1.child("Users").child(uid1).child("Sleep Assessment").child("Number 1");
        hasUserStorageRef1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("I have job")) {
                            setTextY();
                        } else if(hasuserstorage.equals("I go to school")) {
                            setTextN();
                        } else if(hasuserstorage.equals("I am a working student"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });

        String uid2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef2= db2.child("Users").child(uid2).child("Sleep Assessment").child("Number 2");
        hasUserStorageRef2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("13 - 17 years old")) {
                            setTextY();
                        } else if(hasuserstorage.equals("18 - 60 years old")) {
                            setTextN();
                        } else if(hasuserstorage.equals("61 years old and above"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });

        String uid3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db3 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef3 = db3.child("Users").child(uid3).child("Sleep Assessment").child("Number 3");
        hasUserStorageRef3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("Fall asleep faster")) {
                            setTextY();
                        } else if(hasuserstorage.equals("Wake up well-rested")) {
                            setTextN();
                        } else if(hasuserstorage.equals("Sleep through the night"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });

        String uid4 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db4 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef4 = db4.child("Users").child(uid4).child("Sleep Assessment").child("Number 4");
        hasUserStorageRef4.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef4 = snapshot.getValue(String.class);
                        if(hasUserStorageRef4.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef4.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef4.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });

        String uid5 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db5 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef5 = db5.child("Users").child(uid5).child("Sleep Assessment").child("Number 5");
        hasUserStorageRef5.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef5 = snapshot.getValue(String.class);
                        if(hasUserStorageRef5.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef5.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef5.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid6 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db6 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef6 = db6.child("Users").child(uid6).child("Sleep Assessment").child("Number 6");
        hasUserStorageRef6.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef6 = snapshot.getValue(String.class);
                        if(hasUserStorageRef6.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef6.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef6.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid7 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db7 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef7 = db7.child("Users").child(uid7).child("Sleep Assessment").child("Number 7");
        hasUserStorageRef7.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef7 = snapshot.getValue(String.class);
                        if(hasUserStorageRef7.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef7.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef7.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid8 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db8 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef8 = db8.child("Users").child(uid8).child("Sleep Assessment").child("Number 8");
        hasUserStorageRef8.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef8 = snapshot.getValue(String.class);
                        if(hasUserStorageRef8.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef8.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef8.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid9 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db9 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef9 = db9.child("Users").child(uid9).child("Sleep Assessment").child("Number 9");
        hasUserStorageRef9.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef9 = snapshot.getValue(String.class);
                        if(hasUserStorageRef9.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef9.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef9.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid10 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db10 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef10 = db10.child("Users").child(uid10).child("Sleep Assessment").child("Number 10");
        hasUserStorageRef10.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef10 = snapshot.getValue(String.class);
                        if(hasUserStorageRef10.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef10.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef10.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid11 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db11 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef11 = db11.child("Users").child(uid11).child("Sleep Assessment").child("Number 11");
        hasUserStorageRef11.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef10 = snapshot.getValue(String.class);
                        if(hasUserStorageRef10.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef10.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef10.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid12 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db12 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef12 = db12.child("Users").child(uid12).child("Sleep Assessment").child("Number 12");
        hasUserStorageRef12.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef12 = snapshot.getValue(String.class);
                        if(hasUserStorageRef12.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef12.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef12.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid13 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db13 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef13 = db13.child("Users").child(uid13).child("Sleep Assessment").child("Number 13");
        hasUserStorageRef13.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef13 = snapshot.getValue(String.class);
                        if(hasUserStorageRef13.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef13.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef13.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid14 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db14 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef14 = db14.child("Users").child(uid14).child("Sleep Assessment").child("Number 14");
        hasUserStorageRef14.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef14 = snapshot.getValue(String.class);
                        if(hasUserStorageRef14.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef14.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef14.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid15 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db15 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef15 = db15.child("Users").child(uid15).child("Sleep Assessment").child("Number 15");
        hasUserStorageRef15.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef15 = snapshot.getValue(String.class);
                        if(hasUserStorageRef15.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef15.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef15.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid16 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db16 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef16 = db16.child("Users").child(uid16).child("Sleep Assessment").child("Number 16");
        hasUserStorageRef16.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef16 = snapshot.getValue(String.class);
                        if(hasUserStorageRef16.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef16.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef16.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid17 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db17 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef17 = db17.child("Users").child(uid17).child("Sleep Assessment").child("Number 17");
        hasUserStorageRef17.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef17 = snapshot.getValue(String.class);
                        if(hasUserStorageRef17.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef17.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef17.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid18 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db18 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef18 = db18.child("Users").child(uid18).child("Sleep Assessment").child("Number 18");
        hasUserStorageRef18.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef18 = snapshot.getValue(String.class);
                        if(hasUserStorageRef18.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef18.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef18.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });
        String uid19 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db19 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef19 = db19.child("Users").child(uid19).child("Sleep Assessment").child("Number 19");
        hasUserStorageRef19.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasUserStorageRef19 = snapshot.getValue(String.class);
                        if(hasUserStorageRef19.equals("Yes")) {
                            setTextY();
                        } else if(hasUserStorageRef19.equals("No")) {
                            setTextN();
                        } else if(hasUserStorageRef19.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });








        String uid20 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db20 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef20 = db20.child("Users").child(uid20).child("Sleep Assessment").child("Number 20");
        hasUserStorageRef20.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("Yes")) {
                            setTextY();
                        } else if(hasuserstorage.equals("No")) {
                            setTextN();
                        } else if(hasuserstorage.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });

        String uid21 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db21 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef21 = db21.child("Users").child(uid21).child("Sleep Assessment").child("Number 21");
        hasUserStorageRef21.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("Yes")) {
                            setTextY();
                        } else if(hasuserstorage.equals("No")) {
                            setTextN();
                        } else if(hasuserstorage.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });


        String uid22 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db22 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hasUserStorageRef22 = db22.child("Users").child(uid22).child("Sleep Assessment").child("Number 22");
        hasUserStorageRef22.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.exists()) {
                        String hasuserstorage = snapshot.getValue(String.class);
                        if(hasuserstorage.equals("Yes")) {
                            setTextY();
                        } else if(hasuserstorage.equals("No")) {
                            setTextN();
                        } else if(hasuserstorage.equals("Sometimes"))
                        {
                            setTextS();
                        }
                    }
                }
            }
        });


    }

    public void setTextY()
    {
        String[] array = getResources().getStringArray(R.array.recomm);
        String recommend = array[new Random().nextInt(array.length)];
        String recommend1 = array[new Random().nextInt(array.length)];
        String recommend2 = array[new Random().nextInt(array.length)];
        String recommend3 = array[new Random().nextInt(array.length)];
        String recommend4 = array[new Random().nextInt(array.length)];

        String recoms = recommend + "<br/><br/>" + recommend1 + "<br/><br/>" + recommend2 + "<br/><br/>" + recommend3+ "<br/><br/>" + recommend4 ;
        reco.setText(Html.fromHtml(recoms));
    }
    public void setTextN()
    {
        String[] array = getResources().getStringArray(R.array.recomm);
        String recommend = array[new Random().nextInt(array.length)];
        String recommend1 = array[new Random().nextInt(array.length)];
        String recommend2 = array[new Random().nextInt(array.length)];
        String recommend3 = array[new Random().nextInt(array.length)];
        String recommend4 = array[new Random().nextInt(array.length)];
        String recoms = recommend + "<br/><br/>" + recommend1 + "<br/><br/>" + recommend2+ "<br/><br/>" + recommend+ "<br/><br/>" + recommend4 ;
        reco.setText(Html.fromHtml(recoms));
    }
    public void setTextS()
    {
        String[] array = getResources().getStringArray(R.array.recomm);
        String recommend = array[new Random().nextInt(array.length)];
        String recommend1 = array[new Random().nextInt(array.length)];
        String recommend2 = array[new Random().nextInt(array.length)];
        String recommend3 = array[new Random().nextInt(array.length)];
        String recommend4 = array[new Random().nextInt(array.length)];

        String recoms = recommend + "<br/><br/>" + recommend1 + "<br/><br/>" + recommend2+ "<br/><br/>" + recommend+ "<br/><br/>" + recommend4 ;
        reco.setText(Html.fromHtml(recoms));

    }

}