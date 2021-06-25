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
        final GroupReadModel group = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + group.getId())).body(group);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.badRequest().build();
    }

}
