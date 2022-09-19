import classes.Evaluator;
import classes.Task;
import classes.Workflow;
import enums.TaskColor;
import enums.TaskState;
import enums.TaskType;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluatorTest {
    final Evaluator sut = new Evaluator();
    final Workflow wf = new Workflow();

    @Test
    public void givenObject_whenCreateEvaluator_thenReturnEvaluator() {
        Evaluator singleEvaluator = new Evaluator();
        assertThat(singleEvaluator).isNotNull();
    }

    @Test
    public void givenTasksCollectionAndWorkflow_whenEvaluate_thenReturnTreeMapOfTasksAndColors() {
        // Given
        wf.getTasks().add(new Task(TaskState.COMPLETE, 1L));

        // When
        TreeMap<Long, TaskColor> newWorkFlow = sut.evaluate(0, wf.getTasks());
        // Then
        assertThat(newWorkFlow).isNotNull();
    }

    @Test
    public void givenTaskWithNoPrecedingInCollection_whenEvaluate_thenSetTaskColorGreen() {
        wf.getTasks().add(new Task(TaskState.PENDING, 1L));
        TreeMap<Long, TaskColor> newWorkFlow = sut.evaluate(0, wf.getTasks());
        assertThat(newWorkFlow.firstEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenFirstGreenTaskAndSecondTaskPending_whenEvaluate_thenSetSecondTaskColorGreen() {
        final Task task1 = new Task(TaskState.COMPLETE);
        task1.setTaskId(1L);
        final Task task2 = new Task(TaskState.PENDING);
        task2.setTaskId(2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(1, wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenMandatoryTaskAndHisPreviousTaskStateIsPending_whenEvaluate_thenSetMandatoryTaskColorYellow() {
        final Task task1 = new Task(TaskState.PENDING, TaskType.MANDATORY, 1L);
        final Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY, 2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(1, wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenPreviousOptionalTask_whenEvaluate_thenSetTaskColorGreen() {
        final Task task1 = new Task(TaskState.PENDING, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY, 2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(1, wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenThreeTasksFirstCompleteSecondOptional_whenEvaluate_thenSetTaskColorGreen() {
        // Given
        final Task task1 = new Task(TaskState.COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.PENDING, TaskType.OPTIONAL, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenThreeTasksFirstCompleteSecondMandatoryNotComplete_whenEvaluate_thenSetTaskColorYellow() {
        // Given
        final Task task1 = new Task(TaskState.COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenThreeTasksFirstPendingSecondAny_whenEvaluate_thenSetTaskColorYellow() {
        // Given
        final Task task1 = new Task(TaskState.PENDING, TaskType.MANDATORY, 1L);
        final Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenThreeTasksFirstOptionalSecondAny_whenEvaluate_thenSetTaskColorGreen() {
        // Given
        final Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenThreeTasksFirstOptionalSecondMandatoryComplete_whenEvaluate_thenSetTaskColorGreen() {
        // Given
        final Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.COMPLETE, TaskType.MANDATORY, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenThreeTasksFirstOptionalSecondMandatoryNotComplete_whenEvaluate_thenSetTaskColorYellow() {
        // Given
        final Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY, 2L);
        final Task task3 = new Task(TaskState.PENDING, TaskType.MANDATORY, 3L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(2, wf.getTasks());

        // Then
        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenFiveTasks_whenEvaluate_thenTreeMapIsNotNull(){

        //Given
        final Task task1 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 1L);
        final Task task2 = new Task(TaskState.NOT_COMPLETE, TaskType.OPTIONAL, 2L);
        final Task task3 = new Task(TaskState.NOT_COMPLETE, TaskType.MANDATORY, 3L);
        final Task task4 = new Task(TaskState.PENDING, TaskType.MANDATORY, 4L);
        final Task task5 = new Task(TaskState.PENDING, TaskType.MANDATORY, 5L);

        wf.getTasks().add(task1);
        wf.getTasks().add(task2);
        wf.getTasks().add(task3);
        wf.getTasks().add(task4);
        wf.getTasks().add(task5);

        // When
        TreeMap<Long, TaskColor> workflow = sut.evaluate(4, wf.getTasks());

        // Then
        assertThat(workflow).isNotNull();
    }
}
