package com.example.scheduler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scheduler.databinding.FragmentTodoBinding;

import org.w3c.dom.Text;

public class TodoFragment extends Fragment {

    private List<Task> taskList= new ArrayList<>();;
    private TaskAdapter taskAdapter;
    private EditText taskNameEdit, courseNameEdit, descriptionEdit;
    private TextView dueDateTextview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        taskAdapter = new TaskAdapter(taskList);
        RecyclerView recyclerView = view.findViewById(R.id.taskListLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);

        taskNameEdit = view.findViewById(R.id.taskNameEditText);
        courseNameEdit = view.findViewById(R.id.courseNameEditText);
        descriptionEdit = view.findViewById(R.id.descriptionEditText);
        dueDateTextview = view.findViewById(R.id.dueDateTextView);
        Button add = view.findViewById(R.id.addButton);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addTask(); }
        });

        dueDateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showDatePickerDialog(); }
        });


        return view;

    }

    private void addTask() {
        String taskName = taskNameEdit.getText().toString();
        String courseName = courseNameEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        String dueDate = dueDateTextview.getText().toString();

        if (!taskName.isEmpty() && !courseName.isEmpty() && isValidDate(dueDate) && !description.isEmpty()) {
            Task task = new Task(taskName, dueDate, courseName, description);
            taskList.add(task);
            taskAdapter.notifyDataSetChanged();

            taskNameEdit.getText().clear();
            courseNameEdit.getText().clear();
            descriptionEdit.getText().clear();
            dueDateTextview.setText("Select Date");
        }

    }

    private boolean isValidDate(String inputDate) {
        return inputDate.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])");
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
                        dueDateTextview.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void editTask(Task task, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Task");
        View editTaskView = LayoutInflater.from(getContext()).inflate(R.layout.edit_task_dialog, null);

        EditText editTaskNameEditView = editTaskView.findViewById(R.id.editTaskNameEdit);
        EditText editDueDateEditView = editTaskView.findViewById(R.id.editDueDateEdit);
        EditText editCourseEditView = editTaskView.findViewById(R.id.editCourseName);
        EditText editDescriptionEditView = editTaskView.findViewById(R.id.editDescriptionEdit);

        editTaskNameEditView.setText(task.getTaskName());
        editDueDateEditView.setText(task.getDueDate());
        editCourseEditView.setText(task.getCourseName());
        editDescriptionEditView.setText(task.getDescription());

        builder.setView(editTaskView);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editedTaskName = editTaskNameEditView.getText().toString();
                String editedDueDate = editDueDateEditView.getText().toString();
                String editedCourse = editCourseEditView.getText().toString();
                String editedDescription = editDescriptionEditView.getText().toString();

                task.setTaskName(editedTaskName);
                task.setDueDate(editedDueDate);
                task.setCourseName(editedCourse);
                task.setDescription(editedDescription);

                taskAdapter.notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

        private List<Task> taskList;

        public TaskAdapter(List<Task> taskList) { this.taskList = taskList; }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcrow1, parent, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = taskList.get(position);
            holder.bind(task);
            holder.completedCheckBox.setChecked(task.getIsCompleted());
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        public class TaskViewHolder extends RecyclerView.ViewHolder {
            public TextView taskNameTextView;
            public TextView dueDateTextView;
            public TextView courseNameTextView;
            public TextView descriptionTextView;
            public CheckBox completedCheckBox;

            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
                dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
                courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                completedCheckBox = itemView.findViewById(R.id.checkBox);

                completedCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Task currentTask = taskList.get(position);
                            currentTask.setCompleted(completedCheckBox.isChecked());

                            if (completedCheckBox.isChecked()) {
                                taskList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Task currentTask = taskList.get(position);
                            editTask(currentTask, position);
                        }
                    }
                });
            }

            public void bind(Task task) {
                taskNameTextView.setText(task.getTaskName());
                dueDateTextView.setText("Due Date: " + task.getDueDate());
                courseNameTextView.setText("Course: " + task.getCourseName());
                descriptionTextView.setText(task.getDescription());
            }
        }
    }
}