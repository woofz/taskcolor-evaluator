package classes;

import java.util.LinkedList;

public class Workflow {


    private LinkedList<Task> tasks;
    public Workflow() {
        this.tasks = new LinkedList<>();
    }

    public LinkedList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(LinkedList<Task> tasks) {
        this.tasks = tasks;
    }
}
