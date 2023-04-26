package gui.adapters;

import gui.RobotInternalFrame;
import localization.Localization;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class RobotInternalFrameAdapter extends InternalFrameAdapter {
    RobotInternalFrame window;

    public RobotInternalFrameAdapter(RobotInternalFrame window) {
        this.window = window;
    }
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        int option = JOptionPane.showInternalConfirmDialog(
                window,
                Localization.getString("exit.question"),
                Localization.getString("exit.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION)
            window.dispose();
        else
            window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }


}
