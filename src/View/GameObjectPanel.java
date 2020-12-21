package View;

import Model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameObjectPanel extends JPanel {

    ArrayList<GameObject> ObjectsToDraw = new ArrayList<>();

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (GameObject GO : ObjectsToDraw) {
            g.setColor(GO.InternalColor);
            g.fillRect((int) GO.xPosition, (int) GO.yPosition, (int) GO.xSize, (int) GO.ySize);
        }
    }

    public void AddObject(GameObject GO) {
        ObjectsToDraw.add(GO);
    }

    public GameObjectPanel(){ ;
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    System.out.println("left");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("right");
                    break;

            }
        }
    }


}
