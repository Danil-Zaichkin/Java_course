package gui;

import localization.LangDispatcher;

import java.awt.*;

import javax.swing.*;

public class GameWindow extends RobotInternalFrame {
    private final GameVisualizer m_visualizer;

    public GameWindow(Dimension dimension) {
        super("window.game", LangDispatcher.getInstance());
        setSize(dimension);
        m_visualizer = new GameVisualizer(dimension);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
