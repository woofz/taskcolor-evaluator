import classes.Task;
import enums.TaskState;
import enums.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskClassTest {
    private final Task myTask = new Task();

    @BeforeEach
    public void init() {
        myTask.setType(TaskType.OPTIONAL);
    }

    @Test
    public void givenObj_whenCreateTask_thenReturnCreatedTask() {
        Task myNewTask = new Task();

        assertThat(myNewTask).isNotNull();
    }

    @Test
    public void givenFreshTask_whenGetState_thenReturnStateNotComplete() {
        assertThat(myTask.getState()).isEqualTo(TaskState.NOT_COMPLETE);
    }

    @Test
    public void givenExistentTask_whenSetState_thenChangeStateVar() {
        TaskState newState = TaskState.COMPLETE;
        myTask.setState(newState);

        assertThat(myTask.getState()).isEqualTo(TaskState.COMPLETE);
    }

    @Test
    public void givenExistentTask_whenGetTaskType_thenReturnTaskType() {
        assertThat(myTask.getType()).isEqualTo(TaskType.OPTIONAL);
    }
}
