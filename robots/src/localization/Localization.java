package localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private static ResourceBundle resourceBundle;
    private static Locale locale;

    static {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault());
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static void changeLocalisation(Locale newLocale) {
        locale = newLocale;
        resourceBundle = ResourceBundle.getBundle("lang", newLocale);
    }

    public static Locale getLocale() {
        return locale;
    }
}
