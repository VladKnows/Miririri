package Entities;

import Abilities.Ability;
import Main.GameScreen;
import Util.ImageVector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.abs;

public abstract class Enemies extends MovingEntity {
    String name;
    GameScreen gs;

    Ability[] abilities;
    int onAbilityPriority = 0;
    int []abilityPriority;
    int []abilityPriorityTime;
    int abilityPriorityTimer = 0;

    int castTime;
    boolean castOn = false;
    boolean abilityOn = false;
    int onAbility;
    int abilityTimer = 0;

    ImageVector []castImages;
    ImageVector images;
    BufferedImage dead;

    protected Enemies(GameScreen gs, String name, int worldX, int worldY, int HP) {
        this.gs = gs;
        this.name = name;
        this.HP = HP;
        this.worldX = worldX;
        this.worldY = worldY;

        direction = "down";
        standing = true;
    }

    public Ability[] getAbilities() { return abilities; }
    public boolean isAbilityOn() { return abilityOn; }
    public int getOnAbility() { return onAbility; }

    abstract void loadAbilities() throws IOException;


    void loadStandardImages(int numberOfImages, int []numberOfFrames) throws IOException {
        images = new ImageVector("/Enemies/" + name, name, numberOfImages, numberOfFrames);
        dead = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/" + name + "/" + name +  "_Dead" + ".png")));
    }

    void loadCastImagesAndTimes(int numberOfAbilities, int []numberOfImages, int [][]numberOfFramesPerImage) throws IOException {
        castImages = new ImageVector[numberOfAbilities];
        if(name != "Ghostly") {
            for (int i = 0; i < numberOfAbilities; i++) {
                castImages[i] = new ImageVector("/Enemies/" + name, name + "_Cast_" + i, numberOfImages[i], numberOfFramesPerImage[i]);
            }
        } else {
            for (int i = 0; i < numberOfAbilities; i++) {
                castImages[i] = new ImageVector("/Enemies/" + name, name + "_Cast_" + i, 96, numberOfImages[i], numberOfFramesPerImage[i]);
            }
        }
    }

    void loadAbilityPriority(int []abilityPriorityTime) {
        abilityPriority = new int[abilities.length];
        int []temp = new int[abilities.length];
        for(int i = 0; i < abilities.length; i++) {
            abilityPriority[i] = i;
            temp[i] = abilities[i].range;
        }
        for(int i = 0; i < abilities.length; i++) {
            for(int j = i + 1; j < abilities.length; j++) {
                if(temp[i] > temp[j]) {
                    int r = temp[i];
                    temp[i] = temp[j];
                    temp[j] = r;
                    r = abilityPriority[i];
                    abilityPriority[i] = abilityPriority[j];
                    abilityPriority[j] = r;
                }
            }
        }
        onAbility = abilityPriority[0];
        this.abilityPriorityTime = abilityPriorityTime;
    }

    BufferedImage imageGetter() {
        if(HP > 0) {
            if (!castOn || (abilityOn && abilities[onAbility].moveDuringAbility)) {
                if(!GameScreen.timeStop)
                    return images.GetImage();
                else
                    return images.GetCurrentImage();
            } else {
                if (!abilityOn || abilities[onAbility].seeDuringAbility)
                    if(!GameScreen.timeStop)
                        return castImages[onAbility].GetImage();
                    else
                        return castImages[onAbility].GetCurrentImage();
                else
                    return null;
            }
        }
        else {
            return dead;
        }
    }

    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = worldX - gs.getPlayer().worldX + gs.getPlayer().screenX;
        int screenY = worldY - gs.getPlayer().worldY + gs.getPlayer().screenY;

        if(worldX + gs.tileSize > gs.getPlayer().worldX - gs.getPlayer().screenX &&
                worldX - gs.tileSize< gs.getPlayer().worldX + gs.getPlayer().screenX &&
                worldY + gs.tileSize> gs.getPlayer().worldY - gs.getPlayer().screenY &&
                worldY - gs.tileSize< gs.getPlayer().worldY + gs.getPlayer().screenY) {
            g2.drawImage(imageGetter(), screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }

    public void draw(GameScreen gs, Graphics2D g2, boolean ok) {
        int screenX = worldX - gs.getPlayer().worldX + gs.getPlayer().screenX;
        int screenY = worldY - gs.getPlayer().worldY + gs.getPlayer().screenY;

        if(worldX + gs.tileSize > gs.getPlayer().worldX - gs.getPlayer().screenX &&
                worldX - gs.tileSize< gs.getPlayer().worldX + gs.getPlayer().screenX &&
                worldY + gs.tileSize> gs.getPlayer().worldY - gs.getPlayer().screenY &&
                worldY - gs.tileSize< gs.getPlayer().worldY + gs.getPlayer().screenY) {
            g2.drawImage(imageGetter(), screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }

    void chooseAbility() {
        if(onAbility != abilityPriority[abilities.length - 1]) {
            abilityPriorityTimer++;
            if(abilityPriorityTimer > abilityPriorityTime[onAbilityPriority]) {
                onAbilityPriority++;
                onAbility = abilityPriority[onAbilityPriority];
                abilityPriorityTimer = 0;
            }
        }
    }

    void checkIfInRange(int distanceX, int distanceY) {
        if(abs(distanceX) < abilities[onAbility].range && abs(distanceY) < abilities[onAbility].range && !castOn) {
            castOn = true;
            castTime = 0;
        }
    }

    void tryCastOrAbility() {
        if(castOn && !abilityOn) {
            castTime++;
            if(castTime >= castImages[onAbility].getTotalDuration()) {
                abilityOn = true;
                Ability ability = new Ability(abilities[onAbility]);
                ability.init(worldX, worldY);
                gs.enemyAbilities.add(ability);
            }
        }
        if(abilityOn) {
            abilityTimer++;

            //resets
            if(abilityTimer >= abilities[onAbility].duration) {
                abilityTimer = 0;
                castTime = 0;
                abilityOn = false;
                castOn = false;

                for(int i = 0; i < abilities[onAbility].projectiles.length; i++) {
                    abilities[onAbility].projectiles[i].image.ResetToFirst();
                    abilities[onAbility].projectiles[i].itHit = false;
                    abilities[onAbility].projectiles[i].timerUntilNextHit = 0;
                    abilities[onAbility].projectiles[i].coordSet = false;
                }

                for(int i = 0; i < castImages.length; i++) {
                    castImages[i].ResetToFirst();
                }

                onAbility = abilityPriority[0];
                abilityPriorityTimer = 0;
                onAbilityPriority = 0;
            }
        }
    }

    void checkDirection(int distanceX, int distanceY) {
        if(abs(distanceX) >= speed && abs(distanceY) >= speed) {
            if (distanceX == 0) {
                if (distanceY == 0) direction = "stand";
                else if (distanceY < 0) direction = "down";
                else direction = "up";
            } else if (distanceX < 0) {
                if (distanceY == 0) direction = "right";
                else if (distanceY < 0) direction = "dr";
                else direction = "ur";
            } else {
                if (distanceY == 0) direction = "left";
                else if (distanceY < 0) direction = "dl";
                else direction = "ul";
            }
        }
    }

    //AI
    boolean goAfterPlayer(int distanceX, int distanceY) {
        boolean ok;
        if(abs(distanceX) < 800 && abs(distanceY) < 800 && (!castOn || (abilityOn && abilities[onAbility].moveDuringAbility))) {
            ok = true;

            collisionOn1 = false;
            collisionOn2 = false;
            checkDirection(distanceX, distanceY);
            gs.getcChecker().CheckTile(this);

            if(!collisionOn1 && abs(distanceY) > speed) {
                if (distanceY > 0)
                    worldY -= speed;
                else
                    worldY += speed;
            }

            if(!collisionOn2 && abs(distanceX) > speed) {
                if (distanceX < 0)
                    worldX += speed;
                else
                    worldX -= speed;
            }
        }
        else ok = false;
        return ok;
    }


    public void update(int x, int y) {
        if(HP > 0 && !GameScreen.timeStop) {
            int distanceX, distanceY;
            distanceX = worldX - x;
            distanceY = worldY - y;

            if (goAfterPlayer(distanceX, distanceY)) {
                chooseAbility();
                checkIfInRange(distanceX, distanceY);
                checkDirection(distanceX, distanceY);
            }
            tryCastOrAbility();
        }
    }
}
