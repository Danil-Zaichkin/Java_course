package gui;

import localization.LangDispatcher;
import serializer.SerializeDispatcher;

import java.awt.*;

import javax.swing.*;

public class GameWindow extends RobotInternalFrame {
    private final GameVisualizer m_visualizer;

    public GameWindow(Dimension dimension, SerializeDispatcher serializeDispatcher) {
        super("window.game", LangDispatcher.getInstance(), serializeDispatcher);
        setSize(dimension);
        m_visualizer = new GameVisualizer(dimension);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
