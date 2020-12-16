package Model;

import java.awt.*;

public class GameObject {

    public double xPosition;
    public double yPosition;
    public double xSize;
    public double ySize;
    public double xVelocity;
    public double yVelocity;

    public Color InternalColor;

    public GameObject(Color C, double xPos, double yPos, double xSz, double ySz) {
        InternalColor = C;
        xPosition = xPos;
        yPosition = yPos;
        xSize = xSz;
        ySize = ySz;
    }
    public void setPosition(double x, double y){
        xPosition = x;
        yPosition = y;
    }

    public void SetVelocity(double vx, double vy) {
        xVelocity = vx;
        yVelocity = vy;
    }
}