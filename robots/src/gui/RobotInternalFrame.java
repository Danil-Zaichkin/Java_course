package gui;

import gui.adapters.LangChangeAdapter;
import gui.adapters.RobotInternalFrameAdapter;
import localization.LangChangeable;
import localization.LangDispatcher;
import localization.Localization;

import javax.swing.*;
public abstract class RobotInternalFrame extends JInternalFrame implements LangChangeable {
    private final String windowNameKey;
    private final LangDispatcher langDispatcher;
    RobotInternalFrame(String windowNameKey, LangDispatcher langDispatcher) {
        super(Localization.getString(windowNameKey), true, true, true, true);
        this.langDispatcher = langDispatcher;
        this.windowNameKey = windowNameKey;
        addInternalFrameListener(new RobotInternalFrameAdapter(this));
        addPropertyChangeListener(new LangChangeAdapter(this, langDispatcher));
    }
    @Override
    public void updateLang() {
        setTitle(Localization.getString(windowNameKey));
    }
}
