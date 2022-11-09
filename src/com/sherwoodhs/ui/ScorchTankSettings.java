package com.sherwoodhs.ui;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.bullet.ScorchMIRVBullet;
import com.sherwoodhs.tank.ScorchTank;
import com.sherwoodhs.bullet.ScorchBullet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ScorchTankSettings extends JPanel implements ActionListener, ItemListener, ChangeListener {

    //  This is a Swing panel that appears below the playfield. It displays
    //  the current weapon that is selected, the amount of that weapon that the
    //  player still has, the current power with adjustment buttons, the current
    //  angle with adjustment buttons, and the fire button, which is entirely in red.

    //  Reference the game object
    ScorchGame game;

    //  Reference to the current tank object
    ScorchTank currentTank;

    //  Following are Swing components that are used in the panel
    JComboBox artillery = new JComboBox();
    JLabel amountLabel = new JLabel("Amount: ");
    JLabel amountValue = new JLabel();

    JLabel space1 = new JLabel("    ");     //  Currently unused, but is there if you want it

    JLabel powerLabel = new JLabel("Power: ");
    JLabel powerValue = new JLabel();
    JButton decreasePowerButton = new JButton("-");
    JButton increasePowerButton = new JButton("+");
    JSlider powerSlider = new JSlider(0, 100, 50);

    JLabel angleLabel = new JLabel("Angle: ");
    JLabel angleValue = new JLabel();
    JButton decreaseAngleButton = new JButton("-");
    JButton increaseAngleButton = new JButton("+");
    JSlider angleSlider = new JSlider(0, 180, 90);

    JButton fireButton = new JButton("Fire");

    public ScorchTankSettings(ScorchGame game) {

        //  Must call the JPanel superclass constructor
        super();

        //  Set the reference to the current game object
        this.game = game;

        // Set the visuals of the fire button
        fireButton.setBorderPainted(false);
        fireButton.setFocusPainted(false);
        fireButton.setOpaque(true);

        //  Layout the Swing objects using the FlowLayout manager
        setLayout(new FlowLayout());

        //  Add them in order from left to right
        add(artillery);

        add(amountLabel);
        add(amountValue);

        add(powerLabel);
        add(powerValue);
        add(decreasePowerButton);
        //add(powerSlider);
        add(increasePowerButton);


        add(angleLabel);
        add(angleValue);
        add(decreaseAngleButton);
        //add(angleSlider);
        add(increaseAngleButton);
        add(fireButton);

        //  Add listeners for each of the Swing components. When the components
        //  are interacted with (button pressed, combo box selected, the appropriate
        //  listener methods are called.

        artillery.addItemListener(this);
        decreasePowerButton.addActionListener(this);
        increasePowerButton.addActionListener(this);
        decreaseAngleButton.addActionListener(this);
        increaseAngleButton.addActionListener(this);
        fireButton.addActionListener(this);
        powerSlider.addChangeListener(this);
        angleSlider.addChangeListener(this);
    }

    //  Reset the contents of the combo box based on the current weapon
    //  count inside the tank object passed in.

    public void resetWeapons(ScorchTank tank) {

        currentTank = tank;

        artillery.removeAllItems();
        for (String s : tank.getWeaponsCount().keySet()) {
            artillery.addItem(s);
        }

    }

    //  Reset the status of the Swing components in the panel based on
    //  the tank object passed in

    public void setStatus(ScorchTank tank) {

        currentTank = tank;

        //  Reset the contents of the combo box
        resetWeapons(tank);
        drawStatusPanel(tank);
    }

    public void drawStatusPanel(ScorchTank tank) {
        artillery.setSelectedItem(currentTank.getSelectedWeapon());

        //  Reset the amount that is available
        amountValue.setText(""+currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));

        //  Reset the angle and the power
        angleValue.setText(""+currentTank.getGunAngle());
        powerValue.setText(""+currentTank.getPower());

        // changing fireButton color based on current player
        fireButton.setBackground(currentTank.playerColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //  This method handles all the button presses. First, get
        //  the angle and power of the current tank

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

            //  Only perform the action if the player has more than 0 of the selected weapon
            if (currentNumber > 0) {

                currentNumber--;
                currentTank.getWeaponsCount().put((String) artillery.getSelectedItem(), currentNumber);

                //  Add the appropriate bullet type to the array list of active game bullets

                if (artillery.getSelectedItem().equals("Normal Bullet")) {
                    game.bullets.add(new ScorchBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("MIRV"))) {
                    game.bullets.add(new ScorchMIRVBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                }

                //  Set the boolean in the game object that the fire button has been pressed.
                //  At this point, the main thread will start processing animating objects.

                game.waitForPlayerFire = false;
            }
        }

        //  Redraw the status panel

        drawStatusPanel(currentTank);
    }


    //Slider listeners
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == powerSlider) {
            int power = currentTank.getPower();
            currentTank.setPower(power);
            } else if(e.getSource() == angleSlider){
                int angle = currentTank.getGunAngle();
                currentTank.setGunAngle(angle);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        //  This method is called when the combo box has been set to a new item.
        //  Reset the amount that is available.

        currentTank.setSelectedWeapon((String)artillery.getSelectedItem());
        amountValue.setText(""+currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));
    }
}
