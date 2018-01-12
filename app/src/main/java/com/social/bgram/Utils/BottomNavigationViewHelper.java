package com.social.bgram.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.social.bgram.Add.AddActivity;
import com.social.bgram.Home.HomeActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.Profile.ProfileActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by user on 12/10/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationView";

    public static void setUpBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setUpBottomNavigationView: BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent1= new Intent(context, HomeActivity.class); //ACTIVITY_NO =0
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2= new Intent(context, SearchActivity.class); //ACTIVITY_NO =1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_add:
                        Intent intent3= new Intent(context, AddActivity.class); //ACTIVITY_NO =2
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_notification:
                        Intent intent4= new Intent(context, NotificationActivity.class); //ACTIVITY_NO =3
                        context.startActivity(intent4);
                        break;

                    case R.id.ic_profile:
                        Intent intent5= new Intent(context, ProfileActivity.class); //ACTIVITY_NO =4
                        context.startActivity(intent5);
                        break;
                }

                return false;
            }
        });
    }
}
