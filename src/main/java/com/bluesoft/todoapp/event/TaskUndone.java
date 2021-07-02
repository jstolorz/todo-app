package com.bluesoft.todoapp.event;

import com.bluesoft.todoapp.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
     TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
