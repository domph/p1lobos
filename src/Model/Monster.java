package Model;

import java.awt.*;

public class Monster extends GameObject{
    public static final double SIZE_X = 75;
    public static final double SIZE_Y = 75;
    private static final Color DEFAULT_COLOR = new Color(3, 123, 139);

    private double BounceVelY = -350;

    public void SetBounceVelY(double VelY) { BounceVelY = VelY; }
    public double GetBounceVelY() { return BounceVelY; }

    public Monster(double PosX, double PosY) {
        super(PosX, PosY, SIZE_X, SIZE_Y, DEFAULT_COLOR);

    }
}
