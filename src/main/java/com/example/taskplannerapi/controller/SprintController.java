package com.example.taskplannerapi.controller;

import com.example.taskplannerapi.service.Impl.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SprintController {
    TaskService taskService = TaskService.getTaskService();

    @GetMapping("createsprint")
    public Integer createSprint(@RequestParam(value = "sprint") String sprint) {
        return taskService.createSprint(sprint);
    }

    @GetMapping("addtasktosprint")
    public void addTaskToSprint(@RequestParam(value = "task") Integer taskID,
                                @RequestParam(value = "sprint") String sprint) {
        taskService.addTaskToSprint(sprint, taskID);
    }

    @GetMapping("removetaskfromsprint")
    public String removeTaskFromSprint(@RequestParam(value = "sprint") String sprint) {
        return String.format("Deleted-", taskService.deleteSprint(sprint).toString());
    }
    @DeleteMapping("/deletesprint/{name}")
    public ResponseEntity deleteSprint(@PathVariable("name") String name)
    {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteSprint1(@RequestParam(value = "sprint") String sprint)
    {

        return ResponseEntity.ok().build();
    }

    @GetMapping("changesprint")
    public void changeSprint(@RequestParam(value = "task") Integer taskID,
                             @RequestParam(value = "oldsprint") String oldSprint, @RequestParam(value = "newsprint") String newSprint) {
        taskService.changeSprintForTask(oldSprint, newSprint, taskID);
    }
}
