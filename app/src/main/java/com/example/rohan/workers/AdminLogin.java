package com.example.rohan.workers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity  {
    private Button mButton;
    private EditText uname,upass;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        mButton = (Button)findViewById(R.id.login);
        uname = (EditText)findViewById(R.id.useriD);
        upass = (EditText)findViewById(R.id.password);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if((uname.getText().toString().equals("admin123"))&& upass.getText().toString().equals("125pass@"))
//                {
//                    openunitchange();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Try Again",
//                            Toast.LENGTH_LONG).show();
//                }
                userlogin();
            }
        });
    }

    private void userlogin() {
        String email = uname.getText().toString().trim();
        String password = upass.getText().toString().trim();
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                openunitchange();
                            } else {
                                // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(AdminLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }

    public void openunitchange()
    {
        Intent intent = new Intent(this, AddRemoveUnit.class);
        startActivity(intent);
    }
}
