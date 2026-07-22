package taskmanager.controller;
import java.util.Scanner;

import com.google.gson.JsonIOException;
import java.io.UncheckedIOException;

import taskmanager.services.TaskService;
import taskmanager.models.*;
import taskmanager.console.ConsoleInput;

public class MenuController {
    private final TaskService taskService;
    private final Scanner scanner;
    private final ConsoleInput consoleInput;

    public MenuController(Scanner scanner, TaskService taskService, ConsoleInput consoleInput){
        this.taskService = taskService;
        this.scanner = scanner;
        this.consoleInput = consoleInput;
    }

    public void printMenu(){
        int choice;

        System.out.println("***************************\n        TASK MANAGER\n***************************");
        System.out.println("Choose one of the following options:\n\t1. Create task\n\t2. Modify task\n\t3. Delete task\n\t4. Show all tasks\n\t5. Filter by task status\n\t6. Exit");
        choice = consoleInput.readIntInRange("Your choice is: ", 1, 6);

        // Variables used regularly
        String title;
        String description;
        int tempStatus;
        TaskStatus status = TaskStatus.TODO;
        int id = 0;
        int fieldToMod = 0;
        
        switch (choice){
            case 1:
                title = consoleInput.readString("\nType the task title: ");
                description = consoleInput.readString("\nType the description: ");
                tempStatus = consoleInput.readIntInRange("\nType task status (1. TO DO, 2. IN PROGRESS, 3. DONE): ", 1, 3);

                switch(tempStatus){
                    case 1: status = TaskStatus.TODO; break;
                    case 2: status = TaskStatus.IN_PROGRESS; break;
                    case 3: status = TaskStatus.DONE; break;
                    default: System.out.println("* Error: choice not valid.");
                }

                try{
                    taskService.createTask(title, description, status);
                    System.out.println("+ Succesfully created and saved new task. Press enter to continue.");
                    scanner.nextLine();
                }catch(JsonIOException | UncheckedIOException | IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
             
            case 2:

                if(taskService.isEmptyList()){
                    System.out.println("* Error: no tasks available to modify - Task list is empty.");
                }else{
                    id = consoleInput.readIntInRange("Type the ID of the task you would like to modify: ", taskService.getMinId(), taskService.getMaxId());
                    
                    System.out.println("Choose the field you would like to modify: ");
                    System.out.println("\t1. Title.\n\t2. Description\n\t3. Status");
                    fieldToMod = consoleInput.readIntInRange("\nYour choice: ", 1, 3);
                }

                switch(fieldToMod){
                    case 1:
                    case 2:
                        String newText;
                        do{ 
                            if(fieldToMod == 1){
                                newText = consoleInput.readString("Type desired title: ");
                            }else{
                                newText = consoleInput.readString("Type desired description: ");
                            }

                            if(newText.isBlank()){
                                System.out.println("* Error: new title or description cannot be blank");
                            }
                        }while(newText.isBlank());
                        
                        try{
                            taskService.modifyTask(id, fieldToMod, newText, null);
                            System.out.println("+ Succesfully saved modified task. Press enter to continue.");
                            scanner.nextLine();
                        }catch(JsonIOException | UncheckedIOException | IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        TaskStatus statusMod = null;

                        tempStatus = consoleInput.readIntInRange("\nType task status (1. TO DO, 2. IN PROGRESS, 3. DONE): ", 1, 3);

                        try{
                            taskService.modifyTask(id, fieldToMod, null, statusMod);
                            System.out.println("+ Succesfully saved modified task. Press enter to continue.");
                            scanner.nextLine();
                        }catch(JsonIOException | UncheckedIOException | IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                }
            case 3:

                if(!taskService.isEmptyList()){
                    id = consoleInput.readIntInRange("Type the ID of the task you would like to delete: ", taskService.getMinId(), taskService.getMaxId());
                    
                    try{
                        taskService.deleteTask(id);
                        System.out.println("+ Succesfully saved new list. Press enter to continue.");
                        scanner.nextLine();
                    }catch(JsonIOException | UncheckedIOException | IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }else{
                    System.out.println("\n* Error: Cannot delete any task due to list being empty.\n");
                }
                break;

            case 4:
                if(!taskService.isEmptyList()){
                    taskService.showTaskList(false, null);
                }else{
                    System.out.println("\nNo tasks avaliable\n");
                }

                System.out.print("Press enter to continue.");
                scanner.nextLine();
                break;
            case 5:
                if(!taskService.isEmptyList()){
                    System.out.println("Choice the status you would like to filter the tasks by: ");
                    System.out.println("\t1. TO DO\n\t2. IN PROGRESS\n\t3. DONE");
                    tempStatus = consoleInput.readIntInRange("\nYour choice is: ", 1, 3);

                    switch(tempStatus){
                        case 1: status = TaskStatus.TODO; break;
                        case 2: status = TaskStatus.IN_PROGRESS; break;
                        case 3: status = TaskStatus.DONE; break;
                        default: System.out.println("* Error: choice not valid.");
                    }

                    taskService.showTaskList(true, status);
                }else{
                    System.out.println("* Error: cannot show any tasks due to list being empty.");
                }
                System.out.print("Press enter to continue.");
                scanner.nextLine();
                break;
            case 6: 
                scanner.close();
                System.exit(0);
        }   
    }
}
