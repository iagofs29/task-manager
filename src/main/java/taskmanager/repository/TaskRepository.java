package taskmanager.repository;

import java.util.List;
import taskmanager.models.Task;

public interface TaskRepository {
    List<Task> loadAll();
    void saveAll(List<Task> taskList);
}
