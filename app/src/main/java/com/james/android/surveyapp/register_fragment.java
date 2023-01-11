package com.james.android.surveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class register_fragment extends AppCompatActivity {

    EditText emailReg, passwordReg, cpasswordReg, nameReg, userReg, ageReg;
    Button btnReg;
    CheckBox termsck;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView signIn;
    String emailPattern = "^(.+)@(.+)$";
    ProgressDialog progressDialog;
    DatabaseReference userDbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fragment);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        nameReg = findViewById(R.id.namereg);
        userReg = findViewById(R.id.usernamereg);
        emailReg = findViewById(R.id.emailreg);
        ageReg = findViewById(R.id.agereg);
        passwordReg = findViewById(R.id.passwordreg);
        cpasswordReg = findViewById(R.id.confirm_password);
        btnReg = findViewById(R.id.btnCreateAccount);
        termsck = findViewById(R.id.termsCkb);

        userDbref = FirebaseDatabase.getInstance().getReference().child("Users");

        signIn = findViewById(R.id.haveAccountnow);
        progressDialog = new ProgressDialog(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_fragment.class);
                startActivity(intent);
                finish();

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth()
    {
        String name = nameReg.getText().toString();
        String username = userReg.getText().toString();
        String age = ageReg.getText().toString();
        String email = emailReg.getText().toString();
        String password = passwordReg.getText().toString();
        String apassword = cpasswordReg.getText().toString();


        if (!email.matches(emailPattern) || email.isEmpty())
        {
            emailReg.setError("Enter a valid email address");
            return;
        } else if(password.isEmpty() || password.length()<6)
        {
            passwordReg.setError("Enter password at least 8 characters");
            return;
        } else if(!password.equals(apassword)) {
            cpasswordReg.setError("Password do not match");
            return;
        } else if(!termsck.isChecked())
        {
            Toast.makeText(register_fragment.this, "Please agree to the Terms and Conditions by checking the box",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if(age.isEmpty())
        {
            ageReg.setError("Age is required");
            ageReg.requestFocus();
            return;
        } else if (name.isEmpty())
        {
            nameReg.setError("Name is required");
            nameReg.requestFocus();
            return;

        } else if (username.isEmpty())
        {
            userReg.setError("Username is required");
            userReg.requestFocus();
            return;
        }
            else
        {




            progressDialog.setMessage("Creating your account...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Users users = new Users(name, username, age, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        sendUsertoNextActivity();
                                        Toast.makeText(register_fragment.this, "Account created",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }

                    else if (!task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        try
                        {
                            throw task.getException();
                        }
                        catch (FirebaseAuthUserCollisionException existEmail)
                        {

                            emailReg.setError("Email already exist");
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
            });

        }
    }
    private void sendUsertoNextActivity()
    {
        Intent intent =  new Intent(register_fragment.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   }