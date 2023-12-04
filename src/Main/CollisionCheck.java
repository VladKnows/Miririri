package Main;

import Abilities.Projectile;
import Entities.MovingEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class CollisionCheck {
    GameScreen gs;

    public CollisionCheck(GameScreen gs) {
        this.gs = gs;
    }

    public void CheckHit(MovingEntity entity, Projectile projectile) {
        Rectangle p = new Rectangle(entity.getWorldX() + entity.getSolid().x, entity.getWorldY() + entity.getSolid().y, entity.getSolid().width, entity.getSolid().height);
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
            entity.HP -= projectile.damage;
            projectile.itHitOne = true;
        }

        if(entity.HP <= 0) {
            GameScreen.scor += 20;
            entity.HP = 0;
        }
    }

    public void CheckTile(MovingEntity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolid().x;
        int entityRightWorldX = entityLeftWorldX + entity.getSolid().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolid().y;
        int entityBottomWorldY = entityTopWorldY + entity.getSolid().height;

        int entityLeftCol = entityLeftWorldX / gs.tileSize;
        int entityRightCol = entityRightWorldX / gs.tileSize;
        int entityTopRow = entityTopWorldY / gs.tileSize;
        int entityBottomRow = entityBottomWorldY / gs.tileSize;

        int tile1, tile2;

        if(Objects.equals(entity.direction, "up") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "ur")) {
            entityTopWorldY -= entity.speed;
            entityTopRow = entityTopWorldY / gs.tileSize;
            tile1 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityLeftCol][entityTopRow];
            tile2 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityRightCol][entityTopRow];
            if (gs.tileM[gs.onMap].tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn1 = true;
            }
            if (gs.tileM[gs.onMap].tile[tile2].collision && !entity.collisionOn1) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn1 = true;
            }
            entityTopWorldY += entity.speed;
        }
        if (Objects.equals(entity.direction, "down") || Objects.equals(entity.direction, "dl") || Objects.equals(entity.direction, "dr")) {
            entityBottomWorldY += entity.speed;
            entityBottomRow = entityBottomWorldY / gs.tileSize;
            tile1 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityLeftCol][entityBottomRow];
            tile2 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityRightCol][entityBottomRow];
            if (gs.tileM[gs.onMap].tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn1 = true;
            }
            if (gs.tileM[gs.onMap].tile[tile2].collision && !entity.collisionOn1) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn1 = true;
            }
            entityBottomWorldY -= entity.speed;
        }
        if(Objects.equals(entity.direction, "left") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "dl")) {
            entityLeftWorldX -= entity.speed;
            entityLeftCol = entityLeftWorldX / gs.tileSize;
            tile1 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityLeftCol][entityTopRow];
            tile2 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityLeftCol][entityBottomRow];
            if (gs.tileM[gs.onMap].tile[tile1].collision) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile1].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
            if (gs.tileM[gs.onMap].tile[tile2].collision && !entity.collisionOn2) {
                int tileX1 = entityLeftCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile2].solid.height;
                if ((tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityLeftWorldX && tileX2 > entityLeftWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
        }
        if(Objects.equals(entity.direction, "right") || Objects.equals(entity.direction, "ur") || Objects.equals(entity.direction, "dr")) {
            entityRightWorldX += entity.speed;
            entityRightCol = entityRightWorldX / gs.tileSize;
            tile1 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityRightCol][entityTopRow];
            tile2 = gs.tileM[gs.onMap].mapTile[gs.onMap][entityRightCol][entityBottomRow];
            if (gs.tileM[gs.onMap].tile[tile1].collision) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.x;
                int tileY1 = entityTopRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile1].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile1].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile1].solid.height;
                if ((tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY))
                    entity.collisionOn2 = true;
            }
            if (gs.tileM[gs.onMap].tile[tile2].collision && !entity.collisionOn2) {
                int tileX1 = entityRightCol * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.x;
                int tileY1 = entityBottomRow * gs.tileSize + gs.tileM[gs.onMap].tile[tile2].solid.y;
                int tileX2 = tileX1 + gs.tileM[gs.onMap].tile[tile2].solid.width;
                int tileY2 = tileY1 + gs.tileM[gs.onMap].tile[tile2].solid.height;
                if ((tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityBottomWorldY && tileY2 > entityBottomWorldY) ||
                        (tileX1 < entityRightWorldX && tileX2 > entityRightWorldX && tileY1 < entityTopWorldY && tileY2 > entityTopWorldY))
                    entity.collisionOn2 = true;
            }
        }
    }

    public void CheckNPC(MovingEntity entity) {
        for(int i = 0; i < gs.npc.length; i++) {
            if(gs.npc[gs.onMap][i] != null) {
                int entityLeftWorldX = entity.getWorldX() + entity.getSolid().x;
                int entityRightWorldX = entity.getWorldX() + entity.getSolid().x + entity.getSolid().width;
                int entityTopWorldY = entity.getWorldY() + entity.getSolid().y;
                int entityBottomWorldY = entity.getWorldY() + entity.getSolid().y + entity.getSolid().height;

                if (Objects.equals(entity.direction, "up") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "ur")) {
                    entityTopWorldY -= entity.speed;

                    int objectX1 = gs.npc[gs.onMap][i].getWorldX() + gs.npc[gs.onMap][i].getSolid().x;
                    int objectY1 = gs.npc[gs.onMap][i].getWorldY() + gs.npc[gs.onMap][i].getSolid().y;
                    int objectX2 = objectX1 + gs.npc[gs.onMap][i].getSolid().width;
                    int objectY2 = objectY1 + gs.npc[gs.onMap][i].getSolid().height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY))
                        entity.collisionOn1 = true;
                    entityTopWorldY += entity.speed;
                }
                if (Objects.equals(entity.direction, "down") || Objects.equals(entity.direction, "dl") || Objects.equals(entity.direction, "dr")) {
                    entityBottomWorldY += entity.speed;

                    int objectX1 = gs.npc[gs.onMap][i].getWorldX() + gs.npc[gs.onMap][i].getSolid().x;
                    int objectY1 = gs.npc[gs.onMap][i].getWorldY() + gs.npc[gs.onMap][i].getSolid().y;
                    int objectX2 = objectX1 + gs.npc[gs.onMap][i].getSolid().width;
                    int objectY2 = objectY1 + gs.npc[gs.onMap][i].getSolid().height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn1 = true;
                    entityBottomWorldY -= entity.speed;
                }
                if (Objects.equals(entity.direction, "left") || Objects.equals(entity.direction, "ul") || Objects.equals(entity.direction, "dl")) {
                    entityLeftWorldX -= entity.speed;

                    int objectX1 = gs.npc[gs.onMap][i].getWorldX() + gs.npc[gs.onMap][i].getSolid().x;
                    int objectY1 = gs.npc[gs.onMap][i].getWorldY() + gs.npc[gs.onMap][i].getSolid().y;
                    int objectX2 = objectX1 + gs.npc[gs.onMap][i].getSolid().width;
                    int objectY2 = objectY1 + gs.npc[gs.onMap][i].getSolid().height;
                    if ((objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityLeftWorldX && objectX2 > entityLeftWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
                if (Objects.equals(entity.direction, "right") || Objects.equals(entity.direction, "ur") || Objects.equals(entity.direction, "dr")) {
                    entityRightWorldX += entity.speed;

                    int objectX1 = gs.npc[gs.onMap][i].getWorldX() + gs.npc[gs.onMap][i].getSolid().x;
                    int objectY1 = gs.npc[gs.onMap][i].getWorldY() + gs.npc[gs.onMap][i].getSolid().y;
                    int objectX2 = objectX1 + gs.npc[gs.onMap][i].getSolid().width;
                    int objectY2 = objectY1 + gs.npc[gs.onMap][i].getSolid().height;

                    if ((objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityTopWorldY && objectY2 > entityTopWorldY) ||
                            (objectX1 < entityRightWorldX && objectX2 > entityRightWorldX && objectY1 < entityBottomWorldY && objectY2 > entityBottomWorldY))
                        entity.collisionOn2 = true;
                }
            }
        }
    }
}
