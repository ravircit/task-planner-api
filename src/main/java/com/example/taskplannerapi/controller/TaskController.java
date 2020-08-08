package com.example.taskplannerapi.controller;

import com.example.taskplannerapi.enums.Status;
import com.example.taskplannerapi.enums.TaskType;
import com.example.taskplannerapi.model.Story;
import com.example.taskplannerapi.model.Task;
import com.example.taskplannerapi.service.ITaskService;
import com.example.taskplannerapi.service.Impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    ITaskService taskService;// = TaskService.getTaskService();

//    @PostMapping("/createtask")
//    public Integer CreateTask(@RequestBody Task task) {
//        taskService.addTask(task.getNextTaskID(), task);
//        return task.getTaskID();
//    }
    @PostMapping("/createtask")
    public ResponseEntity<Object> createUser(@Valid @RequestBody Task task) {
        taskService.addTask(task.getNextTaskID(), task);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{taskID}")
                .buildAndExpand(task.getTaskID()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/changestatus")
    public ResponseEntity changeStatus(@RequestParam(value = "taskid") Integer taskID) {
//        if (taskService.getTask(taskID) != null)
//            return String.format("Status changed to {0}", taskService.getTask(taskID).changeStatus());

        return ResponseEntity.accepted().body(MessageFormat.format("Changed to-{0}",taskService.getTask(taskID).changeStatus()));
        //return ResponseEntity.accepted().build();
    }

    @GetMapping("createsubtrack")
    public ResponseEntity createSubTrack(@RequestParam(value = "name") String subTrack,
                                  @RequestParam(value = "taskID") Integer taskID) {
        Task task = taskService.getTask(taskID);
        if(task.getTaskType().getTaskType().equals(TaskType.STORY)) {
            if (Status.COMPLETED.equals(task.getTaskStatus()))
                return ResponseEntity.badRequest().body("Status is already complete, cant add sub track");
            else {
                ((Story) task.getTaskType()).addSubTracks(((Story) task.getTaskType()).new SubTrack(subTrack));//downcast
            }
            return ResponseEntity.accepted().
                    body(String.format("Story created with ID-",((Story) task.getTaskType()).getSubTrackID().toString()));
        }
        return ResponseEntity.badRequest().
                body("Story not found.");
    }

    @GetMapping("changeassignee")
    public void changeAssignee(@RequestParam(value = "name") String name,
                               @RequestParam(value = "taskID") Integer taskID) {
        if (taskService.getTask(taskID) != null)
            taskService.getTask(taskID).setAssignee(name);
    }

    @GetMapping("getalltask")
    @ResponseBody
    public List<Task> getAllTask() {
        List<Task> tasks = new ArrayList<>();
        for (Task t : taskService.getAllTask().values()) {
            tasks.add(t);
        }
        return tasks;
    }

    @GetMapping("gettaskforuser")
    public List<Task> getTasksForUser(@RequestParam(value = "user") String name) {
        return taskService.taskAssignedToUser(name);
    }
}
