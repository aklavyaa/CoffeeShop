package com.example.coffeeshopandroid;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.bolts.Task;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton signInBtn;
    private TextView text_forgot,signup;
     String TAG = "Yuvraj";
    private FirebaseAuth mAuth;


    private CallbackManager callbackManager;
    private EditText user_name,password;

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
            }
        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
        }
    }

    private static final String EMAIL = "email";


    private void handleFacebookAccessToken(AccessToken token) {
        Log.e(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this,Dashboard.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }


                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        mAuth = FirebaseAuth.getInstance();


        user_name = findViewById(R.id.user_name);
                password = findViewById(R.id.password);


        signInBtn = findViewById(R.id.sign_in);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });
        text_forgot = findViewById(R.id.text_forgot);
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        // If you are using in a fragment, call loginButton.setFragment(this);
         callbackManager = CallbackManager.Factory.create();

//        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        Log.e(TAG, "onSuccess: " );

                        handleFacebookAccessToken(loginResult.getAccessToken());


//                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),(jsonObject, graphResponse) -> {
//                            try {
//                                Log.e(TAG, "fb json object: " + jsonObject);
//                                Log.e(TAG, "fb graph response: " + graphResponse);
//
//                                String id = jsonObject.getString("id");
//                                String first_name = jsonObject.getString("first_name");
//                                String last_name = jsonObject.getString("last_name");
//                                String gender = jsonObject.getString("gender");
//                                String birthday = jsonObject.getString("birthday");
//                                String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
//
//
//
//
//
//                                String email;
//                                if (jsonObject.has("email")) {
//                                    email = jsonObject.getString("email");
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        });
//
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "first_name,last_name,email"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
//                        request.setParameters(parameters);
//                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e(TAG, "onCancel: " );
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e(TAG, "onError: " );
                    }
                });

        printHashKey(MainActivity.this);
        text_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,Signup.class);
//                startActivity(intent);


                mAuth.signInWithEmailAndPassword(user_name.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.e(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(MainActivity.this,Dashboard.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.e(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this,task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });




            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}