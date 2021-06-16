package com.bluesoft.todoapp.logic;

import com.bluesoft.todoapp.TaskConfigurationProperties;
import com.bluesoft.todoapp.TaskTemplateProperties;
import com.bluesoft.todoapp.model.*;
import com.bluesoft.todoapp.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        // and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
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

    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createAndSaveGroup(){
        //given
        var today = LocalDate.now().atStartOfDay();
        // and
        var project = projectWith("bar",Set.of(-1,-2));
        // and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        // and
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepo.count();
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);

        // when
         GroupReadModel result = toTest.createGroup(1,today);

         // then
         assertThat(result)
                 .hasFieldOrPropertyWithValue("description","bar");

         assertThat(result.getDescription()).isEqualTo("bar");
         assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));


         assertThat(countBeforeCall + 1).isEqualTo(inMemoryGroupRepo.count());

    }

    private Project projectWith(String projectDescription, Set<Integer> daystoDeadLine){

        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        final Set<ProjectSteps> steps = daystoDeadLine.stream()
                .map(days -> {
                    var step = mock(ProjectSteps.class);
                    when(step.getDescription()).thenReturn("");
                    when(step.getDays_to_deadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        when(result.getSteps()).thenReturn(
                steps
        );
        return result;
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

    private InMemoryGroupRepository inMemoryGroupRepository(){
       return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements TaskGroupRepository{
        private int index = 0;
        private Map<Integer, TaskGroup> map = new HashMap<>();

        public int count(){
            return map.values().size();
        }

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroup> findById(final Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroup save(final TaskGroup entity) {
            if(entity.getId() == 0){
                try {
                    var field = TaskGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity,++index);


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                map.put(index,entity);
            }
            map.put(entity.getId(),entity);map.put(entity.getId(),entity);

            return entity;
        }

        @Override
        public List<TaskGroup> getAllTaskGroupsByDoneIsFalseAndProject_Id(final Integer projectId) {
            return null;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(final Integer projectId) {
            return map.values().stream()
                    .filter(group -> !group.isDone())
                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);

        }
    }


}
