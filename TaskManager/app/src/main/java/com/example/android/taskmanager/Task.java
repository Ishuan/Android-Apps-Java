package com.example.android.taskmanager;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private String taskName;
    private String taskDate;
    private String taskTime;
    private String taskPriority;

    public Task(String taskName, String taskDate, String taskTime, String taskPriority) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskPriority = taskPriority;
    }

    public Task(String taskName, String taskPriority) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskPriority() {
        return taskPriority;
    }
}
