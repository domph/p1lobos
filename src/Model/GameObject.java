package Model;

import java.awt.*;

public class GameObject extends GraphicsElement {

    private double VelX;
    private double VelY;

    public GameObject(double PosX, double PosY, double SizeX, double SizeY, Color DisplayColor) {
        super(PosX, PosY, SizeX, SizeY, DisplayColor);
    }

    public void SetVelocity(double VX, double VY) {
        VelX = VX;
        VelY = VY;
    }

    public void UpdatePhysics(double TimeDelta) {
        SetPosition(GetPosX() + (VelX * TimeDelta), GetPosY() + (VelY * TimeDelta));
    }
}