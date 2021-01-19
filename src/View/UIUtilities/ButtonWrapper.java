package View.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Responsible for creating a button w/ mouse hover/click color animations
public class ButtonWrapper {
	private final JButton Button;
	private boolean IsHovering = false;
	private boolean IsLocked = false;
	private Color DefaultColor;

	// HoverDelta: Change of r, g, b components when button is hovered; ClickDelta: same but when button is clicked
	public ButtonWrapper(JPanel Parent, String Text, int PosX, int PosY, int SizeX, int SizeY, Color DefaultColor, int HoverDelta, int ClickDelta, int FontSize) {
		Button = new JButton();

		Button.setBounds(PosX, PosY, SizeX, SizeY);
		Button.setText(Text);
		Button.setForeground(Color.WHITE);
		Button.setBackground(DefaultColor);
		Button.setFont(new Font("Segoe UI", Font.PLAIN, FontSize));
		Button.setHorizontalTextPosition(SwingConstants.CENTER);
		Button.setVerticalTextPosition(SwingConstants.CENTER);
		Button.setBorderPainted(false);
		Button.setFocusPainted(false);
		this.DefaultColor = DefaultColor;

		int R = DefaultColor.getRed(), G = DefaultColor.getGreen(), B = DefaultColor.getBlue();
		Color HoverColor = new Color(R + HoverDelta, G + HoverDelta, B + HoverDelta);
		Color ClickColor = new Color(R + ClickDelta, G + ClickDelta, B + ClickDelta);

		// Responsible for tweening a button's color upon hover and click; we'll use 100ms
		Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!IsLocked) {
					new TweenColor(Button, ClickColor, 100);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (IsHovering && !IsLocked) {
					new TweenColor(Button, HoverColor, 100);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (!IsLocked) {
					IsHovering = true;
					new TweenColor(Button, HoverColor, 100);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!IsLocked) {
					IsHovering = false;
					new TweenColor(Button, DefaultColor, 100);
				}
			}
		});

		Parent.add(Button);
	}

	public void Lock() {
		IsLocked = true;
	}

	public void UnLock() {
		IsLocked = false;
		Button.setBackground(DefaultColor);
	}

	public boolean IsLocked() {
		return IsLocked;
	}

	public JButton GetButtonObject() {
		return Button;
	}	// so the main function can edit the button if desired
}
