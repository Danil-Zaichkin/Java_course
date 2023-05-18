package gui;

import log.VarietyTargets;
import logic.Robot;
import logic.Target;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import static gui.DrawFigure.drawOval;
import static gui.DrawFigure.fillOval;

public class GameVisualizer extends JPanel {

    CreatorTimer timer;
    Map<Integer, VarietyTargets> targetViewMap = new HashMap<>();
    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;


    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private Dimension screenSize;

    private Robot robot;
    private Target target;

    public GameVisualizer(Dimension dimension) {
        timer = new CreatorTimer();
        for (int i = 0; i < 8; i++) {
            targetViewMap.put(i + 1, new VarietyTargets());
        }
        robot = new Robot(m_robotPositionX, m_robotPositionY, dimension);
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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                target.setPosition(e.getPoint());
                repaint();
            }
        });
        timer.progressBar.setString("Сытость");
        timer.progressBar.setStringPainted(true);
        this.add(timer.progressBar);

        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void paintTTL(Graphics g) {
        timer.setTTL(robot.getTtl());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robot.getPositionX()), round(robot.getPositionY()), robot.getDirection());
        drawTarget(g2d, target.getPositionX(), target.getPositionY());
        paintTTL(g);
        for (VarietyTargets values:targetViewMap.values()){
            drawTarget(g2d, (int) values.getPositionX(), (int) values.getPositionY());
        }
//        for (int i = 0; i < 8; i++) {
//            if (!varietyIsDead[i]){
//                targetViewMap.get(i + 1).drawTargets(g2d, entityState.getCurrentTargets().get(i + 1));
//            }
//
//        }
    }

//    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
//        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
//    }

//    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
//        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
//    }

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
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
