package taskmanager.services;

import taskmanager.repository.TaskRepository;
import taskmanager.models.*;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskService {
    private final TaskRepository repository;
    private final List<Task> taskList;
    private int nextId;

    public TaskService(TaskRepository repository){
        this.repository = repository;
        this.taskList = repository.loadAll();
        this.nextId = this.getMaxId();
    }

    public int getMaxId(){
        int maxID = 0;

        for(Task task : this.taskList){
            if(task.getId() > maxID){
                maxID = task.getId();
            }
        }
        return maxID + 1;
    }

    public int getMinId(){
        return taskList.get(0).getId();
    }

    public boolean isEmptyList(){
        return taskList.isEmpty();
    }

    private Task findById(long id){     // Preconditions: 'id' must be a valid ID within the task list.

        for(Task task : taskList){
            if(task.getId() == id){
                return task;
            }
        }
        throw new IllegalArgumentException("* Error: could not find task by ID.");
    }

    public boolean existsById(long id){
        for(Task task : taskList){
            if(task.getId() == id){
                return true;
            }
        }
        return false;
    }

    public void createTask(String title, String description, TaskStatus status){
        Task task = new Task(nextId, title, description, status);
        this.nextId++;
        
        taskList.add(task);
        repository.saveAll(taskList);
        System.out.println("Task created succesfully with ID: " + task.getId());
    }


    public void modifyTask(long id, int fieldToMod, String newText, TaskStatus taskStatus){    // fieldToMod: desired field to modify

        Task desiredTask = this.findById(id);

        switch(fieldToMod){
            case 1:
                desiredTask.setTitle(newText);
                break;
            case 2: 
                desiredTask.setDescription(newText);
                break;
            case 3:
                desiredTask.setStatus(taskStatus);
                break;
            default:
                throw new IllegalArgumentException("* Error: Unexpected value on fieldToMod.");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        desiredTask.setUpdateTime(now.format(formatter));

        this.repository.saveAll(taskList);
    }

    public void deleteTask(long id){
        Task desiredTask = this.findById(id);

        taskList.remove(desiredTask);
        this.repository.saveAll(taskList);
    }

    public void showTaskList(boolean filtered, TaskStatus statusFilter){
        if(!filtered){
            for(Task task : taskList){
                System.out.println(task);
            }
        }else{
            for(Task task : taskList){
                if(task.getStatus() == statusFilter){
                    System.out.println(task);
                }
            }
        }
    }

}
