package Model;

import java.awt.*;

public class Player extends GameObject {
	public static final double SIZE_X = 25;
	public static final double SIZE_Y = 25;
	private static final Color DEFAULT_COLOR = new Color(22, 139, 3);

	private static final double INITIAL_VELY = -400;
	private static final double ACCEL_Y = 300;

	public Player(double PosX, double PosY) {
		super(PosX, PosY, SIZE_X, SIZE_Y, DEFAULT_COLOR);
		SetVelocity(0, INITIAL_VELY);
		SetAcceleration(0, ACCEL_Y);
	}
}
