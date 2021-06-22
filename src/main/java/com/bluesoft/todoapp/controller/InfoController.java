package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
class InfoController {

    private final DataSourceProperties dataSource;
    private final TaskConfigurationProperties myProp;

    @Autowired
    InfoController(final DataSourceProperties dataSource, final TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/url")
    String dataSourceProp(){
      return dataSource.getUrl();
    }

    @GetMapping("/prop")
    boolean myProp(){
      return myProp.getTemplate().isAllowMultipleTasks();
    }
}
