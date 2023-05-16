package Abilities;

import Main.GameScreen;

import java.awt.*;
import java.io.IOException;

public class Projectile_Static extends Projectile{
    public Projectile_Static(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(friendly, rotatable, seeAfterHit, name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);
        stationary = true;
    }

    public Projectile_Static(boolean friendly, boolean rotatable, boolean seeAfterHit, String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(friendly, rotatable, seeAfterHit, name, width, height, deviationX, deviationY, solid, damage, timeUntilNextHit, numberOfImages, durationsOfImages);
        stationary = true;
    }

    @Override
    void setCoord(int x, int y) {
        if(!coordSet) {
            this.x = x - deviationX;
            this.y = y - deviationY;
            coordSet = true;
        }
    }

    @Override
    public void update(GameScreen gs, int entityX, int entityY) {
        if(!friendly) {
            gs.getcChecker().CheckHit(gs.getPlayer(), this);
            if(itHitOne) {
                itHit = true;
                itHitOne = false;
            }
        } else {
            for(int i = 0; i < gs.enemies.length; i++) {
                if(gs.enemies[i] != null && gs.enemies[i].HP != 0)
                    gs.getcChecker().CheckHit(gs.enemies[i], this);
            }
            if(itHitOne) {
                itHit = true;
                itHitOne = false;
            }
        }
    }
    
}
