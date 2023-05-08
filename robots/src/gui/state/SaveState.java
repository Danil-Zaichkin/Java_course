package gui.state;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveState {
    @SuppressWarnings("unchecked")
    public void save(JInternalFrame window){
        JSONObject obj = new JSONObject();
        obj.put("Y",window.getY());
        obj.put("X",window.getX());
        obj.put("Width",window.getWidth());
        obj.put("Height",window.getHeight());
        try(FileWriter file = new FileWriter("myJson" + window.getTitle() + ".json")){
            file.write(obj.toString());
            file.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void recover(JInternalFrame window){
        JSONParser parser = new JSONParser();
        System.out.println(window);
        try {
            Object obj = parser.parse(new FileReader("myJson" + window.getTitle() + ".json"));
            JSONObject jsonObject = (JSONObject) obj;
            String x = String.valueOf(jsonObject.get("X"));
            String y = String.valueOf(jsonObject.get("Y"));
            String width = String.valueOf(jsonObject.get("Width"));
            String height = String.valueOf(jsonObject.get("Height"));
            System.out.println(width);
            window.setSize(Integer.parseInt(width),Integer.parseInt(height));
            System.out.println(x);
            window.setLocation(Integer.parseInt(x),Integer.parseInt(y));
            System.out.println(window.getX());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean checkFileNotFound(JInternalFrame window){
        JSONParser parser = new JSONParser();
        try {
            parser.parse(new FileReader("myJson" + window.getTitle() + ".json"));
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
