package gui.state;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;

public class SaveState<T> {
    public T recoverT(Path path, Type type) throws FileNotFoundException {
        FileReader fr = new FileReader(path.toFile());
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(fr, type);
    }

    public void saveT(Path path, T object) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try(FileWriter file = new FileWriter(path.toFile())){
            file.write(gson.toJson(object));
            file.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
