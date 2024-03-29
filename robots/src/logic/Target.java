package logic;

import java.awt.Point;

public class Target {
    private volatile int m_positionX;
    private volatile int m_positionY;

    public Target(int positionX, int positionY) {
        m_positionX = positionX;
        m_positionY = positionY;
    }

    public void setPosition(Point p) {
        m_positionX = p.x;
        m_positionY = p.y;
    }

    public int getPositionX() {
        return m_positionX;
    }

    public int getPositionY() {
        return m_positionY;
    }
}
