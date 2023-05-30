package logic;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RobotDispatcher {
    public final static String CHANGE_COORDINATES = "ChangeCoordinates";
    public final static String CHANGE_DISTANCE = "ChangeDistance";
    private final PropertyChangeSupport dispatcher = new PropertyChangeSupport(this);
    private static final RobotDispatcher instance = new RobotDispatcher();
    public static RobotDispatcher getInstance() {
        return instance;
    }

    public void setCoordinates(Point newCoordinates) {
        dispatcher.firePropertyChange(CHANGE_COORDINATES, null, newCoordinates);
    }

    public void setDistanceToTarget(int distanceToTarget) {
        dispatcher.firePropertyChange(CHANGE_DISTANCE, null, distanceToTarget);
    }
    private RobotDispatcher() {}

    public void addListener(PropertyChangeListener listener) {
        dispatcher.addPropertyChangeListener(listener);
    }

}
