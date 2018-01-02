package com.social.bgram.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.social.bgram.R;
import com.social.bgram.Utils.SectionStatePagerAadpter;

import java.util.ArrayList;

/**
 * Created by user on 12/22/2017.
 */

public class AccountSettingActivity extends AppCompatActivity {

    private static final String TAG = "AccountSettingActivity";
    private Context mContext;

    private SectionStatePagerAadpter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        mContext = AccountSettingActivity.this;
        Log.d(TAG, "onCreate: started.");

        // initialization for view pager in account setting activity
        mViewPager = (ViewPager) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        setupSettingsList(); // implement SettingsList
        setupFragments();    // implement Fragments




        //setup the backarrow for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'ProfileActivity'");
                finish();
            }
        });
    }

       // setupFragments
    private void setupFragments(){
        pagerAdapter = new SectionStatePagerAadpter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment)); //fragment 0
        pagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out_fragment)); //fragment 1
    }

    private void setViewPager(int fragmentNumber){
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setViewPager: navigating to fragment #: " + fragmentNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }



     // setupSettingsList with display item in list view by array adapter
    private void setupSettingsList(){
        Log.d(TAG, "setupSettingsList: initializing 'Account Settings' list.");
        ListView listView = (ListView) findViewById(R.id.lvAccountSetting);

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment)); //fragment 0
        options.add(getString(R.string.sign_out_fragment)); //fragement 1

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

    // when clock on item in list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating to fragment#: " + position);
                setViewPager(position);
            }
        });

    }
}


    //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         //   @Override
         //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         //       Log.d(TAG, "onItemClick: navigating to fragment#: " + position);
          //      setViewPager(position);
          //  }
        //});
