package Model;
import javax.swing.*;


import java.awt.*;

public class Platform extends GameObject{
    int width;
    int height;
    double position;
    public final double RADIUS = 2.0;
    public int platformType; //0=normal, etc.

    public Platform(Color c, double x, double y, double xV, double yV, int type){
        super(c,x,y,xV,yV);
        platformType = type;
    }

    public static void main(String[] args) {

    }

    public void RandomGeneration(JFrame frame){
        int width = frame.getContentPane().getWidth();
        int height = frame.getContentPane().getHeight();
        double Random = Math.random();
        if (Random > 0.5)
            position = width*Random;
        else if (Random< 0.5)
            position = -width*Random;
        else
            position = 0;

        CreatePlatform(position, (height/2));
    }
    public void CreatePlatform (double xpos,double ypos){

    }
}

