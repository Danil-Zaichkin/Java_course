package gui;

import gui.adapters.CoordinatesChangeAdapter;
import localization.LangDispatcher;
import localization.Localization;
import logic.RobotDispatcher;
import logic.CoordinatesChangeListener;
import serializer.SerializeDispatcher;

import javax.swing.*;
import java.awt.*;

public class RobotCoordinatesWindow extends RobotInternalFrame implements CoordinatesChangeListener {

    private Point robotCoordinates;
    private RobotDispatcher dispatcher;
    private final JTextArea content = new JTextArea();
    RobotCoordinatesWindow(String windowNameKey,
                           LangDispatcher langDispatcher,
                           SerializeDispatcher serializeDispatcher,
                           RobotDispatcher dispatcher) {

        super(windowNameKey, langDispatcher, serializeDispatcher);
        this.dispatcher = dispatcher;
        addPropertyChangeListener(new CoordinatesChangeAdapter(this, dispatcher));
        robotCoordinates = new Point(0, 0);
        addContent();
    }


    @Override
    public void changeCoordinates(Point newRobotCoordinates) {
        robotCoordinates = newRobotCoordinates;
        updateContent();
    }

    private void addContent() {
        content.setEditable(false);
        updateContent();
        getContentPane().add(content);
    }
    private void updateContent() {
        String newContent = Localization.getString("coordinates.text") +
                ": " + robotCoordinates.x +
                ", " + robotCoordinates.y + "\n";
        content.setText(newContent);
        content.invalidate();
    }
}
