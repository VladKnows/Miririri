package Main;

import Entities.Bat;
import Entities.NPC;
import Entities.Rock_Head;
import Objects.Item;
import Objects.PowerUp;
import Objects.Teleporter;
import Util.CoordinatesMapXY;

import java.io.IOException;

import static Objects.SuperObject.*;
import static Objects.SuperObjectFactory.makeObject;

public class Placement {
    GameScreen gs;

    public Placement(GameScreen gs) {
        this.gs = gs;
    }

    int searchForPlaceInObj(int mapNumber) {
        for(int i = 0; i < gs.obj[mapNumber].length; i++) {
            if(gs.obj[mapNumber][i] == null)
                return i;
        }
        return 0;
    }

    void placeOneObject(int type, String name, String message, CoordinatesMapXY coordinates, int... param) throws IOException {
        int onMap = coordinates.getMap();
        int space = searchForPlaceInObj(onMap);
        gs.obj[onMap][space] = makeObject(type, name, message, coordinates.getX(), coordinates.getY(), param);
    }

    void placeMultipleObjects(int type, String name, String message, CoordinatesMapXY[] coordinates, int[]... param) throws IOException {
        for(int i = 0; i < coordinates.length; i++) {
            if(param.length > 0) {
                placeOneObject(type, name, message, coordinates[i], param[i]);
            } else {
                placeOneObject(type, name, message, coordinates[i]);
            }
        }
    }

    static CoordinatesMapXY[] makeCoordinatesArray(int []array) {
        int l = array.length / 3;
        CoordinatesMapXY []coord = new CoordinatesMapXY[l];
        for (int i = 0 ; i < l; i++) {
            int index = i * 3;
            coord[i] = new CoordinatesMapXY(array[index], array[index + 1], array[index + 2]);
        }
        return coord;
    }

    public void placeObject() throws IOException {
        CoordinatesMapXY []coords = null;
        int []tmp;

        tmp = new int[] {0, 17, 24, 0, 38, 74, 0, 11, 47, 1, 28, 8, 1, 28, 21, 1, 31, 31, 2, 38, 5, 2, 24, 29, 2, 27, 19, 2, 49, 14, 2, 43, 21};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_ITEM, "Health_Potion", "HP Potion, gives 30 HP", coords);
        tmp = new int[] {0, 16, 24, 0, 20, 74, 1, 44, 27, 1, 23, 12, 1, 13, 18, 2, 60, 5, 2, 52, 22, 2, 7, 24, 2, 7, 33, 2, 8, 18};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_ITEM, "Mana_Potion", "MP Potion, gives 50 MP", coords);
        tmp = new int[] {0, 9, 56, 0, 35, 50, 1, 45, 5, 1, 11, 29, 2, 37, 8, 2, 25, 17, 2, 47, 30};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_ITEM, "Stamina_Potion", "Stamina Potion, fully restores ST", coords);
        tmp = new int[] {0, 21, 60, 2, 7, 5};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_POWERUP, "Boots", "Your movement speed has increased!", coords);
        tmp = new int[] {1, 46, 8, 2, 40, 16};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_POWERUP, "Sword", "Your damage has increased!", coords);
        tmp = new int[] {1, 13, 26, 2, 54, 18};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_POWERUP, "Gem", "Your maximum MP has increased!", coords);
        tmp = new int[] {0, 12, 68, 2, 37, 20, 0, 25, 10};
        coords = makeCoordinatesArray(tmp);
        placeMultipleObjects(OBJECT_ITEM, "Hour_Glass", "Stops enemies in place for a short time.", coords);
        placeOneObject(OBJECT_ITEM, "Scroll_Ability_0", "You learned Shot! A long range projectile!", new CoordinatesMapXY(0, 18, 17));
        placeOneObject(OBJECT_ITEM, "Scroll_Ability_1", "You learned Loader! A multi-target long range ability!", new CoordinatesMapXY(0, 26, 73));
        placeOneObject(OBJECT_ITEM, "Scroll_Ability_2", "You learned Abstract! A long range, high damage, slow projectile!", new CoordinatesMapXY(1, 24, 26));
        placeOneObject(OBJECT_ITEM, "Scroll_Ability_3", "You learned Death Spikes! A short range, very high damage ability!", new CoordinatesMapXY(2, 60, 17));
        placeOneObject(OBJECT_TELEPORTER, "Teleporter", "2nd Level", new CoordinatesMapXY(0, 38, 7), 1, 8, 6);
        placeOneObject(OBJECT_TELEPORTER, "Teleporter", "Village", new CoordinatesMapXY(1, 8, 6), 0, 38, 7);
        placeOneObject(OBJECT_TELEPORTER, "Teleporter", "3rd Level", new CoordinatesMapXY(0, 9, 69), 2, 62, 5);
        placeOneObject(OBJECT_TELEPORTER, "Teleporter", "Village", new CoordinatesMapXY(2, 62, 5), 0, 9, 69);

    }

    public void placeNPC() throws IOException {
        gs.npc[0][0] = new NPC(13 * gs.tileSize, 32 * gs.tileSize, 11, 3,10, 27, "Mom", 2, 4);
        gs.npc[0][0].writeDialogue(new String[] {"Please help my daughter!",
                "The monsters took her away!",
                "Thank you so much!",
                "I could never thank you enough"},
                new int[] {200, 200, 100, 200});
    }

    public void placeEnemy() throws IOException {
        gs.enemies[0][0] = new Rock_Head(gs, 23 * gs.tileSize,9 * gs.tileSize,200);
        gs.enemies[0][1] = new Bat(gs, 18 * gs.tileSize,10 * gs.tileSize,200);

//        gs.enemies[1] = new Rock_Head(gs, 15 * gs.tileSize, 20 * gs.tileSize, 100);
    }
}
