package com.sherwoodhs.ui;

import com.sherwoodhs.tank.ScorchTank;

import javax.swing.*;
import java.awt.*;

public class ScorchStatus extends JPanel {

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

        playerNumber.setText(""+t.getPlayerNumber());
        windValue.setText(""+wind);

    }

}
