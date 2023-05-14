package gui.adapters;

import localization.Localization;

import javax.swing.*;
import java.awt.*;

public interface ConfirmWindow {
    default int askUser(Component parent, String message, String title) {
        Object[] options = {Localization.getString("answer.yes"), Localization.getString("answer.no")};
        System.out.println(parent);
        return JOptionPane.showOptionDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    default int askUserInInternalWindow(Component parent, String message, String title) {
        return JOptionPane.showInternalConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    }
}
