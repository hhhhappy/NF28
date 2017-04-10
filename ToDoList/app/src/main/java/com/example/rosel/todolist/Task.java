package com.example.rosel.todolist;

/**
 * Created by rosel on 2017/4/10.
 */

public class Task {
    private String name;
    private String deadline;
    private boolean status;
    private String priority;

    public Task() {
        name = "";
        deadline = "";
        status = false;
        priority = "";
    }

    public Task(String p_name, String p_deadline, boolean p_status, String p_priority) {
        name = p_name;
        deadline = p_deadline;
        status = p_status;
        priority = p_priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status ? "Finished" : "Ongoing";
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String toString() {
        return "Task name : " + getName() + "\n" +
                "Priority : " + getPriority() + "\n" +
                "Status : " + getStatus() + "\n" +
                "Deadline : " + getDeadline();
    }
}
