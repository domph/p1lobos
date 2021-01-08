package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
	/* GAME CONSTANTS */

	private static final int PLATFORM_GEN_MIN_PADDING = 5;	// when creating a platform; minimum space b/w platforms
	private static final int PLATFORM_GEN_MAX_PADDING = 300;	// when creating a platform; maximum space b/w platforms

	private static final int PLAYER_START_Y_PADDING = 50;	// start player 50 pixels from bottom
	private static final int PLAYER_MOVE_X_SPEED = 2;

	/* CLASS MEMBERS */

	private Color BACKGROUND_COLOR = new Color(255, 255, 255);

	private ArrayList<GameObject> ObjectsToDraw = new ArrayList<>();
	private Window CurrentWindow;
	private boolean GameStarted = false;

	// Key related members
	private KeyEventDispatcher CurrentKeyEventDispatcher;
	boolean LeftKeyDown = false;
	boolean RightKeyDown = false;


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
			// Update new positions
			GO.UpdatePhysics(DeltaTime);

			// Check if the player collided with an object
			if (GO.getClass() == Player.class) {
				GameObject CollidingObject = GO.GetCollidingObject(ObjectsToDraw);
				if (CollidingObject != null) {
					// Colliding w/ a platform; only calculate if the object is falling down (VelY > 0)
					if (GO.GetVelY() > 0 && CollidingObject.getClass() == Platform.class) {
						GO.SetVelocity(0, ((Platform) CollidingObject).GetBounceVelY());

						// TO-DO: begin moving everything down to simulate "scrolling"

					}
				}

				// Move left/right based on whether left/right keys are down
				// Don't move if both are down
				if ((LeftKeyDown || RightKeyDown) && !(LeftKeyDown && RightKeyDown)) {
					GO.SetPosition(GO.GetPosX() + (LeftKeyDown ? -PLAYER_MOVE_X_SPEED : PLAYER_MOVE_X_SPEED), GO.GetPosY());
				}
			}
		}
		repaint();
	}

	// Add object to the list of objects rendered
	public void AddObject(GameObject GO) {
		ObjectsToDraw.add(GO);
	}

	public void RandomGeneration(){
		for (int i = 1; i < 10; ++i) {
			int width = getWidth();
			int height = (int)((i / 10.0) * getHeight());
			double Random = Math.random();
			double xpos, ypos;
			if (Random > 0.5) {
				xpos = width * Random;
				//ypos = height * Random;
			} else if (Random< 0.5) {
				xpos = -width * Random;
			//ypos = -height * Random;
			} else {
				xpos = 0;
				//ypos = 0;
			}
			AddObject(new Platform(xpos, height));
		}

	}

	public void StartGame() {
		// Prevent accidentally calling this twice
		if (!GameStarted) {
			GameStarted = true;

			// Create the main player; center it & position it 50 pixels above bottom
			Player CurrentPlayer = new Player((getWidth() / 2.0) - (Player.SIZE_X / 2), getHeight() - PLAYER_START_Y_PADDING);
			AddObject(CurrentPlayer);

			CurrentKeyEventDispatcher = e -> {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_LEFT -> LeftKeyDown = true;
						case KeyEvent.VK_RIGHT -> RightKeyDown =  true;
					}
				} else if (e.getID() == KeyEvent.KEY_RELEASED) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_LEFT -> LeftKeyDown = false;
						case KeyEvent.VK_RIGHT -> RightKeyDown =  false;
					}
				}
				return false;
			};
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(CurrentKeyEventDispatcher);

			// Test platform to demo
			AddObject(new Platform(getWidth() / 2.0 - 5, 400));

			Platform HigherPlatform = new Platform(0, 400);
			HigherPlatform.SetBounceVelY(-100);
			AddObject(HigherPlatform);

			RandomGeneration();
		}
	}

	public void EndGame() {
		GameStarted = false;
		if (CurrentKeyEventDispatcher != null) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(CurrentKeyEventDispatcher);
		}
	}
}
