package com.example.taskplannerapi.service;

import com.example.taskplannerapi.model.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public interface ITaskService {
    public void addTask(Integer i, Task t);
    public void addTaskToSprint(String s,Integer i);
    public void changeSprintForTask(String s,String t,Integer i);
    public void changeStatus(Integer i);
    public Integer createSprint(String s);
    public void createSprintWithTask(String s,Integer i);
    public Integer deleteSprint(String s);
    public HashMap<Integer, Task> getAllTask();
    public Task getTask(Integer i);
    public List<Task> taskAssignedToUser(String s);
}
