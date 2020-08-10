package com.example.taskplannerapi.model;

import com.example.taskplannerapi.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;


public class Task {
    @NotNull(message = "Title cannot be null.")
    @Size(min = 2, max = 10, message
            = "Title must be between 2 and 10 characters.")
    String title;
    @NotNull(message = "Creator cannot be null")
    String creator;
    String Assignee;
    String attribute;
    Type taskType;
    @Future(message = "Due Date can not be in past.")
    Instant dueDate;
    static Integer taskID = 100;
    Status taskStatus = Status.OPEN;

    public Task() {
    }

    public Task(String title, String creator, String assignee, Type taskType, Instant dueDate, String attribute) {
        this.title = title;
        this.creator = creator;
        Assignee = assignee;
        this.taskType = taskType;
        this.dueDate = dueDate;
        this.attribute = attribute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Type getTaskType() {
        return taskType;
    }

    public void setTaskType(Type taskType) {
        this.taskType = taskType;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public static void setTaskID(Integer taskID) {
        Task.taskID = taskID;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskID() {
        return taskID;
    }

    @JsonIgnore
    public Integer getNextTaskID() {
        return ++taskID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", creator='" + creator + '\'' +
                ", Assignee='" + Assignee + '\'' +
                ", attribute='" + attribute + '\'' +
                ", taskType=" + taskType +
                ", dueDate=" + dueDate +
                ", taskStatus=" + taskStatus +
                '}';
    }

    public String changeStatus() {
        this.taskStatus = taskType.nextStatus(taskStatus);
        return taskStatus.name();
    }
}
