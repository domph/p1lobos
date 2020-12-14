package Model;

public class Player extends GameObject {

    public final double GRAV_CONST = -1; //player bound by gravity which affects his yVelocity
    public final double JUMP_VELOCITY = 5;

    public Player(double x, double y, double xV, double yV){
        super(x, y, xV, yV);
    }

    public void accelerate(){
        yVelocity += GRAV_CONST;
    }

    public void jump(){
        yVelocity = JUMP_VELOCITY;
    }
}