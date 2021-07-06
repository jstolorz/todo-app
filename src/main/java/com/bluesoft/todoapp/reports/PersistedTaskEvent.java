package com.bluesoft.todoapp.reports;


import com.bluesoft.todoapp.event.TaskEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "task_events")
class PersistedTaskEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDateTime occurrence;
    int taskId;
    String name;

    public PersistedTaskEvent() {
    }

    PersistedTaskEvent(TaskEvent event) {
        taskId = event.getTaskId();
        name = event.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(event.getOccurrence(), ZoneId.systemDefault());
    }


}
