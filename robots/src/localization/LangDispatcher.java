package localization;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

public class LangDispatcher {
    public static String PROPERTY_NAME = "LangDispatcher.m_lang";
    private final PropertyChangeSupport dispatcher = new PropertyChangeSupport(this);
    private static final LangDispatcher instance = new LangDispatcher();

    public static LangDispatcher getInstance() {
        return instance;
    }
    private LangDispatcher() {}

    public void setLocale(Locale newLocale) {
        if (!Localization.getLocale().equals(newLocale)) {
            Locale oldLocale = Localization.getLocale();
            Localization.changeLocalisation(newLocale);
            dispatcher.firePropertyChange(PROPERTY_NAME, oldLocale, newLocale);
        }
    }

    public void addLangChangeListener(PropertyChangeListener listener)
    {
        dispatcher.addPropertyChangeListener(PROPERTY_NAME, listener);
    }
}
