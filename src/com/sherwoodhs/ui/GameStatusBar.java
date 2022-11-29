package com.sherwoodhs.ui;

import com.sherwoodhs.tank.Tank;
import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class GameStatusBar extends JPanel {

    //  This is a Swing panel that appears above the playfield. It only displays
    //  the current wind speed and the current player. A setStatus() method is
    //  provided to update both values based on the tank representing the current
    //  player.

    JLabel windLabel = new JLabel("Wind: ");
    JLabel windValue = new JLabel();
    JLabel space = new JLabel("      ");
    JLabel space2 = new JLabel("      ");
    JLabel playerLabel = new JLabel("Player: ");
    JLabel playerNumber = new JLabel();

    JLabel playerHealth = new JLabel("Heath: ");

    public GameStatusBar() {

        super();

        setLayout(new FlowLayout());

        add(playerLabel);
        add(playerNumber);

        add(space);

        add(windLabel);
        add(windValue);

        add(space2);
        add(playerHealth);

    }

    public void setStatus(Tank t, int wind) {

        // Set color of status bar to player color
        setBackground(t.playerColor);

        //  Update the current player number and the wind value. A quick and
        //  dirty way to convert a number to a string is to concatenate it on
        //  to the end of an empty string

        playerNumber.setText("<html>" + t.getPlayerNumber() + " (" + getStringFromColor(t.playerColor) + ")" + "</html>");

        playerHealth.setText(""+ "Health: " + t.Hp);

        // displays wind at the top
        // for numbers below 0 I used abs to return a positive int
        // wind is not actually positive, just displays as positive
        if(wind < 0) {
            windValue.setText("" + "<-- "+ abs(wind));
        } else if(wind == 0) {
            windValue.setText("" + wind);
        } else {
                windValue.setText("" + wind+" -->");
            }
        }
    public String getStringFromColor(Color c) {
        for (Field f : Color.class.getDeclaredFields()) {
            if (f.getType().equals(Color.class))
                try {
                    if (f.get(null).equals(c)) {
                        String fs = f.getName();
                        return fs.substring(0, 1).toUpperCase() + fs.substring(1).toLowerCase();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
}
