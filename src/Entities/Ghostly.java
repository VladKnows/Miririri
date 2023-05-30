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

public class Ghostly extends Enemies{
    public Ghostly(GameScreen gs, int worldX, int worldY, int HP) throws IOException {
        super(gs, "Ghostly", worldX, worldY, HP);
        name = "Ghostly";

        solid = new Rectangle(2 * scale, 2 * scale, 28 * scale, 28 * scale);
        this.initSpeed = 2;
        this.speed = 2;

        loadAbilities();
        loadStandardImages(6, new int[] {7, 7, 7, 7, 7, 7});
        loadCastImagesAndTimes(1, new int[] {6}, new int[][] {{7, 7, 7, 7, 7, 25}});

        loadAbilityPriority(new int[] {0});
    }

    @Override
    void loadAbilities() throws IOException {
        abilities = new Ability[1];
        abilities[0] = new Ability(60, 24, false, false, new Projectile[]{new Projectile_Static(false, false, true, "Slash", 480, 480, 192, 192, new Rectangle(0, 192, 480, 192), 30, 8, new int[] {3, 3, 3, 3, 3, 3, 3, 3})});
    }

    @Override
    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = worldX - gs.getPlayer().worldX + gs.getPlayer().screenX;
        int screenY = worldY - gs.getPlayer().worldY + gs.getPlayer().screenY;

        if(worldX + gs.tileSize > gs.getPlayer().worldX - gs.getPlayer().screenX &&
                worldX - gs.tileSize< gs.getPlayer().worldX + gs.getPlayer().screenX &&
                worldY + gs.tileSize> gs.getPlayer().worldY - gs.getPlayer().screenY &&
                worldY - gs.tileSize< gs.getPlayer().worldY + gs.getPlayer().screenY) {
            if(!castOn)
                g2.drawImage(imageGetter(), screenX, screenY, gs.tileSize, gs.tileSize, null);
            else
                g2.drawImage(imageGetter(), screenX - 96, screenY - 96, gs.tileSize * 3, gs.tileSize * 3, null);
        }
    }


}
