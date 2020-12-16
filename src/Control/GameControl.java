package Control;

import Model.GameState;
import View.Window;

import javax.swing.*;

public class GameControl {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new Window();
        });
        GameState model = new GameState();

    }
}