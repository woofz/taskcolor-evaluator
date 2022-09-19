package classes;

import enums.TaskColor;
import enums.TaskState;
import interfaces.BaseEvaluator;

import java.util.LinkedList;
import java.util.TreeMap;

public class Evaluator implements BaseEvaluator {
    public Evaluator() {
    }

    @Override
    public TreeMap<Long, TaskColor> evaluate(LinkedList<? extends Task> tasks) {
        TreeMap<Long, TaskColor> treeMap = new TreeMap<>();
        // Evaluating...
        if (tasks.size() == 1) {
            treeMap.put(tasks.getFirst().getTaskId(), TaskColor.GREEN);
        } else {
            Task previousTask = tasks.getFirst();
            Task currentTask;

            for (Task task : tasks) {

                currentTask = task;

                TaskColor taskColor = this.getColor(currentTask, previousTask);
                task.setState(this.getStateFromColor(taskColor));
                treeMap.put(task.getTaskId(), taskColor);

                previousTask = task;
            }
        }
        return treeMap;
    }

    private TaskColor getColor(Task currentTask, Task previousTask) {
        TaskColor colorToReturn;
        if (previousTask.getState().equals(TaskState.COMPLETE)) {
            colorToReturn = TaskColor.GREEN;
        } else {
            if (currentTask.getState().equals(TaskState.COMPLETE))
                colorToReturn = TaskColor.GREEN;
            else if (currentTask.getState().equals(TaskState.NOT_COMPLETE)) {
                colorToReturn = TaskColor.BLACK;
            } else {
                colorToReturn = TaskColor.YELLOW;
            }
        }
        return colorToReturn;
    }

    private TaskState getStateFromColor(TaskColor color) {
        switch(color) {
            case GREEN:
                return TaskState.COMPLETE;
            case BLACK:
                return TaskState.NOT_COMPLETE;
            default:
                return TaskState.PENDING;
        }
    }
}

