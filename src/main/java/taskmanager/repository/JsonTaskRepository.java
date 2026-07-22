package taskmanager.repository;
import taskmanager.models.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.Writer;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

public class JsonTaskRepository implements TaskRepository{
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Path filePath;
    private static final Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();

    public JsonTaskRepository(){
        this("data", "tasks.json");
    }

    public JsonTaskRepository(String directory, String file){

        Path filePath;

        if(file == null){
            throw new IllegalArgumentException("\n* Error: please specify a file name with .json extension.\n");
        }

        if(file.isBlank()){
            throw new IllegalArgumentException("\n* Error: must specify a name + .json file. \n");
        }

        if(!file.endsWith(".json")){        // Comprobamos que el archivo específicado sea formato json
            throw new IllegalArgumentException("\n* Error: file does does not contain '.json' extension.\n");
        }

        if(file.contentEquals(".json")){
            throw new IllegalArgumentException("\n* Error: must specify a name for the file.\n");
        }

        if(directory == null || directory.isBlank()){
            filePath = Path.of(file);
        }else{
            filePath = Path.of(directory, file);
        }
        
        if(filePath.getParent() != null){
            try{
                Files.createDirectories(filePath.getParent());
            }catch(IOException e){
                throw new UncheckedIOException("\n* Error: could not create desired directory.\n", e);
            }
        }
        
        if(Files.notExists(filePath)){
            try(Writer writer = Files.newBufferedWriter(filePath)){
                writer.write("[]");          // Rellenamos el nuevo file con '[]' para que sea un archivo json vacío
            }catch(IOException e){
                throw new UncheckedIOException("\n* Error: could not fill file with '[]'.\n", e);
            }
        }

        try{
            long fileSize = Files.size(filePath);
            if (fileSize == 0){
                try(Writer writer = Files.newBufferedWriter(filePath)){
                    writer.write("[]");
                }catch(IOException e){
                    throw new UncheckedIOException("\n* Error: could not fill the file with '[]'.\n", e);
                }
            }
        }catch(IOException e){
            throw new UncheckedIOException("\n* Error: could not check if file is empty. \n", e);
        }

        this.filePath = filePath;
    }

    @Override
    public List<Task> loadAll(){                   // Leer tasks.json y convertirlo en objetos Task
        List<Task> taskList = new ArrayList<>();
        
        try(Reader reader = Files.newBufferedReader(filePath)){
            taskList = gson.fromJson(reader, taskListType);
        }catch(IOException e){
            System.out.println("\n* Error: unable to open file.\n");
        }
        if (taskList == null){
            throw new IllegalArgumentException("\n* Error: could not load the list\n");
        }
        return taskList;
    }

    @Override
    public void saveAll(List<Task> taskList){      // Convertir los Task a JSON y escribir tasks.json

        try(Writer writer = Files.newBufferedWriter(filePath)){
            gson.toJson(taskList, writer);
        }catch(IOException e){
            throw new UncheckedIOException("* Error: unable to open file.", e);
        }catch(JsonIOException e){
            throw new JsonIOException("* Error: JSON problem writing to the writer", e);
        }
    }
}