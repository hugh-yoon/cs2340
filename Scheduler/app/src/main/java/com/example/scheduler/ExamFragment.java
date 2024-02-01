package com.example.scheduler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;

public class ExamFragment extends Fragment {

    private ExamAdapter examAdapter;
    private List<Exam> examList = new ArrayList<>();
    private EditText nameEditText, classNameEditText, examLocationEditText;
    private TextView examDateTextView, examTimeTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.examListLayout);
        examAdapter = new ExamAdapter(examList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(examAdapter);

        nameEditText = view.findViewById(R.id.nameEditText);
        classNameEditText = view.findViewById(R.id.classNameEditText);
        examDateTextView = view.findViewById(R.id.examDateTextView);
        examTimeTextView = view.findViewById(R.id.examTimeTextView);
        examLocationEditText = view.findViewById(R.id.examLocationEditText);
        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExamToList();
            }
        });

        examDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        examTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        return view;
    }

    private void addExamToList() {
        String name = nameEditText.getText().toString();
        String className = classNameEditText.getText().toString();
        String examDate = examDateTextView.getText().toString();
        String examTime = examTimeTextView.getText().toString();
        String examLocation = examLocationEditText.getText().toString();

        if (!name.isEmpty() && !className.isEmpty() && isValidDate(examDate) && isValidTime(examTime) && !examLocation.isEmpty()) {

            TextView examTextView = new TextView(requireContext());
            examTextView.setText(String.format("%s\nDate: %s\nTime: %s\nLocation: %s", className,
                    examDate, examTime, examLocation));

            Exam newExam = new Exam(name, className, examDate, examTime, examLocation);
            examList.add(newExam);
            examAdapter.notifyDataSetChanged();

            nameEditText.getText().clear();
            classNameEditText.getText().clear();
            examDateTextView.setText(getString(R.string.select_date));
            examTimeTextView.setText(getString(R.string.select_time));
            examLocationEditText.getText().clear();
        } else {
            showToast("Please fill in all fields.");
        }

    }

    private boolean isValidDate(String inputDate) {
        return inputDate.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])");
    }

    private boolean isValidTime(String inputTime) {
        return inputTime.matches("([01][0-9]|2[0-3]):[0-5][0-9]");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format(Locale.US, "%02d/%02d", month + 1, dayOfMonth);
                        examDateTextView.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
                        examTimeTextView.setText(formattedTime);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

        private List<Exam> examList;

        public ExamAdapter(List<Exam> examList) {
            this.examList = examList;
        }

        @NonNull
        @Override
        public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcrow, parent, false);
            return new ExamViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
            Exam exam = examList.get(position);
            holder.bind(exam);
            holder.completedCheckBox.setChecked(exam.getIsCompleted());
        }

        @Override
        public int getItemCount() {
            return examList.size();
        }

        public class ExamViewHolder extends RecyclerView.ViewHolder {

            private TextView nameTextView;
            private TextView classTextView;
            private TextView dateTextView;
            private TextView timeTextView;
            private TextView locationTextView;
            private CheckBox completedCheckBox;

            public ExamViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                classTextView = itemView.findViewById(R.id.classTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
                timeTextView = itemView.findViewById(R.id.timeTextView);
                locationTextView = itemView.findViewById(R.id.locationTextView);
                completedCheckBox = itemView.findViewById(R.id.checkBox);

                completedCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Exam currentExam = examList.get(position);
                            currentExam.setCompleted(completedCheckBox.isChecked());

                            if (completedCheckBox.isChecked()) {
                                examList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }
                    }
                });
            }

            public void bind(Exam exam) {
                nameTextView.setText(exam.getName());
                classTextView.setText(exam.getClassName());
                dateTextView.setText("Date: " + exam.getExamDate());
                timeTextView.setText("Time: " + exam.getExamTime());
                locationTextView.setText("Location: " + exam.getLocation());
            }
        }
    }

}