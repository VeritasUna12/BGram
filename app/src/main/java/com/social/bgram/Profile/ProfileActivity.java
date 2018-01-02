package com.social.bgram.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.social.bgram.R;
import com.social.bgram.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by user on  12/10/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int  ACTIVITY_NO =4 ;

    private Context mContext= ProfileActivity.this;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");

        //to not display all time in profile activity
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);

        setUpBottomNavigationView();
        setupToolbar();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);

        // implement when click on menu button
        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingActivity.class);
                startActivity(intent);
            }
        });

        //toolbar.setOnMenuItemClickListener (new Toolbar.OnMenuItemClickListener() {
         //   @Override
           // public boolean onMenuItemClick(MenuItem item) {

//                Log.d(TAG, "onMenuItemClick: clicked menu item: " + item);

//                switch (item.getItemId()) {
  //                  case R.id.profileMenu:
    //                    Log.d(TAG, "onMenuItemClick: Navigating to Profile Preferences. ");
        //        }
      //          return false;
          //  }
        //});
    }

    // BottomNavigationView Setup
    private void setUpBottomNavigationView() {
        Log.d(TAG, "setUpBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavVeiwbar);
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu= bottomNavigationViewEx.getMenu();
        MenuItem menuItem= menu.getItem(ACTIVITY_NO);
        menuItem.setChecked(true);
    }

    //@Override
   // public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.profile_menu, menu);
       // return true;
   // }
}
