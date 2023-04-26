package gui.adapters;

import localization.Localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RobotsFrameAdapter extends WindowAdapter {
    private final JFrame window;
    public RobotsFrameAdapter(JFrame window) {
        this.window = window;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        Object[] options = {Localization.getString("exit.yes"), Localization.getString("exit.no")};
        int n = JOptionPane.showOptionDialog(window,
                Localization.getString("exit.question"),
                Localization.getString("exit.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        if (n == 0) {
            window.setVisible(false);
            System.exit(0);
        } else
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
