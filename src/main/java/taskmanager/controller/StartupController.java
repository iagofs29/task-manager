package taskmanager.controller;

import java.io.UncheckedIOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import taskmanager.repository.*;

public class StartupController {
    private Scanner scanner;

    public StartupController(Scanner scanner){
        this.scanner = scanner;
    }

    public TaskRepository configureRepository(){
        int choice = 0;
        TaskRepository repository;
        
        do{
            try{
                System.out.println("1. Use predefined path for data: /data/tasks.json");
                System.out.println("2. Use custom path for data.");
                System.out.print("Type your choice (1 or 2): ");
                choice = scanner.nextInt();

                if(choice != 1 && choice != 2){
                    System.out.println("\n* Error: please choose a valid option.\n");
                }
            }catch(InputMismatchException e){
                System.out.println("\n* Error: you must type a number.\n");
                scanner.nextLine();
            }
        }while(choice != 1 && choice != 2);

        scanner.nextLine();

        switch(choice){
            case 1:
                try{
                    repository = new JsonTaskRepository(); 
                    return repository;
                }catch(UncheckedIOException | IllegalArgumentException e){     // If repository throws exception due to failure when creating file / directory:
                    System.out.println(e.getMessage() + ". Using custom data path is recommended.");
                    return this.configureRepository();
                }
            case 2:
                String directory;
                String file; 
                boolean success2 = false;    // Variable used to control success in repository instantiation.

                do{ 
                    System.out.print("\nPlease type file name (must be .json format): ");
                    file = scanner.nextLine();
                    System.out.print("\nPlease type folder name (press enter if you want the file to remain on root directory): ");
                    directory = scanner.nextLine();

                    try{
                        repository = new JsonTaskRepository(directory, file);
                        success2 = true;
                        return repository;
                    }catch(IllegalArgumentException | UncheckedIOException e){     // If repository throws exception due to failure when creating file / directory:
                        System.out.println(e.getMessage());
                    }
                }while(!success2);
                
            default:
                System.out.println("\n* Error: Unexpected option: " + choice);
                return this.configureRepository();
        }
    }
}
