package com.bluesoft.todoapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("template")
public class TaskTemplateProperties {
    private boolean allowMultipleTasks;

    public boolean isAllowMultipleTasks() {
        return allowMultipleTasks;
    }

    void setAllowMultipleTasks(final boolean allowMultipleTasks) {
        this.allowMultipleTasks = allowMultipleTasks;
    }
}
