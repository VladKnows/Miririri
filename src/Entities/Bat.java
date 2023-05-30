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

public class Bat extends Enemies {
    public Bat(GameScreen gs, int worldX, int worldY, int HP) throws IOException {
        super(gs, "Bat", worldX, worldY, HP);
        name = "Bat";

        solid = new Rectangle(5 * scale, 6 * scale, 22 * scale, 10 * scale);
        this.initSpeed = 2;
        this.speed = 2;

        loadAbilities();
        loadStandardImages(7, new int[] {7, 7, 7, 7, 7, 7, 7});
        loadCastImagesAndTimes(4, new int[] {2, 5, 2, 3}, new int[][] {{30, 20}, {3, 3, 7, 7, 7}, {5, 13}, {20, 20, 20}});

        loadAbilityPriority(new int[] {120, 200, 300});
    }

    @Override
    void loadAbilities() throws IOException {
        abilities = new Ability[4];
        abilities[0] = new Ability(200, 180, true, false, new Projectile[]{new Projectile_Static(false, false, true, "Ultra_Sound", 416, 416, 160, 160, new Ellipse2D.Double(0, 0, 416, 416), 2, 15, 6, new int[] {10, 10, 10, 10, 10, 10})});
        abilities[1] = new Ability(90, 40, false, false, new Projectile[]{new Projectile_Static(false, false, true, "Bat_Bite", 156, 156, 30, 30, new Ellipse2D.Double(0, 0, 156, 156), 20, 2, new int[] {10, 30})});
        abilities[2] = new Ability(70, 40, false, false, new Projectile[]{new Projectile_Static(false, false, true, "Wing_Swing", 96, 96, 0, 0, new Rectangle(0, 0, 96, 96), 10, 5, new int[] {5, 5, 5, 5, 20})});
        abilities[3] = new Ability(350, 300, true, true, new Projectile[]{new Projectile_Moving(false, false, true, "Bat_Ball", 96, 96, 0, -50, gs.getPlayer().worldX, gs.getPlayer().worldY, 3, false, new Ellipse2D.Double(0, 0, 96, 96), 4, 10,11, new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2})});
    }

}
