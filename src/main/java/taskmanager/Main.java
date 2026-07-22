package taskmanager;

import taskmanager.console.ConsoleInput;
import taskmanager.controller.MenuController;
import taskmanager.controller.StartupController;
import taskmanager.repository.*;
import java.util.Scanner;
import taskmanager.services.TaskService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StartupController startController = new StartupController(scanner);
        TaskRepository repository = startController.configureRepository();
        TaskService service = new TaskService(repository);
        ConsoleInput consoleInput = new ConsoleInput(scanner);
        MenuController menuController = new MenuController(scanner, service, consoleInput);

        while(true){
            menuController.printMenu();
        }
    }
}