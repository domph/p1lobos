package View;

import Network.Server;
import View.UIUtilities.ButtonWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static javax.swing.BorderFactory.createEmptyBorder;

// Class to handle main menu navigation
public class LoginMenu extends JPanel {
	private Color BACKGROUND_COLOR = new Color(50, 50, 50);	// background color of the main menu
	private int TITLE_SIZE_X = 300;
	private int TITLE_SIZE_Y = 100;
	private int TITLE_POSITION_Y = 50;

	private int LOADING_SIZE_X = 200;
	private int LOADING_SIZE_Y = 50;
	private int LOADING_POSITION_Y = 200;

	private int LOADINGBAR_PADDING = 20;
	private int LOADINGBAR_HEIGHT = 10;
	private Color LOADINGBAR_BACKGROUND_COLOR = new Color(85, 85, 85);
	private Color LOADBINGBAR_FILL_COLOR = new Color(200, 200, 200);

	private int TEXTAREA_HEIGHT = 100;
	private int TEXTAREA_PADDING = 20;
	private Color TEXTAREA_BACKGROUND_COLOR = new Color(65, 65, 65);
	private Color TEXTAREA_FONT_COLOR = new Color(200, 200, 200);

	private int REGISTER_PADDING = 10;
	private int REGISTER_HEIGHT = 30;
	private int REGISTER_MIDDLE_PADDING = 10;
	private Color REGISTER_BACKGROUND_COLOR = new Color(60, 60, 60);
	private Color REGISTER_FONT_COLOR = new Color(190, 190, 190);
	private Color REGISTER_PLACEHOLDER_FONT_COLOR = new Color(140, 140, 140);
	private Color REGISTER_BUTTON_COLOR = new Color(45, 83, 128);

	private Window CurrentWindow;
	private JLabel LoadingBar;
	private JLabel LoadingBarFill;
	private JTextArea TextArea;
	private JScrollPane ScrollPane;

	private JTextField RegisterName;

	private boolean IsMovingProgressBar = false;
	private boolean RegisterEnabled = false;

	public LoginMenu(Window CurrentWindow) {
		// Basic design -- can change later
		setLayout(null);
		setBackground(BACKGROUND_COLOR);
		setVisible(true);
		this.CurrentWindow = CurrentWindow;

		// Loading bar
		LoadingBar = new JLabel();
		LoadingBar.setBounds(LOADINGBAR_PADDING, LOADINGBAR_PADDING, CurrentWindow.getContentPane().getSize().width - (2 * LOADINGBAR_PADDING), LOADINGBAR_HEIGHT);
		LoadingBar.setBackground(LOADINGBAR_BACKGROUND_COLOR);
		LoadingBar.setOpaque(true);
		add(LoadingBar);

		LoadingBarFill = new JLabel();
		LoadingBarFill.setBounds(0, 0, 0, LOADINGBAR_HEIGHT);
		LoadingBarFill.setBackground(LOADBINGBAR_FILL_COLOR);
		LoadingBarFill.setOpaque(true);
		LoadingBar.add(LoadingBarFill);

		// Text area
		TextArea = new JTextArea();
		TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		TextArea.setForeground(TEXTAREA_FONT_COLOR);
		TextArea.setEditable(false);
		TextArea.setBackground(TEXTAREA_BACKGROUND_COLOR);
		TextArea.setLineWrap(true);
		TextArea.setWrapStyleWord(true);
		TextArea.setBorder(createEmptyBorder(5, 5, 5, 5));

		ScrollPane = new JScrollPane(TextArea);
		ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ScrollPane.setBounds(LOADINGBAR_PADDING, LOADINGBAR_PADDING + TEXTAREA_PADDING + LOADINGBAR_HEIGHT, CurrentWindow.getContentPane().getSize().width - (2 * LOADINGBAR_PADDING), TEXTAREA_HEIGHT);
		ScrollPane.setBorder(createEmptyBorder());

		add(ScrollPane);
	}

	public void EnableRegister() {
		if (!RegisterEnabled) {
			RegisterEnabled = true;

			RegisterName = new JTextField();
			RegisterName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			RegisterName.setForeground(REGISTER_FONT_COLOR);
			RegisterName.setBackground(REGISTER_BACKGROUND_COLOR);

			// Calculate size; 70% of loading bar length - 10 for padding
			int TextWidth = (int)(LoadingBar.getWidth() * 0.7) - REGISTER_MIDDLE_PADDING;
			int YPos = ScrollPane.getY() + TEXTAREA_HEIGHT + REGISTER_PADDING;
			RegisterName.setBounds(LOADINGBAR_PADDING, YPos, TextWidth, REGISTER_HEIGHT);
			RegisterName.setBorder(createEmptyBorder(0, 5, 0, 0));

			RegisterName.setText("Enter a username");
			RegisterName.setForeground(REGISTER_PLACEHOLDER_FONT_COLOR);

			RegisterName.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (RegisterName.getForeground().equals(REGISTER_PLACEHOLDER_FONT_COLOR)) {
						RegisterName.setText("");
						RegisterName.setForeground(REGISTER_FONT_COLOR);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (RegisterName.getText().length() == 0) {
						RegisterName.setText("Enter a username");
						RegisterName.setForeground(REGISTER_PLACEHOLDER_FONT_COLOR);
					}
				}
			});
			add(RegisterName);

			// Button
			int ButtonWidth = LoadingBar.getWidth() - 10 - TextWidth;
			ButtonWrapper RegisterButtonWrapper = new ButtonWrapper(this, "Register", RegisterName.getX() + RegisterName.getWidth() + REGISTER_MIDDLE_PADDING, YPos, ButtonWidth, REGISTER_HEIGHT, REGISTER_BUTTON_COLOR, 50, 80, 16);
			JButton RegisterButton = RegisterButtonWrapper.GetButtonObject();
			RegisterButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if (!RegisterButtonWrapper.IsLocked()) {
						RegisterButtonWrapper.Lock();
						if (RegisterName.getText().length() < 20) {
							// https://stackoverflow.com/questions/8248277/how-to-determine-if-a-string-has-non-alphanumeric-characters
							Pattern p = Pattern.compile("[^a-zA-Z0-9]");
							boolean HasSpecialChar = p.matcher(RegisterName.getText()).find();
							if (!HasSpecialChar) {
								boolean RegisterResult = Server.RegisterName(RegisterName.getText());
								if (!RegisterResult) {
									AppendText("ERROR: Unable to connect to the server. Defaulting to offline mode. Your stats will not save!");
									SetProgressBar(100, true, () -> CurrentWindow.ShowMainMenu());
								} else {
									if (Server.IsExistingUser()) {
										AppendText("Successfully linked your computer to the name '" + RegisterName.getText() + "'. Welcome!");
										SetProgressBar(100, true, () -> CurrentWindow.ShowMainMenu());
									} else {
										AppendText("ERROR: The name your entered has already been taken. Please choose another name.");
									}
								}
							} else {
								AppendText("ERROR: Usernames can only contain letters and digits.");
							}
						} else {
							AppendText("ERROR: Usernames must be less than 20 characters.");
						}
					}
					RegisterButtonWrapper.UnLock();
				}
			});
			repaint();
		}
	}

	public void AppendText(String Str) {
		TextArea.append(Str + "\n");
		JScrollBar Vertical = ScrollPane.getVerticalScrollBar();
		Vertical.setValue(Vertical.getMaximum());
	}

	public void SetProgressBar(int Value, boolean SmoothTransition, Runnable OnComplete) {
		// Value from 0 to 100
		Value = Math.max(0, Math.min(100, Value));
		if (!SmoothTransition) {
			LoadingBarFill.setSize((int)(LoadingBar.getWidth() * (Value / 100.0)), LOADINGBAR_HEIGHT);
		} else {
			int FinalValue = Value;
			new Thread(() -> {
				IsMovingProgressBar = true;
				int CurrentSize = LoadingBarFill.getWidth();
				int DesiredSize = (int)(LoadingBar.getWidth() * (FinalValue / 100.0));
				int Difference = DesiredSize - CurrentSize;
				if (Difference > 0) {
					for (int i = CurrentSize; i <= DesiredSize; i += 2) {
						LoadingBarFill.setSize(i, LOADINGBAR_HEIGHT);
						try {
							Thread.sleep(10);
						} catch (Exception ignored) {}
					}
				} else if (Difference < 0) {
					for (int i = CurrentSize; i >= DesiredSize; i -= 2) {
						LoadingBarFill.setSize(i, LOADINGBAR_HEIGHT);
						try {
							Thread.sleep(1);
						} catch (Exception ignored) {}
					}
				}
				LoadingBarFill.setSize(DesiredSize, LOADINGBAR_HEIGHT);
				IsMovingProgressBar = false;
				OnComplete.run();
			}).start();
		}
	}

	public void SetProgressBar(int Value, boolean SmoothTransition) {
		SetProgressBar(Value, SmoothTransition, () -> {});
	}
}