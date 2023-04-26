package gui.adapters;

import localization.LangChangeable;
import localization.LangDispatcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LangChangeAdapter implements PropertyChangeListener {

    private final LangChangeable instance;
    private final LangDispatcher dispatcher;
    public LangChangeAdapter(LangChangeable instance, LangDispatcher dispatcher) {
        this.instance = instance;
        this.dispatcher = dispatcher;
        dispatcher.addLangChangeListener(this);
    }
    @Override
    public void propertyChange(PropertyChangeEvent ev) {
        if (dispatcher.equals(ev.getSource())) {
            if (LangDispatcher.PROPERTY_NAME.equals(ev.getPropertyName())) {
                instance.updateLang();
            }
        }
    }
}
