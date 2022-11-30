package com.james.survey.android.auth.fragment;

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


public class SignUpFragment extends Fragment{

    private EditText fullName;
    private EditText user_name;
    private EditText email;
    private EditText password;
    private EditText conFirmPassword;
    private Button signUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.signup_fragment, container, false);
        init(view);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        return view;
    }

    private void validate() {
        String name = fullName.getText().toString();
        String email_in = email.getText().toString();
        String password_in = password.getText().toString();
        String re_password = conFirmPassword.getText().toString();
        String username = user_name.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email_in) && !TextUtils.isEmpty(password_in)){
           if (email_in.contains("@")){
               if (!username.contains(" ")){
                   if (password_in.equals(re_password)){
                       startRegistration(name, email_in, password_in, username);
                   }else {
                       Toast.makeText(getActivity(), getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
                   }
               }else {
                   Toast.makeText(getActivity(), getString(R.string.no_space), Toast.LENGTH_LONG).show();
               }
           }else {
               Toast.makeText(getActivity(), getString(R.string.email_not_valid), Toast.LENGTH_LONG).show();
           }
        }else {
            Toast.makeText(getActivity(), getString(R.string.check_fields), Toast.LENGTH_LONG).show();
        }
    }

    private void startRegistration(String name, String email_in, String password_in, String username) {
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("NAME",name);
            editor.putString("EMAIL",email_in);
            editor.putString("USERNAME",username);
            editor.putString("PASSWORD",password_in);
            editor.putBoolean("ISLOGGEDIN",true);
            editor.apply();
            startMainActivity();
    }

    private void startMainActivity() {
        MainActivity.start(getActivity());
        getActivity().finish();
    }

    private void init(View view) {
        fullName = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        conFirmPassword = view.findViewById(R.id.confirm_password);
        signUpBtn = view.findViewById(R.id.btnCreateAccount);
        user_name = view.findViewById(R.id.username);
    }

}
