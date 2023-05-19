package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import gui.adapters.LangChangeAdapter;
import gui.adapters.RobotsFrameAdapter;
import localization.LangChangeable;
import localization.LangDispatcher;
import localization.Localization;
import log.Logger;
import serializer.SerializeDispatcher;

public class MainApplicationFrame extends JFrame implements LangChangeable {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final LangDispatcher langDispatcher = LangDispatcher.getInstance();
    private final ResourceBundle bundle = ResourceBundle.getBundle("lang", new Locale("RU"));
    private final SerializeDispatcher serializeDispatcher = SerializeDispatcher.getInstance();
    public MainApplicationFrame() {
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

        setJMenuBar(generateMenuBar());

        addPropertyChangeListener(new LangChangeAdapter(this, langDispatcher));
        addWindowListener(new RobotsFrameAdapter(this, serializeDispatcher, langDispatcher));
    }

    public JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(generateLanguageMenu());
        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateLogMenu());
        menuBar.add(generateExitMenu());
        return menuBar;
    }

    private JMenu generateLanguageMenu() {
        JMenu local = new JMenu(Localization.getString("language"));
        local.setMnemonic(KeyEvent.VK_W);
        local.getAccessibleContext().setAccessibleDescription("Смена языка на доступные");
        JMenuItem setLangEn = new JMenuItem(Localization.getString("language.english"), KeyEvent.VK_N);
        JMenuItem setLangRu = new JMenuItem(Localization.getString("language.russian"), KeyEvent.VK_N);
        setLangEn.addActionListener((event) -> {
            serializeDispatcher.updateBundle(bundle.getBaseBundleName(), "UK");
            langDispatcher.setLocale(new Locale("UK"));
        });
        setLangRu.addActionListener((event) -> {
            serializeDispatcher.updateBundle(bundle.getBaseBundleName(), "RU");
            langDispatcher.setLocale(new Locale("RU"));
        });
        local.add(setLangEn);
        local.add(setLangRu);
        return local;
    }

    private JMenu generateLogMenu() {
        JMenu testMenu = new JMenu(Localization.getString("tests"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        JMenuItem addLogMessageItem = new JMenuItem(Localization.getString("test.message"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(Localization.getString("message.log"));
        });
        testMenu.add(addLogMessageItem);
        return testMenu;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Localization.getString("display"));
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
                Localization.getString(name),
                KeyEvent.VK_S
        );
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(className);
            this.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu(Localization.getString("exit"));
        JMenuItem exitMenuItem = new JMenuItem(Localization.getString("exit"));
        exitMenuItem.addActionListener((event) -> {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        exitMenu.add(exitMenuItem);
        return exitMenu;
    }
    private void updateGUI() {
        this.setJMenuBar(generateMenuBar());
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(Localization.getString("message.work"));
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    @Override
    public void updateLang() {
        updateGUI();
    }
}
