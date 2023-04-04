package gui;

import localisation.Localisation;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractWindow extends JInternalFrame {
    private final String windowNameKey;
    AbstractWindow(String windowNameKey, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(Localisation.getString(windowNameKey), resizable, closable, maximizable, iconifiable);
        this.windowNameKey = windowNameKey;
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showInternalConfirmDialog(
                        AbstractWindow.this,
                        Localisation.getString("exit.question"),
                        Localisation.getString("exit.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
                else
                    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    public void updateLocalisation() {
        setTitle(Localisation.getString(windowNameKey));
    }
}
