package Main;

import Util.ImageVector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    GameScreen gs;
    Font font, font1;
    BufferedImage ST_Bar, ST_One, HP_Bar, HP_One, MP_Bar, MP_One;
    BufferedImage []itemFrame = new BufferedImage[8];
    BufferedImage []ability = new BufferedImage[4];
    ImageVector selected;

    static boolean messageCheck = false;
    boolean dialogueCheck = false;

    static int messageTimer, dialogueTimer;
    int objectNumber, npcNumber;
    int dialogueX, dialogueY;
    static String message;

    final int[] HotBarAbilities = new int[] { 20, 130, 240, 350 };
    final int[] HotBarItems = new int[] { 440, 330, 220, 110 };


    public UI(GameScreen gs) throws IOException {
        this.gs = gs;
        font = new Font("Open Sans", Font.BOLD, 30);
        font1 = new Font("Open Sans", Font.BOLD, 13);
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

    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.drawString("", 0, 0);

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

    public static void loadMessage(String message) {
        UI.message = message;
        UI.messageCheck = true;
        UI.messageTimer = 0;
    }

}
