package Entities;

import Abilities.Ability;
import Main.GameScreen;
import Util.ImageVector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Math.abs;

public abstract class Enemies extends MovingEntity {
    String name;
    GameScreen gs;

    public Ability[] abilities;
    public Ability[] usedAbilities;
    int onAbilityPriority = 0;
    int []abilityPriority;
    int []abilityPriorityTime;
    int abilityPriorityTimer = 0;

    int castTime;
    boolean castOn = false;
    public boolean abilityOn = false;
    public int onAbility;
    int abilityTimer = 0;

    ImageVector []castImages;
    ImageVector images;

    protected Enemies(GameScreen gs, String name, int worldX, int worldY, int HP) {
        this.gs = gs;
        this.name = name;
        this.HP = HP;
        this.worldX = worldX;
        this.worldY = worldY;

        direction = "down";
        standing = true;
    }

    abstract void loadAbilities() throws IOException;


    void loadStandardImages(int numberOfImages, int []numberOfFrames) throws IOException {
        images = new ImageVector("/Enemies/" + name, name, numberOfImages, numberOfFrames);
    }

    void loadCastImagesAndTimes(int numberOfAbilities, int []numberOfImages, int [][]numberOfFramesPerImage) throws IOException {
        castImages = new ImageVector[numberOfAbilities];
        for(int i = 0; i < numberOfAbilities; i++) {
            castImages[i] = new ImageVector("/Enemies/" + name, name + "_Cast_" + i, numberOfImages[i], numberOfFramesPerImage[i]);
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
        if(!castOn || (abilityOn && abilities[onAbility].moveDuringAbility)) {
            return images.GetImage();
        } else {
            if(!abilityOn || abilities[onAbility].seeDuringAbility)
                return castImages[onAbility].GetImage();
            else
                return null;
        }
    }

    public void draw(GameScreen gs, Graphics2D g2) {
        int screenX = worldX - gs.player.worldX + gs.player.screenX;
        int screenY = worldY - gs.player.worldY + gs.player.screenY;

        if(worldX + gs.tileSize> gs.player.worldX - gs.player.screenX &&
                worldX - gs.tileSize< gs.player.worldX + gs.player.screenX &&
                worldY + gs.tileSize> gs.player.worldY - gs.player.screenY &&
                worldY - gs.tileSize< gs.player.worldY + gs.player.screenY) {
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
            }
        }
        if(abilityOn) {
            abilityTimer++;
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
        if(abs(distanceX) < 1000 && abs(distanceY) < 1000 && (!castOn || (abilityOn && abilities[onAbility].moveDuringAbility))) {
            ok = true;

            collisionOn1 = false;
            collisionOn2 = false;
            checkDirection(distanceX, distanceY);
            gs.cChecker.CheckTile(this);

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

//    void abilityGoAfterPlayer(Abilities ability, int x, int y) {
//    }

    public void update(int x, int y) {
        int distanceX, distanceY;
        distanceX = worldX - x;
        distanceY = worldY - y;

        if(goAfterPlayer(distanceX, distanceY)) {
            chooseAbility();
            checkIfInRange(distanceX, distanceY);
            checkDirection(distanceX, distanceY);
        }
        tryCastOrAbility();
    }
}
