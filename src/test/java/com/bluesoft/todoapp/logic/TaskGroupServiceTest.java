package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.model.TaskGroup;
import com.bluesoft.todoapp.model.TaskGroupRepository;
import com.bluesoft.todoapp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException(){
        // given
        TaskRepository mockTaskRepository = getTaskRepository(true);
        // system under test
        var toTest = new TaskGroupService(null,mockTaskRepository);
        //when
        var exception = catchThrowable(()-> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when TaskGroup with given id not found")
    void toggleGroupWhenTaskGroupWithGivenIdNotFound(){
        // given
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskRepository mockTaskRepository = getTaskRepository(false);
        // system under test
        var service = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
        // when
        var exception = catchThrowable(()-> service.toggleGroup(1));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");

    }


    @Test
    @DisplayName("should should toggle group")
    void toggleGroup_worksEsExpected(){
        // given
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //and
        TaskRepository mockTaskRepository = getTaskRepository(false);
        // system under test
        var service = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
        // when
        service.toggleGroup(0);
        // then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);

    }

    private TaskRepository getTaskRepository(final boolean b) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroupId(anyInt())).thenReturn(b);
        return mockTaskRepository;
    }


//    @Test
//    @DisplayName("should throw  IllegalStateException when group has undone tasks")
//    void toggleGroupWhenGroupHasUndoneTasks(){
//        // given
//        var mockTaskRepository = mock(TaskRepository.class);
//        when(mockTaskRepository.existsByDoneIsFalseAndGroupId(anyInt())).thenReturn(true);
//        // system under test
//        var service = new TaskGroupService(null,mockTaskRepository);
//        // when
//        var exception = catchThrowable(()-> service.toggleGroup(1));
//        // then
//        assertThat(exception)
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("has undone tasks");
//
//    }
//
//    @Test
//    @DisplayName("should throw IllegalArgumentException when TaskGroup with given id not found")
//    void toggleGroupWhenTaskGroupWithGivenIdNotFound(){
//        // given
//        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
//        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
//        //and
//        var mockTaskRepository = mock(TaskRepository.class);
//        when(mockTaskRepository.existsByDoneIsFalseAndGroupId(anyInt())).thenReturn(false);
//        // system under test
//        var service = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
//        // when
//        var exception = catchThrowable(()-> service.toggleGroup(1));
//        // then
//        assertThat(exception)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("given id not found");
//
//    }
//
//    @Test
//    @DisplayName("should toggle group")
//    void toggleGroupWhenToggleGroupProperly(){
//        // given
//        TaskGroup taskGroup =  new TaskGroup();
//
//        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
//        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
//        //and
//        var mockTaskRepository = mock(TaskRepository.class);
//        when(mockTaskRepository.existsByDoneIsFalseAndGroupId(anyInt())).thenReturn(false);
//        // system under test
//        var service = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
//        // when
//        taskGroup.setDone(true);
//        service.toggleGroup(1);
//        // then
//        assertThat(taskGroup.isDone()).isEqualTo(false);
//
//    }

}