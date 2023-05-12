package gui.state;

import java.beans.PropertyChangeSupport;

public class StateDispatcher {
    public static String PROPERTY_NAME = "SaveStateDispatcher.m_saveState";
    private final PropertyChangeSupport dispatcher = new PropertyChangeSupport(this);
    private static final StateDispatcher instance = new StateDispatcher();

    public static StateDispatcher getInstance() {
        return instance;
    }
    private StateDispatcher() {}
}
