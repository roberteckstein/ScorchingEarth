package com.sherwoodhs.ui;

import com.sherwoodhs.tank.ScorchTank;

import javax.swing.*;
import java.awt.*;

public class ScorchStatus extends JPanel {

    //  This is a Swing panel that appears above the playfield. It only displays
    //  the current wind speed and the current player. A setStatus() method is
    //  provided to update both values based on the tank representing the current
    //  player.

    JLabel windLabel = new JLabel("Wind: ");
    JLabel windValue = new JLabel();
    JLabel space = new JLabel("      ");
    JLabel playerLabel = new JLabel("Player: ");
    JLabel playerNumber = new JLabel();

    public ScorchStatus() {

        super();

        setLayout(new FlowLayout());

        add(playerLabel);
        add(playerNumber);

        add(space);

        add(windLabel);
        add(windValue);

    }

    public void setStatus(ScorchTank t, int wind) {

        //  Update the current player number and the wind value. A quick and
        //  dirty way to convert a number to a string is to concatenate it on
        //  to the end of an empty string.

        playerNumber.setText(""+t.getPlayerNumber());
        windValue.setText(""+wind);

    }

}
