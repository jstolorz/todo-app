package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.model.TaskGroup;
import com.bluesoft.todoapp.model.TaskGroupRepository;
import com.bluesoft.todoapp.model.TaskRepository;
import com.bluesoft.todoapp.model.projection.GroupReadModel;
import com.bluesoft.todoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

//@Service
public class TaskGroupService {

    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;


    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        final TaskGroup result = repository.save(source.toGroup());
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

}
