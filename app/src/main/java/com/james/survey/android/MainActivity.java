package com.james.survey.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.james.survey.android.auth.Authentication;

public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private FloatingActionButton logout_container;
    private  SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        logout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("ISLOGGEDIN",false).apply();
                startLogin();
                finish();

            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        logout_container = findViewById(R.id.logout_c);
        setSupportActionBar(toolbar);
        setSystemBarColor(this, R.color.colorPrimary);

        sp = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        String name = sp.getString("NAME", null);
        String username = sp.getString("USERNAME", null);
        if (username != null){
            toolbar.setSubtitle("Good day @" + name + "!");
        }
        if (name != null) {
            Toast.makeText(this, "Welcome @" + username + "!", Toast.LENGTH_LONG).show();
            toolbar.setTitle("Welcome to Survey app");
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }
    private void startLogin(){
        Authentication.start(this);
    }
}
