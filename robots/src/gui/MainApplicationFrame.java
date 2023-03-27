package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final GameWindow gameWindow = new GameWindow();
    private final LogWindow logWindow;
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        logWindow = createLogWindow();
        addWindow(logWindow);

        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu local = new JMenu("Язык");
        local.setMnemonic(KeyEvent.VK_W);
        local.getAccessibleContext().setAccessibleDescription("Смена языка на доступные");
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        Listener(lookAndFeelMenu);
        addAction(lookAndFeelMenu);
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        addLanguage(local,lookAndFeelMenu,testMenu);
        addLog(testMenu);
        menuBar.add(local);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }
    // добавлен метод для создания кнопок смены языка.

    /**
     *
     * @param local - меню языка
     * @param look - меню для отображения
     * @param testMenu - меню для тестов
     */
    private void addLanguage(JMenu local,JMenu look,JMenu testMenu){
        JMenuItem setLang = new JMenuItem("Английский язык",KeyEvent.VK_N);
        JMenuItem setLangRu = new JMenuItem("Русский язык",KeyEvent.VK_N);
        setLang.addActionListener((event) -> {Locale curr = new Locale("UK");
            changeToLan(look,curr,testMenu,local);this.invalidate();});
        local.add(setLang);
        setLangRu.addActionListener((event) -> {Locale curr = new Locale("RU");
            changeToLan(look,curr,testMenu,local);this.invalidate();});
        local.add(setLangRu);
    }
    // метод меняющий язык для каждого слова

    /**
     *
     * @param lookAndFeelMenu - меню отображения
     * @param curr - параметр языка
     * @param testMenu - меню теста
     * @param local - меню языка
     */
    private void changeToLan(JMenu lookAndFeelMenu ,Locale curr,JMenu testMenu,JMenu local){
        ResourceBundle rb = ResourceBundle.getBundle("lang",curr);

//        Iterator<String> list = rb.getKeys().asIterator();
//        while (list.hasNext()) {
//            System.out.println(list.next());
//        }
        logWindow.getMLogContent().setText(rb.getString("work"));
        logWindow.setTitle(rb.getString("protocol"));
        gameWindow.setTitle(rb.getString("window"));
        local.setText(rb.getString("language"));
        local.getItem(0).setText(rb.getString("english"));
        local.getItem(1).setText(rb.getString("russian"));
        lookAndFeelMenu.setText(rb.getString("display"));
        lookAndFeelMenu.getItem(0).setText(rb.getString("system"));
        lookAndFeelMenu.getItem(1).setText(rb.getString("universal"));
        testMenu.setText(rb.getString("test"));
        testMenu.getItem(0).setText(rb.getString("message"));



    }
    private void addAction(JMenu lookAndFeelMenu){
        JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);
    }
    private void Listener(JMenu lookAndFeelMenu) {
        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);
    }

    private void addLog(JMenu testMenu) {
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
