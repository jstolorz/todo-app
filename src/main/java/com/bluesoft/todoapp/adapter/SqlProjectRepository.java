package com.bluesoft.todoapp.adapter;

import com.bluesoft.todoapp.model.ProjectRepository;
import com.bluesoft.todoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("from Project p join fetch p.project_steps")
    List<Project> findAll();

}
