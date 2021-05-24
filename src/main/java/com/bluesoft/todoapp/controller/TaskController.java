package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
class TaskController {
    private final TaskRepository repository;
    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);


    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/tasks")
    ResponseEntity<?> readAllTasks(){
        LOGGER.warn("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }
}
