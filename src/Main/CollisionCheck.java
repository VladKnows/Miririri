package Main;

import Abilities.Projectile;
import Entities.MovingEntity;
import Entities.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class CollisionCheck {
    GameScreen gs;

    public CollisionCheck(GameScreen gs) {
        this.gs = gs;
    }

    public void CheckHit(Player player, Projectile projectile) {
        Rectangle p = new Rectangle(player.worldX + player.solid.x, player.worldY + player.solid.y, player.solid.width, player.solid.height);
        AffineTransform a0 = AffineTransform.getTranslateInstance(projectile.x, projectile.y);
        Shape a = a0.createTransformedShape(projectile.solid);

        //se poate reface sub forma de functie
        if(!projectile.oneTime) {
            projectile.timerUntilNextHit++;
            if(projectile.timerUntilNextHit >= projectile.timeUntilNextHit) {
                projectile.timerUntilNextHit = 0;
                projectile.itHit = false;
            }
        }

        if(!projectile.itHit && a.intersects(p)) {
            player.HP -= projectile.damage;
            projectile.itHit = true;
        }
        if(player.HP < 0) {
            player.HP = 0;
        }
    }

    public void CheckTile(MovingEntity entity) {
        int entityLeftWorldX = entity.worldX + entity.solid.x;
        int entityRightWorldX = entity.worldX + entity.solid.x + entity.solid.width;
        int entityTopWorldY = entity.worldY + entity.solid.y;
        int entityBottomWorldY = entity.worldY + entity.solid.y + entity.solid.height;

        int entityLeftCol = entityLeftWorldX / gs.tileSize;
        int entityRightCol = entityRightWorldX / gs.tileSize;
        int entityTopRow = entityTopWorldY / gs.tileSize;
        int entityBottomRow = entityBottomWorldY / gs.tileSize;

        int tile1, tile2;

        if(Objects.equals(entity.direction, "up") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "ur")) {
            entityTopWorldY -= entity.speed;
            entityTopRow = entityTopWorldY / gs.tileSize;
            tile1 = gs.tileM.mapTile[entityLeftCol][entityTopRow];
            tile2 = gs.tileM.mapTile[entityRightCol][entityTopRow];
            if (gs.tileM.tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM.tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM.tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn1 = true;
            }
            if (gs.tileM.tile[tile2].collision && !entity.collisionOn1) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM.tile[tile2].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM.tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn1 = true;
            }
            entityTopWorldY += entity.speed;
        }
        if (Objects.equals(entity.direction, "down") || Objects.equals(entity.direction, "dl") || Objects.equals(entity.direction, "dr")) {
            entityBottomWorldY += entity.speed;
            entityBottomRow = entityBottomWorldY / gs.tileSize;
            tile1 = gs.tileM.mapTile[entityLeftCol][entityBottomRow];
            tile2 = gs.tileM.mapTile[entityRightCol][entityBottomRow];
            if (gs.tileM.tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM.tile[tile1].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM.tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn1 = true;
            }
            if (gs.tileM.tile[tile2].collision && !entity.collisionOn1) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM.tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM.tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn1 = true;
            }
            entityBottomWorldY -= entity.speed;
        }
        if(Objects.equals(entity.direction, "left") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "dl")) {
            entityLeftWorldX -= entity.speed;
            entityLeftCol = entityLeftWorldX / gs.tileSize;
            tile1 = gs.tileM.mapTile[entityLeftCol][entityTopRow];
            tile2 = gs.tileM.mapTile[entityLeftCol][entityBottomRow];
            if (gs.tileM.tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM.tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM.tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
            if (gs.tileM.tile[tile2].collision && !entity.collisionOn2) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM.tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM.tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
        }
        if(Objects.equals(entity.direction, "right") || Objects.equals(entity.direction, "ur") || Objects.equals(entity.direction, "dr")) {
            entityRightWorldX += entity.speed;
            entityRightCol = entityRightWorldX / gs.tileSize;
            tile1 = gs.tileM.mapTile[entityRightCol][entityTopRow];
            tile2 = gs.tileM.mapTile[entityRightCol][entityBottomRow];
            if (gs.tileM.tile[tile1].collision) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM.tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM.tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile1].solid.height;
                if ((tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
            if (gs.tileM.tile[tile2].collision && !entity.collisionOn2) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM.tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM.tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM.tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM.tile[tile2].solid.height;
                if ((tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn2 = true;
            }
        }
    }

    public void CheckObject(MovingEntity entity) {
        for(int i = 0; i < gs.obj.length; i++) {
            if(gs.obj[i] != null && gs.obj[i].collision) {
                int entityLeftWorldX = entity.worldX + entity.solid.x;
                int entityRightWorldX = entity.worldX + entity.solid.x + entity.solid.width;
                int entityTopWorldY = entity.worldY + entity.solid.y;
                int entityBottomWorldY = entity.worldY + entity.solid.y + entity.solid.height;

                if (Objects.equals(entity.direction, "up") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "ur")) {
                    entityTopWorldY -= entity.speed;

                    int objectX1 = gs.obj[i].worldX + gs.obj[i].solid.x;
                    int objectY1 = gs.obj[i].worldY + gs.obj[i].solid.y;
                    int objectX2 = objectX1 + gs.obj[i].solid.width;
                    int objectY2 = objectY1 + gs.obj[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY))
                        entity.collisionOn1 = true;
                    entityTopWorldY += entity.speed;
                }
                if (Objects.equals(entity.direction, "down") || Objects.equals(entity.direction, "dl") || Objects.equals(entity.direction, "dr")) {
                    entityBottomWorldY += entity.speed;

                    int objectX1 = gs.obj[i].worldX + gs.obj[i].solid.x;
                    int objectY1 = gs.obj[i].worldY + gs.obj[i].solid.y;
                    int objectX2 = objectX1 + gs.obj[i].solid.width;
                    int objectY2 = objectY1 + gs.obj[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn1 = true;
                    entityBottomWorldY -= entity.speed;
                }
                if (Objects.equals(entity.direction, "left") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "dl")) {
                    entityLeftWorldX -= entity.speed;

                    int objectX1 = gs.obj[i].worldX + gs.obj[i].solid.x;
                    int objectY1 = gs.obj[i].worldY + gs.obj[i].solid.y;
                    int objectX2 = objectX1 + gs.obj[i].solid.width;
                    int objectY2 = objectY1 + gs.obj[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
                if (Objects.equals(entity.direction, "right") || Objects.equals(entity.direction, "ur") || Objects.equals(entity.direction, "dr")) {
                    entityRightWorldX += entity.speed;

                    int objectX1 = gs.obj[i].worldX + gs.obj[i].solid.x;
                    int objectY1 = gs.obj[i].worldY + gs.obj[i].solid.y;
                    int objectX2 = objectX1 + gs.obj[i].solid.width;
                    int objectY2 = objectY1 + gs.obj[i].solid.height;

                    if ((objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
            }
        }
    }

    public void CheckNPC(MovingEntity entity) {
        for(int i = 0; i < gs.npc.length; i++) {
            if(gs.npc[i] != null) {
                int entityLeftWorldX = entity.worldX + entity.solid.x;
                int entityRightWorldX = entity.worldX + entity.solid.x + entity.solid.width;
                int entityTopWorldY = entity.worldY + entity.solid.y;
                int entityBottomWorldY = entity.worldY + entity.solid.y + entity.solid.height;

                if (Objects.equals(entity.direction, "up") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "ur")) {
                    entityTopWorldY -= entity.speed;

                    int objectX1 = gs.npc[i].worldX + gs.npc[i].solid.x;
                    int objectY1 = gs.npc[i].worldY + gs.npc[i].solid.y;
                    int objectX2 = objectX1 + gs.npc[i].solid.width;
                    int objectY2 = objectY1 + gs.npc[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY))
                        entity.collisionOn1 = true;
                    entityTopWorldY += entity.speed;
                }
                if (Objects.equals(entity.direction, "down") || Objects.equals(entity.direction, "dl") || Objects.equals(entity.direction, "dr")) {
                    entityBottomWorldY += entity.speed;

                    int objectX1 = gs.npc[i].worldX + gs.npc[i].solid.x;
                    int objectY1 = gs.npc[i].worldY + gs.npc[i].solid.y;
                    int objectX2 = objectX1 + gs.npc[i].solid.width;
                    int objectY2 = objectY1 + gs.npc[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn1 = true;
                    entityBottomWorldY -= entity.speed;
                }
                if (Objects.equals(entity.direction, "left") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "dl")) {
                    entityLeftWorldX -= entity.speed;

                    int objectX1 = gs.npc[i].worldX + gs.npc[i].solid.x;
                    int objectY1 = gs.npc[i].worldY + gs.npc[i].solid.y;
                    int objectX2 = objectX1 + gs.npc[i].solid.width;
                    int objectY2 = objectY1 + gs.npc[i].solid.height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
                if (Objects.equals(entity.direction, "right") || Objects.equals(entity.direction, "ur") || Objects.equals(entity.direction, "dr")) {
                    entityRightWorldX += entity.speed;

                    int objectX1 = gs.npc[i].worldX + gs.npc[i].solid.x;
                    int objectY1 = gs.npc[i].worldY + gs.npc[i].solid.y;
                    int objectX2 = objectX1 + gs.npc[i].solid.width;
                    int objectY2 = objectY1 + gs.npc[i].solid.height;

                    if ((objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
            }
        }
    }
}
