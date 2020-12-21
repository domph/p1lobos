package View;

import Model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
	private Color BACKGROUND_COLOR = new Color(50, 50, 50);

	private ArrayList<GameObject> ObjectsToDraw = new ArrayList<>();
	private Window CurrentWindow;

	public GamePanel(Window CurrentWindow) {
		// Basic design -- can change later
		setLayout(null);
		setBackground(BACKGROUND_COLOR);
		setVisible(true);
		this.CurrentWindow = CurrentWindow;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (GameObject GO : ObjectsToDraw) {
			// Depending on whether an image is set or not, draw a rectangle or draw the image
			if (GO.GetImage() != null) {
				g.drawImage(GO.GetImage(), (int)GO.GetPosX(), (int)GO.GetPosY(), null);
			} else {
				g.setColor(GO.GetColor());
				g.fillRect((int)GO.GetPosX(), (int)GO.GetPosY(), (int)GO.GetSizeX(), (int)GO.GetSizeY());
			}
		}
	}

	// Should be called every frame (every 1/60 second or so...)
	public void UpdatePhysics(double DeltaTime) {	// DeltaTime = how much time has passed since the last call
		for (GameObject GO : ObjectsToDraw) {
			GO.UpdatePhysics(DeltaTime);
		}
		repaint();
	}

	// Add object to the list of objects rendered
	public void AddObject(GameObject GO) {
		ObjectsToDraw.add(GO);
	}
}
