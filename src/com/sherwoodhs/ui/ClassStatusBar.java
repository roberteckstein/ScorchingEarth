package com.sherwoodhs.ui;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.weapons.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Objects;

public class ClassStatusBar extends JPanel implements ActionListener, ItemListener, ChangeListener {

    //  This is a Swing panel that appears below the playfield. It allows
    //  players to choose what class they would like to play as at the beginning
    //  of the game.

    //  Reference the game object
    ScorchGame game;

    //  Reference to the current tank object
    Tank currentTank;

    //  Following are Swing components that are used in the panel
    JComboBox classes = new JComboBox();

    JLabel space1 = new JLabel("    ");     //  Currently unused, but is there if you want it

    JButton confirmButton = new JButton("Confirm");

    HashMap<String, HashMap<String, Integer>> classList;


    JDialog classFrame = new JDialog();
    JButton infoBtn = new JButton("Class Info");
    JEditorPane classInfo = new JEditorPane("text/html", "");
    String classTxt = "<b>Class Info</b><br>ALL classes contain infinite default bullets." +
            "<br><br><b>Sargent class</b>" + "<br>Standard Issue, Good ol' reliable." +
            "<br><br><b>Scientist class</b>" + "<br>Care to test some new weapons, Straight from the lab!" +
            "<br><br><b>Nuclear class</b>" + "<br>A pair of Warheads and whatever is left in the fridge." +
            "<br><br><b>Engineer class</b>" + "<br>All american engineering, the perfect bunker buster." +
            "<br><br><b>Pirate class</b>" + "<br>Ahoy, Prepare this pack of powerful projectiles perfect for plundering Foes ";

    public ClassStatusBar(ScorchGame game) {

        //  Must call the JPanel superclass constructor
        super();

        //  Set the reference to the current game object
        this.game = game;

        classList = new HashMap<>();
        
        /*
        REALLY IMPORTANT SECTION HERE FOR WEAPONS PEOPLE AND EVERYONE WORKING ON MORE CLASSES
        */
        // A Hashmap of weapons each class contains
        HashMap<String, Integer> sergeantWeapons = new HashMap<>();
        HashMap<String, Integer> nuclearWeapons = new HashMap<>();
        HashMap<String, Integer> scientistWeapons = new HashMap<>();
        HashMap<String, Integer> engineerWeapons = new HashMap<>();
        HashMap<String, Integer> pirateWeapons = new HashMap<>();

        // Putting weapons into their respective classes. Normal Bullet is in them by default
        sergeantWeapons.put("MIRV", 2);
        sergeantWeapons.put("Mortar", 1);
        sergeantWeapons.put("Sharp Shot", 3);

        nuclearWeapons.put("Nuclear Bomb", 2);
        nuclearWeapons.put("Sharp Shot", 3);
        nuclearWeapons.put("Boomerang", 1);

        scientistWeapons.put("Flare Bomber", 2);
        scientistWeapons.put("Skipper", 1);
        scientistWeapons.put("Impact", 2);
        scientistWeapons.put("Mirror", 1);

        engineerWeapons.put("Line", 2);
        engineerWeapons.put("Paper Airplane", 1);
        engineerWeapons.put("Drill", 1);
        engineerWeapons.put("Firework", 2);

        pirateWeapons.put("Grape shot", 5);
        pirateWeapons.put("Cannon Ball", 2);
        pirateWeapons.put("Anchor", 2);

        //Puts Each Class in the Hashmap of classes.
        classList.put("Sergeant", sergeantWeapons);
        classList.put("Nuclear", nuclearWeapons);
        classList.put("Scientist", scientistWeapons);
        classList.put("Engineer", engineerWeapons);
        classList.put("Pirate", pirateWeapons);

        /*
        End of Really Important Section of This file
        */
        
        // lock given component sizes in the panel
        setLayout(new FlowLayout());

        // Set the visuals of the fire button
        confirmButton.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.black));
        confirmButton.setPreferredSize(new Dimension(60, 30));
        confirmButton.setFocusPainted(false);
        confirmButton.setOpaque(true);

        //  Add them in order from left to right
        classes.setPreferredSize(new Dimension(150, 25));

        classes.setSelectedItem("Medium");

        add(infoBtn);
        add(classes);
        add(confirmButton);


        //  Add listeners for each of the Swing components. When the components
        //  are interacted with (button pressed, combo box selected, the appropriate
        //  listener methods are called.

        classes.addItemListener(this);
        confirmButton.addActionListener(this);
        infoBtn.addActionListener(this);
    }

    //  Reset the contents of the combo box based on the current weapon
    //  count inside the tank object passed in.

    public void resetClasses() {

        classes.removeAllItems();
        for (String s : classList.keySet()) {
            classes.addItem(s);
        }
    }

    //  Reset the status of the Swing components in the panel based on
    //  the tank object passed in

    public void setStatus(Tank tank) {

        currentTank = tank;

        //  Reset the contents of the combo box
        resetClasses();
        drawStatusPanel(tank);
        classes.setSelectedItem("Medium");
    }

    public void drawStatusPanel(Tank tank) {
        currentTank = tank;
        classes.setSelectedItem(currentTank.getSelectedWeapon());

        //  Reset the amount that is available
        //  If it's the normal bullet it sets the value label to ∞

        //  Reset the angle and the power

        // changing fireButton color based on current player
        confirmButton.setBackground(currentTank.playerColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //  This method handles all the button presses. First, get
        //  the angle and power of the current tank

        if (e.getSource() == confirmButton && game.waitForPlayerFire) {
            currentTank.addWeapons(classList.get(classes.getSelectedItem()));
            game.waitForPlayerFire = false;
        } else if(e.getSource() == infoBtn){
            classFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            classFrame.setSize(300, 470);
            classInfo.setEditable(false);
            classInfo.setText(classTxt);
            classFrame.add(classInfo);
            classFrame.setResizable(false);
            classFrame.setTitle("Class Info");

            classFrame.setVisible(true);
        }

        //  Redraw the status panel

        drawStatusPanel(currentTank);
    }


    //Slider listeners

    @Override
    public void itemStateChanged(ItemEvent e) {

        //  This method is called when the combo box has been set to a new item.
        //  Reset the amount that is available.
        currentTank.setSelectedWeapon((String) classes.getSelectedItem());
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
