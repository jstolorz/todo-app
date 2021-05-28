package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
class TaskController {
    private final TaskRepository repository;
    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);


    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/tasks",
            params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        LOGGER.warn("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path ="/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        LOGGER.warn("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(method = RequestMethod.GET, path ="/tasks/{id}")
    ResponseEntity<Task> getTaskById(@PathVariable int id){
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST, path ="/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate){
        Task task =  repository.save(toCreate);
        URI location = URI.create(String.format("/%s", task.getId()));
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.PUT, path ="/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if(!repository.existsById(id)){
           return ResponseEntity.notFound().build();
        }

        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PATCH, path ="/tasks/{id}")
    public ResponseEntity<Task> toggleTask(@PathVariable int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }


}
