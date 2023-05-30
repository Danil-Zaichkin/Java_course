package gui.adapters;

import logic.RobotDispatcher;
import logic.CoordinatesChangeListener;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CoordinatesChangeAdapter implements PropertyChangeListener {
    private final CoordinatesChangeListener instance;
    private final RobotDispatcher dispatcher;

    public CoordinatesChangeAdapter(CoordinatesChangeListener instance, RobotDispatcher dispatcher) {
        this.instance = instance;
        this.dispatcher = dispatcher;
        dispatcher.addListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (dispatcher.equals(evt.getSource())) {
            if (RobotDispatcher.CHANGE_COORDINATES.equals(evt.getPropertyName())) {
                instance.changeCoordinates((Point) evt.getNewValue());
            }
        }

    }
}
