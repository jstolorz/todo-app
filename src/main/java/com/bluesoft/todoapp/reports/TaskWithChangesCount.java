package com.bluesoft.todoapp.reports;

import com.bluesoft.todoapp.model.Task;

import java.util.List;

class TaskWithChangesCount {

    public String description;
    public boolean done;
    public int changesCount;

    TaskWithChangesCount(final Task task, final List<PersistedTaskEvent> events) {
       description = task.getDescription();
       done = task.isDone();
       changesCount = events.size();
    }


}
