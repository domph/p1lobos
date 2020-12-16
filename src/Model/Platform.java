package Model;

import java.awt.*;

public class Platform extends GameObject{

    public final double RADIUS = 2.0;
    public int platformType; //0=normal, etc.

    public Platform(Color c, double x, double y, double xV, double yV, int type){
        super(c,x,y,xV,yV);
        platformType = type;
    }
}