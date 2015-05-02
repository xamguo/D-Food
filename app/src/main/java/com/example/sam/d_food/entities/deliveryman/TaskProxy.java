package com.example.sam.d_food.entities.deliveryman;

import java.util.ArrayList;

/**
 * Created by Sam on 5/1/2015.
 */
public class TaskProxy {
    public static ArrayList<Task> taskList = new ArrayList<Task>();

    public void addTask(String userName, double latitude, double longitude) {
        Task task = new Task(userName, latitude, longitude);
        taskList.add(task);
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }
}
