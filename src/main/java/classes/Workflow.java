package classes;

import java.util.ArrayList;
import java.util.LinkedList;

public class Workflow {


    private ArrayList<Task> tasks;
    public Workflow() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
