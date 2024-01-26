package com.example.scheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

    private EditText taskNameEditText, deadlineEditText;
    private Button addButton;
    private LinearLayout todoListLayout;

    private List<TaskItem> taskItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        taskNameEditText = view.findViewById(R.id.taskNameEditText);
        deadlineEditText = view.findViewById(R.id.deadlineEditText);
        addButton = view.findViewById(R.id.addButton);
        todoListLayout = view.findViewById(R.id.todoListLayout);

        taskItemList = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskToList();
            }
        });

        return view;

    }

    private void addTaskToList() {
        String taskName = taskNameEditText.getText().toString();
        String deadline = deadlineEditText.getText().toString();

        if (!taskName.isEmpty() && !deadline.isEmpty()) {
            TaskItem taskItem = new TaskItem(taskName, deadline, false);
            taskItemList.add(taskItem);

            LinearLayout taskItemLayout = new LinearLayout(requireContext());
            taskItemLayout.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(taskName);
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                taskItem.setCompleted(isChecked);
            });

            TextView deadlineTextView = new TextView(requireContext());
            deadlineTextView.setText(String.format("Deadline: %s", deadline));

            Button editButton = new Button(requireContext());
            editButton.setText("Edit");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditDialog(taskItem, taskItemLayout);
                }
            });

            Button deleteButton = new Button(requireContext());
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeTaskFromList(taskItem, taskItemLayout);
                }
            });

            taskItemLayout.addView(checkBox);
            taskItemLayout.addView(deadlineTextView);
            taskItemLayout.addView(editButton);
            taskItemLayout.addView(deleteButton);

            todoListLayout.addView(taskItemLayout);

            taskNameEditText.getText().clear();
            deadlineEditText.getText().clear();
        }
    }

    private void removeTaskFromList(TaskItem taskItem, LinearLayout taskItemLayout) {
        taskItemList.remove(taskItem);
        todoListLayout.removeView(taskItemLayout);
    }

    private void showEditDialog(TaskItem taskItem, LinearLayout taskItemLayout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Task");

        LinearLayout editLayout = new LinearLayout(requireContext());
        editLayout.setOrientation(LinearLayout.VERTICAL);

        EditText editTaskNameEditText = new EditText(requireContext());
        editTaskNameEditText.setText(taskItem.getTaskName());
        editTaskNameEditText.setHint("Task Name");

        EditText editDeadlineEditText = new EditText(requireContext());
        editDeadlineEditText.setText(taskItem.getDeadline());
        editDeadlineEditText.setHint("Deadline");

        editLayout.addView(editTaskNameEditText);
        editLayout.addView(editDeadlineEditText);

        builder.setView(editLayout);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newTaskName = editTaskNameEditText.getText().toString();
                String newDeadline = editDeadlineEditText.getText().toString();

                if (!newTaskName.isEmpty() && !newDeadline.isEmpty()) {
                    taskItem.setTaskName(newTaskName);
                    taskItem.setDeadline(newDeadline);

                    CheckBox checkBox = (CheckBox) taskItemLayout.getChildAt(0);
                    checkBox.setText(newTaskName);

                    TextView deadlineTextView = (TextView) taskItemLayout.getChildAt(1);
                    deadlineTextView.setText(String.format("Deadline: %s", newDeadline));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    private static class TaskItem {
        private String taskName;
        private String deadline;
        private boolean completed;

        public TaskItem(String taskName, String deadline, boolean completed) {
            this.taskName = taskName;
            this.deadline = deadline;
            this.completed = completed;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getDeadline() {
            return deadline;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}