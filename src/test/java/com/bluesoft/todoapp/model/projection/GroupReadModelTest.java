package com.bluesoft.todoapp.model.projection;

import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.TaskGroup;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GroupReadModelTest {

    @Test
    void constructor_noDeadline_createsNullDeadline(){
        // given
        var source = new TaskGroup();
        source .setDescription("foo");
        source.setTasks(Set.of(new Task("bar",null)));

        // when
        var result = new GroupReadModel(source);

        // then
        assertThat(result).hasFieldOrPropertyWithValue("deadline",null);
    }

}