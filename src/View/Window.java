package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import Network.*;

public class Window extends JFrame {
	public MainMenu MM;
	public GamePanel GP;
	public LoginMenu LM;
	private Timer PhysicsUpdater;
	private double DesiredDelta;

	private int Width;
	private int Height;

	private final int LOGIN_WINDOW_HEIGHT = 205;
	private final int REGISTER_HEIGHT = 45;

	public Window(int Width, int Height, double DesiredDelta) {
		this.DesiredDelta = DesiredDelta;
		this.Width = Width;
		this.Height = Height;

		setTitle("Doodle Jump");	// Official name TBD
		setSize(Width, Height);
		setPreferredSize(new Dimension(Width, Height));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		MM = new MainMenu(this);
		GP = new GamePanel(this);
		LM = new LoginMenu(this);

		// Temporary transparent icon until we get an official icon
		Image TransparentIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		setIconImage(TransparentIcon);

		// Add these before we change the size of the window
		MM.AddButton("PLAY", new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowGameMenu();
			}
		});
		MM.AddButton("LEADERBOARD", new MouseAdapter() {});	// TO-DO

		// LOGIN - change the window size temporarily
		setSize(Width, LOGIN_WINDOW_HEIGHT);
		setPreferredSize(new Dimension(Width, LOGIN_WINDOW_HEIGHT));

		add(LM);
		validate();
		repaint();

		if (!Server.DoesHWIDExist()) {
			LM.AppendText("Could not find a marker to identify your computer. Defaulting to offline mode. Your stats will not save!");
			LM.SetProgressBar(100, true, () -> ShowMainMenu());
		} else {
			// Begin login procedure
			LM.AppendText("Connecting to server...");
			new Thread(() -> {
				boolean Result = Server.GetInit();

				if (!Result) {
					LM.AppendText("ERROR: Unable to connect to the server. Defaulting to offline mode. Your stats will not save!");
					LM.SetProgressBar(100, true, () -> ShowMainMenu());
				} else {
					if (Server.IsExistingUser()) {
						LM.AppendText("Welcome, " + Server.GetName() + ".");
						LM.SetProgressBar(100, true, () -> ShowMainMenu());
					} else {
						LM.AppendText("It looks like your computer is not registered with Doodle Jump! Enter a username (alphanumeric only) to link your computer with the server.");
						LM.SetProgressBar(50, true, () -> new Thread(() -> {
							for (int i = LOGIN_WINDOW_HEIGHT; i <= LOGIN_WINDOW_HEIGHT + REGISTER_HEIGHT; i += 2) {
								setSize(Width, i);
								setPreferredSize(new Dimension(Width, i));
								try {
									Thread.sleep(1);
								} catch (Exception ignored) {}
							}
							setSize(Width, LOGIN_WINDOW_HEIGHT + REGISTER_HEIGHT);
							setPreferredSize(new Dimension(Width, LOGIN_WINDOW_HEIGHT + REGISTER_HEIGHT));
							LM.EnableRegister();
						}).start());
					}
				}
			}).start();
		}
	}

	public void ShowMainMenu() {
		setSize(Width, Height);
		setPreferredSize(new Dimension(Width, Height));

		remove(LM);
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
