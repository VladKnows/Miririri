package Entities;

import Abilities.Ability;
import Abilities.Projectile;
import Abilities.Projectile_Moving;
import Abilities.Projectile_Static;
import Main.GameScreen;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import static Main.GameScreen.scale;

public class Rock_Head extends Enemies {
    public Rock_Head(GameScreen gs, int worldX, int worldY, int HP) throws IOException {
        super(gs, "Rock_Head", worldX, worldY, HP);
        name = "Rock_Head";

        solid = new Rectangle(9 * scale, 20 * scale, 14 * scale, 12 * scale);
        this.initSpeed = 1;
        this.speed = 1;

        loadAbilities();
        loadStandardImages(4, new int[] {10, 10, 10, 10});
        loadCastImagesAndTimes(2, new int[] {8, 15}, new int[][] {{6, 6, 6, 6, 6, 6, 6, 16}, {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10}});

        loadAbilityPriority(new int[] {400});
    }

    @Override
    void loadAbilities() throws IOException {
        abilities = new Ability[2];
        abilities[0] = new Ability(70, 90, true, false, new Projectile[]{new Projectile_Static(false, false, true, "Ground_Rise", 416, 416, 160, 200, new Rectangle(30, 30, 356, 356), 20, 5, new int[] {5, 5, 74, 3, 3})});
        abilities[1] = new Ability(230, 250, true, false, new Projectile[]{new Projectile_Moving(false, false, false, "Tornado", 192, 192, 48, 48, gs.getPlayer().worldX + 48, gs.getPlayer().worldY + 48, 2, false, new Rectangle(20, 0, 152, 180), 30, 4, new int[] {3, 3, 3, 3})});
    }

}
