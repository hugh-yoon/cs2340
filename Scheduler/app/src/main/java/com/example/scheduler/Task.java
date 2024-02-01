package com.example.scheduler;

public class Task {
    private String taskName;
    private String dueDate;
    private String courseName;
    private String description;
    private boolean isCompleted;

    public Task(String taskName, String dueDate, String courseName, String description) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.courseName = courseName;
        this.description = description;
    }

    public String getTaskName() { return this.taskName; }

    public String getDueDate() { return this.dueDate; }

    public String getCourseName() { return this.courseName; }

    public String getDescription() { return this.description; }

    public boolean getIsCompleted() { return this.isCompleted; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    public void setDescription(String description) { this.description = description; }

}
