package gui;

import gui.adapters.RobotsFrameAdapter;
import gui.state.RobotWindowState;
import gui.state.StateChangeable;

import javax.swing.*;
import java.nio.file.Path;

public abstract class RobotFrame extends JFrame implements StateChangeable {
    public RobotFrame() {
        super();

    }

}
