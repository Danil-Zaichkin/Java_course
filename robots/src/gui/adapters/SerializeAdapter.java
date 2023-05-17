package gui.adapters;

import gui.RobotInternalFrame;
import localization.Localization;
import serializer.SerializeDispatcher;
import serializer.Serializer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SerializeAdapter implements PropertyChangeListener {
    RobotInternalFrame instance;
    SerializeDispatcher dispatcher;

    public SerializeAdapter(RobotInternalFrame instance, SerializeDispatcher dispatcher) {
        this.instance = instance;
        this.dispatcher = dispatcher;
        dispatcher.addSerializeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SerializeDispatcher.BUNDLE_CHANGED)) {
            instance.setTitle(Localization.getString(instance.getWindowNameKey()));
        } else if (evt.getPropertyName().equals(SerializeDispatcher.SAVING_STATE)) {
            Serializer.serialize(instance);

        } else if (evt.getPropertyName().equals(SerializeDispatcher.RESTORING_STATE)) {
            Serializer.deserialize(instance);
        }
    }
}
