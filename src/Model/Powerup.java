package Model;

import java.awt.*;

public class Powerup extends GameObject {
    public static final double SIZE_X = 25;
    public static final double SIZE_Y = 25;
    private static final Color DEFAULT_COLOR = new Color(0, 255, 0);

    private double BounceVelY = -700;
    int random = (int)(Math.random*100);
    if (random%2 == 0){
        DEFAULT_COLOR = new Color(0,255,0);
        BounceVelY = -400;

    }
    else if (random%3 == 0){
        DEFAULT_COLOR = new Color(0,255,255);
        BounceVelY = -500;
    }
    else if (random%5 == 0){
        DEFAULT_COLOR = new Color(255,255,0);
        BounceVelY = -600;
    }
    else if (random%7 == 0){
        DEFAULT_COLOR = new Color(255,0,255);
        BounceVelY = -700;
    }
    else if (random%11 == 0){
        DEFAULT_COLOR = new Color(0,155,255);
        BounceVelY = -700;
    }

    public void SetBounceVelY(double VelY) { BounceVelY = VelY; }
    public double GetBounceVelY() { return BounceVelY; }

    public Powerup(double PosX, double PosY) {
        super(PosX, PosY, SIZE_X, SIZE_Y, DEFAULT_COLOR);
    }
}
