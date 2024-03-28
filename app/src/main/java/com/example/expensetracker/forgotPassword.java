package com.example.expensetracker;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText resetEmail;
    TextView submit;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        resetEmail = findViewById(R.id.resetemail);
        submit = findViewById(R.id.submit);
        auth = FirebaseAuth.getInstance();
        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(view -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        submit.setOnClickListener(view -> {
            String email = resetEmail.getText().toString();
            resetpassword(email);
        });

    }
    public void resetpassword(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(forgotPassword.this, "Password Reset Link Send To Your Email Kindly Check", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(forgotPassword.this, "Enter Your Registered Email Address", Toast.LENGTH_SHORT).show();
            }
        });
    }
}