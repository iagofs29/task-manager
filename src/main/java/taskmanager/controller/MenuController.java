package taskmanager.controller;
import java.util.Scanner;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import taskmanager.services.TaskService;
import taskmanager.models.*;

public class MenuController {
    private final TaskService taskService;
    private Scanner scanner;

    public MenuController(Scanner scanner, TaskService taskService){
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public void printMenu(){
        int choice = 0;
        do{
            System.out.println("***************************\n        TASK MANAGER\n***************************");
            System.out.println("Choose one of the following options:\n\t1. Create task\n\t2. Modify task\n\t3. Delete task\n\t4. Show all tasks\n\t5. Filter by task status\n\t6. Exit");
            System.out.print("\nYour choice is: ");
            
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                if(choice < 1 && choice > 6){
                    System.out.println("* Error: Choice not possible. Try again.");
                }
            }catch(InputMismatchException e){
                System.out.println("* Error: your choice must be an integer. Choose the number which identifies each option.");
            }
        }while(choice < 1 && choice > 6);

        switch (choice){
            case 1:
                String title;
                String description;
                int tempStatus;
                TaskStatus status = TaskStatus.TODO;

                System.out.print("\nType the task title: ");
                title = scanner.nextLine();

                System.out.print("\nType the description: ");
                description = scanner.nextLine();

                do{
                    try{
                    System.out.print("\nType task status (1. TO DO, 2. IN PROGRESS, 3. DONE): ");
                    tempStatus = scanner.nextInt();
                    scanner.nextLine();

                        switch(tempStatus){
                            case 1: status = TaskStatus.TODO; break;
                            case 2: status = TaskStatus.IN_PROGRESS; break;
                            case 3: status = TaskStatus.DONE; break;
                            default: System.out.println("\n* Error: choice not valid.");
                        }
                    }catch(InputMismatchException e){
                        System.out.println("* Error: you must type a number.");
                        scanner.nextLine();
                        tempStatus = 4;
                    }
                }while(tempStatus != 1 && tempStatus != 2 && tempStatus != 3);

                taskService.createTask(title, description, status);
                break;
            
            case 2:
                long id = 0;
                int fieldToMod = 0;
                boolean validIdInput = false;
                boolean validFieldInput = false;
                do{
                    try{
                        System.out.print("Type the ID of the task you would like to modify: ");
                        id = scanner.nextLong();
                        scanner.nextLine();
                        if(id < 1 || !taskService.existsById(id)){
                            System.out.println("* Error: invalid ID");
                        }else{
                            validIdInput = true;
                        }
                    }catch(InputMismatchException e){
                        System.out.println("* Error: you must type an Integer number.");
                    }
                }while(!validIdInput);

                do{
                    try{
                        System.out.println("Choose the field you would like to modify:");
                        System.out.println("\t1. Title.\n\t2. Description\n\t3. Status");
                        System.out.print("\nYour choice: ");
                        fieldToMod = scanner.nextInt();
                        scanner.nextLine();
                        if(fieldToMod > 0 && fieldToMod < 4){
                            validFieldInput = true;
                        }else{
                            System.out.println("* Error: choose a valid value.");
                        }
                    }catch(InputMismatchException e){
                        System.out.println("* Error: you must type an Integer number.");
                        scanner.nextLine();
                    }
                }while(!validFieldInput);

                switch(fieldToMod){
                    case 1:
                    case 2:
                        String newText;
                        do{    
                            try{
                                if(fieldToMod == 1){
                                    System.out.print("Type desired title: ");
                                }
                                else{
                                    System.out.print("Type desired description: ");
                                }
                                newText = scanner.nextLine();
                                if(newText.isBlank()){
                                    System.out.println("* Error: new title or description cannot be blank.\n");
                                }
                            }catch(NoSuchElementException e){
                                newText = "";
                                System.out.println("* Error: no such element exception.\n");
                            }
                        }while(newText.isBlank());

                        try{
                            taskService.modifyTask(id, fieldToMod, newText, null);
                            System.out.println("+ Saved modified task succesfully.");
                        }catch(UncheckedIOException | IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        TaskStatus statusMod = null;
                        do{
                            try{
                                System.out.print("\nType task status (1. TO DO, 2. IN PROGRESS, 3. DONE): ");
                                tempStatus = scanner.nextInt();
                                scanner.nextLine();

                                switch(tempStatus){
                                    case 1: statusMod = TaskStatus.TODO; break;
                                    case 2: statusMod = TaskStatus.IN_PROGRESS; break;
                                    case 3: statusMod = TaskStatus.DONE; break;
                                    default: System.out.println("\n* Error: choice not valid.");
                                }
                            }catch(InputMismatchException e){
                                System.out.println("* Error: you must type a number.");
                                scanner.nextLine();
                                tempStatus = 4;
                            }
                        }while(tempStatus != 1 && tempStatus != 2 && tempStatus != 3);
                        try{
                            taskService.modifyTask(id, fieldToMod, null, statusMod);
                            System.out.println("+ Saved modified task succesfully.");
                        }catch(UncheckedIOException | IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                }
            case 3:
                
        
            }
    }
}
