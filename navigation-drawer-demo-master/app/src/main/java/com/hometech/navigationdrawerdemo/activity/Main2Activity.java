package com.hometech.navigationdrawerdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.hometech.navigationdrawerdemo.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    //google api client
    private GoogleApiClient mGoogleApiClient;
    private Button butoonout;
    ImageView iv;
    CircleImageView circleImageView;
    //Signing Options
    private GoogleSignInOptions gso;
    private String u_name = null, u_mail = null, u_image = null;
    private TextView textViewName;
    private TextView textViewEmail;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b = getIntent().getExtras();
        u_name = b.getString("name");
        u_mail = b.getString("mail");
        u_image = b.getString("image");
        Log.e("PROFILE IMAGE", u_image);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        iv = (ImageView) findViewById(R.id.p_image);
        circleImageView=(CircleImageView)findViewById(R.id.profile_image);

        textViewName.setText(u_name);
        textViewEmail.setText(u_mail);




        Picasso.with(Main2Activity.this)

                .load(u_image)
                .resize(110, 100)
                .into(iv);

        Picasso.with(Main2Activity.this)

                .load(u_image)
                .resize(110, 100)
                .into(circleImageView);

        butoonout = (Button) findViewById(R.id.bout);
        butoonout.setOnClickListener(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

    }

  /* // protected void onStart() {
    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
    .build();
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
    .build();
    mGoogleApiClient.connect();
    super.onStart();

    //}*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        Intent i = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(i);
                        finish();


                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}