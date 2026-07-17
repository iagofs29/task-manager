package taskmanager.services;

import taskmanager.repository.TaskRepository;
import taskmanager.models.*;

import java.io.UncheckedIOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskService {
    private final TaskRepository repository;
    private final List<Task> taskList;
    private long nextId;

    public TaskService(TaskRepository repository){
        this.repository = repository;
        this.taskList = repository.loadAll();
        this.nextId = this.getMaxId(taskList);
    }

    private long getMaxId(List<Task> taskList){
        long maxID = 0;

        for(Task task : taskList){
            if(task.getId() > maxID){
                maxID = task.getId();
            }
        }
        return maxID + 1;
    }

    public void createTask(String title, String description, TaskStatus status){
        Task task = new Task(nextId, title, description, status);
        this.nextId++;
        System.out.println("Task created succesfully with ID: " + task.getId());
        
        taskList.add(task);
        repository.saveAll(taskList);
        System.out.println("Task saved succesfully in json file.");
    }

    public boolean existsById(long id){
        for(Task task : taskList){
            if(task.getId() == id){
                return true;
            }
        }
        return false;
    }

    private Task findById(long id){     // Preconditions: id must be a valid ID within the task list.

        for(Task task : taskList){
            if(task.getId() == id){
                return task;
            }
        }
        throw new IllegalArgumentException("* Error: could not find task by ID.");
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

}
