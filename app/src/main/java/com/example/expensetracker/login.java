package com.example.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText Email, password;
    TextView Signin;
    TextView forgotpassword, signup;
    FirebaseAuth auth;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();
        Signin = findViewById(R.id.signin);
        forgotpassword = findViewById(R.id.forgotpassword);
        preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Check if the user is already logged in
        if (preferences.getBoolean("loggedIn", false)) {
            startMainPageActivity();
            finish(); // finish the login activity
        }


        signup.setOnClickListener(view -> {
            Intent i = new Intent(login.this, signup.class);
            startActivity(i);
        });
        Signin.setOnClickListener(view -> {
            String userEmail = Email.getText().toString();
            String userPassword = password.getText().toString();
            if (userEmail.isEmpty()) {
                Email.requestFocus();
                Email.setError("Enter Your Email");
            } else if (userPassword.isEmpty()) {
                password.requestFocus();
                password.setError("Enter Your Password");
            } else {

                signin(userEmail, userPassword);

            }

        });
        forgotpassword.setOnClickListener(view -> {
            Intent i = new Intent(login.this, forgotPassword.class);
            startActivity(i);
        });
    }

    public void signin(String userEmail, String userPassword) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null && user.isEmailVerified()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.apply();
                    startMainPageActivity();
                    clearInputFields();
                    finish();
                    clearInputFields();
                } else {
                    // Email is not verified
                    Toast.makeText(login.this, "Email not verified. Check your email for a verification link.", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(login.this, "Check your Email and Password", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void clearInputFields() {
        Email.setText("");
        password.setText("");
    }

    private void startMainPageActivity() {
        Intent i = new Intent(login.this, MainPage.class);
        startActivity(i);
    }
}