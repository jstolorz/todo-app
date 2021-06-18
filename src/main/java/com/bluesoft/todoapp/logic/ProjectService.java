package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.TaskConfigurationProperties;
import com.bluesoft.todoapp.model.*;
import com.bluesoft.todoapp.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties configurationProperties;

    public ProjectService(final ProjectRepository projectRepository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties configurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.configurationProperties = configurationProperties;
    }

    List<Project> readAll(){
      return  projectRepository.findAll();
    }

    public Project save(Project toSave){
        return projectRepository.save(toSave);
    }


    public GroupReadModel createGroup(int projectId, LocalDateTime deadline){
        if(!configurationProperties.getTemplate().isAllowMultipleTasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one group on project is allowed");
        }

        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                    var taskGroup = new TaskGroup();
                    taskGroup.setDescription(project.getDescription());
                    taskGroup.setTasks(
                            project.getSteps().stream()
                            .map(step ->
                                    new Task(step.getDescription(), deadline.plusDays(step.getDays_to_deadline()))
                                 )
                                    .collect(Collectors.toSet())
                  );
                    taskGroup.setProject(project);
                 return taskGroupRepository.save(taskGroup);
              }).orElseThrow(()-> new IllegalArgumentException("Project with given id not found"));

         return new GroupReadModel(result);
      }
}











//@Service
//public class ProjectService {
//    private final ProjectRepository projectRepository;
//    private final TaskGroupRepository taskGroupRepository;
//    private final TaskConfigurationProperties configurationProperties;
//
//    public ProjectService(final ProjectRepository projectRepository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties configurationProperties) {
//        this.projectRepository = projectRepository;
//        this.taskGroupRepository = taskGroupRepository;
//        this.configurationProperties = configurationProperties;
//    }
//
//    public List<Project> findAll(){
//       return projectRepository.findAll();
//    }
//
//    public Project save(Project entity){
//        return projectRepository.save(entity);
//    }
//
//    public TaskGroup createGroup(int projectId, LocalDateTime deadline){
//       Project project = projectRepository.findById(projectId).get();
//
//       if(project.getTask_group().stream()
//               .filter(taskGroup ->
//                       taskGroupRepository.existsByDoneIsFalseAndProject_Id(taskGroup.getId())
//                       && !configurationProperties.getTemplate().isAllowMultipleTasks())
//               .findFirst().isPresent()){
//           throw new IllegalStateException("");
//       }
//
//        TaskGroup taskGroup = new TaskGroup();
//        taskGroup.setDescription(project.getDescription());
//        taskGroup.setProject(project);
//        taskGroup.setTasks(
//                project.getSteps().stream()
//                .map(projectSteps -> {
//                            return new Task(projectSteps.getDescription(),
//                                    deadline.plusDays(projectSteps.getDays_to_deadline()))
//
//                        }
//                )
//                .collect(Collectors.toSet())
//        );
//
//
//       return taskGroup;
//    }
//}
