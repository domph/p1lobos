package Model;

public class GameObject {

    public double xPosition;
    public double yPosition;
    public double xVelocity;
    public double yVelocity;

    public GameObject(double x, double y, double xV, double yV){
        xPosition = x;
        yPosition = y;
        xVelocity = xV;
        yVelocity = yV;
    }

    public void setPositionTo(double x, double y){
        xPosition = x;
        yPosition = y;
    }

    public void updatePosition(){
        setPositionTo(xPosition + xVelocity, yPosition + yVelocity);
    }
}