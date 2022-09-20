package interfaces;

import classes.Task;
import enums.TaskColor;

import java.util.List;
import java.util.TreeMap;

public interface BaseEvalAlt {
    TreeMap<Long, TaskColor> evaluate(Task currentTask, List<Task> previousTasks);
}
