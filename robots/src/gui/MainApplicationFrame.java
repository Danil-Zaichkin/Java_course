package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import localisation.Localisation;
import log.Logger;
import gui.menu.MenuBar;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ArrayList<AbstractWindow> windows  = new ArrayList<>();
    private MenuBar menuBar;

    public MainApplicationFrame() {
//        super("window.main", true, true, true, true);
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        Dimension dimension = new Dimension(400, 400);
        GameWindow gameWindow = new GameWindow(dimension);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        windows.add(logWindow);
        windows.add(gameWindow);

        menuBar = new MenuBar(this, windows);
        setJMenuBar(menuBar.generate());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addClosingEvent();
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(Localisation.getString("message.work"));
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void addClosingEvent() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {Localisation.getString("exit.yes"), Localisation.getString("exit.no")};
                int n = JOptionPane.showOptionDialog(e.getWindow(),
                                Localisation.getString("exit.question"),
                            Localisation.getString("exit.title"),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });
    }
}
