package edu.utsa.cs3443.silvesbro.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StudyTask {
    private String taskId;
    private String taskName;
    private Date dueDate;
    private boolean isCompleted;
    private String priority;



    public StudyTask(String taskId, String taskName, String dueDate, boolean isCompleted, String priority) {
        this.taskId = taskId;
        this.taskName = taskName;

        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dueDate = dd.parse(dueDate);
        } catch (ParseException e) {
          e.printStackTrace();
        }

        this.isCompleted = isCompleted;
        this.priority = priority;

    }

    public void markComplete() {
        this.isCompleted = true;
    }

    public boolean isOverdue() {
        Date currentDate = new Date();
        return currentDate.after(this.dueDate);
    }

    public long getTimeRemaining() {
        Date currentDate = new Date();
        return this.dueDate.getTime() - currentDate.getTime();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDueDateStr() {
        String formDate;
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        formDate = dd.format(dueDate);

        return formDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
