package edu.utsa.cs3443.silvesbro.model;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TaskList {
    private ArrayList<StudyTask> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(StudyTask task, Context context) throws IOException {
        tasks.add(task);

        saveToCSV(context);
    }

    public void saveToCSV(Context context) throws IOException {
        FileOutputStream file = context.openFileOutput("tasks.csv", Context.MODE_PRIVATE);
        OutputStreamWriter writer = new OutputStreamWriter(file);

        for (StudyTask task : tasks) {
            SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
            String formDate = dd.format(task.getDueDate());

            String isCompleted = task.getCompleted() ? "true" : "false";

            String taskLine = task.getTaskId() + "," + task.getTaskName() + "," + formDate + "," + isCompleted + "," + task.getPriority() + "\n";
            writer.write(taskLine);
        }

        writer.close();
    }

    public void loadFromCSV(Context context) throws IOException {
        FileInputStream file = context.openFileInput("tasks.csv");
        InputStreamReader reader = new InputStreamReader(file);
        BufferedReader buffer = new BufferedReader(reader);

        String line;
        buffer.readLine(); //skip header

        while ((line = buffer.readLine()) != null) {
            String[] taskData = line.split(",");

            String taskId = taskData[0];
            String taskName = taskData[1];
            String formDate = taskData[2];
            String taskComplete = taskData[3];
            String priority = taskData[4];

            SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = null;
            try{
                dueDate = dd.parse(formDate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            boolean isComplete = Boolean.parseBoolean(taskComplete);

            StudyTask task = new StudyTask(taskId, taskName, formDate, isComplete, priority);
            tasks.add(task);
        }

        buffer.close();
    }

    public void removeTask(String taskId, Context context) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getTaskId().equalsIgnoreCase(taskId)) {
                tasks.remove(i);
                break;
            }
        }

        saveToCSV(context);
    }

    public void markTaskComplete(String taskId, Context context) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getTaskId().equalsIgnoreCase(taskId)) {
                tasks.get(i).markComplete();
                break;
            }
        }

        saveToCSV(context);
    }

    public ArrayList<StudyTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<StudyTask> tasks) {
        this.tasks = tasks;
    }

    public int getSize() {

        return tasks.size();
    }

    public void checkOverdueTasks() {
        Date currDate = new Date();

        for (StudyTask task : tasks) {
            if (task.getDueDate().before(currDate) && !task.getCompleted()) {
                //do something w overdue tasks
            }
        }
    }


}
