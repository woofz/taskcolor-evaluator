package classes;

import enums.TaskState;
import enums.TaskType;

public class Task {

    private TaskState state;

    private TaskType type;

    private Long taskId;

    public Task(TaskState state, TaskType type, Long taskId) {
        this.state = state;
        this.type = type;
        this.taskId = taskId;
    }

    public Task() {
        this.state = TaskState.NOT_COMPLETE;
        this.type = TaskType.OPTIONAL;
    }

    public Task(TaskState state) {
        this.state = state;
        this.type = TaskType.OPTIONAL;
    }

    public Task(TaskState state, Long taskId) {
        this.state = state;
        this.taskId = taskId;
        this.type = TaskType.OPTIONAL;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean isMandatory() { return this.type.equals(TaskType.MANDATORY) ? true : false; }

    public boolean isComplete() { return this.state.equals(TaskState.COMPLETE) ? true : false; }

    public boolean isOptional() { return this.type.equals(TaskType.OPTIONAL) ? true : false; }
}
