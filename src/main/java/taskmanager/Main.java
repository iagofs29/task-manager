package taskmanager;

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
        MenuController menuController = new MenuController(scanner, service);

        while(true){
            menuController.printMenu();
        }
    }
}