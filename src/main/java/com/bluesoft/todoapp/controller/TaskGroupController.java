package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.logic.TaskGroupService;
import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.projection.GroupReadModel;
import com.bluesoft.todoapp.model.projection.GroupWriteModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskGroupService service;

    TaskGroupController(final TaskGroupService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}/tasks")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(service.findAllTaskFromGroupById(id));
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createTask(@RequestBody @Valid GroupWriteModel toCreate){
        return ResponseEntity.created(URI.create("/")).body(service.createGroup(toCreate));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){

        try{
            service.toggleGroup(id);
        }catch(IllegalStateException ex){
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
