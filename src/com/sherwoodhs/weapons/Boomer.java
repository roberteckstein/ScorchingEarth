package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;

public class Boomer extends DefaultBullet {
    protected double face;
    public Boomer(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity, int di) {
        super(game, x, y, deltaX, deltaY, gravity);
        if (di > 0) {
            this.face = -0.05;
        }else if (di < 0){
            this.face = 0.05;
        }else {
            this.face = 0;
        }
    }

    @Override
    public void moveBullet() {
        super.moveBullet();
        deltaX += face;
    }
}
