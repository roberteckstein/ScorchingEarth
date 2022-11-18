package com.sherwoodhs.ui;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.weapons.*;
import com.sherwoodhs.tank.Tank;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

public class TankStatusBar extends JPanel implements ActionListener, ItemListener, ChangeListener {

    //  This is a Swing panel that appears below the playfield. It displays
    //  the current weapon that is selected, the amount of that weapon that the
    //  player still has, the current power with adjustment buttons, the current
    //  angle with adjustment buttons, and the fire button, which is entirely in red.

    //  Reference the game object
    ScorchGame game;

    //  Reference to the current tank object
    Tank currentTank;

    //  Following are Swing components that are used in the panel
    JComboBox artillery = new JComboBox();
    JLabel amountLabel = new JLabel("Amount: ");
    JLabel amountValue = new JLabel();

    JLabel space1 = new JLabel("    ");     //  Currently unused, but is there if you want it


    JLabel angleLabel = new JLabel("Angle: ");
    JLabel angleValue = new JLabel();
    JButton decreaseAngleButton = new JButton("-");
    JButton increaseAngleButton = new JButton("+");
    JSlider angleSlider = new JSlider(0, 180, 0);

    JLabel powerLabel = new JLabel("Power: ");
    JLabel powerValue = new JLabel();
    JButton decreasePowerButton = new JButton("-");
    JButton increasePowerButton = new JButton("+");
    JSlider powerSlider = new JSlider(0, 100, 50);

    JButton fireButton = new JButton("Fire");

    public TankStatusBar(ScorchGame game) {

        //  Must call the JPanel superclass constructor
        super();

        //  Set the reference to the current game object
        this.game = game;

        // lock given component sizes in the panel
        setLayout(new FlowLayout());

        // Set the visuals of the fire button
        fireButton.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.black));
        fireButton.setPreferredSize(new Dimension(60, 30));
        fireButton.setFocusPainted(false);
        fireButton.setOpaque(true);

        //  Add them in order from left to right
        artillery.setPreferredSize(new Dimension(150, 25));
        add(artillery);

        amountValue.setPreferredSize(new Dimension(25, 20));
        add(amountLabel);
        add(amountValue);

        JPanel angle = new JPanel(new GridBagLayout());
        angle.setPreferredSize(new Dimension(225, 30));
        angleValue.setPreferredSize(new Dimension(25, 20));
        angle.add(angleLabel);
        angle.add(angleValue);
        //add(decreaseAngleButton);
        angle.add(angleSlider);
        angleSlider.setPreferredSize(new Dimension(150,30)); // sets AngleSlider dimensions/size
        //add(increaseAngleButton);

        JPanel power = new JPanel(new GridBagLayout());
        power.setPreferredSize(new Dimension(225, 30));
        powerValue.setPreferredSize(new Dimension(25, 20));
        power.add(powerLabel);
        power.add(powerValue);
        //add(decreasePowerButton);
        power.add(powerSlider);
        powerSlider.setPreferredSize(new Dimension(150,30)); // sets PowerSlide dimensions/size
        //add(increasePowerButton);

        add(angle);
        add(power);

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

    public void resetWeapons(Tank tank) {

        currentTank = tank;

        artillery.removeAllItems();
        for (String s : tank.getWeaponsCount().keySet()) {
            artillery.addItem(s);
        }

    }

    //  Reset the status of the Swing components in the panel based on
    //  the tank object passed in

    public void setStatus(Tank tank) {

        currentTank = tank;

        //  Reset the contents of the combo box
        resetWeapons(tank);
        drawStatusPanel(tank);
        artillery.setSelectedItem("Normal Bullet");
    }

    public void drawStatusPanel(Tank tank) {
        artillery.setSelectedItem(currentTank.getSelectedWeapon());

        //  Reset the amount that is available
        //  If it's the normal bullet it sets the value label to ∞
        if (artillery.getSelectedItem().equals("Normal Bullet")) {
            amountValue.setText("" + "∞");
        } else {
            amountValue.setText("" + currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));
        }

        //  Reset the angle and the power
        powerValue.setText("" + currentTank.getPower());
        powerSlider.setValue(currentTank.getPower());
        angleSlider.setValue(currentTank.getGunAngle());

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
            if (currentPower > 100) {
                currentPower = 100;
            }

            //  Set the current tank gun power
            currentTank.setPower(currentPower);

        } else if (e.getSource() == fireButton && game.waitForPlayerFire == true) {

            // The fire button has been pressed, so calculate the opening position and
            // movement of the selected bullet type.

            double x = currentTank.getX() + 20;
            double y = currentTank.getY();
            double a = Math.toRadians((double) currentTank.getGunAngle() - 90.0);
            double dx = x + (WIDTH / 2) + (int) (currentPower / 10.0 * Math.sin(a)) - x + (WIDTH / 2);
            double dy = y - (int) (currentPower / 10.0 * Math.cos(a)) - y;

            int currentNumber = currentTank.getWeaponsCount().get(artillery.getSelectedItem());

            //  Only perform the action if the player has more than 0 of the selected weapon
            if (currentNumber > 0) {

                //only removes ammo if not normal bullet
                //if it is then it doesn't do anything
                if (artillery.getSelectedItem().equals("Normal Bullet")) {
                    currentTank.getWeaponsCount().put((String) artillery.getSelectedItem(), currentNumber);
                } else {
                    currentTank.getWeaponsCount().put((String) artillery.getSelectedItem(), currentNumber);
                }

                //  Add the appropriate bullet type to the array list of active game bullets

                if (artillery.getSelectedItem().equals("Normal Bullet")) {
                    game.bullets.add(new DefaultBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("MIRV"))) {
                    game.bullets.add(new MIRVBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("Flare Bomber"))) {
                game.bullets.add(new FAEBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("Nuclear Bomb"))) {
                    game.bullets.add(new NukeBullet(game, (int) x, (int) (y - 10), dx, dy, .75));
                } else if (artillery.getSelectedItem().equals(("Line"))) {
                    game.bullets.add(new LineBullet(game, (int) x, (int) (y - 10), dx, dy, .5));
                } else if (artillery.getSelectedItem().equals(("Cannon Ball"))) {
                    game.bullets.add(new CannonBullet(game, (int) x, (int) (y - 10), dx, dy, 1));
                }else if (artillery.getSelectedItem().equals(("Firework"))) {
                    game.bullets.add(new Firework(game, (int) x, (int) (y - 10), dx, dy, 0.5));
                }else if (artillery.getSelectedItem().equals(("Drill"))) {
                    game.bullets.add(new Drill(game, (int) x, (int) (y - 10), dx, dy, 0.75));
                }else if (artillery.getSelectedItem().equals(("Impact"))) {
                    game.bullets.add(new Impact(game, (int) x, (int) (y - 10), dx, dy, 0.75));
                }else if (artillery.getSelectedItem().equals(("Antimatter Bomb"))) {
                    game.bullets.add(new AntimatterBomb(game, (int) x, (int) (y - 10), dx, dy, 0.5));
                }else if (artillery.getSelectedItem().equals(("Paper Airplane"))) {
                    game.bullets.add(new PaperAirplane(game, (int) x, (int) (y - 10), dx, dy, 0.5));
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
        JSlider source = (JSlider) e.getSource();
        if (source == powerSlider) {
            int power = source.getValue();
            currentTank.setPower(power);
            powerValue.setText(Integer.toString(power));
        } else if (source == angleSlider) {
            int angle = source.getValue();
            currentTank.setGunAngle(angle);
            angleValue.setText(Integer.toString( angle- 90 ));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        //  This method is called when the combo box has been set to a new item.
        //  Reset the amount that is available.
        currentTank.setSelectedWeapon((String) artillery.getSelectedItem());
        if (Objects.equals(artillery.getSelectedItem(), "Normal Bullet")) {
            amountValue.setText("" + "∞");
        } else {
            amountValue.setText("" + currentTank.getWeaponsCount().get(currentTank.getSelectedWeapon()));
        }

    }
}
