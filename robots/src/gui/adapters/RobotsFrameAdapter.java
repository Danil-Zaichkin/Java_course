package gui.adapters;

import localization.Localization;
import serializer.SerializeDispatcher;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class RobotsFrameAdapter extends WindowAdapter {
    private final JFrame window;
    private final SerializeDispatcher dispatcher;
    public RobotsFrameAdapter(JFrame window, SerializeDispatcher dispatcher) {
        this.dispatcher = dispatcher;
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
            dispatcher.saveState();
            window.setVisible(false);
            System.exit(0);
        } else
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Preferences preferences = Preferences.userNodeForPackage(ResourceBundle.class);
        try {
            preferences.sync();
            String baseName = preferences.get("baseName", "messages");
            String language = preferences.get("language", "ru");
            dispatcher.updateBundle(baseName, language);

            Object[] options = {Localization.getString("exit.yes"), Localization.getString("exit.no")};
            int n = JOptionPane.showOptionDialog(window,
                    Localization.getString("save.question"),
                    Localization.getString("save.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            if (n == 0) {
                dispatcher.restoreState();
            }
        } catch (BackingStoreException ex) {
            // файл с настройками отсутствует или недоступен
        }
    }
}
