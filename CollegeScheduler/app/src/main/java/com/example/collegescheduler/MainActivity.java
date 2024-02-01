package com.example.collegescheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button story1Btn = findViewById(R.id.story1Btn);
        Button story2Btn = findViewById(R.id.story2Btn);
        Button story3Btn = findViewById(R.id.story3Btn);
        Button story4Btn = findViewById(R.id.story4Btn);

        story1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Story1.class));
            }
        });

        story2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, story2.class));
            }
        });

        story3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, story3.class);
                story3 fragment = new story3();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_st3, fragment, "Story3FragmentTag")
                        .addToBackStack(null)
                        .commit();
//                startActivity(intent);
            }
        });

        story4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, story4.class));
            }
        });
    }
}