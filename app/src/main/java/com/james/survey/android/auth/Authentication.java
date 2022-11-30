package com.james.survey.android.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.james.survey.android.MainActivity;
import com.james.survey.android.R;
import com.james.survey.android.auth.fragment.LoginFragment;
import com.james.survey.android.auth.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

public class Authentication extends AppCompatActivity {

    private static final String[] pageTitle = {"REGISTER","LOG IN"};

    public static void start(Context context) {
        Intent intent = new Intent(context, Authentication.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("");
        }

        TabLayout tabLayout =  findViewById(R.id.tab_layout_loginsignup6);
        ViewPager viewPager = findViewById(R.id.viewpager_loginsignup6);
        AuthenticationAdapter adapter = new AuthenticationAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public class AuthenticationAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public AuthenticationAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            fragments.add(new SignUpFragment());
            fragments.add(new LoginFragment());

        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int arrayPos) {
            return pageTitle[arrayPos];
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForLogin();
    }

    private void checkForLogin() {
        final SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        final Boolean isloggedin=sharedPreferences.getBoolean("ISLOGGEDIN",false);

        if(isloggedin){
           startMainActivity();
        }
    }

    private void startMainActivity() {
        MainActivity.start(Authentication.this);
        finish();
    }
}
