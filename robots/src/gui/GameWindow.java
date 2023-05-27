package gui;

import localization.LangDispatcher;
import serializer.SerializeDispatcher;

import java.awt.*;

import javax.swing.*;

public class GameWindow extends RobotInternalFrame {
    private GameVisualizer m_visualizer;
    public Dimension dimension;
    public GameWindow(Dimension dimension) {
        super("window.game", LangDispatcher.getInstance(), SerializeDispatcher.getInstance());
        this.dimension = dimension;
        setSize(dimension);
        getContentPane().add(initPanel());
        pack();
    }
    public void restartGame(){
        setPreferredSize(dimension);
        getContentPane().removeAll();
        getContentPane().add(initPanel());
        pack();
    }
    private JPanel initPanel(){
        m_visualizer = new GameVisualizer(dimension);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        return panel;
    }
}
