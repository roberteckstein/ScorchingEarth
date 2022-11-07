package com.sherwoodhs.tank;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class ScorchTank {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;

    private int x;
    private int y;

    private boolean destroyed = false;

    private int playerNumber;
    private int gunAngle = 90;
    private double radius = 15.0;
    private HashMap<String, Integer> weaponsCount;

    private String selectedWeapon;

    private Color playerColor;
    private int power;

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

        this.selectedWeapon = (String)weaponsCount.keySet().toArray()[0];

    }

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

    public void setGunAngle(int gunAngle) {
        int oldValue = this.gunAngle;
        this.gunAngle = gunAngle;
        this.pcs.firePropertyChange("gunAngle", oldValue, this.gunAngle);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        int oldValue = this.power;
        this.power = power;
        this.pcs.firePropertyChange("power", oldValue, this.power);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        int oldValue = this.y;
        this.y = y;
        this.pcs.firePropertyChange("y", oldValue, this.y);
    }


    public String getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(String selectedWeapon) {
        String oldValue = this.selectedWeapon;
        this.selectedWeapon = selectedWeapon;
        this.pcs.firePropertyChange("selectedWeapon", oldValue, this.selectedWeapon);
    }

    public void draw(Graphics2D g) {

        g.setPaint(playerColor);
        g.fill3DRect(x, y, WIDTH, HEIGHT, true);
        g.setStroke(new BasicStroke(2));

        double angle = Math.toRadians((double)gunAngle-90.0);
        g.drawLine(x+(WIDTH/2), y,
                x+(WIDTH/2)+(int)(radius * Math.sin(angle)), y-(int)(radius * Math.cos(angle)));

    }

}
