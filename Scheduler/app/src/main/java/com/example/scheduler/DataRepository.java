package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private List<Exam> examList;

    private DataRepository() {
        examList = new ArrayList<>();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public List<Exam> getEdamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }
}
