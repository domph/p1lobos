package Model;

import java.awt.*;

public class Platform extends GameObject {
	public static final double SIZE_X = 100;
	public static final double SIZE_Y = 25;
	private static final Color DEFAULT_COLOR = new Color(95, 95, 95);

	private double BounceVelY = -350;

	public void SetBounceVelY(double VelY) { BounceVelY = VelY; }
	public double GetBounceVelY() { return BounceVelY; }

	public Platform(double PosX, double PosY) {
		super(PosX, PosY, SIZE_X, SIZE_Y, DEFAULT_COLOR);
	}
}
