package Abilities;

import Main.GameScreen;

import java.awt.*;

public class Ability {
    public int range;
    public int duration;
    public boolean seeDuringAbility;
    public boolean moveDuringAbility;
    public Projectile[] projectiles;

    public Ability(int range, int duration, boolean seeDuringAbility, boolean moveDuringAbility, Projectile[] projectiles) {
        this.range = range;
        this.duration = duration;
        this.seeDuringAbility = seeDuringAbility;
        this.moveDuringAbility = moveDuringAbility;
        this.projectiles = projectiles;
    }

    public void update(GameScreen gs, int entityX, int entityY) {
        for(int i = 0; i < projectiles.length; i++) {
            projectiles[i].update(gs, entityX, entityY);
        }
    }

    public void draw(GameScreen gs, Graphics2D g2) {
        for(int i = 0; i < projectiles.length; i++) {
            projectiles[i].draw(gs, g2);
        }
    }
}