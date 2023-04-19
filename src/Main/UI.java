package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    GameScreen gs;
    Font font;
    BufferedImage ST_Bar, ST_One, HP_Bar, HP_One, MP_Bar, MP_One;
    BufferedImage itemFrame1, itemFrame2,itemFrame3,itemFrame4,itemFrame5,itemFrame6,itemFrame7,itemFrame8;

    boolean messageCheck = false;
    boolean dialogueCheck = false;

    int messageTimer, dialogueTimer;
    int objectNumber, npcNumber;
    int dialogueX, dialogueY;
    String message;


    public UI(GameScreen gs) throws IOException {
        this.gs = gs;
        font = new Font("Open Sans", Font.BOLD, 30);
        itemFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame1.png")));
        itemFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame2.png")));
        itemFrame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame3.png")));
        itemFrame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame4.png")));
        itemFrame5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame5.png")));
        itemFrame6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame6.png")));
        itemFrame7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame7.png")));
        itemFrame8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/UI/Item_Frame8.png")));
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

    public void pickedUp(int index) {
        gs.getUi().messageCheck = true;
        gs.getUi().objectNumber = index;
        gs.getUi().message = gs.getObj()[index].getMessage();
        gs.setObj(index, null);
    }

    public void talkToNPC(int index) {
        gs.getUi().dialogueCheck = true;
        gs.getUi().npcNumber = index;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.drawString("", 0, 0);

        g2.drawImage(HP_Bar, 20, 15,null);
        g2.drawImage(HP_One, 22, 17, gs.player.HP, 12, null);

        g2.drawImage(ST_Bar, 150, 15,null);
        g2.drawImage(ST_One, 152, 17, gs.player.ST, 12, null);

        g2.drawImage(MP_Bar, 280, 15, null);
        g2.drawImage(MP_One, 282, 17, gs.player.MP, 12, null);

        g2.drawImage(itemFrame1, 20, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame2, 100, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame3, 180, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame4, 260, gs.getHeight() - 70, 60, 60, null);

        g2.drawImage(itemFrame8, gs.getWidth() - 80, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame7, gs.getWidth() - 160, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame6, gs.getWidth() - 240, gs.getHeight() - 70, 60, 60, null);
        g2.drawImage(itemFrame5, gs.getWidth() - 320, gs.getHeight() - 70, 60, 60, null);

        if(messageCheck) {
            g2.drawString(message, 20, 300);
            messageTimer++;
            if(messageTimer == 320) {
                messageCheck = false;
                messageTimer = 0;
            }
        }

        if(dialogueCheck) {
            g2.drawString(gs.npc[npcNumber].dialogue[gs.npc[npcNumber].onLine], dialogueX, dialogueY);
            dialogueTimer++;
            if(dialogueTimer >= gs.npc[npcNumber].dialogueDuration[gs.npc[npcNumber].onLine]) {
                if(gs.npc[npcNumber].toLine - 1 > gs.npc[npcNumber].onLine)
                    gs.npc[npcNumber].onLine++;
                else
                    dialogueCheck = false;
                dialogueTimer = 0;
            }

        }

    }

}
