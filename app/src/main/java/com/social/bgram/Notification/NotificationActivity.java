package com.social.bgram.Notification;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.social.bgram.R;
import com.social.bgram.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by user on 12/10/2017.
 */

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    private static final int  ACTIVITY_NO =3 ;

    private Context mContext= NotificationActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: started.");

        //setUpBottomNavigationView();
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
}
