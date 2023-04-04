package gui.menu;

import gui.AbstractWindow;
import localisation.Localisation;
import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Locale;

public class MenuBar {

    private final JFrame mainFrame;
    private final ArrayList<AbstractWindow> windows;


    public MenuBar(JFrame mainFrame, ArrayList<AbstractWindow> windows) {
        this.mainFrame = mainFrame;
        this.windows = windows;
    }

    public JMenuBar generate() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(generateLanguageMenu());
        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateLogMenu());
        menuBar.add(generateExitMenu());
        return menuBar;
    }

    private JMenu generateLanguageMenu() {
        JMenu local = new JMenu(Localisation.getString("language"));
        local.setMnemonic(KeyEvent.VK_W);
        local.getAccessibleContext().setAccessibleDescription("Смена языка на доступные");
        JMenuItem setLangEn = new JMenuItem(Localisation.getString("language.english"), KeyEvent.VK_N);
        JMenuItem setLangRu = new JMenuItem(Localisation.getString("language.russian"), KeyEvent.VK_N);
        setLangEn.addActionListener((event) -> {
            Localisation.changeLocalisation(new Locale("UK"));
            updateGUI();
        });
        setLangRu.addActionListener((event) -> {
            Localisation.changeLocalisation(new Locale("RU"));
            updateGUI();
        });
        local.add(setLangEn);
        local.add(setLangRu);
        return local;
    }

    private JMenu generateLogMenu() {
        JMenu testMenu = new JMenu(Localisation.getString("tests"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        JMenuItem addLogMessageItem = new JMenuItem(Localisation.getString("test.message"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(Localisation.getString("message.log"));
        });
        testMenu.add(addLogMessageItem);
        return testMenu;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Localisation.getString("display"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");
        addLookAndFeelItem(lookAndFeelMenu, "display.universal",
                            UIManager.getCrossPlatformLookAndFeelClassName());
        addLookAndFeelItem(lookAndFeelMenu, "display.system",
                            UIManager.getSystemLookAndFeelClassName());

        return lookAndFeelMenu;

    }
    private void addLookAndFeelItem(JMenu lookAndFeelMenu, String name, String className) {
        JMenuItem crossplatformLookAndFeel = new JMenuItem(
                Localisation.getString(name),
                KeyEvent.VK_S
        );
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(className);
            mainFrame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(mainFrame);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu(Localisation.getString("exit"));
        JMenuItem exitMenuItem = new JMenuItem(Localisation.getString("exit"));
        exitMenuItem.addActionListener((event) -> {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        });
        exitMenu.add(exitMenuItem);
        return exitMenu;
    }
    private void updateGUI() {
        mainFrame.setJMenuBar(generate());
        for (AbstractWindow window: windows) {
            window.updateLocalisation();
        }
    }
}
