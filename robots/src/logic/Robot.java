package logic;

import java.awt.*;

public class Robot {
    private volatile double m_positionX;
    private volatile double m_positionY;
    private volatile double m_robotDirection = 0;
    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.002;

    private Dimension dimension;

    public Robot(double positionX, double positionY, Dimension dimension) {
        m_positionX = positionX;
        m_positionY = positionY;
        this.dimension = dimension;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration, Dimension dimension) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);

        double newX = m_positionX + velocity * duration * Math.cos(newDirection);
        double newY = m_positionY + velocity * duration * Math.sin(newDirection);

        m_positionX = applyLimits(newX, 0, dimension.width);
        m_positionY = applyLimits(newY, 0, dimension.height);

        m_robotDirection = newDirection;
    }

    public void onModelUpdateEvent(double m_targetPositionX, double m_targetPositionY) {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_positionX, m_positionY);
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_positionX, m_positionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;

        if (Math.abs(m_robotDirection - angleToTarget) < 10e-7) {
            angularVelocity = m_robotDirection;
        } else if (m_robotDirection >= Math.PI) {
            if (m_robotDirection - Math.PI < angleToTarget && angleToTarget < m_robotDirection)
                angularVelocity = -maxAngularVelocity;
            else
                angularVelocity = maxAngularVelocity;
        } else {
            if (m_robotDirection < angleToTarget && angleToTarget < m_robotDirection + Math.PI)
                angularVelocity = maxAngularVelocity;
            else
                angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10, dimension);


    }


    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double getPositionX() {
        return m_positionX;
    }

    public double getPositionY() {
        return m_positionY;
    }

    public double getDirection() {
        return m_robotDirection;
    }
}
