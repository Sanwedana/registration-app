package com.example.registrationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView tvReceivedData;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvReceivedData = findViewById(R.id.tvReceivedData);
        btnBack = findViewById(R.id.btnBack);

        // Receive data passed through Intent
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null) {
            tvReceivedData.setText("Welcome, " + userName + "!");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}
