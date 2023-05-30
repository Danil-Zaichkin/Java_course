package gui;

import localization.Localization;
import logic.Food;
import logic.GameController;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.*;

import javax.swing.JPanel;

import static gui.DrawFigure.drawOval;
import static gui.DrawFigure.fillOval;

public class GameVisualizer extends JPanel {

    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private GameController gameController;

    private double zoomLevel = 1;

    public GameVisualizer(Dimension dimension) {
        gameController = new GameController(dimension);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameController.onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                gameController.getTarget().setPosition(e.getPoint());
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> increaseZoomLevel();
                    case KeyEvent.VK_S -> decreaseZoomLevel();
                }
            }
        });

        setFocusable(true);
        requestFocus();
        setDoubleBuffered(true);
    }

    private void increaseZoomLevel() {
        if (zoomLevel < 2)
            zoomLevel += 0.2;
    }

    private void decreaseZoomLevel() {
        if (zoomLevel > 1)
            zoomLevel -= 0.2;
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void paintTTL(Graphics g) {
        g.drawString(Localization.getString("game.thirst") + ": " +gameController.getRobot().getThirst(),5,10);
        g.drawString(Localization.getString("game.hungry") + ": " + gameController.getRobot().getHungry(), 5, 30);}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        paintTTL(g);
        applyZoomLevel(g2d);
        drawRobot(g2d, round(gameController.getRobot().getPositionX()), round(gameController.getRobot().getPositionX()),
                gameController.getRobot().getDirection());
        drawTarget(g2d, gameController.getTarget().getPositionX(), gameController.getTarget().getPositionY());

        for (Food values : gameController.getRedFood().values()) {
            drawFood(g2d, (int) values.getPositionX(), (int) values.getPositionY(),Color.RED);
        }
        for (Food values: gameController.getBlueFood().values()) {
            drawFood(g2d, (int) values.getPositionX(), (int) values.getPositionY(), Color.BLUE);
        }
    }

    private void applyZoomLevel(Graphics2D graphics) {
        int userPositionX = (int) gameController.getRobot().getPositionX();
        int userPositionY = (int) gameController.getRobot().getPositionY();

        AffineTransform transform = new AffineTransform();

        transform.translate(userPositionX, userPositionY);
        transform.scale(zoomLevel, zoomLevel);
        transform.translate(-userPositionX, -userPositionY);

        graphics.setTransform(transform);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(gameController.getRobot().getPositionX());
        int robotCenterY = round(gameController.getRobot().getPositionY());

        Graphics2D robotGraphics = (Graphics2D) g.create();
        robotGraphics.rotate(direction, robotCenterX, robotCenterY);
        robotGraphics.setColor(Color.MAGENTA);
        fillOval(robotGraphics, robotCenterX, robotCenterY, 30, 10);
        robotGraphics.setColor(Color.BLACK);
        drawOval(robotGraphics, robotCenterX, robotCenterY, 30, 10);
        robotGraphics.setColor(Color.WHITE);
        fillOval(robotGraphics, robotCenterX + 10, robotCenterY, 5, 5);
        robotGraphics.setColor(Color.BLACK);
        drawOval(robotGraphics, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void drawFood(Graphics2D g, int x, int y,Color color) {
        g.setColor(color);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
