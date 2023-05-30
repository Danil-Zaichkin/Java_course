package gui;

import gui.adapters.CoordinatesChangeAdapter;
import gui.adapters.DistanceChangeAdapter;
import localization.LangDispatcher;
import localization.Localization;
import logic.DistanceChangeListener;
import logic.RobotDispatcher;
import serializer.SerializeDispatcher;

import javax.swing.*;
import java.awt.*;

public class DistanceWindow extends RobotInternalFrame implements DistanceChangeListener {
    private int distanceToTarget;
    private final JTextArea content = new JTextArea();
    DistanceWindow(String windowNameKey,
                   LangDispatcher langDispatcher,
                   SerializeDispatcher serializeDispatcher,
                   RobotDispatcher dispatcher){
        super(windowNameKey, langDispatcher, serializeDispatcher);
        addPropertyChangeListener(new DistanceChangeAdapter(this, dispatcher));
        distanceToTarget = 0;
        addContent();
    };

    @Override
    public void changeDistance(int distance) {
        distanceToTarget = distance;
        updateContent();
    }

    private void addContent() {
        content.setEditable(false);
        updateContent();
        getContentPane().add(content);
    }
    private void updateContent() {
        String newContent = Localization.getString("distance.text") +
                ": " + distanceToTarget + "\n";
        content.setText(newContent);
        content.invalidate();
    }
}