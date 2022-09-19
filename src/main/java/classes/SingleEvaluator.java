package classes;

import enums.TaskColor;
import interfaces.BaseSingleEval;

import java.util.ArrayList;
import java.util.TreeMap;

public class SingleEvaluator implements BaseSingleEval {

    public SingleEvaluator() {

    }

    @Override
    public TreeMap<Long, TaskColor> evaluate(Task currentNode, ArrayList<Task> previousTasks) {
        return null;
    }
}
