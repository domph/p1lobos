package Model;

import java.awt.*;
import java.util.ArrayList;

public class GameObject extends GraphicsElement {

    private double VelX;
    private double VelY;

    private double AccelX;
    private double AccelY;

    public GameObject(double PosX, double PosY, double SizeX, double SizeY, Color DisplayColor) {
        super(PosX, PosY, SizeX, SizeY, DisplayColor);
    }

    public void SetVelocity(double VX, double VY) {
        VelX = VX;
        VelY = VY;
    }

    public void SetAcceleration(double AX, double AY) {
        AccelX = AX;
        AccelY = AY;
    }

    public double GetVelX() { return VelX; }
    public double GetVelY() { return VelY; }
    public double GetAccelX() { return AccelX; }
    public double GetAccelY() { return AccelY; }

    // https://developer.mozilla.org/en-US/docs/Games/Techniques/2D_collision_detection
    public GameObject GetCollidingObject(ArrayList<GameObject> OtherObjects) {
        for (GameObject Other : OtherObjects) {
            if (this != Other &&
                GetPosX() < Other.GetPosX() + Other.GetSizeX() &&
                GetPosX() + GetSizeX() > Other.GetPosX() &&
                GetPosY() < Other.GetPosY() + Other.GetSizeY() &&
                GetPosY() + GetSizeY() > Other.GetPosY()) {
                return Other;
            }
        }
        return null;
    }

    // Calculates new position based on velocity + time elapsed (delta T)
    public void UpdatePhysics(double TimeDelta) {
        SetVelocity(VelX + (AccelX * TimeDelta), VelY + (AccelY * TimeDelta));
        SetPosition(((500 + GetPosX()) % 500) + (VelX * TimeDelta), GetPosY() + (VelY * TimeDelta));
        //needs to scale properly if we decide the project window size scales
    }
}