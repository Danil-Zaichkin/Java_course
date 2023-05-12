package gui;

import gui.adapters.LangChangeAdapter;
import gui.adapters.RobotInternalFrameAdapter;
import gui.state.RobotWindowState;
import gui.state.StateChangeable;
import localization.LangChangeable;
import localization.LangDispatcher;
import localization.Localization;

import javax.swing.*;
import java.nio.file.Path;

public abstract class RobotInternalFrame extends JInternalFrame implements LangChangeable, StateChangeable {
    private final String windowNameKey;
    private final LangDispatcher langDispatcher;
    RobotInternalFrame(String windowNameKey, LangDispatcher langDispatcher) {
        super(Localization.getString(windowNameKey), true, true, true, true);
        this.langDispatcher = langDispatcher;
        this.windowNameKey = windowNameKey;
        addInternalFrameListener(new RobotInternalFrameAdapter(this, Path.of(windowNameKey + ".json")));
        addPropertyChangeListener(new LangChangeAdapter(this, langDispatcher));
    }
    @Override
    public void updateLang() {
        setTitle(Localization.getString(windowNameKey));
    }
    @Override
    public RobotWindowState getWindowState() {
        return new RobotWindowState(this.getX(), this.getY(), this.getHeight(), this.getWidth(), windowNameKey);
    }
    @Override
    public void changeWindowState(RobotWindowState windowState) {
        this.setSize(windowState.width(), windowState.height());
        this.setLocation(windowState.x(), windowState.y());
    }
}
