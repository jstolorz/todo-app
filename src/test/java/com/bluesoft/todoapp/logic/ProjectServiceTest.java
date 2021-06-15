package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.TaskConfigurationProperties;
import com.bluesoft.todoapp.TaskTemplateProperties;
import com.bluesoft.todoapp.model.ProjectRepository;
import com.bluesoft.todoapp.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {



    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just one group and undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneOpenGroups_throwsIllegalStateException() {
        // given
        var mockGroupRepository = groupRepositoryReturning(true);
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        // system under test
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);


        // when
         var exception = catchThrowable(() -> toTest.createGroup(12, LocalDateTime.now()));

        // then

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Only one group on project");

        assertThatThrownBy(()->{
            toTest.createGroup(12, LocalDateTime.now());
        }).isInstanceOf(IllegalStateException.class);

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> toTest.createGroup(12, LocalDateTime.now()));

        assertThatIllegalStateException()
                .isThrownBy(() -> toTest.createGroup(12, LocalDateTime.now()));

    }

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just one group and no project an no groups")
    void createGroup_noMultipleGroupsConfignoMultipleGroupsConfig_When_Configuration_OK_and_NoProject_And_GroupsNo_throwsIllegalStateException(){
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);

        // when
        var exception = catchThrowable(()-> toTest.createGroup(1,LocalDateTime.now()));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should throw IllegalStateException when configuration ok and no project")
    void createGroup_When_Configuration_OK_and_NoProject_throwsIllegalStateException(){
        // given
        var mockGroupRepository = groupRepositoryReturning(false);
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, mockGroupRepository, mockConfig);

        // when
        var exception = catchThrowable(()-> toTest.createGroup(1,LocalDateTime.now()));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    private TaskGroupRepository groupRepositoryReturning(final boolean b) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(b);
        return mockGroupRepository;
    }


    private TaskConfigurationProperties configurationReturning(final boolean b) {
        var mockTemplate = mock(TaskTemplateProperties.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(b);
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }


}
