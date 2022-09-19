import classes.Task;
import classes.Workflow;
import enums.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowClassTest {

    private final Workflow workflow = new Workflow();

    @BeforeEach
    public void init() {

    }

    @Test
    public void givenObj_whenCreateWorkflow_thenReturnWorkflow() {
        Workflow workflow = new Workflow();

        assertThat(workflow).isNotNull();
    }

    @Test
    public void givenWorkflow_whenGetTasks_thenReturnCollection() {
        assertThat(workflow.getTasks()).isNotNull();
    }

    @Test
    public void givenTaskAndWorkflowTasks_whenAdd_thenAddTaskToCollection() {
        Task task = new Task();
        workflow.getTasks().add(task);

        assertThat(workflow.getTasks()).size().isEqualTo(1);
    }

    @Test
    public void givenTask_whenSetTasks_thenTasksIsNotEmpty(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(TaskState.COMPLETE,1l));
        workflow.setTasks(tasks);
        assertThat(workflow.getTasks().get(0).getTaskId()).isEqualTo(1L);
    }
}
