package com.bluesoft.todoapp.model;

import com.bluesoft.todoapp.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table( name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Task's description must be not empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    @Embedded
    private Audit audit = new Audit();


    public Task() {
    }

    public Task(final String description, final LocalDateTime deadline) {
         this(description,deadline,null);
    }

    public Task(final String description, final LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if(group != null){
            this.group = group;
        }
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskEvent toggle(){
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    public void updateFrom(final Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }



}
