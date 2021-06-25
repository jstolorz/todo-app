package com.bluesoft.todoapp.model;

import javax.persistence.*;

@Entity
@Table(name = "project_steps")
public class ProjectSteps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private int daysToDeadline;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(final int days_to_deadline) {
        this.daysToDeadline = days_to_deadline;
    }
}
