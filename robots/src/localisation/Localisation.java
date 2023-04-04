package localisation;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localisation {
    private static ResourceBundle resourceBundle;
    static  {
        resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault());
    }
    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static void changeLocalisation(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("lang", locale);
    }
}
