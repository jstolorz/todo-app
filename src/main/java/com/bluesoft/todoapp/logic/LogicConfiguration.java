package com.bluesoft.todoapp.logic;


import com.bluesoft.todoapp.TaskConfigurationProperties;
import com.bluesoft.todoapp.model.ProjectRepository;
import com.bluesoft.todoapp.model.TaskGroupRepository;
import com.bluesoft.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties properties,
            final TaskGroupService service
            ){
        return new ProjectService(projectRepository,taskGroupRepository,properties, service);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository repository,
            final TaskRepository taskRepository){
        return new TaskGroupService(repository,taskRepository);
    }
}
