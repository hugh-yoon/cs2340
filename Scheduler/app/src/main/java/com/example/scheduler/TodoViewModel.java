package com.example.scheduler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TodoViewModel extends ViewModel {
    private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();

    public void setTaskList(List<Task> tasks) {
        taskList.setValue(tasks);
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }
}
