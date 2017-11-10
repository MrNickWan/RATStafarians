package com.example.android.RATStafarians;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
/**
 * Milestone V : Team Rastafarians (59)
 * @author Team Rastafarians
 * @version 1.0
 */
public class RegistrationActivity extends AppCompatActivity {

    private Button clickRegister, clickCancel;
    private TextView showAdminKeyText;
    private EditText  newEmail, newPassword,newAdminKey;
    private ProgressDialog progress;
    private Spinner userAdminSpinner;
    private FirebaseAuth fAuth;
    private final String adminKey = "ADMIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progress = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();

        newEmail = findViewById(R.id.entEmail);
        newPassword = findViewById(R.id.entPassword);

        clickRegister = findViewById(R.id.entRegister);
        clickCancel = findViewById(R.id.entCancel);

        showAdminKeyText = findViewById(R.id.entRegister);
        newAdminKey= findViewById(R.id.entAdminKey);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("User");
        spinnerArray.add("Admin");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAdminSpinner = findViewById(R.id.typeSpin);
        userAdminSpinner.setAdapter(adapter);

        clickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

        clickCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegistrationActivity.this, FirstScreenActivity.class);
                startActivity(mainIntent);
            }
        });
    }
    /**
     * Method registers user into Firebase Authentication page and tests to see if user is
     * Admin or a Basic User
     */
    private void registerUser() {
        String email = newEmail.getText().toString().trim();
        String password = newPassword.getText().toString().trim();
        String selected = userAdminSpinner.getSelectedItem().toString();

        if(email.contains("admin") && selected.equals("User")) {
            Toast.makeText(RegistrationActivity.this, "Can Not Have Admin as Username", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email Not Entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password Not Entered", Toast.LENGTH_SHORT).show();
            return;
        }
        //&& newAdminKey.getText().toString().trim() != adminKey
        //
        boolean adminResult = newAdminKey.getText().toString().trim().equals(adminKey);
        if (selected.equals("Admin")) {
            if (adminResult) {
                Toast.makeText(RegistrationActivity.this, "Admin Entered, Correct Admin Key", Toast.LENGTH_SHORT).show();
                email = "admin." + email;
            } else {
                Toast.makeText(RegistrationActivity.this, "Admin Entered, Incorrect Admin Key", Toast.LENGTH_SHORT).show();
                Intent failIntent = new Intent(RegistrationActivity.this, FirstScreenActivity.class);
                startActivity(failIntent);
            }
        } else if (selected.equals("User")){
            Toast.makeText(RegistrationActivity.this, "User Entered", Toast.LENGTH_SHORT).show();
        }

        progress.setMessage("Registering...");
        progress.show();

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful!",Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            Intent mainIntent = new Intent(RegistrationActivity.this, FirstScreenActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed!",Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            Intent mainIntent = new Intent(RegistrationActivity.this, FirstScreenActivity.class);
                            startActivity(mainIntent);
                        }
                    }
                });
    }
}