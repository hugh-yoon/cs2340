package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;
    EditText loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput.findViewById(R.id.username_in);
        usernameInput.findViewById(R.id.password_in);
        loginBtn.findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(view -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            Log.i("Test Credentials", "Username: " + username + " and Password: " + password);
        });
    }
}