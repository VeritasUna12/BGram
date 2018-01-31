package com.social.bgram.Profile;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.social.bgram.Add.AddActivity;
import com.social.bgram.Home.HomeActivity;
import com.social.bgram.Login.LoginActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;
import com.social.bgram.Utils.FirebaseMethods;
import com.social.bgram.Utils.UniversalImageLoader;
import com.social.bgram.models.User;
import com.social.bgram.models.UserAccountSettings;
import com.social.bgram.models.UserSettings;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";


    /*private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;*/

    //Firebase : Declare an instance of Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private FirebaseMethods mFirebaseMethods;


    //widgets
    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mUsername, mWebsite, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mDisplayName = (TextView) view.findViewById(R.id.display_name);
        mUsername = (TextView) view.findViewById(R.id.profileName);
        mWebsite = (TextView) view.findViewById(R.id.website);
        mDescription = (TextView) view.findViewById(R.id.description);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mPosts = (TextView) view.findViewById(R.id.tvPosts);
        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        /*mProgressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);*/
        /*gridView = (GridView) view.findViewById(R.id.gridView);*/
        toolbar = (Toolbar) view.findViewById(R.id.profileToolBar);
        profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(getActivity());
        Log.d(TAG, "onCreateView: stared.");


        setupToolbar();
        circularFloatingActionMenu();
        setupFirebaseAuth();

        return view;
    }


    /*
     *********************** Setting Profile Fragment Widgets ************************
     */

    private void setProfileWidgets(UserSettings userSettings) {
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());


        /*User user = userSettings.getUser();*/
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        /*mWebsite.setText(settings.getWebsite());*/
        /*mDescription.setText(settings.getDescription());*/
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        /*mProgressBar.setVisibility(View.GONE);*/
    }


    /*
     *********************** Responsible for setting up the profile toolbar ************************
     */

    private void setupToolbar(){

        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


/*
     ************************************ Firebase DataBase ****************************************
     */

    // Setup the firebase auth object
    private void setupFirebaseAuth(){

        //initialize the FirebaseAuth Object.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase =  FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
    private void circularFloatingActionMenu() {

        final ImageView icon = new ImageView(mContext); // Create an icon
        icon.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_launch_black_24dp));

        final FloatingActionButton actionButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(icon)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        ImageView homeIcon          = new ImageView(mContext);
        ImageView searchIcon        = new ImageView(mContext);
        ImageView addIcon           = new ImageView(mContext);
        ImageView profileIcon       = new ImageView(mContext);
        ImageView notificationIcon  = new ImageView(mContext);

        homeIcon            .setImageDrawable( ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_home) );
        profileIcon         .setImageDrawable( ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_profile) );
        notificationIcon    .setImageDrawable( ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_notification) );
        addIcon             .setImageDrawable( ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_add) );
        searchIcon          .setImageDrawable( ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.ic_search) );

        SubActionButton homeButton = itemBuilder.setContentView(homeIcon).build();
        SubActionButton searchButton = itemBuilder.setContentView(searchIcon).build();
        SubActionButton addButton = itemBuilder.setContentView(addIcon).build();
        SubActionButton profileButton = itemBuilder.setContentView(profileIcon).build();
        SubActionButton notificationButton = itemBuilder.setContentView(notificationIcon).build();

        //show icons in Floating Action Menu
        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
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
                Toast.makeText(getActivity().getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                Intent hometnt = new Intent(mContext, HomeActivity.class);
                startActivity(hometnt);
                getActivity().finish();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                Intent searchtnt = new Intent(mContext, SearchActivity.class);
                startActivity(searchtnt);
                getActivity().finish();
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent addtnt = new Intent(mContext, AddActivity.class);
                startActivity(addtnt);
                getActivity().finish();
            }
        });

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "NOTIFICATION", Toast.LENGTH_SHORT).show();
                Intent notificationtnt = new Intent(mContext, NotificationActivity.class);
                startActivity(notificationtnt);
                getActivity().finish();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "PROFILE", Toast.LENGTH_SHORT).show();
                Intent profiletnt = new Intent(mContext, ProfileActivity.class);
                startActivity(profiletnt);
                getActivity().finish();
            }
        });
    }

    /*
     ***********************************************************************************************
     */

}