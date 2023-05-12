package gui.state;

public interface StateChangeable {
    RobotWindowState getWindowState();
    void changeWindowState(RobotWindowState windowState);
}
