package Abilities;

import Main.GameScreen;

import java.io.IOException;

public class Ability_Friendly extends Ability {
    public Ability_Friendly(int range, int duration, boolean seeDuringAbility, boolean moveDuringAbility, Projectile[] projectiles) {
        super(range, duration,seeDuringAbility, moveDuringAbility, projectiles);
    }

    public Ability_Friendly(Ability ability) {
        super(ability);
    }

    public void update(GameScreen gs) throws IOException {
        for (Projectile projectile : projectiles) {
            projectile.update(gs, 0, 0);
        }
    }
}
