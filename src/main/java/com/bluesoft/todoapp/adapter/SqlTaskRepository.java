package com.bluesoft.todoapp.adapter;

import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {

    @Override
    boolean existsByDoneIsFalseAndGroupId(Integer groupId);



}
