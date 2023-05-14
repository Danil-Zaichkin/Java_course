package gui.adapters;

import gui.RobotInternalFrame;
import gui.state.RobotWindowState;
import gui.state.SaveState;
import localization.Localization;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class RobotInternalFrameAdapter extends InternalFrameAdapter implements ConfirmWindow {
    RobotInternalFrame window;
    Path jsonPath;

    public RobotInternalFrameAdapter(RobotInternalFrame window, Path jsonPath) {
        this.jsonPath = jsonPath;
        this.window = window;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        int userAnswer = askUserInInternalWindow(
                window,
                Localization.getString("exit.question"),
                Localization.getString("exit.title")
        );
        if (userAnswer == JOptionPane.YES_OPTION) {
            window.setVisible(false);
            window.dispose();
        } else
            window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        SaveState<RobotWindowState> saveState = new SaveState<>();
        saveState.saveT(jsonPath, window.getWindowState());
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        SaveState<RobotWindowState> saveState = new SaveState<>();
        try {
            RobotWindowState windowState = saveState.recoverT(jsonPath, RobotWindowState.class);
            int userAnswer = askUserInInternalWindow(
                    window,
                    Localization.getString("save.state"),
                    Localization.getString("save.title")
            );
            if (userAnswer == JOptionPane.YES_OPTION) {
                window.changeWindowState(windowState);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
