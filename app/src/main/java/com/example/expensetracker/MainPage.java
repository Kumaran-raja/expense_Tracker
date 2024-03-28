package com.example.expensetracker;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class MainPage extends AppCompatActivity {

    EditText description,amount,date;
    TextView submit,expense;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);

        description=findViewById(R.id.description);
        amount=findViewById(R.id.amount);
        date=findViewById(R.id.date);
        submit=findViewById(R.id.submit);
        expense=findViewById(R.id.expense1);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        date.setText(currentDate);

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,listPage.class);
                startActivity(i);
            }
        });
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.length()==0){
                    description.requestFocus();
                    description.setError("Enter Description");
                } else if (amount.length()==0) {
                    amount.requestFocus();
                    amount.setError("Enter Amount");
                }
                else{
                    submitTo(description,amount,date);
                }
            }
        });
    }

    private void submitTo(EditText description, EditText amount, EditText date) {

        String description1=description.getText().toString();
        String amount1=amount.getText().toString();
        String date1=date.getText().toString();

        String key = myRef.child(auth.getUid()).push().getKey();

        // Create a Value object with the submitted data
        Value data = new Value(description1, amount1, date1);

        // Store the data under the user's ID with the generated key
        if (key != null) {
            myRef.child(auth.getUid()).child(key).setValue(data);
        }
    }
}