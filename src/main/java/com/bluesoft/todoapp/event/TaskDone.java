package com.bluesoft.todoapp.event;

import com.bluesoft.todoapp.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
     TaskDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
