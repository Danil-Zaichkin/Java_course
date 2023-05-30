package logic;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameController {
    private Robot robot;
    private final Target target;
    private final int epsilone = 5;

    private final int redFoodBonus = 99;
    private final int blueFoodBonus = 87;
    private final double startRobotPositionY;
    private final double startRobotPositionX;
    private Dimension fieldDimension;
    Map<Integer, Food> redFood = new HashMap<>();
    Map<Integer, Food> blueFood = new HashMap<>();

    private final RobotDispatcher dispatcher = RobotDispatcher.getInstance();

    public GameController(Dimension fieldDimension) {
        this.fieldDimension = fieldDimension;
        startRobotPositionY = 100;
        startRobotPositionX = 100;
        robot = new Robot(startRobotPositionX, startRobotPositionY, fieldDimension, 1400, 1400);
        int startTargetPositionX = 100;
        int startTargetPositionY = 150;
        target = new Target(startTargetPositionX, startTargetPositionY);

        for (int i = 0; i < 5; i++) {
            redFood.put(i + 1, new Food(fieldDimension));
            blueFood.put(i + 1, new Food(fieldDimension));
        }
    }

    public void restartGame() {
        robot = new Robot(startRobotPositionX, startRobotPositionY, fieldDimension, 1400, 1400);
        for (int i = 0; i < 5; i++) {
            redFood.put(i + 1, new Food(fieldDimension));
            blueFood.put(i + 1, new Food(fieldDimension));
        }
    }
    public void onModelUpdateEvent() {
        robot.decrementTTL();
        double robotDistanceToPoint = robot.distanceTo(target.getPositionX(), target.getPositionY());
        if (robotDistanceToPoint < 0.5 || robot.getThirst() <= 0 || robot.getHungry() <=0) {
            return;
        }
        robot.onModelUpdateEvent(target.getPositionX(), target.getPositionY());
        dispatcher.setCoordinates(new Point((int) robot.getPositionX(), (int) robot.getPositionY()));
        checkCoordinates();
        calculateDistanceToTarget();
    }

    private void checkCoordinates() {
        checkValues(redFood.values(), FoodType.RED);
        checkValues(blueFood.values(), FoodType.BLUE);
    }

    private void checkValues(Collection<Food> values, FoodType foodType) {
        for (Food target : values) {
            if (Math.abs(robot.getPositionX() - target.getPositionX()) < epsilone
                    && Math.abs(robot.getPositionY() - target.getPositionY()) < epsilone) {
                target.setPosition();
                switch (foodType) {
                    case RED -> robot.setHungry(robot.getHungry() + redFoodBonus);
                    case BLUE -> robot.setThirst(robot.getThirst() + blueFoodBonus);
                }
            }
        }
    }

    private void calculateDistanceToTarget() {
        double minDistance = Double.POSITIVE_INFINITY;
        for (Food food : redFood.values()) {
            minDistance = Math.min(
                    robot.distanceTo(food.getPositionX(), food.getPositionY()),
                    minDistance);
        }
        for (Food food : blueFood.values()) {
            minDistance = Math.min(
                    robot.distanceTo(food.getPositionX(), food.getPositionY()),
                    minDistance);
        }
        dispatcher.setDistanceToTarget((int) minDistance);
    }

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }

    public Map<Integer, Food> getRedFood() {
        return redFood;
    }

    public Map<Integer, Food> getBlueFood() {
        return blueFood;
    }

}
