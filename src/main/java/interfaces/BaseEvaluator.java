package interfaces;

import classes.Task;
import enums.TaskColor;

import java.util.LinkedList;
import java.util.TreeMap;

public interface BaseEvaluator {
    TreeMap<Long, TaskColor> evaluate(LinkedList<? extends Task> collection);
}
