package com.sherwoodhs.ui;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.bullet.ScorchMIRVBullet;
import com.sherwoodhs.tank.ScorchTank;
import com.sherwoodhs.bullet.ScorchBullet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ScorchTankSettings extends JPanel implements ActionListener, ItemListener {

    ScorchGame game;
    ScorchTank currentTank;

    JComboBox artillery;
    JLabel amountLabel = new JLabel("Amount: ");
    JLabel amountValue = new JLabel();

    JLabel space1 = new JLabel("    ");

    JLabel powerLabel = new JLabel("Power: ");
    JLabel powerValue = new JLabel();
    JButton decreasePowerButton = new JButton("-");
    JButton increasePowerButton = new JButton("+");

    JLabel angleLabel = new JLabel("Angle: ");
    JLabel angleValue = new JLabel();
    JButton decreaseAngleButton = new JButton("-");
    JButton increaseAngleButton = new JButton("+");
    JButton fireButton = new JButton("Fire");

    public ScorchTankSettings(ScorchGame game) {

        super();

        this.game = game;

        artillery = new JComboBox();

        setLayout(new FlowLayout());

        fireButton.setBackground(Color.red);
        fireButton.setBorderPainted(false);
        fireButton.setOpaque(true);


        add(artillery);

        add(amountLabel);
        add(amountValue);

       // add(space1);

        add(powerLabel);
        add(powerValue);
        add(decreasePowerButton);
        add(increasePowerButton);

      //  add(space2);

        add(angleLabel);
        add(angleValue);
        add(decreaseAngleButton);
        add(increaseAngleButton);
        add(fireButton);

        artillery.addItemListener(this);
        decreasePowerButton.addActionListener(this);
        increasePowerButton.addActionListener(this);
        decreaseAngleButton.addActionListener(this);
        increaseAngleButton.addActionListener(this);
        fireButton.addActionListener(this);

    }

    public void resetWeapons(ScorchTank tank) {

        currentTank = tank;

        artillery.removeAllItems();
        for (String s : tank.getWeaponsCount().keySet()) {
            artillery.addItem(s);
        }

    }

    public void setStatus(ScorchTank tank) {

        currentTank = tank;

        //  Reset the contents of the combo box
        artillery.removeAllItems();
        for (String k : currentTank.getWeaponsCount().keySet())
            artillery.addItem(k);
        artillery.setSelectedItem(currentTank.getSelectedWeapon());

        //  Reset the amount that is available
        amountValue.setText(""+currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));

        //  Reset the angle and the power
        angleValue.setText(""+currentTank.getGunAngle());
        powerValue.setText(""+currentTank.getPower());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //  This method handles all the button presses

        int currentAngle = currentTank.getGunAngle();
        int currentPower = currentTank.getPower();

        if (e.getSource() == decreaseAngleButton) {

            currentAngle--;
            if (currentAngle < 0) {
                currentAngle = 0;
            }

            //  Set the current tank gun angle, which will redraw it
            currentTank.setGunAngle(currentAngle);

        } else if (e.getSource() == increaseAngleButton) {

            currentAngle++;
            if (currentAngle > 180) {
                currentAngle = 180;
            }

            //  Set the current tank gun angle, which will redraw it
            currentTank.setGunAngle(currentAngle);

        } else if (e.getSource() == decreasePowerButton) {

            currentPower--;
            if (currentPower < 0) {
                currentPower = 0;
            }

            //  Set the current tank gun power
            currentTank.setPower(currentPower);

        } else if (e.getSource() == increasePowerButton) {

            currentPower++;
            if (currentPower > 250) {
                currentPower = 250;
            }

            //  Set the current tank gun power
            currentTank.setPower(currentPower);

        } else if (e.getSource() == fireButton) {

            // The fire button has been pressed, so calculate the opening position and
            // movement of the selected bullet type.

            double x = currentTank.getX()+20;
            double y = currentTank.getY();
            double a = Math.toRadians((double)currentTank.getGunAngle()-90.0);
            double dx = x+(WIDTH/2)+(int)(currentPower/10.0 * Math.sin(a)) - x+(WIDTH/2);
            double dy = y-(int)(currentPower/10.0 * Math.cos(a)) - y;

            int currentNumber = currentTank.getWeaponsCount().get(artillery.getSelectedItem());
            if (currentNumber > 0) {

                currentNumber--;
                currentTank.getWeaponsCount().put((String) artillery.getSelectedItem(), currentNumber);

                //  Add the appropriate bullet type to the array list of active game bullets

                if (artillery.getSelectedItem().equals("Normal Bullet")) {
                    game.bullets.add(new ScorchBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("MIRV"))) {
                    game.bullets.add(new ScorchMIRVBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                }

                game.waitForPlayerFire = false;
            }
        }

        setStatus(currentTank);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //  Reset the amount that is available
        currentTank.setSelectedWeapon((String)artillery.getSelectedItem());
        amountValue.setText(""+currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));
    }
}
