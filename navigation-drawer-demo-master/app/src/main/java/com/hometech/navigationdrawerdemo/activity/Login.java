package com.hometech.navigationdrawerdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.hometech.navigationdrawerdemo.R;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

   // boolean doubleBackToExitPressedOnce = false;

    //TextViews
  /*  private TextView textViewName;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;*/

    //Image Loader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initializing Views
       /* textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        profilePhoto = (NetworkImageView) findViewById(R.id.profileImage);*/

        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.e("TAG", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            finish();
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialog();
                    Log.e("TAG", "-------");
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
       // mGoogleApiClient.connect();
    }
   /* protected void onPause()
    {
        super.onPause();
        mGoogleApiClient.connect();
    }*/

    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.e("Log In------","Proccesing");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("Account",""+Auth.GoogleSignInApi.getSignInResultFromIntent(data));
            //Calling a new function to handle signin
            handleSignInResult(result);
            Log.e("Log In------","Proccesing1");
        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            Log.e("Log In------","Proccesing2");
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String uname = acct.getDisplayName();
            String umail = acct.getEmail();
            String photo = acct.getPhotoUrl().toString();
            Log.e("USER NAME", uname);
            Log.e("EMAIL ID", umail);
            Log.e("IMAGE", photo);
            //Displaying name and email
          /*  textViewName.setText(acct.getDisplayName());
            textViewEmail.setText(acct.getEmail());*/
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("name", uname);
            intent.putExtra("mail", umail);
            intent.putExtra("image", photo);
            startActivity(intent);
            finish();
            Log.e("Log In------","Proccesing3");
            /*
            //Initializing image loader
            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();

            imageLoader.get(acct.getPhotoUrl().toString(),
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

            //Loading image
            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);
            Log.d("image",acct.getPhotoUrl().toString());*/
        } else {

            //If login fails
           //Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Calling signin
            signIn();
            Log.e("Log In------","Proccesing0");
        } /*else if (v == signOutButton) {
            //Calling signout
            signOut();
        }*/
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("TAG", "onConnectionFailed:" + connectionResult);
    }
/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {

            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/
}

