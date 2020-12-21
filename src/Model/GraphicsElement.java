package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsElement {
	private double PosX;
	private double PosY;
	private double SizeX;
	private double SizeY;
	private Color DisplayColor;
	private BufferedImage DisplayImage;	// if this is NOT null, then it will be drawn (see GamePanel)

	GraphicsElement(double PosX, double PosY, double SizeX, double SizeY, Color DisplayColor) {
		this.PosX = PosX;
		this.PosY = PosY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.DisplayColor = DisplayColor;
	}

	GraphicsElement(double PosX, double PosY, double SizeX, double SizeY, BufferedImage DisplayImage) {
		this.PosX = PosX;
		this.PosY = PosY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.DisplayImage = DisplayImage;
	}

	public void SetPosition(double PosX, double PosY) {
		this.PosX = PosX;
		this.PosY = PosY;
	}

	public void SetSize(double SizeX, double SizeY) {
		this.SizeX = SizeX;
		this.SizeY = SizeY;
	}

	public void SetColor(Color NewColor) {
		this.DisplayColor = NewColor;
	}

	public double GetPosX() {
		return PosX;
	}

	public double GetPosY() {
		return PosY;
	}

	public double GetSizeX() {
		return SizeX;
	}

	public double GetSizeY() {
		return SizeY;
	}

	public Color GetColor() {
		return DisplayColor;
	}

	public BufferedImage GetImage() { return DisplayImage; }
}
