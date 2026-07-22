package taskmanager.console;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner;

    public ConsoleInput(Scanner scanner){
        this.scanner = scanner;
    }

    public int readIntInRange(String message, int min, int max){
        int choice = 0;
        System.out.print(message);

        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                
                if(choice < min || choice > max){
                    System.out.println("* Error: Choose a number between " + min + " and " + max);
                    continue;
                }
                return choice;

            }catch(InputMismatchException e){
                System.out.println("* Error: you must type an integer number.");
            }
        }
    }

    public String readString(String message){
        System.out.print(message);
        String line;

        while(true){
            try{
                line = scanner.nextLine();
                return line;
            }catch(NoSuchElementException e){
                System.out.println("* Error: please type a valid line of text.");
            }
        }
    }
}
