package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {


    EditText Email, password, confirmpassword;
    TextView signup;
    TextView existaccount;
    CheckBox conditions;
    TextView terms;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        signup = findViewById(R.id.signup);
        existaccount = findViewById(R.id.existaccount);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        existaccount.setOnClickListener(view -> {
            Intent i = new Intent(signup.this, login.class);
            startActivity(i);
        });

        signup.setOnClickListener(view -> {
            String userEmail = Email.getText().toString();
            String userPassword = password.getText().toString();

            String userConfirmPassword = confirmpassword.getText().toString();
            if (userEmail.isEmpty()) {
                Email.requestFocus();
                Email.setError("Enter Your Email");
            } else if (userPassword.isEmpty()) {
                password.requestFocus();
                password.setError("Enter Your Password");
            } else if (userConfirmPassword.isEmpty()) {
                confirmpassword.requestFocus();
                confirmpassword.setError("Enter Confirm Password");
            }
            else if(!userPassword.equals(userConfirmPassword)){
                confirmpassword.requestFocus();
                confirmpassword.setError("Enter Correct Account Number");
            }

            else {
                signUp(userEmail, userPassword);
            }
        });


    }

    private void signUp(String userEmail, String userPassword) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DatabaseReference myRef = database.getReference("Exist Email").child(auth.getUid());
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("email", userEmail);
                myRef.setValue(dataMap);
                sendVerificationEmail();
                Toast.makeText(signup.this, "Verification Link Send To Your Email", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(signup.this,login.class);
                startActivity(i);
            }
            else {
                // Registration failed
                Toast.makeText(signup.this, "Already Exist Login Your Account", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup.this, "Verification email sent to Your EMAIL", Toast.LENGTH_SHORT).show();
                            // You can add code to navigate to a verification activity or handle the UI accordingly
                        } else {
                            Toast.makeText(signup.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}