package Model;

import java.awt.*;

public class PowerUp extends GameObject{

    public int powerUpType; // something like 1 = propeller, 2 = shield, 3 = jetpack

    public PowerUp(Color c, double x, double y, double xsize, double ysize, int type){
        super(c, x, y, xsize, ysize);
        powerUpType = type;
    }
}