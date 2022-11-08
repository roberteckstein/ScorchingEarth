package com.sherwoodhs.tank;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class ScorchTank {

    //  This class represents the players tank, and all the data that goes
    //  with it. This includes its position, whether it is destroyed, the
    //  player number that matches to it, the angle, the power, the color
    //  of the tank, etc.

    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;

    private int x;
    private int y;

    private boolean destroyed = false;

    private int playerNumber;
    private Color playerColor;

    private int gunAngle = 90;
    private int power;

    private double radius = 15.0;

    private HashMap<String, Integer> weaponsCount;
    private String selectedWeapon;

    //  This is a utility class provided by the Swing APIs to enable property change
    //  listeners and events in your own classes. Look up the Javadocs on this one.
    //  It makes life much easier.
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ScorchTank(int playerNumber, int x, int gunAngle, Color playerColor) {

        this.playerNumber = playerNumber;

        this.x = x;
        this.gunAngle = gunAngle;
        this.power = 50;
        this.playerColor = playerColor;

        this.weaponsCount = new HashMap<>();
        this.weaponsCount.put("Normal Bullet", new Integer(100));
        this.weaponsCount.put("MIRV", new Integer(2));

        //  Set the selected weapon to the first one in the list.
        this.selectedWeapon = (String)weaponsCount.keySet().toArray()[0];

    }

    //  Standard add and remove listener events.

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public HashMap<String, Integer> getWeaponsCount() {
        return weaponsCount;
    }

    public int getGunAngle() {
        return gunAngle;
    }

    //  This setter fires off a property change if the value is different. Any
    //  classes that register as a listener will be notified that this property
    //  has changed.

    public void setGunAngle(int gunAngle) {
        int oldValue = this.gunAngle;
        this.gunAngle = gunAngle;
        this.pcs.firePropertyChange("gunAngle", oldValue, this.gunAngle);
    }

    public int getPower() {
        return power;
    }

    //  This setter fires off a property change if the value is different. Any
    //  classes that register as a listener will be notified that this property
    //  has changed.

    public void setPower(int power) {
        int oldValue = this.power;
        this.power = power;
        this.pcs.firePropertyChange("power", oldValue, this.power);
    }

    public int getX() {
        return x;
    }

    //  Will the X position of a tank ever change? Up to you.

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    //  This setter fires off a property change if the value is different. Any
    //  classes that register as a listener will be notified that this property
    //  has changed.

    public void setY(int y) {
        int oldValue = this.y;
        this.y = y;
        this.pcs.firePropertyChange("y", oldValue, this.y);
    }


    public String getSelectedWeapon() {
        return selectedWeapon;
    }

    //  This setter fires off a property change if the value is different. Any
    //  classes that register as a listener will be notified that this property
    //  has changed.

    public void setSelectedWeapon(String selectedWeapon) {
        String oldValue = this.selectedWeapon;
        this.selectedWeapon = selectedWeapon;
        this.pcs.firePropertyChange("selectedWeapon", oldValue, this.selectedWeapon);
    }

    public void draw(Graphics2D g) {

        g.setPaint(playerColor);
        g.fill3DRect(x, y, WIDTH, HEIGHT, true);
        g.setStroke(new BasicStroke(2));

        //  This is standard trigonometric math. Let me know if you don't understand it.
        //  Or ask your math teacher about radians and sine/cosine.

        double angle = Math.toRadians((double)gunAngle-90.0);
        g.drawLine(x+(WIDTH/2), y,
                x+(WIDTH/2)+(int)(radius * Math.sin(angle)), y-(int)(radius * Math.cos(angle)));

    }

}
