package com.bluesoft.todoapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "project"
    )
    private Set<TaskGroup> task_group;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "project"
    )
    private Set<ProjectSteps> project_steps;

    public Project() {
    }

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    Set<TaskGroup> getTask_group() {
        return task_group;
    }

    void setTask_group(final Set<TaskGroup> task_group) {
        this.task_group = task_group;
    }

    Set<ProjectSteps> getProject_steps() {
        return project_steps;
    }

    void setProject_steps(final Set<ProjectSteps> project_steps) {
        this.project_steps = project_steps;
    }
}
