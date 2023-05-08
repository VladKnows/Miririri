package Abilities;

import Main.GameScreen;

import java.awt.*;
import java.io.IOException;

public class Projectile_Static extends Projectile{
    public Projectile_Static(String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(name, width, height, deviationX, deviationY, solid, damage, numberOfImages, durationsOfImages);
        stationary = true;
    }

    public Projectile_Static(String name, int width, int height, int deviationX, int deviationY, Shape solid, int damage, int timeUntilNextHit, int numberOfImages, int[] durationsOfImages) throws IOException {
        super(name, width, height, deviationX, deviationY, solid, damage, timeUntilNextHit, numberOfImages, durationsOfImages);
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
        gs.getcChecker().CheckHit(gs.getPlayer(), this);
    }
    
}
