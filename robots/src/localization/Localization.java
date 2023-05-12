package localization;

import gui.state.LocalizationState;
import gui.state.SaveState;

import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private static ResourceBundle resourceBundle;
    private static Locale locale;
    private static Path jsonPath;

    static {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault());
        jsonPath = Path.of("localization.json");
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

    public static void save() {
        SaveState<LocalizationState> saveState = new SaveState<>();
        saveState.saveT(jsonPath, new LocalizationState(getLocale()));
    }
}
