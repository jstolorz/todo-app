package com.bluesoft.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);
    Task save(Task entity);
    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroupId(Integer groupId);

    List<Task> findAllByGroupId(Integer groupId);

    List<Task>  findByDone(@Param("state") boolean done);
}
