package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myfirstapp.databinding.FragmentFirstBinding;


public class FirstFragment extends Fragment {

    TextView showCountTextView;

    private FragmentFirstBinding binding;

    private void countMe(View view) {
        // Get the value of the text view
        String countString = showCountTextView.getText().toString();
        // Convert value to a number and increment it
        Integer count = Integer.parseInt(countString);
        count++;
        // Display the new value in the text view.
        showCountTextView.setText(count.toString());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        // Get the count text view
        showCountTextView = fragmentFirstLayout.findViewById(R.id.textview_first);

        return fragmentFirstLayout;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContentView(R.layout.activity_main);

        Button story1Btn = findViewById(R.id.story1Btn);
        Button story2Btn = findViewById(R.id.story2Btn);
        Button story3Btn = findViewById(R.id.story3Btn);
        Button story4Btn = findViewById(R.id.story4Btn);

        story1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstFragment.this, SecondFragment.class));
            }
        });

        story2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstFragment.this, ThirdFragment.class));
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

//        story4Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FirstFragment.this, story4.class));
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}