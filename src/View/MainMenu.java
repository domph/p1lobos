package View;

import View.UIUtilities.ButtonWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

// Class to handle main menu navigation
public class MainMenu extends JPanel {
	private Color BACKGROUND_COLOR = new Color(50, 50, 50);	// background color of the main menu
	private Color BUTTON_COLOR = new Color(45, 83, 128);		// color of the buttons -- in the future, we may change this and have the buttons be images
	private Dimension BUTTON_SIZE = new Dimension(200, 50);	// size of buttons
	private int BUTTON_PADDING = 20;	// space in between each button

	private ArrayList<JButton> ButtonList = new ArrayList<>();
	private Window CurrentWindow;

	public JButton AddButton(String Text, MouseAdapter MA) {
		JButton NewButton = new ButtonWrapper(this, Text, 0, 0, BUTTON_SIZE.width, BUTTON_SIZE.height, BUTTON_COLOR, 50, 80, 16).GetButtonObject();
		NewButton.addMouseListener(MA);
		ButtonList.add(NewButton);
		OrderButtons();
		return NewButton;
	}

	private void OrderButtons() {
		// Buttons
		int ButtonPosX = (CurrentWindow.getSize().width / 2) - (BUTTON_SIZE.width / 2);	// centers buttons on the x axis
		int ButtonStartPosY = (CurrentWindow.getSize().height / 2) - (((ButtonList.size() * BUTTON_SIZE.height) + ((ButtonList.size() - 1) * BUTTON_PADDING)) / 2);
		int Counter = 0;
		for (JButton Button : ButtonList) {
			Button.setBounds(ButtonPosX, ButtonStartPosY + (Counter * (BUTTON_SIZE.height + BUTTON_PADDING)), BUTTON_SIZE.width, BUTTON_SIZE.height);
			++Counter;
		}
	}

	public MainMenu(Window CurrentWindow) {
		// Basic design -- can change later
		setLayout(null);
		setBackground(BACKGROUND_COLOR);
		setVisible(true);
		this.CurrentWindow = CurrentWindow;
	}
}
