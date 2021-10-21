import java.io.*;
import java.util.*;

enum TaskType{
    STORY, FEATURE, BUG;
}

enum TaskStatus{
    OPEN, IN_PROGRESS, RESOLVED, DELAYED, COMPLETED;
}

class Sprint{
    private int begin;
    private int end;
    private String name;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public Sprint(int begin, int end, String name){
        this.begin = begin;
        this. end = end;
        this.name = name;
    }

    public boolean isEquals(Sprint sprint){
        return (this.begin == sprint.begin) && (this.end == sprint.end) && (this.name == sprint.name);
    }

    public ArrayList<Task> getTasks(){
        return this.tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void printDetails(){
        System.out.println("Sprint Name: " + this.name);
        System.out.println("Sprint Begins: " + this.begin);
        System.out.println("Sprint Ends: " + this.end);
    }

    public void eraseTask(int taskNumber){
        tasks.remove(taskNumber);
    }
}

class User{
    private ArrayList<Task> taskList = new ArrayList<Task>();
    private ArrayList<Sprint> sprintList = new ArrayList<Sprint>();

    public Task createTask(TaskType taskType){
        if(taskType == TaskType.STORY){
            System.out.println("Warning! Task of type Story is being created with no subtract.");
        }

        Task task = new Task();
        task.setTaskType(taskType);
        task.setUser(this);
        taskList.add(task);
        
        return task;
    }

    public Task createTask(String subtract){
        Task task = new Task();
        task.setTaskType(TaskType.STORY);
        task.setSubtract(subtract);
        task.setUser(this);
        taskList.add(task);

        return task;
    }

    public Sprint createSprint(int begin, int end, String name){
        Sprint sprint = new Sprint(begin, end, name);
        sprintList.add(sprint);
        
        return sprint;
    }

    public boolean addToSprint(Sprint sprint, Task task){
        for(Sprint sp: sprintList){
            if(sp.isEquals(sprint)){
                sp.addTask(task);
                
                return true;
            }
        }
        
        return false;
    }

    public boolean removeFromSprint(Sprint sprint, Task task){
        ArrayList<Task> taskList = new ArrayList<Task>();
        for(Sprint spr: sprintList){
            if(spr.isEquals(sprint)){
                taskList = spr.getTasks();
                break;
            }
        }

        int i = 0;
        for(Task t: taskList){
            if(t.getId() == task.getId()){
                sprint.eraseTask(i);
                return true;
            }
            i += 1;
        }
        
        return false;
    }

    public void printAllTasks(){
        for(Task task: taskList){
            System.out.print(task.getId() + " ");
        }

        System.out.println();
    }
    
    public boolean changeStatus(Task task, TaskStatus taskStatus){
        for(Task t: taskList){
            if(t.getId() == task.getId()){
                t.setTaskStatus(taskStatus);
                
                return true;
            }
        }
        
        return false; 
    }
}

class Task{
    private int id;
    private String subtract;
    private User user;
    private TaskType taskType;
    private TaskStatus taskStatus;
    private static int uniqueTaskId;

    static{
        Task.uniqueTaskId = 1;
    }

    private int getUniqueId(){
        return Task.uniqueTaskId++;
    }

    public Task(){
        this.id = getUniqueId();
        this.taskStatus = TaskStatus.OPEN;
    }

    public int getId(){
        return this.id;
    }

    public void setSubtract(String subtract){
        this.subtract = subtract;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTaskType(TaskType taskType){
        this.taskType = taskType;
    }

    public void setTaskStatus(TaskStatus taskStatus){
        this.taskStatus = taskStatus;
    }
}

public class task_planner {
    public static void main(String[] args){
        User user1 = new User();
        User user2 = new User();

        Task task1 = user1.createTask(TaskType.FEATURE);
        Task task11 = user1.createTask(TaskType.BUG);
        Task task2 = user2.createTask(TaskType.BUG);
        Task task22 = user2.createTask("This is a subtract");

        Sprint sprint1 = user1.createSprint(22, 33, "Sprint1");
        Sprint sprint2 = user2.createSprint(44, 55, "Sprint2");

        System.out.println(user1.changeStatus(task11, TaskStatus.IN_PROGRESS));
        System.out.println(user1.addToSprint(sprint1, task1));
        System.out.println(user1.addToSprint(sprint1, task11)); 
        System.out.println(user1.addToSprint(sprint2, task1)); 
        System.out.println(user1.removeFromSprint(sprint1, task11));
        System.out.println(user2.addToSprint(sprint1, task1)); 
        System.out.println(user2.removeFromSprint(sprint1, task2));
        System.out.println(user2.addToSprint(sprint2, task1)); 
        System.out.println(user2.addToSprint(sprint2, task2)); 

        sprint1.printDetails();
        System.out.println();
        
        user1.printAllTasks();
        user2.printAllTasks();
    }
}
