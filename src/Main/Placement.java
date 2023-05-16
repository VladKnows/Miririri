package Main;

import Entities.Bat;
import Entities.NPC;
import Objects.PowerUp;

import java.io.IOException;

public class Placement {
    GameScreen gs;

    public Placement(GameScreen gs) {
        this.gs = gs;
    }

    public void placeObject() throws IOException {
        gs.obj[0] = new PowerUp(24 * gs.tileSize, 6 * gs.tileSize, "Boots", "Your speed has increased!");
    }

    public void placeNPC() throws IOException {
        gs.npc[0] = new NPC(13 * gs.tileSize, 32 * gs.tileSize, 11, 3,10, 27, "Mom", 2, 4);
        gs.npc[0].writeDialogue(new String[] {"Please help my daughter!",
                "The monsters took her away!",
                "Thank you so much!",
                "I could never thank you enough"},
                new int[] {200, 200, 100, 200});
    }

    public void placeEnemy() throws IOException {
        gs.enemies[0] = new Bat(gs, 23 * gs.tileSize,9 * gs.tileSize,200);
        gs.enemies[1] = new Bat(gs, 18 * gs.tileSize,10 * gs.tileSize,200);
//        gs.enemies[1] = new Rock_Head(gs, 15 * gs.tileSize, 20 * gs.tileSize, 100);
    }
}
