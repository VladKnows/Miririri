package Entities;

import Main.GameScreen;

import java.awt.*;
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

//        this.numberOfImages = 4;
//        loadStandardImages(4);
//
//        castTimes = new int[] {60};
//        loadCastImagesAndTimes(1, new int[] {8}, new int[][] {{6, 6, 23, 6, 6, 6, 6, 1}});
    }

    @Override
    void loadAbilities() throws IOException {
//        abilities = new Ability[1];
//        abilities[0] = new Abilities("Ground_Rise", 400, 400, 0, 0, 400, 400,60, 30, 5, new int[] {6, 6, 40, 4, 4}, true, false, 0, true, 100, 152, 152);
//        abilities[1] = new Abilities("Bite", 156, 156, 0, 0, 156, 156,40,30, 2, new int[] {10, 30}, false, false, 0, false, 30, 30, 30);
//        abilities[2] = new Abilities("Wing_Swing", 96, 96, 0, 0, 96, 96,40, 20, 5, new int[] {5, 5, 5, 5, 20}, false, false, 0, false, 20, 0, 0);
//        abilities[3] = new Abilities("Bat_Ball", 96, 96, 18, 18, 66 , 60,250, 40, 11, new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, true, true, 3, true, 180, 0, -80);
//
//        onAbility = 3;
    }

}
