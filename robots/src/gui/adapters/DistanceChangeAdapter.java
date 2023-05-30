package gui.adapters;

import logic.DistanceChangeListener;
import logic.RobotDispatcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DistanceChangeAdapter implements PropertyChangeListener {
    private final DistanceChangeListener instance;
    private final RobotDispatcher dispatcher;

    public DistanceChangeAdapter(DistanceChangeListener instance, RobotDispatcher dispatcher) {
        this.instance = instance;
        this.dispatcher = dispatcher;
        dispatcher.addListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (dispatcher.equals(evt.getSource())) {
            if (RobotDispatcher.CHANGE_DISTANCE.equals(evt.getPropertyName())) {
                instance.changeDistance((int) evt.getNewValue());
            }
        }

    }
}
