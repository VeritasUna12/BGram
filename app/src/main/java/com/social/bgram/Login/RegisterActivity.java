package com.social.bgram.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.social.bgram.Home.HomeActivity;
import com.social.bgram.R;
import com.social.bgram.Utils.FirebaseMethods;
import com.social.bgram.models.User;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Context mContext;
    private String email, username, password;
    private EditText mEmail, mPassword, mUsername;
    private Button btnRegister;

    // Declare an instance of firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseMethods firebaseMethods;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String append = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);

        Log.d(TAG, "onCreate: started.");

        initWidgets();
        setupFirebaseAuth();
        init();
    }

        // When Click SignUp Button Greate New User
        private void init(){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = mEmail.getText().toString();
                    username = mUsername.getText().toString();
                    password = mPassword.getText().toString();

                    if(checkInputs(email ,username, password)){

                        firebaseMethods.registerNewEmail(email,password/*,username*/);
                    }
                }
            });
        }

        // Check All Fields Filled In SignUp Activity
        private boolean checkInputs(String email, String username, String password){
            Log.d(TAG, "checkInputs: checking inputs for null values.");
            if(email.equals("") || username.equals("") || password.equals("")){
                Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        // Initi the activity widgets

        private void initWidgets(){
            Log.d(TAG, "initWidgets: Initializing Widgets.");
            mEmail = (EditText) findViewById(R.id.input_email);
            mUsername = (EditText) findViewById(R.id.input_username);
            mPassword = (EditText) findViewById(R.id.input_password);
            btnRegister = (Button) findViewById(R.id.btn_register);
            mContext = RegisterActivity.this;

        }


        // Check if edit text null
        private boolean isStringNull(String string) {
            Log.d(TAG, "isStringNull: checking string if null.");

            if (string.equals("")) {
                return true;
            } else {
                return false;
            }
        }

     /*
     ************************************ Firebase DataBase ****************************************
     */

            /*
             ************ Check is email already exists in teh database ***************
             */
            private void checkIfUsernameExists(final String username) {
                Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference
                        .child(getString(R.string.dbname_users))
                        .orderByChild(getString(R.string.field_username))
                        .equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                            if (singleSnapshot.exists()){
                                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                                append = mReference.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);

                            }
                        }

                        String mUsername = "";
                        mUsername = username + append;

                        //add new user to the database
                        firebaseMethods.addNewUser(email, mUsername, "", "", "");

                        Toast.makeText(mContext, "Signup successful. Login Now ", Toast.LENGTH_SHORT).show();

                        mAuth.signOut();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }



    // Setup the firebase auth object

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        //initialize the FirebaseAuth Object.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkIfUsernameExists(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();

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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*
     ***********************************************************************************************
     */

}