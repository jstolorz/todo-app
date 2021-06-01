package com.bluesoft.todoapp.model;

import javax.persistence.*;

@Entity
@Table(name = "project_steps")
class ProjectSteps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private int days_to_deadline;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectSteps() {
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

    Project getProjects() {
        return project;
    }

    void setProjects(final Project project) {
        this.project = project;
    }

    int getDays_to_deadline() {
        return days_to_deadline;
    }

    void setDays_to_deadline(final int days_to_deadline) {
        this.days_to_deadline = days_to_deadline;
    }
}
