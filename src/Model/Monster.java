package Model;

public class Monster extends GameObject{

    public int monsterType;
    public double radius;

    public Monster(double x, double y, double xV, double yV, int type, double r){
        super(x, y, xV, yV);
        monsterType = type;
        radius = r;
    }
}