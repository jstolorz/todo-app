package com.bluesoft.todoapp.model.projection;

import com.bluesoft.todoapp.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Set;

public class GroupReadModel {
    private String description;
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    GroupReadModel(TaskGroup source) {
        description = source.getDescription();

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }
}
