package classes;

import enums.TaskColor;
import enums.TaskState;
import enums.TaskType;
import interfaces.BaseEval;

import java.util.ArrayList;
import java.util.TreeMap;

public class Evaluator implements BaseEval {

    public Evaluator() {

    }

    @Override
    public TreeMap<Long, TaskColor> evaluate(int taskIndex, ArrayList<Task> tasks) {
        //Initialization
        TreeMap<Long, TaskColor> treeMap = new TreeMap<>();

        //Check task

        //if the task is the first task of the list ...
        if (taskIndex == 0){
            // ... then set to COMPLETE
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

            }
        }

        //Return
        return treeMap;
    }
}
