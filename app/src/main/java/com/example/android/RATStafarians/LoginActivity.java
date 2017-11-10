package com.example.android.RATStafarians;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Milestone V : Team Rastafarians (59)
 * @author Team Rastafarians
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private EditText Email, Password;
    private Button Login, Cancel;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress = new ProgressDialog(this);

        Email = findViewById(R.id.userInput);
        Password = findViewById(R.id.passInput);
        Login = findViewById(R.id.button);
        Cancel = findViewById(R.id.cancelLogin);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            Intent hereIntent = new Intent(LoginActivity.this, ListActivity.class);
            startActivity(hereIntent);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Email.getText().toString(), Password.getText().toString());
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(LoginActivity.this, FirstScreenActivity.class);
                startActivity(cancelIntent);
            }
        });
    }

    /**
     * Validates whether or not user exists on Firebase.
     * @param userEmail User's email
     * @param userPassword User's password
     */
    private void validate(String userEmail, String userPassword) {

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email Not Entered!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password Not Entered", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Logging In...");
        progress.show();

        fAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful!",Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            Intent loginPassIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(loginPassIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed!",Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            Intent mainIntent = new Intent(LoginActivity.this, FirstScreenActivity.class);
                            startActivity(mainIntent);
                        }
                    }
                });
    }
}


