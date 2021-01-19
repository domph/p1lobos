package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	//mouse
	boolean canShoot = true;
	int mouseX;
	int mouseY;
	int dx;
	int dy;

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

					// colliding with a monster from above (pretends its a platform)
					else if((GO.GetVelY() > 0 && CollidingObject.getClass() == Monster.class)){
						GO.SetVelocity(0, ((Monster) CollidingObject).GetBounceVelY());
						CollidingObject.SetPosition(-75,-75);
					}

					// colliding with a monster from below (dies)
					else if((GO.GetVelY() < 0 && CollidingObject.getClass() == Monster.class)){
						// "despawns" player
						GO.SetPosition(-100,700);
						GO.SetVelocity(0,0);
						EndGame();
					}
				}

				// Move left/right based on whether left/right keys are down
				// Don't move if both are down
				if ((LeftKeyDown || RightKeyDown) && !(LeftKeyDown && RightKeyDown)) {
					GO.SetPosition(GO.GetPosX() + (LeftKeyDown ? -PLAYER_MOVE_X_SPEED : PLAYER_MOVE_X_SPEED), GO.GetPosY());
				}
			}

			//bullet physics
			if (GO.getClass() == Bullet.class){
				if (Math.abs(GO.GetPosX()-250) > 250 || Math.abs(GO.GetPosY()-300) > 300 ){
					GO.SetPosition(-10,-10);  //"despawns" bullet
					GO.SetVelocity(0,0);
					canShoot = true;
				}
				GameObject CollidingObject = GO.GetCollidingObject(ObjectsToDraw);
				if (CollidingObject != null){
					if (CollidingObject.getClass() == Monster.class){

						CollidingObject.SetPosition(-75,-75); //"despawns" monster
						canShoot = true;
					}
				}
			}
		}
		repaint();
	}

	// Add object to the list of objects rendered
	public void AddObject(GameObject GO) {
		ObjectsToDraw.add(GO);
	}

	public void StartGame() {
		// Prevent accidentally calling this twice
		if (!GameStarted) {
			GameStarted = true;




			// Create the main player; center it & position it 50 pixels above bottom
			Player CurrentPlayer = new Player((getWidth() / 2.0) - (Player.SIZE_X / 2), getHeight() - PLAYER_START_Y_PADDING);
			AddObject(CurrentPlayer);

			//create and add bullet
			Bullet bullet = new Bullet(-10,-10);
			AddObject(bullet);

			// creates test monsters
			AddObject(new Monster(50,400));
			AddObject(new Monster(50,100));


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

			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(canShoot){

						canShoot = false;
						System.out.println("shoot");

						mouseX = getMousePosition().x;
						mouseY = getMousePosition().y;

						//creates normalized directional vector
						dx = (int) (mouseX - CurrentPlayer.GetPosX());
						dy = (int) (mouseY - CurrentPlayer.GetPosY());

						bullet.SetPosition(CurrentPlayer.GetPosX(),CurrentPlayer.GetPosY());
						bullet.SetVelocity((bullet.BULLET_SPEED)*dx/Math.hypot(dx,dy),(bullet.BULLET_SPEED)*dy/Math.hypot(dx,dy));
				}
					System.out.println("click");}

				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}


			});

			// Test platform to demo
			AddObject(new Platform(getWidth() / 2.0 - 5, 400));
		}
	}

	public void EndGame() {
		GameStarted = false;
		if (CurrentKeyEventDispatcher != null) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(CurrentKeyEventDispatcher);
			System.out.println("Game over!");
		}
	}
}
