package com.bluesoft.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Project's description must not be empty")
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
    private Set<ProjectSteps> steps;

    public Project() {
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<TaskGroup> getTask_group() {
        return task_group;
    }

    void setTask_group(final Set<TaskGroup> task_group) {
        this.task_group = task_group;
    }

    public Set<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(final Set<ProjectSteps> project_steps) {
        this.steps = project_steps;
    }
}
