package com.social.bgram.Profile;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.social.bgram.Add.AddActivity;
import com.social.bgram.Home.HomeActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private Context mContext = ProfileActivity.this;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");

        //to not display all time in profile activity
//        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
//        mProgressBar.setVisibility(View.GONE);

        init();

    }

    private void init() {
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        /*
     ********************** Add ProfileFragment in ProfileActivity (container) ********************
     */

         /*Replace whatever is in the fragment_container view with this fragment,
         and add the transaction to the back stack so the user can navigate back*/
        ProfileFragment fragment = new ProfileFragment();
        android.support.v4.app.FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }

}