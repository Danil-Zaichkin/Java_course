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
    @SuppressWarnings("unchecked")
    static public void save(RobotWindowState windowState, Path jsonPath){
        JSONObject obj = new JSONObject();
        obj.put("Y",windowState.y());
        obj.put("X",windowState.x());
        obj.put("Width", windowState.width());
        obj.put("Height", windowState.height());
        obj.put("Title", windowState.title());
        try(FileWriter file = new FileWriter(jsonPath.toFile())){
            file.write(obj.toString());
            file.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static public RobotWindowState recover(Path jsonPath) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonPath.toFile()));
            JSONObject jsonObject = (JSONObject) obj;
            int x = Integer.parseInt(String.valueOf(jsonObject.get("X")));
            int y = Integer.parseInt(String.valueOf(jsonObject.get("Y")));
            int width = Integer.parseInt(String.valueOf(jsonObject.get("Width")));
            int height = Integer.parseInt(String.valueOf(jsonObject.get("Height")));
            String title = String.valueOf(jsonObject.get("Title"));
            return new RobotWindowState(x, y, height, width, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static public boolean checkFileNotFound(Path jsonPath){
        JSONParser parser = new JSONParser();
        try {
            parser.parse(new FileReader(jsonPath.toFile()));
            return true;
        }
        catch (Exception e){
//            e.printStackTrace();
            return false;
        }
    }

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
