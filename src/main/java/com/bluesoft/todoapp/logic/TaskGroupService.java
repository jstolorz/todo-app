package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.model.*;
import com.bluesoft.todoapp.model.projection.GroupReadModel;
import com.bluesoft.todoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

public class TaskGroupService {

    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;


    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        return createGroup(source,null);
    }

    public GroupReadModel createGroup(GroupWriteModel source, final Project project){
        final TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }


    public List<GroupReadModel> readAll(){
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
       if(taskRepository.existsByDoneIsFalseAndGroupId(groupId)){
           throw new IllegalStateException("Group has undone tasks. done all the tasks first. ");
       }

      TaskGroup result = repository.findById(groupId)
               .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
       result.setDone(!result.isDone());
       repository.save(result);
    }

    public List<Task> findAllTaskFromGroupById(int groupId){
        return taskRepository.findAllByGroupId(groupId);
    }

    void save(final GroupWriteModel current) {

    }
}
