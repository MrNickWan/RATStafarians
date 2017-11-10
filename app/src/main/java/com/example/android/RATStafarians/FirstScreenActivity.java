package com.example.android.RATStafarians;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
/**
 * Milestone V : Team Rastafarians (59)
 * @author Team Rastafarians
 * @version 1.0
 */
public class FirstScreenActivity extends AppCompatActivity {

    private Button PickLogin, PickReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        PickLogin = findViewById(R.id.loginButton);
        PickReg = findViewById(R.id.regButton);

        PickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(FirstScreenActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        PickReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(FirstScreenActivity.this, RegistrationActivity.class);
                startActivity(loginIntent);
            }

        });
    }
}