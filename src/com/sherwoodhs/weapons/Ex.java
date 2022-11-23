package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.util.ArrayList;

public class Ex extends DefaultBullet {

    public Ex(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity){
            super(game, x, y, deltaX, deltaY, gravity);
        }

    @Override
    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> Players) {
        this.explode(explosions, terrain);
    }
}
