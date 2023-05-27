package gui;

import log.VarietyTargets;
import logic.Robot;
import logic.Target;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.function.Consumer;

import javax.swing.JPanel;

import static gui.DrawFigure.drawOval;
import static gui.DrawFigure.fillOval;

public class GameVisualizer extends JPanel {

//    CreatorTimer timer;
    Map<Integer, VarietyTargets> targetViewMap = new HashMap<>();
    Map<Integer, VarietyTargets> targetsMap = new HashMap<>();
    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private final int epsilone = 5;

    private final int dopTime = 100;
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;


    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private Dimension screenSize;

    private Robot robot;
    private Target target;

    public GameVisualizer(Dimension dimension) {
//        timer = new CreatorTimer();
        for (int i = 0; i < 5; i++) {
            targetViewMap.put(i + 1, new VarietyTargets(dimension));
            targetsMap.put(i + 1,new VarietyTargets(dimension));
        }
        robot = new Robot(m_robotPositionX, m_robotPositionY, dimension,1400,1400);
        target = new Target(m_targetPositionX, m_targetPositionY);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.onModelUpdateEvent(target.getPositionX(), target.getPositionY());
            }
        }, 0, 10);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                target.setPosition(e.getPoint());
            }
        });
//        timer.progressBar.setString("Сытость");
//        timer.progressBar.setStringPainted(true);
//        this.add(timer.progressBar);

        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void paintTTL(Graphics g) {
//        timer.setTTL(robot.getTtl());
        g.drawString("Thirst: " + robot.getThirst() / 100,5,10);
        g.drawString("Hungry: " + robot.getHungry() / 100, 5, 30);}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robot.getPositionX()), round(robot.getPositionY()), robot.getDirection());
        drawTarget(g2d, target.getPositionX(), target.getPositionY());
        paintTTL(g);
        for (VarietyTargets values : targetViewMap.values()) {
            drawFood(g2d, (int) values.getPositionX(), (int) values.getPositionY(),Color.RED);
        }
        for (VarietyTargets values: targetsMap.values()){
            drawFood(g2d, (int) values.getPositionX(), (int) values.getPositionY(),Color.BLUE);
        }
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(robot.getPositionX());
        int robotCenterY = round(robot.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        checkCoordinates();
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void drawFood(Graphics2D g, int x, int y,Color color) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(color);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void checkCoordinates() {
        checkValues(targetViewMap.values(), VarietyTargets::setPosition);
        checkValues(targetsMap.values(), VarietyTargets::setPosition);
    }

    private void checkValues(Collection<VarietyTargets> values, Consumer<VarietyTargets> action) {
        for (VarietyTargets target : values) {
            if (Math.abs(robot.getPositionX() - target.getPositionX()) < epsilone
                    && Math.abs(robot.getPositionY() - target.getPositionY()) <epsilone) {
                action.accept(target);
                robot.setThirst(robot.getThirst() + dopTime);
            }
        }
    }
}
