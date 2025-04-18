package edu.utsa.cs3443.silvesbro;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList () {
        this.taskList = new ArrayList<Task>();
    }

    public void loadTasks() {

    }

    public void addTask(Task task){

    }

    public void removeTask(String taskID) {

    }

    public void markTaskComplete(String taskID) {

    }

    public void checkOverdueTasks() {

    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public int getSize() {
        return this.taskList.size();
    }

}
