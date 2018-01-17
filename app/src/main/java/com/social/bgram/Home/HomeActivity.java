package com.social.bgram.Home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.social.bgram.Add.AddActivity;
import com.social.bgram.Login.LoginActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.Profile.ProfileActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;
import com.social.bgram.Utils.SectionPagerAdapter;
import com.social.bgram.Utils.UniversalImageLoader;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private Context mContext= HomeActivity.this;

    //firebase : Declare an instance of Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting."); // start

        setupFirebaseAuth();
        setUpBottomNavigationView();
        setupViewPager();
        initImageLoader();
    }

    // implement Universal image loader
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    // to Add 3 Tabs Camera , Home and Messeges in Home Activity
    private void setupViewPager()
    {
        SectionPagerAdapter adapter= new SectionPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new CameraFragment());
        adapter.AddFragment(new HomeFragment());
        adapter.AddFragment(new MessagesFragment());
        ViewPager viewPager=(ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_send);

    }

     /*
     ************************************ Firebase DataBase ****************************************
     */

    //checks to see if the 'user' is logged in
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

         if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
         }
    }

     // Setup the firebase auth object
    private void setupFirebaseAuth(){

        //initialize the FirebaseAuth Object.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // check if the user is logged in
                 checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*
     *********************************** Floating Action Menu **************************************
     */

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
        profileIcon         .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_profile) );
        notificationIcon    .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_notification) );
        addIcon             .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_add) );
        searchIcon          .setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_search) );

        SubActionButton homeButton = itemBuilder.setContentView(homeIcon).build();
        SubActionButton searchButton = itemBuilder.setContentView(searchIcon).build();
        SubActionButton addButton = itemBuilder.setContentView(addIcon).build();
        SubActionButton profileButton = itemBuilder.setContentView(profileIcon).build();
        SubActionButton notificationButton = itemBuilder.setContentView(notificationIcon).build();

        //show icons in Floating Action Menu
        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(profileButton)
                .addSubActionView(notificationButton)
                .addSubActionView(addButton)
                .addSubActionView(searchButton)
                .addSubActionView(homeButton)
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
                Intent hometnt = new Intent(mContext, HomeActivity.class);
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

    /*
     ***********************************************************************************************
     */

}