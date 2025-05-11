package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    // reads tasks from internal memory and loads into the TaskList arraylist of tasks
    public void loadTasks(Context context) {
        try {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(context.openFileInput("tasks.csv")));
            } catch (Exception e) {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("tasks.csv")));
            }
            reader.readLine(); // trash line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Task task = new Task(data[0].trim(), data[1].trim(), data[2].trim(), Boolean.parseBoolean(data[3].trim()));
                addTask(task);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("TaskList", "Failed to load profile: " + e.getMessage());
        }
    }

    // overwrites/update internal memory csv file with the current TaskList arraylist of tasks
    public void saveTasks(Context context) throws RuntimeException{
        try { // MODE_APPEND TO APPEND OR MODE_PRIVATE TO WRITE ?
            OutputStream outputStream = context.openFileOutput("tasks.csv", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write("task_id,task_name,due_date,is_completed\n");
            for (Task task : this.getTaskList()) {
                writer.write(String.format("%s,%s,%s,%b\n",
                        task.getId(), task.getName(), task.getDueDate(), task.getIsCompleted()));
            }
            writer.close();
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e("TaskListActivity", "Failed to save profile: " + e.getMessage());
        }
    }

    public void addTask(Task task){
        this.taskList.add(task);
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

    // static bc its not related to an instance
    // appends one singular task to internal memory
    public static void addTaskToCSV (Context context, Task task) throws RuntimeException  {
        try { // MODE_APPEND TO APPEND OR MODE_PRIVATE TO WRITE ?
            OutputStream outputStream = context.openFileOutput("tasks.csv", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            //writer.write("name,total_study_time_minutes,mountain_dew_count,happiness_level,is_music_on,is_sound_on,is_swagger_mode\n");
            writer.write(String.format("%s,%s,%s,%b\n",
                    task.getId(), task.getName(), task.getDueDate(), task.getIsCompleted()));

            writer.close();
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e("AddTaskActivity", "Failed to save profile: " + e.getMessage());
        }
    }

}

