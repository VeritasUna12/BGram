package com.social.bgram.Profile;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.social.bgram.Add.AddActivity;
import com.social.bgram.Home.MainActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;
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

        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launch_black_24dp));

        final FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView homeIcon          = new ImageView(this);
        ImageView searchIcon        = new ImageView(this);
        ImageView addIcon           = new ImageView(this);
        ImageView profileIcon       = new ImageView(this);
        ImageView notificationIcon  = new ImageView(this);

        homeIcon            .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_home) );
        searchIcon          .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_search) );
        addIcon             .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_add) );
        notificationIcon    .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_notification) );
        profileIcon         .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_profile) );

        SubActionButton homeButton = itemBuilder.setContentView(homeIcon).build();
        SubActionButton searchButton = itemBuilder.setContentView(searchIcon).build();
        SubActionButton addButton = itemBuilder.setContentView(addIcon).build();
        SubActionButton profileButton = itemBuilder.setContentView(profileIcon).build();
        SubActionButton notificationButton = itemBuilder.setContentView(notificationIcon).build();


        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(homeButton)
                .addSubActionView(searchButton)
                .addSubActionView(addButton)
                .addSubActionView(profileButton)
                .addSubActionView(notificationButton)
                // ...
                .attachTo(actionButton)
                .build();

        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                icon.setRotation(0);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat(View.ROTATION,45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhr);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                icon.setRotation(45);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat(View.ROTATION,0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhr);
                animation.start();
            }
        });
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                Intent hometnt = new Intent(mContext, MainActivity.class);
                startActivity(hometnt);
                finish();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                Intent searchtnt = new Intent(mContext, SearchActivity.class);
                startActivity(searchtnt);
                finish();
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent addtnt = new Intent(mContext, AddActivity.class);
                startActivity(addtnt);
                finish();
            }
        });

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "NOTIFICATION", Toast.LENGTH_SHORT).show();
                Intent notificationtnt = new Intent(mContext, NotificationActivity.class);
                startActivity(notificationtnt);
                finish();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "PROFILE", Toast.LENGTH_SHORT).show();
                Intent profiletnt = new Intent(mContext, ProfileActivity.class);
                startActivity(profiletnt);
                finish();
            }
        });
    }

    //@Override
   // public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.profile_menu, menu);
       // return true;
   // }
}
