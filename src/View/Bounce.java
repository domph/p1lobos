package View;

import java.awt.*;
//import java.util.Random;

public class Bounce {
    public static void main (String[] args) {
        //Setting the boundaries for the bouncing
        StdDraw.setXscale(-1.0, +1.0);
        StdDraw.setYscale(-1.0, +1.0);

        // Object c of method Color, here we are setting the default color
        Color c = new Color(230, 155, 180);

        //Movement of the first blob
        //position
        double rx = 0.450, ry = 0.560;
        //velocity
        double vx = 0.015, vy = 0.023;

        //Movement of second blob
        //position
        double rx2 = 0.450, ry2 = 0.560;
        //velocity
        double vx2 = -0.015, vy2 = -0.023;

        //Setting ball size
        double sz = 0.05;

        while (true) {
            //Setting color of StdDraw pen
            StdDraw.setPenColor(c);

            //Creating square
            StdDraw.filledSquare(0.0, 0.0, 1.0);

            //Walls collision
            //First ball
            if (rx - sz + vx < -1.0 || rx + sz + vx > 1.0) vx = -vx;
            if (ry - sz + vy < -1.0 || ry + sz + vy > 1.0) vy = -vy;
            //Second ball
            if (rx2 - sz + vx2 < -1.0 || rx2 + sz + vx2 > 1.0) vx2 = -vx2;
            if (ry2 - sz + vy2 < -1.0 || ry2 + sz + vy2 > 1.0) vy2 = -vy2;



            //Apply movement
            //First ball
            rx = rx + vx;
            ry = ry + vy;

            //Second ball
            rx2 = rx2 + vx2;
            ry2 = ry2 + vy2;

            //Setting the balls to be black and drawing them
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledCircle(rx, ry, sz);
            //second ball
            StdDraw.filledCircle(rx2, ry2, sz);

            //Showing it all and setting the animation speed to 20
            StdDraw.show(20);



        }
    }
}
