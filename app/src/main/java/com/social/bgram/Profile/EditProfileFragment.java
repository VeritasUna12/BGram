package com.social.bgram.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.social.bgram.R;

/**
 * Created by AhmedRashad on 12/23/2017.
 */

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        return view;
    }
}


