package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class PaperAirplane extends DefaultBullet{
    protected int fuel;
    public PaperAirplane(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity){
        super(game, x, y, deltaX, deltaY, gravity);
        this.fuel = 8;
    }

    @Override
    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> Players) {
        super.checkForTriggeringExplosion(explosions, terrain, Players);
        if (yPosition + 25 > terrain.getGroundLevelAtColumn(xPosition)) {
            if (fuel > 0) {
                fuel --;
                if (deltaY > 0) {
                    deltaY = deltaY / 1.5;
                }
                deltaY --;
            }
        }
    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        ScorchAudioPlayer sap = new ScorchAudioPlayer();
        sap.play("src/com/sherwoodhs/audio/explosion.wav");
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 5, 15, Color.white));
    }
}
