package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks(){
        //given
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        // when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks",Task[].class);

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void httpGet_returnsTaskById(){

        Task task = new Task("test by id",LocalDateTime.now());
        repo.save(task);

        Task result = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + task.getId(),Task.class);

        assertThat(result.getDescription()).isEqualTo(task.getDescription());
    }

}