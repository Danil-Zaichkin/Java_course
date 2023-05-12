package gui.adapters;
import gui.RobotFrame;
import gui.state.LocalizationState;
import gui.state.RobotWindowState;
import gui.state.SaveState;
import localization.Localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class RobotsFrameAdapter extends WindowAdapter {
    private final RobotFrame window;
    Path jsonPath;
    public RobotsFrameAdapter(RobotFrame window, Path jsonPath) {
        this.jsonPath = jsonPath;
        this.window = window;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        Object[] options = {Localization.getString("answer.yes"), Localization.getString("answer.no")};
        int n = JOptionPane.showOptionDialog(window,
                Localization.getString("exit.question"),
                Localization.getString("exit.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        if (n == 0) {
            window.setVisible(false);
            window.dispose();
        }
        else
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        SaveState<RobotWindowState> saveState = new SaveState<>();
        saveState.saveT(jsonPath, window.getWindowState());;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        SaveState<RobotWindowState> saveState = new SaveState<>();
        try {
            RobotWindowState windowState = saveState.recoverT(jsonPath, RobotWindowState.class);
            Object[] options = {Localization.getString("answer.yes"), Localization.getString("answer.no")};
            int n = JOptionPane.showOptionDialog(window,
                    Localization.getString("save.state"),
                    Localization.getString("save.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            if (n == 0) {
                window.changeWindowState(windowState);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
