package com.prapanca.matsya;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SigninActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private static final String TAG = "SigninActivity";

    private static final int RC_SIGN_IN = 9001;

    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;
    private TextView userinfoF,userinfoG;
    private Button loginfbButton,fbLogin,fbLogout,gLogin,gLogout;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_signin);

        //Views
        userinfoF = (TextView) findViewById(R.id.userinfof);
        loginfbButton = (LoginButton) findViewById(R.id.loginfb_button);
        fbLogin = (Button) findViewById(R.id.fb_login);
        fbLogout=(Button)findViewById(R.id.fb_logout);
        gLogin = (Button) findViewById(R.id.google_login);
        gLogout= (Button)findViewById(R.id.google_sign_out);
        userinfoG = (TextView) findViewById(R.id.userinfog);





        // Button listeners

        fbLogin.setOnClickListener(this);
        fbLogout.setOnClickListener(this);
        gLogin.setOnClickListener(this);
        gLogout.setOnClickListener(this);
        loginfbButton.setOnClickListener(this);



        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
      GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
              .requestEmail()
            .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
       mGoogleApiClient = new GoogleApiClient.Builder(this)
               .enableAutoManage((FragmentActivity)this/* FragmentActivity */, this /* OnConnectionFailedListener */)
              .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
        // [END build_client]
        //Setup necessary options for Facebook login
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                       // userinfoF.setText(
                         //       "User ID: "
                           //             + loginResult.getAccessToken().getUserId()

                       // );
                       // updateUI(true);
                         Intent myIntent = new Intent(SigninActivity.this, TermsConditions.class);
                        // myIntent.putExtra("key", value); //Optional parameters
                         startActivity(myIntent);
                        Toast.makeText(SigninActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                        updateFacebookUI(true);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SigninActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SigninActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_login:
                LoginManager.getInstance().
                        logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.fb_logout:
                LoginManager.getInstance().logOut();
                updateFacebookUI(false);
                break;
            case R.id.google_login:
                signIn();
                break;
            case R.id.google_sign_out:
                signOut();
                break;
            default:
               // flog.wtf("Code shouldn't come here");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
           Intent myIntent = new Intent(SigninActivity.this, TermsConditions.class);
            // myIntent.putExtra("key", value); //Optional parameters
            startActivity(myIntent);
            Toast.makeText(SigninActivity.this, "Logged in with google", Toast.LENGTH_LONG).show();


        }
    }

   /* private void updateGoogleDetails(GoogleSignInResult result) {
        Flog.d("Google Sign in updateGoogleDetails: " + result.isSuccess());
        User user = globals.getUser();
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Flog.wtf("Google user logged in - " + acct.getDisplayName());

            user.setGoogleLoggedIn(true);
            user.setGoogleToken(acct.getIdToken());
            user.setGoogleUid(acct.getEmail());
            user.setGoogleUserName(acct.getId());
            user.setGoogleGivenName(acct.getGivenName());
            user.setGoogleFamilyName(acct.getFamilyName());
            user.setGoogleImageUrl(acct.getPhotoUrl() == null ? "" : acct.getPhotoUrl().toString());
//                mToastInfo.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            registerAndUpdateUser(user, Settings.google, true);
        } else {
            updateGoogleDetailsLogout(user);

        }
    }

    private void updateGoogleDetailsLogout(User user) {
        Flog.wtf("Google user not logged in");
        user.setGoogleLoggedIn(false);
        user.setGoogleUid("");
        user.setGoogleUserName("");
        user.setGoogleGivenName("");
        user.setGoogleFamilyName("");
        user.setGoogleImageUrl("");
        registerAndUpdateUser(user, Settings.google, false);
    }

    public void registerAndUpdateUser(User user, String loginType, boolean loggedIn) {
//        Here register new user, setup global variable, update local storage & update UI if required
        mloginTypeSentForRegister = loginType;
        mUserSentForRegister = user;
        String loginUid = loginType.equals(Settings.fb) ? user.getFbUid() : loginType.equals(Settings.google) ? user.getGoogleUid() : user.getEmail();

        if (loggedIn && !AppUtils.getSpBool(getActivity().getApplicationContext(), String.format(Settings.sp_is_registered, loginType, loginUid))) {
            JSONObject postdata = new JSONObject();
            JSONObject network = new JSONObject();
            try {
                switch (loginType) {
                    case Settings.fb:
                        network.put("type", "F");
                        network.put("auth_token", user.getFbToken());
                        network.put("uid", user.getFbUid());
                        network.put("username", user.getFbUserName());
                        network.put("first_name", user.getFbFirstName());
                        network.put("last_name", user.getFbLastName());
                        network.put("profile_img_url", user.getFbImageUrl());
                        postdata.put("network", network);
                        break;
                    case Settings.google:
                        network.put("type", "G");
                        network.put("auth_token", user.getGoogleToken());
                        network.put("uid", user.getGoogleUid());
                        network.put("username", user.getGoogleUserName());
                        network.put("first_name", user.getGoogleGivenName());
                        network.put("last_name", user.getGoogleFamilyName());
                        network.put("profile_img_url", user.getGoogleImageUrl());
                        postdata.put("network", network);
                        break;
                    case Settings.email:
                        break;
                    default:
                        break;
                }
            } catch (JSONException je) {
                Flog.wtf("Need to handle this exception, json exception getting user data for " + loginType);
            }
            mLaunchedRegister = true;
            new Signup(this, postdata, getActivity());
        } else {
            DataReturnFormat data = new DataReturnFormat();
            data.setData(null);
            data.setStatus(Settings.SUCCESS);
            mLaunchedRegister = false;
            dataFetched(data);
        }

    }

    @Override
    public void dataFetched(DataReturnFormat data) {
        if (data.getStatus().equals(Settings.SUCCESS)) {
            User user = mUserSentForRegister;
            String loginType = mloginTypeSentForRegister;
            String loginUid = loginType.equals(Settings.fb) ? user.getFbUid() : loginType.equals(Settings.google) ? user.getGoogleUid() : user.getEmail();

            globals.setUser(user.updateUser(globals.getUser(), user, loginType));
            UserLocalStorage.setLoggedinUser(getActivity().getApplicationContext(), user);
            if (mLaunchedRegister) {
                AppUtils.putSpBool(getActivity().getApplicationContext(), String.format(Settings.sp_is_registered, loginType, loginUid), true);
                mLaunchedRegister = false;
            }
        } else {
            Flog.wtf("Some error occurred registering, so user will have to re-register later");
        }

    }

   */  @Override
   public void onConnectionFailed(ConnectionResult connectionResult) {
       // An unresolvable error has occurred and Google APIs (including Sign-In) will not
       // be available.
       Log.d(TAG, "onConnectionFailed:" + connectionResult);
   }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }


    }

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            userinfoG.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateGoogleUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateGoogleUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        updateGoogleUI(true);

    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateGoogleUI(false);
                        // [END_EXCLUDE]

                    }
                });
        Toast.makeText(SigninActivity.this, "Logged out from google", Toast.LENGTH_LONG).show();
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateGoogleUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateGoogleUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.google_login).setVisibility(View.GONE);
            findViewById(R.id.google_sign_out).setVisibility(View.VISIBLE);
        } else {
            userinfoG.setText(R.string.signed_out);

            findViewById(R.id.google_login).setVisibility(View.VISIBLE);
            findViewById(R.id.google_sign_out).setVisibility(View.GONE);
           // userinfoG.setText(acct.getDisplayName());
        }

    }

    private void updateFacebookUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.fb_login).setVisibility(View.GONE);
            findViewById(R.id.fb_logout).setVisibility(View.VISIBLE);
        } else {
            userinfoF.setText(R.string.signed_out);

            findViewById(R.id.fb_login).setVisibility(View.VISIBLE);
            findViewById(R.id.fb_logout).setVisibility(View.GONE);
            userinfoF.setText(Profile.getCurrentProfile().getFirstName());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

        }
        return true;
    }
}


