package com.social.bgram.Add;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.social.bgram.Home.HomeActivity;
import com.social.bgram.Notification.NotificationActivity;
import com.social.bgram.Profile.ProfileActivity;
import com.social.bgram.R;
import com.social.bgram.Search.SearchActivity;

import java.security.Permissions;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";

    //constants
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    private ViewPager mViewPager;

    private Context mContext= AddActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        if(checkPermissionsArray(com.social.bgram.Utils.Permissions.PERMISSIONS)){
            /*setupViewPager();*/
        }else{
            verifyPermissions(com.social.bgram.Utils.Permissions.PERMISSIONS);
        }
    }

    /*
    **************** verifiy all the permissions checked and passed to the array *******************
    */

    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                AddActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }


            /*
            ******************** Check permissions in all of array *********************
            */

    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){ // check all of array
            String check = permissions[i]; // for check single permission
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }


    /*
     ******************** Check a single permission is it has been verified *********************
    */

    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(AddActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }


}
