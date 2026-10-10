package com.example.registrationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText etId, etName, etEmail, etMobile;
    Button btnInsert, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);

        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // 1. INSERT USER
        btnInsert.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please enter Name, Email, and Mobile", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertUser(name, email, mobile);
            if (isInserted) {
                Toast.makeText(this, "User Inserted Successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. VIEW ALL USERS
        btnView.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAllUsers();
            if (cursor.getCount() == 0) {
                showMessage("Registered Users", "No user data found.");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append("ID: ").append(cursor.getString(0)).append("\n");
                buffer.append("Name: ").append(cursor.getString(1)).append("\n");
                buffer.append("Email: ").append(cursor.getString(2)).append("\n");
                buffer.append("Mobile: ").append(cursor.getString(3)).append("\n\n");
            }

            showMessage("Registered Users", buffer.toString());
        });

        // 3. UPDATE USER
        btnUpdate.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();

            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter User ID to update", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = dbHelper.updateUser(id, name, email, mobile);
            if (isUpdated) {
                Toast.makeText(this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Update Failed (ID not found)", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. DELETE USER
        btnDelete.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();

            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter User ID to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isDeleted = dbHelper.deleteUser(id);
            if (isDeleted) {
                Toast.makeText(this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Delete Failed (ID not found)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etEmail.setText("");
        etMobile.setText("");
    }
}