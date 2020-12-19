package Model;
import javax.swing.*;


import java.awt.*;

public class Platform extends GameObject{
    int width;
    int height;
    double position;
    double delay;
    public final double RADIUS = 2.0;
    public int platformType; //0=normal, etc.

    public Platform(Color c, double x, double y, double xV, double yV, int type){
        super(c,x,y,xV,yV);
        platformType = type;
    }

    public static void main(String[] args) {

    }

    public void RandomGeneration(JFrame frame, float playerSpeed){
        int width = frame.getContentPane().getWidth();
        int height = frame.getContentPane().getHeight();
        double Random = Math.random();
        if (Random > 0.5)
            position = width*Random;
        else if (Random< 0.5)
            position = -width*Random;
        else
            position = 0;
        delay = ((Math.random()*2)/playerSpeed);
        CreatePlatform(position, (height/2));
        try {
            Thread.sleep((int) delay * 1000);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    public void CreatePlatform (double xpos,double ypos){

    }
}

