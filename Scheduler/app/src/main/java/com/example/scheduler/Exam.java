package com.example.scheduler;

public class Exam {

    private String name;
    private String className;
    private String examDate;
    private String examTime;
    private String location;
    private boolean isCompleted;

    public Exam(String name, String className, String examDate, String examTime, String location) {
        this.name = name;
        this.className = className;
        this.examDate = examDate;
        this.examTime = examTime;
        this.location = location;
    }

    public String getName() { return name;}

    public String getClassName() {
        return className;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsCompleted() { return isCompleted; }

    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }
}
