package Control;

import View.Window;

import javax.swing.*;

public class GameControl {
    private static final int WINDOW_SIZE_X = 500;
    private static final int WINDOW_SIZE_Y = 600;
    private static final double DesiredDelta = 1.0/60.0; // how often physics calculations should be run

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            Window MainWindow = new Window(WINDOW_SIZE_X, WINDOW_SIZE_Y, DesiredDelta);
        });
    }
}