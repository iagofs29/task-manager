package taskmanager.controller;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MenuController {
    public static void printMenu(){
        int choice = 0;
        System.out.println("***************************\n        TASK MANAGER\n***************************");
        System.out.println("Choose one of the following options:\n\t1. Create task\n\t2. Modify task\n\t3. Delete task\n\t4. Show all tasks\n\t5. Filter by task status\n\t6. Exit");
        System.out.print("\nYour choice is: ");
        
        try(Scanner scanner = new Scanner(System.in)){
            choice = scanner.nextInt();
        }catch(InputMismatchException e){
            System.out.println("* Error: your choice must be an integer. Choose the number which identifies each option.");
        }catch(Exception e){
            System.out.println("* Error: " + e.getMessage());
        }
        switch (choice){
            case 1:
        }
    }
}
