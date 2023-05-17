package gui;

import gui.adapters.LangChangeAdapter;
import gui.adapters.RobotInternalFrameAdapter;
import gui.adapters.SerializeAdapter;
import localization.LangChangeable;
import localization.LangDispatcher;
import localization.Localization;
import serializer.SerializeDispatcher;

import javax.swing.*;
public abstract class RobotInternalFrame extends JInternalFrame implements LangChangeable {
    private final String windowNameKey;
    private final LangDispatcher langDispatcher;
    private final SerializeDispatcher serializeDispatcher;

    RobotInternalFrame(String windowNameKey, LangDispatcher langDispatcher, SerializeDispatcher serializeDispatcher) {
        super(Localization.getString(windowNameKey), true, true, true, true);
        this.langDispatcher = langDispatcher;
        this.windowNameKey = windowNameKey;
        this.serializeDispatcher = serializeDispatcher;
        addInternalFrameListener(new RobotInternalFrameAdapter(this));
        addPropertyChangeListener(new LangChangeAdapter(this, langDispatcher));
        addPropertyChangeListener(new SerializeAdapter(this, serializeDispatcher));
    }
    @Override
    public void updateLang() {
        setTitle(Localization.getString(windowNameKey));
    }

    public String getWindowNameKey() {
        return windowNameKey;
    }
}
