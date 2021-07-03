package com.bluesoft.todoapp.reports;

import com.bluesoft.todoapp.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
class ReportController {

    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository eventRepository;

    ReportController(final TaskRepository taskRepository, final PersistedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id){
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
