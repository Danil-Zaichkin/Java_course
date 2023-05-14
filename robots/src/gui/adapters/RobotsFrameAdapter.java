package gui.adapters;
import gui.RobotFrame;
import gui.state.LocalizationState;
import gui.state.RobotWindowState;
import gui.state.SaveState;
import localization.Localization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class RobotsFrameAdapter extends WindowAdapter implements ConfirmWindow {
    private final RobotFrame window;
    Path jsonPath;
    public RobotsFrameAdapter(RobotFrame window, Path jsonPath) {
        this.jsonPath = jsonPath;
        this.window = window;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        int userAnswer = askUser(
                window,
                Localization.getString("exit.question"),
                Localization.getString("exit.title")
        );
        if (userAnswer == 0) {
            window.setVisible(false);
            window.dispose();
        } else
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
            int userAnswer = askUser(
                    window,
                    Localization.getString("save.state"),
                    Localization.getString("save.title")
            );
            if (userAnswer == JOptionPane.YES_OPTION) {
                window.setExtendedState(Frame.MAXIMIZED_BOTH);
                window.changeWindowState(windowState);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
