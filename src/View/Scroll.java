package View;

import javax.swing.*;
import java.awt.*;

public class Scroll extends JPanel{
    private int x;
    private int y;
    private String text;

    public Scroll(){
        x= -50;
        y=150;
        text = "SCROLL DEMO";
        setSize(400,300);
    }
    @Override
    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,400,300);
        g.setColor(Color.black);
        g.drawString(text,x,y);
        System.out.println(x+" "+y);
    }

    public void start() throws InterruptedException{
        while(true){
            while(x<=getWidth()){
                x++;
                y = getHeight()/2;
                repaint();
                Thread.sleep(10);
            }
            while(x>=getWidth()){
                x--;
                y = getHeight()/2;
                repaint();
                Thread.sleep(10);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Scrolling Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scroll scrolling = new Scroll();
        frame.getContentPane().add(scrolling);
        frame.setSize(400,300);
        frame.setVisible(true);
        scrolling.start();
    }
}