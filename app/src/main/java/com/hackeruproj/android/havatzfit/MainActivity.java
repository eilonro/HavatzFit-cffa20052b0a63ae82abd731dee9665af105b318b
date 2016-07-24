package com.hackeruproj.android.havatzfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//set here google login
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener
{
    //start TAG and RC setting
    private static final String TAG = "Google login";
    private static final int RC_SIGN_IN = 9001;
    //finish TAG and RC setting

    // start declare auth
    private FirebaseAuth mAuth;
    // finish declare auth

    //start delcare auth listener and GoogleAPIClient
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    //finish delcare auth listenerand and GoogleAPIClient


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start declaring GoogleSignIn Btn
        findViewById(R.id.googleLoginBtn).setOnClickListener(this);
        //finish declaring GoogleSignIn Btn

        //start config signin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        //end config signin

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this/*activity*/,this/*OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        //start initialize Auth
        mAuth = FirebaseAuth.getInstance();
        //end initialize Auth

        //start auth state listener
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                //if user allready logged, go to lobby page if not stay here
                lobbyPage(user);
            }
        };
        //end auth state listener
    }

    //start add onStart listener
    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    //end add onStart listener

    //start onStop remove Auth state listener
    @Override
    protected void onStop()
    {
        super.onStop();
        //end the listener for the suser state, once the user is online
        //there is no need to keep checking if he is logged in!
        mAuth.removeAuthStateListener(mAuthListener);
    }
    //end onStop remove Auth state listener

    //start onActivity result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent and inputs all the UserData in the UserUtility Static class
        if(requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                // Google Sign In failed, update UI appropriately
                lobbyPage(null);
            }
        }
    }

    //end onActivity result

    //start authWithGoogle
    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        //start client loading
        //start loading progress bar
        //end client loading

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if(!task.isSuccessful())
                    {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    //start end progrewssBar
                    // hideProgressDialog();
                    //end end progrewssBar
                }
            });
    }
    //end authWithGoogle

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //start go to logged page method
    private void lobbyPage(FirebaseUser user)
    {
        //end loading progress bar
        if(user != null)
        {
            //if user logged in do succesfull user logged actions
        }
        else
        {
            //if user logged in failed or user is not logged
        }
    }
    //end go to logged page method

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(MainActivity.this, R.string.cncFailMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.googleLoginBtn:
                signIn();
                break;
        }
    }
}
