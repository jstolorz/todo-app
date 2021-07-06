package com.bluesoft.todoapp.reports;

import com.bluesoft.todoapp.event.TaskDone;
import com.bluesoft.todoapp.event.TaskEvent;
import com.bluesoft.todoapp.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(final PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(TaskDone event){
        extracted(event);
    }

    @Async
    @EventListener
    public void on(TaskUndone event){
        extracted(event);
    }

    private void extracted(final TaskEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
