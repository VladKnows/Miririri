package Main;

import Util.ImageVector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static Main.DataBase.insertB;

public class UI {
    GameScreen gs;
    Font font, font1, font2;
    BufferedImage ST_Bar, ST_One, HP_Bar, HP_One, MP_Bar, MP_One;
    BufferedImage []itemFrame = new BufferedImage[8];
    BufferedImage []ability = new BufferedImage[4];
    Buttons []buttons = new Buttons[8];
    ImageVector selected;

    static boolean messageCheck = false;
    boolean dialogueCheck = false;

    static int messageTimer, dialogueTimer;
    int npcNumber;
    int dialogueX, dialogueY;
    static String message;

    final int[] HotBarAbilities = new int[] { 20, 130, 240, 350 };
    final int[] HotBarItems = new int[] { 440, 330, 220, 110 };



    public UI(GameScreen gs) throws IOException {
        this.gs = gs;
        font = new Font("Open Sans", Font.BOLD, 30);
        font1 = new Font("Open Sans", Font.BOLD, 13);
        font2 = new Font("Open Sans", Font.ITALIC, 100);
        for (int i = 0 ; i < 8; i++) {
            itemFrame[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame" + (i + 1) + ".png")));
        }
        for (int i = 0 ; i < 4; i++) {
            ability[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Player_Ability_" + i + ".png")));
        }
        selected = new ImageVector("/res/UI", "Selected", 60, 13, new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 });

        ST_Bar = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/ST_Bar.png")));
        ST_One = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/ST_One.png")));
        HP_Bar = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/HP_Bar.png")));
        HP_One = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/HP_One.png")));
        MP_Bar = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/MP_Bar.png")));
        MP_One = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/MP_One.png")));

        int x = 1536 / 2 - 48 * 3;
        int y = 845 / 2 - 120 * 2;
        buttons[0] = new Buttons("/res/UI/Play_Button", x, y, 288, 96);
        buttons[4] = new Buttons("/res/UI/Back_Button", x, y, 288, 96);
        y += 120;
        buttons[5] = new Buttons("/res/UI/Save_Button", x, y, 288, 96);
        buttons[1] = new Buttons("/res/UI/Load_Button", x, y, 288, 96);
        y += 120;
        buttons[6] = new Buttons("/res/UI/Menu_Button", x, y, 288, 96);
        buttons[2] = new Buttons("/res/UI/Scores_Button", x, y, 288, 96);
        y += 120;
        buttons[3] = new Buttons("/res/UI/Quit_Button", x, y, 288, 96);

        dialogueX = gs.screenWidthHalf - 200;
        dialogueY = 30;
        dialogueTimer = 0;
    }

    public boolean isDialogueCheck() {
        return dialogueCheck;
    }

    public void talkToNPC(int index) {
        gs.getUi().dialogueCheck = true;
        gs.getUi().npcNumber = index;
    }

    void state0(Graphics2D g2) throws IOException {
        for(int i = 0; i < 4; i++) {
            g2.drawImage(buttons[i].getImage(), buttons[i].bounds.x, buttons[i].bounds.y, buttons[i].bounds.width, buttons[i].bounds.height, null);
            if(buttons[i].currentLabel == 2) {
                switch (i) {
                    case 0:
                        GameScreen.setCurrentState(1);
                        break;
                    case 1:
                        gs.loadAllVariables("DataBase", "TableForGame");
                        GameScreen.setCurrentState(1);
                        break;
                    case 2:
                        GameScreen.setCurrentState(4);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
                break;
            }
        }
    }

    void state1(Graphics2D g2) {
        gs.mouse.leftClick = false;

        g2.setFont(font);
        g2.drawString("", 0, 0);
        g2.drawString("Enemies Remaining: " + gs.numberOfRemainingEnemies[gs.onMap], gs.getWidth() - 350, 30);

        g2.drawImage(HP_Bar, 20, 15,null);
        int HPWidth = (int) ((float) gs.player.HP / (float) gs.player.maxHP * 100);
        g2.drawImage(HP_One, 22, 17, HPWidth, 12, null);

        g2.drawImage(ST_Bar, 150, 15,null);
        int STWidth = (int) ((float) gs.player.ST / (float) gs.player.maxST * 100);
        g2.drawImage(ST_One, 152, 17, STWidth, 12, null);

        g2.drawImage(MP_Bar, 280, 15, null);
        int MPWidth = (int) ((float) gs.player.MP / (float) gs.player.maxMP * 100);
        g2.drawImage(MP_One, 282, 17, MPWidth, 12, null);

        int yOnScreen = gs.getHeight() - 100;

        for(int i = 0; i < 4; i++) {
            g2.drawImage(itemFrame[i], HotBarAbilities[i], yOnScreen, 90, 90, null);
            if(gs.player.abilityUnlocked[i])
                g2.drawImage(ability[i], HotBarAbilities[i], yOnScreen, 90, 90, null);
        }

        for(int i = 0; i < 4; i++) {
            g2.drawImage(itemFrame[i + 4], gs.getWidth() - HotBarItems[i], yOnScreen, 90, 90, null);
            if(gs.player.items[i] != null) {
                g2.drawImage(gs.player.items[i].getImage(), gs.getWidth() - HotBarItems[i], yOnScreen, 90, 90, null);
                if(gs.player.numberOfItems[i] != 1) {
                    g2.setFont(font1);
                    g2.drawString("x" + gs.player.numberOfItems[i], gs.getWidth() - HotBarItems[i] + 67, yOnScreen + 81);
                }
            }
        }

        g2.drawImage(selected.GetImage(), HotBarAbilities[gs.player.onAbility], yOnScreen, 90, 90, null);
        g2.drawImage(selected.GetImage(), gs.getWidth() - HotBarItems[gs.player.onItem], yOnScreen, 90, 90, null);

        g2.setFont(font);

        if(messageCheck) {
            g2.drawString(message, 20, 70);
            messageTimer++;
            if(messageTimer == 320) {
                messageCheck = false;
                messageTimer = 0;
            }
        }

        if(dialogueCheck) {
            g2.drawString(gs.npc[gs.onMap][npcNumber].dialogue[gs.npc[gs.onMap][npcNumber].onLine], dialogueX, dialogueY);
            dialogueTimer++;
            if(dialogueTimer >= gs.npc[gs.onMap][npcNumber].dialogueDuration[gs.npc[gs.onMap][npcNumber].onLine]) {
                if(gs.npc[gs.onMap][npcNumber].toLine - 1 > gs.npc[gs.onMap][npcNumber].onLine)
                    gs.npc[gs.onMap][npcNumber].onLine++;
                else
                    dialogueCheck = false;
                dialogueTimer = 0;
            }
        }
    }
    void state2(Graphics2D g2) throws IOException {
        for(int i = 4; i < 7; i++) {
            g2.drawImage(buttons[i].getImage(), buttons[i].bounds.x, buttons[i].bounds.y, buttons[i].bounds.width, buttons[i].bounds.height, null);
            if(buttons[i].currentLabel == 2) {
                switch (i) {
                    case 4:
                        GameScreen.setCurrentState(1);
                        break;
                    case 5:
                        int []v = new int[4];
                        for (int k = 0; k < 4; k++) {
                            if(gs.player.abilityUnlocked[k])
                                v[k] = 1;
                        }
                        String []s = new String[4];
                        for(int j = 0; j < 4; j++) {
                            if(gs.player.items[j] == null) {
                                s[j] = null;
                            } else {
                                s[j] = gs.player.items[j].getName();
                            }
                        }
                        insertB("DataBase", "TableForGame", gs.onMap, gs.player.getWorldX(), gs.player.getWorldY(), gs.player.initSpeed, GameScreen.scor, gs.player.maxHP, gs.player.maxST, gs.player.maxMP, v[0], v[1], v[2], v[3], gs.numberOfRemainingEnemies[0], gs.numberOfRemainingEnemies[1], gs.numberOfRemainingEnemies[2], s[0], s[1], s[2], s[3], gs.player.numberOfItems[0], gs.player.numberOfItems[1], gs.player.numberOfItems[2], gs.player.numberOfItems[3], GameScreen.scores[0], GameScreen.scores[1], GameScreen.scores[2]);
                        break;
                    case 6:
                        GameScreen.setCurrentState(0);
                        break;
                }
                break;
            }
        }
    }
    void state3(Graphics2D g2) {
        g2.setFont(font2);
        g2.drawString("Victory!", gs.getWidth() / 2 - 150, gs.getHeight() / 2);
        int []v = new int[4];
        for (int k = 0; k < 4; k++) {
            if(gs.player.abilityUnlocked[k])
                v[k] = 1;
        }
        insertB("DataBase", "TableForGame", gs.onMap, gs.player.getWorldX(), gs.player.getWorldY(), gs.player.initSpeed, GameScreen.scor, gs.player.maxHP, gs.player.maxST, gs.player.maxMP, v[0], v[1], v[2], v[3], gs.numberOfRemainingEnemies[0], gs.numberOfRemainingEnemies[1], gs.numberOfRemainingEnemies[2], null, null, null, null, gs.player.numberOfItems[0], gs.player.numberOfItems[1], gs.player.numberOfItems[2], gs.player.numberOfItems[3], GameScreen.scores[0], GameScreen.scores[1], GameScreen.scores[2]);

    }
    void state4(Graphics2D g2) throws IOException {
        g2.drawImage(buttons[4].getImage(), buttons[4].bounds.x, buttons[4].bounds.y, buttons[4].bounds.width, buttons[4].bounds.height, null);
        if(buttons[4].currentLabel == 2)
            GameScreen.setCurrentState(0);
        int []scores = GameScreen.scores;
        for(int i = 0; i < 2; i++) {
            for(int j = i + 1; j < 2; j++) {
                if(scores[i] < scores[j]) {
                    int tmp = scores[i];
                    scores[i] = scores[j];
                    scores[j] = tmp;
                }
            }
        }
        int x = gs.getWidth() / 2 - 30;
        int y = gs.getHeight() / 2;
        for(int i = 0; i < 3; i++) {
            g2.drawString(scores[i] + " ", x, y);
            y += 30;
        }
    }


    public void draw(Graphics2D g2) throws IOException {
        switch (GameScreen.getCurrentState()) {
            case 0 -> state0(g2);
            case 1 -> state1(g2);
            case 2 -> state2(g2);
            case 3 -> state3(g2);
            case 4 -> state4(g2);
        }
        gs.mouse.leftClick = false;
    }

    public static void loadMessage(String message) {
        UI.message = message;
        UI.messageCheck = true;
        UI.messageTimer = 0;
    }
}
