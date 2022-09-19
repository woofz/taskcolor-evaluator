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
    public void givenObj_whenCreateEvaluator_thenReturnEvaluator() {
        Evaluator evaluator = new Evaluator();

        assertThat(evaluator).isNotNull();
    }

    @Test
    public void givenTasksCollectionAndWorkflow_whenEvaluate_thenReturnTreeMapOfTasksAndColors() {
        // Given
        wf.getTasks().add(new Task(TaskState.COMPLETE, 1L));
        wf.getTasks().add(new Task(TaskState.PENDING, 2L));
        wf.getTasks().add(new Task(TaskState.NOT_COMPLETE, 3L));

        // When
        TreeMap<Long, TaskColor> newWorkFlow = sut.evaluate(wf.getTasks());
        // Then
        assertThat(newWorkFlow).isNotNull();
    }

    @Test
    public void givenTaskWithNoPrecedingInCollection_whenEvaluate_thenSetTaskColorGreen() {
        Task task1 = new Task(TaskState.PENDING);
        task1.setTaskId(1L);
        wf.getTasks().add(task1);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(wf.getTasks());

        assertThat(workflow.firstEntry().getValue()).isEqualTo(TaskColor.GREEN);

    }

    @Test
    public void givenFirstGreenTaskAndSecondTaskPending_whenEvaluate_thenSetSecondTaskColorGreen() {
        final Task task1 = new Task(TaskState.COMPLETE);
        task1.setTaskId(1L);
        final Task task2 = new Task(TaskState.PENDING);
        task2.setTaskId(2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.GREEN);
    }

    @Test
    public void givenMandatoryTaskAndHisPreviousTaskStateIsPending_whenEvaluate_thenSetMandatoryTaskColorYellow() {
        final Task task1 = new Task(TaskState.PENDING, TaskType.MANDATORY, 1L);
        final Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY, 2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

    @Test
    public void givenMandatoryTaskAndHisPreviousTaskStateIsNotComplete_whenEvaluate_thenSetMandatoryTaskColorYellow() {
        final Task task1 = new Task(TaskState.PENDING, TaskType.MANDATORY, 1L);
        final Task task2 = new Task(TaskState.PENDING, TaskType.MANDATORY, 2L);
        wf.getTasks().add(task1);
        wf.getTasks().add(task2);

        TreeMap<Long, TaskColor> workflow = sut.evaluate(wf.getTasks());

        assertThat(workflow.lastEntry().getValue()).isEqualTo(TaskColor.YELLOW);
    }

}
