package com.bluesoft.todoapp.adapter;

import com.bluesoft.todoapp.model.TaskGroup;
import com.bluesoft.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

    @Override
    @Query("from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

    @Override
    List<TaskGroup> getAllTaskGroupsByDoneIsFalseAndProject_Id(Integer projectId);

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
