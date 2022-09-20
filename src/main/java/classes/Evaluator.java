package classes;

import enums.TaskColor;
import enums.TaskState;
import enums.TaskType;
import interfaces.BaseEval;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Evaluator implements BaseEval {

    public Evaluator(){}


    /**
     * Evaluate a workflow task and assign a color to it
     * @param taskIndex the task index
     * @param tasks the list of tasks
     * @return
     */
    @Override
    public TreeMap<Long, TaskColor> evaluate(int taskIndex, ArrayList<Task> tasks) {
        //Initialization
        TreeMap<Long, TaskColor> treeMap = new TreeMap<>();

        //Check task
        //If the task is the first task of the list ...
        if (taskIndex == 0){
            //... then set to COMPLETE
            treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.GREEN);
            tasks.get(taskIndex).setState(TaskState.COMPLETE);
        } else {
            //if there's only one previous task ...
            if (taskIndex == 1){

                // ... if previous task is complete ...
                if(tasks.get(0).getState().equals(TaskState.COMPLETE)){
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.GREEN);
                    tasks.get(taskIndex).setState(TaskState.COMPLETE);

                // ... if previous task is mandatory, or it is not complete ...
                } else if (tasks.get(0).getType().equals(TaskType.MANDATORY) &&
                        (!tasks.get(0).getState().equals(TaskState.COMPLETE))) {
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.YELLOW);
                    tasks.get(taskIndex).setState(TaskState.PENDING);

                // ... if previous is optional ...
                } else {
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.GREEN);
                    tasks.get(taskIndex).setState(TaskState.COMPLETE);
                }

            // If there's more than one previous task ...
            } else if(taskIndex > 1 ){
                List<Task> tasksSubList = tasks.subList(0, taskIndex);
                Task parentTask = tasksSubList.get(tasksSubList.size()-1);
                Task granParentTask = tasksSubList.get(tasksSubList.size()-2);

                // ... if gran parent task state is COMPLETE ...
                if(granParentTask.getState().equals(TaskState.COMPLETE)) {
                    if(parentTask.getState().equals(TaskState.COMPLETE)
                            || parentTask.getType().equals(TaskType.OPTIONAL)) {
                        treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.GREEN);
                        tasks.get(taskIndex).setState(TaskState.COMPLETE);
                    }
                    // ... else if parent task is MANDATORY or PENDING/NOT_COMPLETE...
                    if(!parentTask.getState().equals(TaskState.COMPLETE)
                            && parentTask.getType().equals(TaskType.MANDATORY)) {
                        treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.YELLOW);
                        tasks.get(taskIndex).setState(TaskState.PENDING);
                    }
                }
                // ... if gran parent is MANDATORY and is not COMPLETE -> return Yellow
                if(granParentTask.getType().equals(TaskType.MANDATORY) && !granParentTask.getState().equals(TaskState.COMPLETE)) {
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.YELLOW);
                    tasks.get(taskIndex).setState(TaskState.PENDING);
                }

                // ... if gran parent and parent are optional ...
                if((granParentTask.getType().equals(TaskType.OPTIONAL) &&
                        (parentTask.getType().equals(TaskType.OPTIONAL)
                                || (parentTask.getType().equals(TaskType.MANDATORY))
                                && parentTask.getState().equals(TaskState.COMPLETE)))) {
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.GREEN);
                    tasks.get(taskIndex).setState(TaskState.COMPLETE);
                // ... else ...
                } else {
                    treeMap.put(tasks.get(taskIndex).getTaskId(), TaskColor.YELLOW);
                    tasks.get(taskIndex).setState(TaskState.PENDING);
                }
            }
        }
        //Return
        return treeMap;
    }
}
