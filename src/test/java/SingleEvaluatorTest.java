import classes.SingleEvaluator;
import classes.Task;
import classes.Workflow;
import enums.TaskColor;
import enums.TaskState;
import enums.TaskType;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleEvaluatorTest {
    final SingleEvaluator sut = new SingleEvaluator();
    final Workflow wf = new Workflow();

    @Test
    public void givenObject_whenCreateEvaluator_thenReturnEvaluator() {
        SingleEvaluator singleEvaluator = new SingleEvaluator();
        assertThat(singleEvaluator).isNotNull();
    }

    @Test
    public void givenTasksCollectionAndWorkflow_whenEvaluate_thenReturnTreeMapOfTasksAndColors() {
        // Given
        wf.getTasks().add(new Task(TaskState.COMPLETE, 1L));

        // When
        TreeMap<Long, TaskColor> newWorkFlow = sut.evaluate(1, wf.getTasks());
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
}
