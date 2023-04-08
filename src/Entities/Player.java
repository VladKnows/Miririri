package Entities;

import Main.GameScreen;
import Main.Keys;
import Util.ImageVector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static Main.GameScreen.scale;

public class Player extends MovingEntity {
    GameScreen gs;
    Keys keys;

    public int screenX, screenY;
    BufferedImage up, down, right, left, dead;
    ImageVector upWalk, downWalk, rightWalk, leftWalk;

    double speedProportion;

    public int MP;
    public int ST, ST_CounterInc = 0, ST_CounterDec = 0;
    public boolean exhausted = false;

    public int onAbility = 0;
    public boolean usingAbility = false;

    public Player(GameScreen gs, Keys keys) throws IOException {
        //Other
        this.gs = gs;
        this.keys = keys;

        //Position and Dimensions
        worldX = gs.tileSize * 25;
        worldY = gs.tileSize * 5;
        screenX = gs.screenWidth / 2 - (gs.tileSize / 2);
        screenY = gs.screenHeight / 2 - (gs.tileSize / 2);
        solid = new Rectangle(13 * scale, 15 * scale, 7 * scale, 14 * scale);

        //Attributes
        HP = 100;
        ST = 100;
        MP = 100;
        initSpeed = 2;
        speed = 2;
        speedProportion = 1;

        //Direction
        direction = "down";
        standing = false;

        //Abilities


        //Images
        getImage();
    }

    public void getImage() throws IOException {
            up = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Up_Stand.png")));
            down = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Down_Stand.png")));
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Right_Stand.png")));
            left = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Left_Stand.png")));

            upWalk = new ImageVector("/MC", "MC_Up_Walk", 2, new int []{12, 12});
            downWalk = new ImageVector("/MC", "MC_Down_Walk", 2, new int []{12, 12});
            rightWalk = new ImageVector("/MC", "MC_Right_Walk", 4, new int []{12, 12, 12, 12});
            leftWalk = new ImageVector("/MC", "MC_Left_Walk", 4, new int []{12, 12, 12, 12});

            dead = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Dead.png")));
    }

    void objectChecker() {
        int rowPlayer = (this.worldX + 16 * scale) / gs.tileSize, colPlayer = (this.worldY + 16 * scale) / gs.tileSize;

        for(int i = 0; i < gs.obj.length; i++) {
            if(gs.obj[i] != null) {
                if (gs.obj[i].worldX / gs.tileSize == rowPlayer && gs.obj[i].worldY / gs.tileSize == colPlayer) {
                    switch(gs.obj[i].name) {
                        case "Boots":
                            initSpeed += 1;
                            gs.ui.messageCheck = true;
                            gs.ui.objectNumber = i;
                            gs.ui.message = gs.obj[i].message;
                            gs.obj[i] = null;
                            break;
                    }
                }
            }
        }
    }

    void npcChecker() {
        int rowPlayer = (this.worldX + 16 * scale) / gs.tileSize, colPlayer = (this.worldY + 16 * scale) / gs.tileSize;

        for(int i = 0; i < gs.npc.length; i++) {
            if(gs.npc[i] != null) {
                if (gs.npc[i].worldX / gs.tileSize == rowPlayer && gs.npc[i].worldY / gs.tileSize == colPlayer) {
                    gs.ui.dialogueCheck = true;
                    gs.ui.npcNumber = i;
                }
            }
        }
    }

    void Run() {
        switch(initSpeed) {
            case 2:
                speed = 3;
                speedProportion = 1.5;
                break;
            case 3:
                speed = 4;
                speedProportion = 1.33;
                break;
        }

        ST_CounterDec++;
        if(ST_CounterDec >= 3) {
            ST--;
            ST_CounterDec = 0;
        }
    }

    void Walk(int limit) {
        speed = initSpeed;
        speedProportion = 1;
        if(ST < 100) {
            ST_CounterInc++;
            if (ST_CounterInc >= limit) {
                ST_CounterInc = 0;
                ST++;
            }
        }
    }

    void speedSetter() {
        if(ST == 0) { exhausted = true; }

        if (!exhausted) {
            if (keys.shPress) { Run(); }
            else { Walk(5); }
        } else {
            Walk(18);
            if (ST >= 20) { exhausted = false; }
        }
    }

    void collisionChecker() {
        collisionOn1 = false;
        collisionOn2 = false;
        gs.cChecker.CheckTile(this);
        gs.cChecker.CheckObject(this);
        gs.cChecker.CheckNPC(this);
        if(!collisionOn1) {
            switch (direction) {
                case "up", "ul", "ur":
                    worldY -= speed;
                    break;
                case "down", "dl", "dr":
                    worldY += speed;
                    break;
            }
        }
        if(!collisionOn2) {
            switch(direction) {
                case "left", "ul", "dl":
                    worldX -= speed;
                    break;
                case "right", "ur", "dr":
                    worldX += speed;
                    break;
            }
        }
    }

    void directionSetter() {
        if(!gs.ui.dialogueCheck) {
            standing = false;
            if (keys.wPress || keys.dPress || keys.aPress || keys.sPress) {
                if (keys.wPress) {
                    if (keys.aPress)
                        direction = "ul";
                    else if (keys.dPress)
                        direction = "ur";
                    else
                        direction = "up";
                } else if (keys.sPress) {
                    if (keys.aPress)
                        direction = "dl";
                    else if (keys.dPress)
                        direction = "dr";
                    else
                        direction = "down";
                } else if (keys.dPress) {
                    direction = "right";
                } else {
                    direction = "left";
                }
            } else {
                standing = true;
                switch (direction) {
                    case "up":
                        direction = "upS";
                        break;
                    case "down":
                        direction = "downS";
                        break;
                    case "right", "ur", "dr":
                        direction = "rightS";
                        break;
                    case "left", "ul", "dl":
                        direction = "leftS";
                        break;
                }
            }
        }
    }

    BufferedImage imageGetter() {
        BufferedImage image = null;
        if (!standing) {
            switch (direction) {
                case "up":
                    image = upWalk.GetImage(speedProportion);
                    break;
                case "down":
                    image = downWalk.GetImage(speedProportion);
                    break;
                case "left", "ul", "dl":
                    image = leftWalk.GetImage(speedProportion);
                    break;
                case "right", "ur", "dr":
                    image = rightWalk.GetImage(speedProportion);
                    break;
            }
        } else {
            switch (direction) {
                case "upS":
                    image = up;
                    break;
                case "downS":
                    image = down;
                    break;
                case "rightS":
                    image = right;
                    break;
                case "leftS":
                    image = left;
                    break;
                }
        }
        return image;
    }

    public void update() {
        if(HP >= 1) {
            if (!standing) {
                if (!gs.ui.dialogueCheck) {
                    collisionChecker();
                }
            }
            if (keys.ePress) {
                objectChecker();
                keys.ePress = false;
            }
            if (keys.tPress) {
                npcChecker();
                usingAbility = true;
                keys.tPress = false;
            }
            directionSetter();
            speedSetter();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image1;
        image1 = imageGetter();
        g2.drawImage(image1, screenX, screenY, gs.tileSize, gs.tileSize, null);
    }
}
