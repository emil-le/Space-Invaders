package spaceInvaders;

import comp127graphics.Rectangle;
import java.awt.*;

public class Bullet extends Rectangle {
    public Bullet(double x, double y, Color color) {
        super(x, y, 2, 5);
        super.setFilled(true);
        this.setFillColor(color);
        this.setStrokeColor(color);
    }

    public void updatePosition(int range){
        moveBy(0, range);
    }
}
