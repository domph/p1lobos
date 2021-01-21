package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScoreKeeper implements ActionListener{
    //global variables + objects
    JFrame frame = new JFrame();
    JLabel Score = new JLabel();


    //constructor
    ScoreKeeper(){
        Score.setText("Score:");
        timeLabel.setBounds(100,100,200,100);
        timeLabel.setFont(new Font("Times New Roman", Font.PLAIN,35));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        frame.add(ScoreKeeper);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);

    }


    void reset(){
        timer.stop();
        elapsedTime=0;
        seconds=0;
        minutes=0;
        hours=0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);

    }

}
