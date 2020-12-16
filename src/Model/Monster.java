package Model;

import java.awt.*;

public class Monster extends GameObject{

    public int monsterType;
    public double radius;

    public Monster(Color c, double x, double y, double xsize, double ysize, int type, double r){
        super(c, x, y, xsize, ysize);
        monsterType = type;
        radius = r;
    }
}