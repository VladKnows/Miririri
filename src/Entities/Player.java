package Entities;
import Objects.SuperObject;


import Abilities.Ability_Friendly;
import Abilities.Projectile;
import Abilities.Projectile_Moving;
import Abilities.Projectile_Static;
import Main.GameScreen;
import Main.Keys;
import Main.Mouse;
import Util.ImageVector;
import Objects.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static Main.GameScreen.scale;

public class Player extends MovingEntity {
    static Player instance = null;

    GameScreen gs;
    Keys keys;
    Mouse mouse;

    public int screenX, screenY;
    BufferedImage up, down, right, left, dead;
    ImageVector upWalk, downWalk, rightWalk, leftWalk;
    ImageVector [][]castImages = new ImageVector[4][4];

    double speedProportion;

    public int MP;
    public int ST, ST_CounterInc = 0, ST_CounterDec = 0;
    public int maxHP, maxMP, maxST;

    public boolean exhausted = false;

    public int onAbility = 0;
    public boolean usingAbility = false;

    Ability_Friendly []abilities = new Ability_Friendly[4];
    int []MPForAbility = new int[] {5, 20, 30, 50};

    int abilityInUse = 0;
    int castTime = 0;
    public int directionAtCast = 0;
    int []timeForAbilityCast = new int[] {20, 39, 40, 51};
    public boolean []abilityUnlocked = new boolean[] {false, false, false, false};

    public Item []items = new Item[4];
    public int []numberOfItems = new int[4];
    int currentNumberOfItems = 0;
    public int onItem = 0;

    Player(GameScreen gs, Keys keys, Mouse mouse) throws IOException {
        //Other
        this.gs = gs;
        this.keys = keys;
        this.mouse = mouse;

        //Position and Dimensions
        setStartingCoordinates(25, 5);
        screenX = gs.screenWidth / 2 - (gs.tileSize / 2);
        screenY = gs.screenHeight / 2 - (gs.tileSize / 2);
        solid = new Rectangle(13 * scale, 15 * scale, 7 * scale, 14 * scale);

        //Attributes
        HP = 100;
        ST = 100;
        MP = 300;
        maxHP = HP;
        maxMP = MP;
        maxST = ST;

        initSpeed = 4;
        speed = 2;
        speedProportion = 1;

        //Direction
        direction = "down";
        standing = false;

        //Abilities
        abilities[0] = new Ability_Friendly(0, 70, true, true, new Projectile[] {new Projectile_Moving(true, true, false, "Player_Ability_0", 96, 96, 0, 0, 0, 0, 9, true,  new Rectangle(27, 30, 51, 36), 20, 11, new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2})});
        abilities[1] = new Ability_Friendly(0, 180, true, true, new Projectile[] {new Projectile_Moving(true, false,  true, "Player_Ability_1", 96, 96, 0, 0, 0, 0, 3, true, new Ellipse2D.Double(0, 0, 96, 96), 3, 10, 12, new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5})});
        abilities[2] = new Ability_Friendly(0, 200, true, true, new Projectile[] {new Projectile_Moving(true, false, false, "Player_Ability_2", 96, 96, 0, 0, 0, 0, 2, true, new Ellipse2D.Double(10, 10, 76, 76), 100, 14, new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3})});
        abilities[3] = new Ability_Friendly(0, 200, true, false, new Projectile[] {new Projectile_Static(true, false, true, "Player_Ability_3", 400, 400, 152, 152, new Rectangle(0, 0, 400, 400), 100, 3, new int[] {20, 10, 170})});

        //Images
        getImage();
    }

    public static Player getInstance(GameScreen gs, Keys keys, Mouse mouse) throws IOException {
        if(instance == null) {
            instance = new Player(gs, keys, mouse);
        }
        return instance;
    }

    public static Player getInstance() throws IOException {
        return instance;
    }

    public void setStartingCoordinates(int x, int y) {
        worldX = x * gs.tileSize;
        worldY = y * gs.tileSize;
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

            for(int i = 0; i < 4; i++) {
                String d = switch (i) {
                    case 0 -> "Up";
                    case 1 -> "Right";
                    case 2 -> "Down";
                    case 3 -> "Left";
                    default -> null;
                };
                castImages[0][i] = new ImageVector("/MC/Cast", "MC_" + d + "_Cast_0", 2, new int []{5, 15});
                castImages[1][i] = new ImageVector("/MC/Cast", "MC_" + d + "_Cast_1", 3, new int []{13, 13, 13});
                castImages[2][i] = new ImageVector("/MC/Cast", "MC_" + d + "_Cast_2", 2, new int []{20, 20});
                castImages[3][i] = new ImageVector("/MC/Cast", "MC_Cast_3", 14, new int []{5, 5, 5, 5, 3, 2, 5, 3, 3, 3, 3, 3, 3, 3});
            }

            dead = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/MC/MC_Dead.png")));
    }

    int checkIfItemExists(String name) {
        for(int i = 0; i < 4; i++) {
            if(items[i] != null)
                if(Objects.equals(items[i].getName(), name))
                    return i;
        }
        return -1;
    }

    int firstEmptyItem() {
        for(int i = 0; i < 4; i++) {
            if(items[i] == null)
                return i;
        }
        return 0;
    }

    void objectChecker() throws IOException {
        if (keys.ePress) {
            int rowPlayer = (this.worldX + 16 * scale) / gs.tileSize, colPlayer = (this.worldY + 16 * scale) / gs.tileSize;

            for (int i = 0; i < gs.getObj().length; i++) {
                if (gs.getObj(i) != null) {
                    if (gs.getObj(i).getWorldX() / gs.tileSize == rowPlayer && gs.getObj(i).getWorldY() / gs.tileSize == colPlayer) {
                        boolean doUpdate = true;
                        switch (gs.getObj(i).getName()) {
                            case "Boots":
                                initSpeed += 1;
                                break;
                            case "Sword":
                                for(int j = 0; j < 4; j++) {
                                    for(int k = 0; k < abilities[j].projectiles.length; k++) {
                                        switch (j) {
                                            case 0:
                                                abilities[j].projectiles[k].damage += 5;
                                                break;
                                            case 1:
                                                abilities[j].projectiles[k].damage++;
                                                break;
                                            case 2:
                                                abilities[j].projectiles[k].damage += 20;
                                                break;
                                            case 3:
                                                abilities[j].projectiles[k].damage += 40;
                                                break;
                                        }
                                    }
                                }
                                break;
                            case "Mana_Gem":
                                maxMP += 40;
                                MP += 40;
                                break;
                            case "Health_Gem":
                                maxHP += 25;
                                HP += 25;
                            case "Stamina_Gem":
                                maxST += 25;
                                ST += 25;
                            case "Scroll_Ability_0":
                                abilityUnlocked[0] = true;
                                break;
                            case "Scroll_Ability_1":
                                abilityUnlocked[1] = true;
                                break;
                            case "Scroll_Ability_2":
                                abilityUnlocked[2] = true;
                                break;
                            case "Scroll_Ability_3":
                                abilityUnlocked[3] = true;
                                break;
                            case "Health_Potion", "Mana_Potion", "Stamina_Potion", "Hour_Glass":
                                if(currentNumberOfItems != 4) {
                                    int itemIndex = checkIfItemExists(gs.getObj()[i].getName());
                                    if(itemIndex == -1) {
                                        int index = firstEmptyItem();
                                        items[index] = (Item) gs.getObj()[i];
                                        numberOfItems[index] = 1;
                                    }
                                    else {
                                        numberOfItems[itemIndex]++;
                                    }
                                } else
                                    doUpdate = false;
                                break;
                            case "Teleporter":
                                break;
                            default:
                                doUpdate = false;
                        }
                        if(doUpdate) {
                            SuperObject.markedForDeletion = i;
                            GameScreen.getInstance().getObj(i).update();
                        }
                    }
                }
            }
            keys.ePress = false;
        }
    }

    void npcChecker() {
        if (keys.tPress) {
            int rowPlayer = (this.worldX + 16 * scale) / gs.tileSize, colPlayer = (this.worldY + 16 * scale) / gs.tileSize;

            for (int i = 0; i < gs.getNpc().length; i++) {
                if (gs.getNpc()[i] != null) {
                    if (gs.getNpc()[i].worldX / gs.tileSize == rowPlayer && gs.getNpc()[i].worldY / gs.tileSize == colPlayer) {
                        gs.getUi().talkToNPC(i);
                    }
                }
            }
            keys.tPress = false;
        }
    }

    void chooseAbility() {
        if(keys.press1) {
            onAbility = 0;
        } else if (keys.press2) {
            onAbility = 1;
        } else if (keys.press3) {
            onAbility = 2;
        } else if (keys.press4) {
            onAbility = 3;
        }
    }

    void chooseItem() {
        if(keys.press5) {
            onItem = 0;
        } else if (keys.press6) {
            onItem = 1;
        } else if (keys.press7) {
            onItem = 2;
        } else if (keys.press8) {
            onItem = 3;
        }
    }

    void useItem() throws IOException {
        if(keys.qPress) {
            if (numberOfItems[onItem] != 0) {
                numberOfItems[onItem]--;
                switch (items[onItem].getName()) {
                    case "Health_Potion":
                        HP += 30;
                        if (HP > maxHP) {
                            HP = maxHP;
                        }
                        break;
                    case "Mana_Potion":
                        MP += 100;
                        if (MP > maxMP) {
                            MP = maxMP;
                        }
                        break;
                    case "Stamina_Potion":
                        ST = maxST;
                        break;
                    case "Hour_Glass":
                        GameScreen.getInstance().timeStop = true;
                        break;
                }
                if(numberOfItems[onItem] == 0) {
                    items[onItem] = null;
                    currentNumberOfItems--;
                }
            }
            keys.qPress = false;
        }
    }

    void castAbility() {
        if(mouse.leftClick && !usingAbility && abilityUnlocked[onAbility]) {
            if (MP >= MPForAbility[onAbility]) {
                castTime = 0;
                abilityInUse = onAbility;
                directionAtCast = mouse.checkDirection();
                MP -= MPForAbility[onAbility];

                usingAbility = true;
                mouse.leftClick = false;
            }
        }
        if(usingAbility && castTime == timeForAbilityCast[abilityInUse]) {
            Ability_Friendly ability = new Ability_Friendly(abilities[abilityInUse]);
            ability.init(worldX, worldY);
            for (int i = 0; i < ability.projectiles.length; i++) {
                int x = 0, y = 0;
                switch (directionAtCast) {
                    case 0:
                        x = worldX;
                        y = worldY - 1000;
                        break;
                    case 2:
                        x = worldX;
                        y = worldY + 1000;
                        break;
                    case 1:
                        x = worldX + 1000;
                        y = worldY;
                        break;
                    case 3:
                        x = worldX - 1000;
                        y = worldY;
                        break;
                }
                ability.projectiles[i].setToOnce(x, y);
            }
            gs.playerAbilities.add(ability);
        }
    }

    void Run() {
        speed = initSpeed + 1;
        speedProportion = (float) speed / initSpeed;


        ST_CounterDec++;
        if(ST_CounterDec >= 3) {
            ST--;
            ST_CounterDec = 0;
        }
    }

    void Walk(int limit) {
        speed = initSpeed;
        speedProportion = 1;
        if(ST < maxST) {
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
        gs.getcChecker().CheckTile(this);
        gs.getcChecker().CheckNPC(this);
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
        if(!gs.getUi().isDialogueCheck()) {
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
            if(usingAbility && !abilities[abilityInUse].moveDuringAbility && castTime <= timeForAbilityCast[abilityInUse])
                standing = true;
        }

        switch(direction) {
            case "up", "upS":
                directionNumber = 0;
                break;
            case "right", "ur", "dr", "rightS":
                directionNumber = 1;
                break;
            case "down", "downS":
                directionNumber = 2;
                break;
            case "left", "ul", "dl", "leftS":
                directionNumber = 3;
                break;
        }
    }

    BufferedImage imageGetter() {
        BufferedImage image = null;
        if(usingAbility && castTime <= timeForAbilityCast[abilityInUse]) {
            castTime++;
            image = castImages[abilityInUse][directionAtCast].GetImage();
        }
        else if (!standing) {
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
        if(HP == 0) {
            image = dead;
        }
        return image;
    }

    public void update() throws IOException {
        if(HP >= 1) {
            if (!standing) {
                collisionChecker();
            }
            if(keys.pPress) {
                initSpeed = 20;
                abilities[0].projectiles[0].damage = 1000;
                abilityUnlocked[0] = true;
                keys.pPress = false;
            }
            chooseAbility();
            chooseItem();
            castAbility();
            useItem();
            objectChecker();
            npcChecker();
            directionSetter();
            speedSetter();
            if(keys.escPress) {
                GameScreen.setCurrentState(2);
                keys.escPress = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image1;
        image1 = imageGetter();
        g2.drawImage(image1, screenX, screenY, gs.tileSize, gs.tileSize, null);
    }
}
