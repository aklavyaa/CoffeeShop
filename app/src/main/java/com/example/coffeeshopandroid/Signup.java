package com.example.coffeeshopandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private AppCompatButton signUp;
    private FirebaseAuth mAuth;
    private EditText user_name;
    private EditText email,password;
    private String TAG = Signup.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();

        user_name = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Signup.this,Dashboard.class);
//                startActivity(intent);

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
                                    Log.e(TAG, "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    CustomConstants.uuid = user.getUid();
                                    startActivity(new Intent(Signup.this,Dashboard.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.e(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Signup.this, task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }
                            }
                        });

            }
        });
    }
}