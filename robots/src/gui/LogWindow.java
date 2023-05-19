package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.*;

import localization.LangDispatcher;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import serializer.SerializeDispatcher;

public class LogWindow extends RobotInternalFrame implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super("window.protocol", LangDispatcher.getInstance(), SerializeDispatcher.getInstance());
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    // возвращает содержимое текста класса LogWindow
    public TextArea getMLogContent(){
        return m_logContent;
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void doDefaultCloseAction() {
        if (m_logSource != null)
            m_logSource.unregisterListener(this);
        super.doDefaultCloseAction();
    }
}
