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
    ResponseEntity<List<GroupReadModel>> readAllTasks(){
        return ResponseEntity.ok(service.readAll());
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createTask(@RequestBody @Valid GroupWriteModel toCreate){
        service.createGroup(toCreate);
        URI location = URI.create(String.format("/"));
        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Task> toggleGroup(@PathVariable int id){

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
