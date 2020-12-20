package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Window extends JFrame {
	public MainMenu MM = new MainMenu(this);	// default invisible
	public GamePanel GP = new GamePanel(this); // also invisible
	private Timer PhysicsUpdater;
	private double DesiredDelta;

	public Window(int Width, int Height, double DesiredDelta) {
		this.DesiredDelta = DesiredDelta;

		setTitle("Doodle Jump Prototype");	// Official name TBD
		setSize(Width, Height);
		setPreferredSize(new Dimension(Width, Height));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		// Temporary transparent icon until we get an official icon
		Image TransparentIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		setIconImage(TransparentIcon);

		MM.AddButton("PLAY", new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowGameMenu();
			}
		});
		MM.AddButton("LEADERBOARD", new MouseAdapter() {});	// TO-DO

		// Default: show main menu
		ShowMainMenu();
	}

	public void ShowMainMenu() {
		remove(GP);
		add(MM);
		repaint();
		if (PhysicsUpdater != null) {
			PhysicsUpdater.stop();
		}
	}

	public void ShowGameMenu() {
		remove(MM);
		add(GP);
		repaint();
		PhysicsUpdater = new Timer((int)(DesiredDelta * 1000), e -> GP.UpdatePhysics(DesiredDelta));
		PhysicsUpdater.start();
	}
}
