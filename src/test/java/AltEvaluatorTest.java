import classes.AltEvaluator;
import classes.Task;
import classes.Workflow;
import enums.TaskColor;
import enums.TaskState;
import enums.TaskType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class AltEvaluatorTest {
    final AltEvaluator sut = new AltEvaluator();
    final Workflow wf = new Workflow();

    @Test
    public void givenObj_whenCreateEvaluator_thenReturnEvaluator() {
        //Given n when
        AltEvaluator evaluator = new AltEvaluator();
        // Then
        assertThat(evaluator).isNotNull();
    }

    @Test
    public void givenEvaluator_whenEvaluate_thenReturnTreeMap() {
        // Given workflow
        final Task task = new Task(TaskState.COMPLETE, 1L);
        final ArrayList<Task> previousTasks = new ArrayList<>();

        //when
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task, previousTasks);

        //then
        assertThat(coloredTasks).isNotNull();
    }

    @Test
    public void givenSingleTask_whenEvaluate_thenSetColorGreen() {
        //Given
        Task task = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,1L);
        final ArrayList<Task> previousTasks = new ArrayList<>();

        //When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task, previousTasks);

        //Then
        assertThat(coloredTasks.firstEntry().getValue()).isEqualTo(TaskColor.GREEN);
        assertThat(coloredTasks.size()).isEqualTo(1);
    }

    @Test
    public void givenPreviousCompletedTask_whenEvaluateCurrentTask_thenSetItsColorToGreen() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 1);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks).isNotEmpty();
        assertThat(coloredTasks.firstEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenPreviousMandatoryTaskNotComplete_whenEvaluateCurrentTask_thenSetItsColorToYellow() {
        //Given
        Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 1);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks).isNotEmpty();
        assertThat(coloredTasks.firstEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenPreviousPendingTask_whenEvaluateCurrentTask_thenSetItsColorToYellow() {
        //Given
        Task task1 = new Task(TaskState.PENDING, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 1);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks).isNotEmpty();
        assertThat(coloredTasks.firstEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenPreviousOptionalTask_whenEvaluateCurrentTask_thenSetItsColorToGreen() {
        //Given
        Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,1L);
        Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 1);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks).isNotEmpty();
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenThreeTasksFirstCompleteSecondOptional_whenEvaluateCurrentTask_thenSetItsColorToGreen() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.PENDING, TaskType.OPTIONAL,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenFirstTaskCompleteSecondMandatoryNotComplete_whenEvaluateCurrentTask_thenSetColorYellow() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenFirstTaskMandatoryNotComplete_whenEvaluateCurrent_thenColorYellow() {
        //Given
        Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY,1L);
        Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenFirstTaskOptionalSecondOptional_whenEvaluateCurrentTask_thenSetColorGreen() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.OPTIONAL,1L);
        Task task2 = new Task(TaskState.PENDING, TaskType.OPTIONAL,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenFirstOptionalSecondMandatoryComplete_whenEvaluateCurrentTask_thenColorGreen() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.OPTIONAL,1L);
        Task task2 = new Task(TaskState.COMPLETE, TaskType.MANDATORY,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenFirstOptionalSecondMandatoryNotComplete_whenEvaluateCurrentTask_thenColorYellow() {
        //Given
        Task task1 = new Task(TaskState.COMPLETE, TaskType.OPTIONAL,1L);
        Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY,2L);
        Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL,2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        final List<Task> previousTasks =  wf.getTasks().subList(0, 2);

        // When
        final TreeMap<Long, TaskColor> coloredTasks = sut.evaluate(task2, previousTasks);

        // Then
        assertThat(coloredTasks.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }


}
