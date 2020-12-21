package View.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// Specifically designed for a JButton; takes a target color and time and interpolates the current color of the Button to the desired color in the specified time
public class TweenColor implements Runnable {
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