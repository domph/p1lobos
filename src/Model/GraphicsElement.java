package Model;

import java.awt.*;

public class GraphicsElement {
	private double PosX;
	private double PosY;
	private double SizeX;
	private double SizeY;
	private Color DisplayColor;
	// Future: custom graphics (go beyond rectangles)

	GraphicsElement(double PosX, double PosY, double SizeX, double SizeY, Color DisplayColor) {
		this.PosX = PosX;
		this.PosY = PosY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.DisplayColor = DisplayColor;
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
}
