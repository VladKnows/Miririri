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
    public void update(GameScreen gs, int entityX, int entityY) {
        setCoord(entityX, entityY);
        gs.cChecker.CheckHit(gs.player, this);
    }
    
}
