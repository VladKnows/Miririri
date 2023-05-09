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

        solid = new Rectangle(scale, 6 * scale, 31 * scale, 16 * scale);
        this.initSpeed = 2;
        this.speed = 2;

        loadAbilities();
        loadStandardImages(7, new int[] {7, 7, 7, 7, 7, 7, 7});
        loadCastImagesAndTimes(4, new int[] {2, 5, 2, 3}, new int[][] {{60, 40}, {5, 5, 10, 10, 10}, {5, 18}, {20, 20, 20}});

        loadAbilityPriority(new int[] {100, 200, 300});
    }

    @Override
    void loadAbilities() throws IOException {
        abilities = new Ability[4];
        abilities[0] = new Ability(100, 180, true, false, new Projectile[]{new Projectile_Static("Ultra_Sound", 416, 416, 160, 160, new Ellipse2D.Double(0, 0, 416, 416), 3, 15, 6, new int[] {10, 10, 10, 10, 10, 10})});
        abilities[1] = new Ability(60, 40, false, false, new Projectile[]{new Projectile_Static("Bat_Bite", 156, 156, 30, 30, new Ellipse2D.Double(0, 0, 156, 156), 30, 2, new int[] {10, 30})});
        abilities[2] = new Ability(40, 40, false, false, new Projectile[]{new Projectile_Static("Wing_Swing", 96, 96, 0, 0, new Rectangle(0, 0, 96, 96), 40, 5, new int[] {5, 5, 5, 5, 20})});
        abilities[3] = new Ability(150, 300, true, true, new Projectile[]{new Projectile_Moving("Bat_Ball", 96, 96, 0, -50, gs.getPlayer().worldX, gs.getPlayer().worldY, 3, false, new Ellipse2D.Double(0, 0, 96, 96), 5, 10,11, new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2})});
    }

}
