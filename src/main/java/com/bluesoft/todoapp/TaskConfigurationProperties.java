package com.bluesoft.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    private final TaskTemplateProperties template;

    @Autowired
    public TaskConfigurationProperties(final TaskTemplateProperties template) {
        this.template = template;
    }

    public TaskTemplateProperties getTemplate() {
        return template;
    }
}
