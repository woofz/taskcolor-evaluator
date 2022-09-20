package classes;

import enums.TaskColor;
import enums.TaskState;
import interfaces.BaseEvalAlt;

import java.util.List;
import java.util.TreeMap;

public class AltEvaluator implements BaseEvalAlt {

    public AltEvaluator() { }

    /**
     * Evaluates a Task giving him a Color
     * @param currentTask Task to evaluate
     * @param previousTasks Its previous tasks
     * @return TreeMap containing TaskId as Key and a TaskColor as value
     */
    @Override
    public TreeMap<Long, TaskColor> evaluate(Task currentTask, List<Task> previousTasks) {
        // Initializing the item to return
        TreeMap<Long, TaskColor> treeMap = new TreeMap<>();
        TaskColor color = null;

        // Operations
        // If the workflow has a single task, then set color to Green
        if (previousTasks.isEmpty()) {
            color = TaskColor.GREEN;
        } else {
            // If the previous tasks list is composed by only one task
            if (previousTasks.size() == 1)
                color = this.evaluateSingle(previousTasks.get(0));
            else if(previousTasks.size() > 1) {
                // Taking the 2 previous tasks to evaluate
                int lastIndex = previousTasks.size() - 1;
                int secondToLastIndex = lastIndex - 1;
                Task childTask = previousTasks.get(lastIndex);
                Task parentTask = previousTasks.get(secondToLastIndex);
                color = this.evaluateMultiple(previousTasks);
            }
        }
        // Return
        treeMap.put(currentTask.getTaskId(), color);
        return treeMap;
    }

    /**
     * Evaluates a single task returning its color
     * @param task Task to evaluate
     * @return TaskColor
     */
    private TaskColor evaluateSingle(Task task) {
        TaskColor colorToReturn = null;
        TaskState currentState = task.getState();

        // If task type is Optional then return always a Green Color
        if (task.isOptional())
            return TaskColor.GREEN;

        // Checking task's state
        switch (currentState) {
            case COMPLETE -> colorToReturn = TaskColor.GREEN;
            case PENDING, NOT_COMPLETE -> colorToReturn = TaskColor.YELLOW;
        }

        return colorToReturn;
    }

    /**
     * Checks every dependency between tasks in the list and current task and returns current task color after the eval.
     * @param previousTasks List of previous tasks
     * @return TaskColor
     */
    private TaskColor evaluateMultiple(List<Task> previousTasks) {
        // Init
        TaskColor colorToReturn = null;
        // Taking the 2 previous tasks to evaluate
        int lastIndex = previousTasks.size() - 1;
        int secondToLastIndex = lastIndex - 1;
        Task childTask = previousTasks.get(lastIndex);
        Task parentTask = previousTasks.get(secondToLastIndex);

        // If parent task is Mandatory and Not Complete/Pending -> color Yellow
        if(parentTask.isMandatory() && !parentTask.isComplete())
            colorToReturn = TaskColor.YELLOW;

        if(!parentTask.isMandatory()) {
            switch(childTask.getType()) {
                case MANDATORY -> {
                    // If Child Task is Complete -> Color Green
                    if(childTask.isComplete())
                        colorToReturn = TaskColor.GREEN;
                    // else Color Yellow
                    colorToReturn = TaskColor.YELLOW;
                }
                case OPTIONAL -> { colorToReturn = TaskColor.GREEN; }
            }
        }

        // If the parent task is Complete (Green)
        if(parentTask.isComplete()) {

            // ... if the child task is Complete or Optional -> color Green
            if (childTask.isComplete() || !childTask.isMandatory())
                colorToReturn = TaskColor.GREEN;
            // else if child task is Mandatory and Not Complete/Pending -> color Yellow
            else if (childTask.isMandatory() && !childTask.isComplete())
                colorToReturn = TaskColor.YELLOW;
        }
        return colorToReturn;
    }
}
