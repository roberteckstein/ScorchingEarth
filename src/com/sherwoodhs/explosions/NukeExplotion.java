package com.sherwoodhs.explosions;

import com.sherwoodhs.terrain.Terrain;

import java.awt.*;

public class NukeExplotion extends DefaultExplosion{
    public NukeExplotion(Terrain terrain, int x, int y){
        super(terrain, x, y, 1, 50, Color.orange);
    }
}
