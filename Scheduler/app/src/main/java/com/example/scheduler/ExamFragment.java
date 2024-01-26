package com.example.scheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExamFragment extends Fragment {

    private EditText classNameEditText, examDateEditText, examTimeEditText, examLocationEditText;
    private LinearLayout examListLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        classNameEditText = view.findViewById(R.id.classNameEditText);
        examDateEditText = view.findViewById(R.id.examDateEditText);
        examTimeEditText = view.findViewById(R.id.examTimeEditText);
        examLocationEditText = view.findViewById(R.id.examLocationEditText);
        Button addButton = view.findViewById(R.id.addButton);
        examListLayout = view.findViewById(R.id.examListLayout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExamToList();
            }
        });

        return view;
    }

    private void addExamToList() {
        String className = classNameEditText.getText().toString();
        String examDate = examDateEditText.getText().toString();
        String examTime = examTimeEditText.getText().toString();
        String examLocation = examLocationEditText.getText().toString();

        if (!className.isEmpty() && !examDate.isEmpty() && !examTime.isEmpty() && !examLocation.isEmpty()) {
            TextView examTextView = new TextView(requireContext());
            examTextView.setText(String.format("%s\nDate: %s\nTime: %s\nLocation: %s", className,
                    examDate, examTime, examLocation));

            examListLayout.addView(examTextView);

            classNameEditText.getText().clear();
            examDateEditText.getText().clear();
            examTimeEditText.getText().clear();
            examLocationEditText.getText().clear();
        }

    }

}