package com.example.taskplannerapi.service.Impl;

import com.example.taskplannerapi.dao.Storage;
import com.example.taskplannerapi.exceptions.CustomException;
import com.example.taskplannerapi.model.Sprint;
import com.example.taskplannerapi.model.Task;
import com.example.taskplannerapi.service.ITaskService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
public class TaskService implements ITaskService {

    private static TaskService taskService = null;

    Storage storage = Storage.getStorage();

    private TaskService() {
    }

    static {
        taskService = new TaskService();
    }

    public static TaskService getTaskService() {
        return taskService;
    }


    public void addTask(Integer taskID, Task task) {
        storage.getTaskMap().put(taskID, task);
    }

    public void changeStatus(Integer taskID) {
        storage.getTaskMap().get(taskID).changeStatus();
    }

    public List<Task> taskAssignedToUser(String user) {
        List<Task> taskList = new ArrayList<>();
        for (Task t : storage.getTaskMap().values()) {
            if (t.getAssignee().equalsIgnoreCase(user))
                taskList.add(t);
        }
        return taskList;
    }

    public HashMap<Integer, Task> getAllTask() {
        return storage.getTaskMap();
    }

    public Task getTask(Integer taskID)
    {
        if(!storage.getTaskMap().containsKey(taskID))
            throw new CustomException(MessageFormat.format("{0}-Task ID is not correct.",taskID));
        return storage.getTaskMap().get(taskID);
    }

    public Integer createSprint(String sprintName) {
        storage.getSprintHashMap().put(sprintName, new Sprint(sprintName));
        return storage.getSprintHashMap().get(sprintName).getSprintID();
    }

    public Integer deleteSprint(String sprintName) {
        Integer sprintID=storage.getSprintHashMap().get(sprintName).getSprintID();
        storage.getSprintHashMap().remove(sprintName);
        return sprintID;
    }

    public void createSprintWithTask(String sprintName, Integer taskNo) {
        if (!storage.getSprintHashMap().containsKey(taskNo))
            storage.getTaskSprintMap().put(taskNo, sprintName);
        else
            throw new CustomException("Task already attached to sprint");
        storage.getSprintHashMap().put(sprintName, new Sprint(sprintName));
        storage.getSprintHashMap().get(sprintName).addTask(storage.getTaskMap().get(taskNo));
    }

    public void addTaskToSprint(String sprintName, Integer taskNo) {
        if (!storage.getTaskSprintMap().containsKey(taskNo))
            storage.getTaskSprintMap().put(taskNo, sprintName);
        else
            throw new CustomException("Task already attached to sprint");

        if (storage.getTaskMap().containsKey(taskNo)) {
            storage.getSprintHashMap().get(sprintName).addTask(storage.getTaskMap().get(taskNo));
        } else
            throw new CustomException("Task dont exist");
    }

    public void changeSprintForTask(String oldSprint, String newSprint, Integer taskNo) {
        List<Task> list = storage.getSprintHashMap().get(oldSprint).getTasks();
        for (Task t : list) {
            if (t.getTaskID() == taskNo) {
                list.remove(t);
                break;
            }
        }
        storage.getSprintHashMap().get(newSprint).getTasks().add(storage.getTaskMap().get(taskNo));
    }

    private List<Task> tasksForSprint(String sprint) {
        return storage.getSprintHashMap().get(sprint).getTasks();
    }
}
