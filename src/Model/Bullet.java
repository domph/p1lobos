package Model;

import java.awt.*;

public class Bullet extends GameObject {
    public static final double SIZE_X = 10;
    public static final double SIZE_Y = 10;
    public static final double BULLET_SPEED = 750;
    private static final Color DEFAULT_COLOR = new Color(100, 0, 0);

    public Bullet(double PosX, double PosY) {
        super(PosX, PosY, SIZE_X, SIZE_Y, DEFAULT_COLOR);
    }
}
