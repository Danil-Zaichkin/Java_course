package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends AbstractWindow {
    private final GameVisualizer m_visualizer;

    public GameWindow(Dimension dimension) {
        super("window.game", true, true, true, true);
        setSize(dimension);
        m_visualizer = new GameVisualizer(dimension);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
