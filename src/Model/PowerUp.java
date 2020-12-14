package Model;

public class PowerUp extends GameObject{

    public int powerUpType; // something like 1 = propeller, 2 = shield, 3 = jetpack

    public PowerUp(double x, double y, double xV, double yV, int type){
        super(x, y, xV, yV);
        powerUpType = type;
    }
}