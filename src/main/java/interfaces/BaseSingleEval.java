package interfaces;

import classes.Task;
import enums.TaskColor;

import java.util.ArrayList;
import java.util.TreeMap;

public interface BaseSingleEval {
    TreeMap<Long, TaskColor> evaluate(int taskIndex, ArrayList<Task> tasks);
}
