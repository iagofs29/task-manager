package taskmanager.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdAt;
    private String updatedAt;

    public Task(long id, String title, String description, TaskStatus status){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.createdAt = now.format(formatter);
        this.updatedAt = "";
    }

    public long getId(){
        return this.id;
    }
    
    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public TaskStatus getStatus(){
        return this.status;
    }

    public String GetCreationDate(){
        return this.createdAt;
    }

    public String getUpdateDate(){
        return this.updatedAt;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public void setUpdateTime(String updateAt){
        this.updatedAt = updateAt;
    }

    @Override
    public String toString(){
        return "Title: " + this.getTitle() + "\n" + "Description: " + this.getDescription() + "\n" +
                "Status: " + this.getStatus() + "\n" + "Creation Date: " + this.GetCreationDate() + "\n";
    }
}
