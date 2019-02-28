package com.mtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mtp.Activity.RegisterActivity;
import com.mtp.Activity.TabContainerActivity;
import com.mtp.DAO.StaticLocationDao;
import com.mtp.Model.StaticLocation;
import com.mtp.Sync.RestRequests;
import com.mtp.Sync.RetrofitClient;
import com.orm.SugarContext;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // ICON PAGE
    // https://icons8.com/icon/set/google/color
//    otra https://www.iconfinder.com/search/?q=facebook&style=glyph


//    ENDPOINT
    //https://beeceptor.com


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

//    @BindView(R.id.bt_sign_in_gmail)
//    SignInButton signInButtonGmail;

    @BindView(R.id.bt_sign_in_facebook)
    LoginButton signInButtonFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SugarContext.init(this);

        // Close keyboard on background click
        ConstraintLayout constrainLayout = findViewById(R.id.backgroundWallpaper);
        constrainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(MainActivity.this);
            }
        });


        // Facebook LogIn
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    openLocationActivity();

//REVISAR SI CAL
//                    login.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
//                    login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//                            //Log.d("facebook", "succes" + loginResult.getAccessToken().getToken() + "id" + loginResult.getAccessToken().getExpires() + "data" + loginResult.getAccessToken().getUserId());
//                            conectedwithFacebook(loginResult.getAccessToken().getToken(),intent);
//                        }
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    // App code
                }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }




    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
            openLocationActivity();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void signIn() {
        Intent signInGmailIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInGmailIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @OnClick(R.id.tv_register_now)
    public void registerUser(View v){
        startRegisterActivity();
    }

    @OnClick(R.id.tv_no_account)
    public void registerUser2(View v){
        startRegisterActivity();
    }

    @OnClick(R.id.bt_sign_in_facebook_show)
    public void activeOnClickFacebookButton(View v){
        if (isLoggedIn())
            openLocationActivity();
        else
            signInButtonFacebook.callOnClick();
    }

    @OnClick(R.id.bt_login)
    public void loginStandard(){
        openLocationActivity();
    }

    @OnClick(R.id.bt_sign_in_gmail_show)
    public void activeOnClickGmailButton(View v){
        signIn();
    }

    public void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openLocationActivity(){
        syncStaticData();

        Intent intent = new Intent(this, TabContainerActivity.class);
        startActivity(intent);
    }

    private void syncStaticData(){
        syncLocation();
    }

    private void syncLocation(){
        //Create a handler for the RetrofitInstance interface//
        RestRequests restRequests = RetrofitClient.getRetrofitInstance().create(RestRequests.class);
        Call<List<StaticLocation>> call = restRequests.getAllStaticLocation();

        call.enqueue(new Callback<List<StaticLocation>>() {

            @Override
            public void onResponse(Call<List<StaticLocation>> call, Response<List<StaticLocation>> response) {
                for ( StaticLocation staticLocation : response.body()){
                    if (!StaticLocationDao.existStaticLocation(staticLocation))
                    StaticLocationDao.insert(staticLocation);
                }
                Log.d("Error", "Location have been syncronized");
            }

            @Override
            public void onFailure(Call<List<StaticLocation>> call, Throwable throwable) {
                Log.d("Error", "Location have not been syncronized");
            }
        });
    }

}
