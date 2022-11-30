package com.james.survey.android.auth.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.james.survey.android.MainActivity;
import com.james.survey.android.R;

import static android.content.Context.MODE_PRIVATE;


@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment{

    private EditText password;
    private EditText email;
    private Button loginBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        sharedPreferences = this.getActivity().getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        final Boolean isloggedin = sharedPreferences.getBoolean("ISLOGGEDIN",false);

        final String required_email = sharedPreferences.getString("EMAIL","DEFAULT_EMAIL");
        final String required_password = sharedPreferences.getString("PASSWORD","DEFAULT_PASSWORD");
        final String required_username = sharedPreferences.getString("USERNAME","DEFAULT_USERNAME");

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.btnCreatesignin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(required_email, required_password, required_username);
            }
        });
        return view;
    }

    private void validate(String required_email, String required_password, String required_username) {
        String email_in = email.getText().toString();
        String password_in = password.getText().toString();
        if (!TextUtils.isEmpty(email_in) && !TextUtils.isEmpty(password_in)){
            if (email_in.contains("@")){
                isEmailLogin(email_in, password_in, required_email, required_password);
            }else {
                isUsernameLogin(email_in, password_in, required_password, required_username);
            }
        }else {
            Toast.makeText(getActivity(),"Please check the email or username fields",Toast.LENGTH_LONG).show();
        }
    }

    private void isUsernameLogin(String username_in, String password_in, String required_password, String required_username) {
        if(username_in.equals(required_username) && password_in.equals(required_password)) {
            sharedPreferences.edit().putBoolean("ISLOGGEDIN",false).apply();
            startMainActivity();
        }
        else {
            Toast.makeText(getActivity(),"Username or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void isEmailLogin(String email, String password, String required_email, String required_password) {
        if(email.equals(required_email) && password.equals(required_password)) {
            sharedPreferences.edit().putBoolean("ISLOGGEDIN",false).apply();
            startMainActivity();
        }
        else {
            Toast.makeText(getActivity(),"Email address or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void startMainActivity() {
        MainActivity.start(getActivity());
        getActivity().finish();
    }

}
