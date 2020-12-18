package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Model.GameObject;
import View.UIUtilities.*;

class GameObjectPanel extends JPanel {
	ArrayList<GameObject> ObjectsToDraw = new ArrayList<>();

	double DeltaTime;	// in seconds

	GameObjectPanel(double Delta) {
		DeltaTime = Delta;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (GameObject GO : ObjectsToDraw) {
			g.setColor(GO.GetColor());
			g.fillRect((int)GO.GetPosX(), (int)GO.GetPosY(), (int)GO.GetSizeX(), (int)GO.GetSizeY());
		}
	}

	public void UpdatePhysics() {
		for (GameObject GO : ObjectsToDraw) {
			System.out.println(GO.toString() + " : " + DeltaTime);
			GO.UpdatePhysics(DeltaTime);
		}
		repaint();
	}

	public void AddObject(GameObject GO) {
		ObjectsToDraw.add(GO);
	}
}

public class Window extends JFrame {
	private JPanel MenuPanel = new JPanel();

	double DesiredDelta = 0.01;
	private GameObjectPanel GamePanel = new GameObjectPanel(DesiredDelta);

	public Window() {
		setTitle("Doodle Jump Prototype");
		setSize(500, 600);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// Transparent icon... to avoid the default icon
		Image TransparentIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		setIconImage(TransparentIcon);

		MenuPanel.setLayout(null);
		MenuPanel.setBackground(new Color(50, 50, 50));
		add(MenuPanel);

		// Buttons
		JButton PlayButton = new ButtonWrapper(MenuPanel, "PLAY", 150, 100, 200, 50, new Color(45, 83, 128), 50, 80, 16).GetButtonObject();
		PlayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MenuPanel.setVisible(false);
				add(GamePanel);
				GamePanel.setVisible(true);

				GameObject GO = new GameObject(120, 410, 40, 40, new Color(194, 98, 98));
				GO.SetVelocity(100, -100);
				GamePanel.AddObject(GO);
			}
		});
		setVisible(true);

		GameObject GO = new GameObject(90, 450, 100, 15, new Color(100, 100, 100));
		GamePanel.AddObject(GO);

		new Timer((int)(DesiredDelta * 1000), e -> GamePanel.UpdatePhysics()).start();
	}
}
