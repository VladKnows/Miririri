package Main;

import Abilities.Projectile;
import Abilities.Projectile_Static;
import Entities.Enemies;
import Entities.NPC;
import Entities.Player;
import Objects.SuperObject;
import Tiles.Map;
import Tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameScreen extends JPanel implements Runnable {
    final int originalTileSize = 32;
    public static final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int tileSizeHalf = tileSize / 2;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidthHalf = screenWidth / 2;
    public final int screenHeightHalf = screenHeight / 2;

    public Map []maps = new Map[3];

    int FPS = 60;
    Keys keys = new Keys();
    public Player player = new Player(this, keys);
    Thread gameThread;
    public TileManager tileM;
    public CollisionCheck cChecker = new CollisionCheck(this);
    public UI ui = new UI(this);

    public Placement place = new Placement(this);
    public SuperObject []obj = new SuperObject[10];
    public NPC []npc = new NPC[8];
    public Enemies []enemies = new Enemies[10];

    public GameScreen() throws IOException {
        maps[0] = new Map("Map0.txt", 80, 50);
        //maps[1] = new Map("Map1.txt", 100, 100);
        //maps[2] = new Map("Map2.txt", 80, 50);
        tileM = new TileManager(this, 0);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keys);
        this.setFocusable(true);
        startThread();
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        for(int i = 0; i < enemies.length; i++) {
            if(enemies[i] != null) {
                enemies[i].update(player.worldX, player.worldY);
                if(enemies[i].abilityOn) {
                    enemies[i].abilities[enemies[i].onAbility].update(this, enemies[i].worldX, enemies[i].worldY);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        BufferedImage back = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = back.createGraphics();

        //Tiles
        tileM.draw(g2);

        //Objects
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //NPCs
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].draw(g2, this);
            }
        }

        //Enemies && Abilities
        for(int i = 0; i < enemies.length; i++) {
            if(enemies[i] != null) {
                enemies[i].draw(this, g2);
                if(enemies[i].abilityOn) {
                    enemies[i].abilities[enemies[i].onAbility].draw(this, g2);
                }
            }
        }

        //Player
        player.draw(g2);

        //Player Abilities - to rework

        //UI
        ui.draw(g2);
        g2.dispose();

        Graphics2D g2Front = (Graphics2D) g;
        g2Front.drawImage(back, 0, 0, null);
        g2Front.dispose();
    }

    @Override
    public void run() {
        try {
            place.placeObject();
            place.placeNPC();
            place.placeEnemy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long interval = 1000/FPS;
        long start = System.currentTimeMillis();
        while(gameThread != null) {
            update();
            repaint();

            try {
                long sleep_for = start - System.currentTimeMillis();
                if(sleep_for < 0) {
                    sleep_for = 0;
                }
                Thread.sleep(sleep_for);
                start += interval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
