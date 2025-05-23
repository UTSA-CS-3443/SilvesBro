package edu.utsa.cs3443.silvesbro.models;

public class Task {
    private String id;
    private String name;
    private String dueDate;
    private boolean isCompleted;

    public Task(String id, String name, String dueDate, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

}
