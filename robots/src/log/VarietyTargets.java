package log;

import java.awt.*;
import java.util.Random;
public class VarietyTargets {
    Random random;
    private volatile double x;
    private volatile double y;
    public Dimension dimension;
    public VarietyTargets(Dimension dimension) {
        random = new Random();
        this.dimension = dimension;
        this.x = random.nextInt(30, (int) dimension.getWidth());
        this.y = random.nextInt(30, (int) dimension.getHeight());
    }
    public void setPosition(){
        this.x = random.nextInt(30, (int) dimension.getWidth());
        this.y = random.nextInt(30, (int) dimension.getHeight());
    }
    public double getPositionX() {
        return x;
    }

    public double getPositionY() {
        return y;
    }
}
