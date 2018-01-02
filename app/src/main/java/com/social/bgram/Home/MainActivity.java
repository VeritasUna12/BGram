package com.social.bgram.Home;

//import com.example.instagram.Home.HomeFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.social.bgram.R;
import com.social.bgram.Utils.BottomNavigationViewHelper;
import com.social.bgram.Utils.SectionPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int  ACTIVITY_NO =0 ;

    private Context mContext= MainActivity.this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting."); // start


        setUpBottomNavigationView(); // to run bottomNavigationViewEx in mobile
        setupViewPager();  // to run viewpager in mobile
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
        tabLayout.getTabAt(1).setIcon(R.drawable.logo);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_send);

    }

    // BottomNavigationView Setup
    private void setUpBottomNavigationView() {
//        Log.d(TAG, "setUpBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavVeiwbar);
//        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
//       BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
//        Menu menu= bottomNavigationViewEx.getMenu();
//        MenuItem menuItem= menu.getItem(ACTIVITY_NO);
//        menuItem.setChecked(true);


    }
        //bottomNavigationViewEx.enableAnimation(false);
        //bottomNavigationViewEx.enableItemShiftingMode(false);
        //bottomNavigationViewEx.enableShiftingMode(false);
        //bottomNavigationViewEx.setTextVisibility(false);

}
