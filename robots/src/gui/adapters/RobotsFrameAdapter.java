package gui.adapters;
import localization.Localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RobotsFrameAdapter extends WindowAdapter {
    SaveStateAdapter adapter;
    private final JFrame window;
    private final JInternalFrame frame,logFrame;
    public RobotsFrameAdapter(JFrame window ,JInternalFrame frame,JInternalFrame logFrame) {
        this.window = window;
        this.frame = frame;
        this.logFrame = logFrame;
        adapter = new SaveStateAdapter(frame,logFrame);
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
//            SaveStateAdapter adapter = new SaveStateAdapter(frame);
            adapter.makeSave();
            window.setVisible(false);
            System.exit(0);
        } else
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    @Override
    public void windowOpened(WindowEvent e) {
        if (adapter.checkFiles()) {
            Object[] options = {Localization.getString("answer.yes"), Localization.getString("answer.no")};
            int n = JOptionPane.showOptionDialog(window,
                    Localization.getString("save.state"),
                    Localization.getString("save.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            if (n == 0) {
                adapter.recoverSave();
            } else
                window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
