package log;

import java.util.Random;
public class VarietyTargets {
    Random random;
    private volatile double x;
    private volatile double y;
    public VarietyTargets() {
        random = new Random();
        this.x = random.nextInt(30, 350);
        this.y = random.nextInt(30, 350);
    }
    public double getPositionX() {
        return x;
    }

    public double getPositionY() {
        return y;
    }
}
