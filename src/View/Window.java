package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Window extends JFrame {
	public MainMenu MM = new MainMenu(this);
	public GamePanel GP = new GamePanel(this);
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

		validate(); // validate & repaint need to be called after calling add/remove
		repaint();
		if (PhysicsUpdater != null) {
			PhysicsUpdater.stop();
		}
	}

	public void ShowGameMenu() {
		remove(MM);
		add(GP);

		validate();
		repaint();

		// Update physics every `DesiredDelta` seconds (set by gamecontrol)
		GP.StartGame();
		PhysicsUpdater = new Timer((int)(DesiredDelta * 1000), e -> GP.UpdatePhysics(DesiredDelta));
		PhysicsUpdater.start();
	}
}
