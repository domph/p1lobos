package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import Model.GameObject;

// Specifically designed for a JButton; takes a target color and time and interpolates the current color of the Button to the desired color in the specified time
class TweenColor implements Runnable {
	private final Thread InternalThread;
	private final float DesiredTime;
	private final Color StartColor;
	private final Color DesiredColor;
	private final JButton Object;

	// Static: used to keep track of multiple threads that are running on the same object so that color tweening does not conflict (smooth!)
	private static final HashMap<JButton, Thread> Threads = new HashMap<>();

	public TweenColor(JButton Object, Color DesiredColor, long DesiredTime) {
		this.Object = Object;
		this.DesiredTime = DesiredTime;
		this.DesiredColor = DesiredColor;
		StartColor = Object.getBackground();
		InternalThread = new Thread(this);

		// Write the new thread
		Threads.put(Object, InternalThread);
		InternalThread.start();
	}

	private Color Interpolate(double Fraction) {
		// Formula: (end - start) * step + start; where step ranges from 0 to 1.0
		double R = (DesiredColor.getRed() - StartColor.getRed()) * Fraction + StartColor.getRed();
		double G = (DesiredColor.getGreen() - StartColor.getGreen()) * Fraction + StartColor.getGreen();
		double B = (DesiredColor.getBlue() - StartColor.getBlue()) * Fraction + StartColor.getBlue();
		return new Color((int)R, (int)G, (int)B);
	}

	public void run() {
		// The number of steps is always the same: 100; how long we wait depends on the desired time specified in the constructor
		for (double Step = 0; Step <= 1; Step += 0.01) {
			Object.setBackground(Interpolate(Step));
			try {
				// Step time: x * 100 = DesiredTime
				Thread.sleep((long)(DesiredTime / 100));
			} catch (Exception ignored) {}

			// Old threads end here if the new one takes charge
			if (Threads.get(Object) != InternalThread) {
				break;
			}
		}

	}
}

// Responsible for creating a button that is sized and positioned to fit the UI correctly
class ButtonWrapper {
	// Keeps track of how many buttons we use (since we position a button by its #)
	private final JButton Button;
	private boolean IsHovering = false;

	// HoverDelta: Change of r, g, b components when button is hovered; ClickDelta: same but when button is clicked
	ButtonWrapper(JPanel Parent, String Text, int PosX, int PosY, int SizeX, int SizeY, Color DefaultColor, int HoverDelta, int ClickDelta, int FontSize) {
		Button = new JButton();

		// 10 = padding on the x, 120 = padding on the y; 2 pixel padding between each button
		Button.setBounds(PosX, PosY, SizeX, SizeY);
		Button.setText(Text);
		Button.setForeground(Color.WHITE);
		Button.setBackground(DefaultColor);
		Button.setFont(new Font("Segoe UI", Font.PLAIN, FontSize));
		Button.setHorizontalTextPosition(SwingConstants.CENTER);
		Button.setVerticalTextPosition(SwingConstants.CENTER);
		Button.setBorderPainted(false);
		Button.setFocusPainted(false);

		int R = DefaultColor.getRed(), G = DefaultColor.getGreen(), B = DefaultColor.getBlue();
		Color HoverColor = new Color(R + HoverDelta, G + HoverDelta, B + HoverDelta);
		Color ClickColor = new Color(R + ClickDelta, G + ClickDelta, B + ClickDelta);

		// Responsible for tweening a button's color upon hover and click; we'll use 100ms
		Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new TweenColor(Button, ClickColor, 100);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (IsHovering) {
					new TweenColor(Button, HoverColor, 100);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				IsHovering = true;
				new TweenColor(Button, HoverColor, 100);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				IsHovering = false;
				new TweenColor(Button, DefaultColor, 100);
			}
		});

		Parent.add(Button);
	}

	public JButton GetButtonObject() {
		return Button;
	}	// so the main function can edit the button if desired
}

class GameObjectPanel extends JPanel {
	ArrayList<GameObject> ObjectsToDraw = new ArrayList<>();
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (GameObject GO : ObjectsToDraw) {
			g.setColor(GO.InternalColor);
			g.fillRect((int)GO.xPosition, (int)GO.yPosition, (int)GO.xSize, (int)GO.ySize);
		}
	}

	public void AddObject(GameObject GO) {
		ObjectsToDraw.add(GO);
	}
}

public class Window extends JFrame {
	private JPanel MenuPanel = new JPanel();
	private GameObjectPanel GamePanel = new GameObjectPanel();

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

				GameObject GO = new GameObject(new Color(23, 23, 23), 100, 100, 100, 100);
				GamePanel.AddObject(GO);
			}
		});
		setVisible(true);

		GameObject GO = new GameObject(new Color(100, 100, 100), 20, 20, 20, 20);
		GamePanel.AddObject(GO);
	}
}
