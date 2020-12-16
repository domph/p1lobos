package Model;

import java.awt.*;

public class Player extends GameObject {

    public final double GRAV_CONST = -1; //player bound by gravity which affects his yVelocity
    public final double JUMP_VELOCITY = 5;

    public Player(Color c, double x, double y, double xsize, double ysize){
        super(c, x, y, xsize, ysize);
    }

    public void accelerate(){
        yVelocity += GRAV_CONST;
    }

    public void jump(){
        yVelocity = JUMP_VELOCITY;
    }
}