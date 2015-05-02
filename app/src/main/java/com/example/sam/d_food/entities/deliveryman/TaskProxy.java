package com.example.sam.d_food.entities.deliveryman;

import java.util.ArrayList;

public class TaskProxy {
    public static ArrayList<Task> taskList = new ArrayList<>();

    public void addTask(String userName, double latitude, double longitude, String name) {
        Task task = new Task(userName, latitude, longitude, name);
        taskList.add(task);
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }
}
