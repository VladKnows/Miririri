package Main;

import Abilities.Ability;
import Entities.Enemies;
import Entities.NPC;
import Entities.Player;
import Objects.SuperObject;
import Tiles.Map;
import Tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

public class GameScreen extends JPanel implements Runnable {
    static GameScreen instance = null;

    //Tiles
    public static final int scale = 3;
    final static int originalTileSize = 32;
    public final int tileSize = originalTileSize * scale;
    public final int tileSizeHalf = tileSize / 2;

    //Screen
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidthHalf = screenWidth / 2;
    public final int screenHeightHalf = screenHeight / 2;

    //Others
    public int onMap = 0;
    int FPS = 60;
    Thread gameThread;
    Map []maps = new Map[3];
    Keys keys = new Keys();
    Mouse mouse = new Mouse(this);
    TileManager []tileM = new TileManager[3];
    UI ui = new UI(this);
    CollisionCheck cChecker = new CollisionCheck(this);
    Placement place = new Placement(this);

    //Entities & Objects
    NPC [][]npc = new NPC[3][8];
    Player player = Player.getInstance(this, keys, mouse);
    SuperObject [][]obj = new SuperObject[3][20];
    public Enemies [][]enemies = new Enemies[3][20];

    //Abilities
    public Vector<Ability> enemyAbilities = new Vector<>(30);
    public Vector<Ability> playerAbilities = new Vector<>(30);

    //Misc
    public static boolean timeStop = false;
    public static int timeStopTimer = 0;

    //Constructor
    GameScreen() throws IOException {
        maps[0] = new Map("Map0.txt", 80, 50);
        maps[1] = new Map("Map1.txt", 40, 57);
        maps[2] = new Map("Map2.txt", 40, 70);
        tileM[0] = new TileManager(this, 0);
        tileM[1] = new TileManager(this, 1);
        tileM[2] = new TileManager(this, 2);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keys);
        this.addMouseListener(mouse);
        this.setFocusable(true);
        startThread();
    }

    public static GameScreen getInstance() throws IOException {
        if(instance == null) {
            instance = new GameScreen();
        }
        return  instance;
    }

    //Getters
    public Player getPlayer() { return player; }
    public Map[] getMaps() { return maps; }
    public NPC[] getNpc() { return npc[onMap]; }
    public UI getUi() { return ui; }
    public CollisionCheck getcChecker() { return cChecker; }
    public SuperObject[] getObj() { return obj[onMap]; }

    public Map getMaps(int nr) { return maps[nr]; }
    public NPC getNpc(int nr) { return npc[onMap][nr]; }
    public SuperObject getObj(int nr) { return obj[onMap][nr]; }

    //Setters
    public void setObj(int index, SuperObject obj) {
        this.obj[onMap][index] = null;
    }

    //Thread
    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Updates
    public void updateEnemies() {
        for(int i = 0; i < enemies[onMap].length; i++) {
            if(enemies[onMap][i] != null) {
                enemies[onMap][i].update(player.getWorldX(), player.getWorldY());
            }
        }
        //CHANGED
        for(int i = 0; i < enemyAbilities.size(); i++) {
            if(enemyAbilities.elementAt(i) != null) {
                enemyAbilities.elementAt(i).currentDuration++;
                if(enemyAbilities.elementAt(i).currentDuration >= enemyAbilities.elementAt(i).duration) {
                    enemyAbilities.elementAt(i).currentDuration = 0;
                    enemyAbilities.remove(i);
                }
                else
                    enemyAbilities.elementAt(i).update(this, 0, 0);
            }
        }

        //to move
        for(int i = 0; i < playerAbilities.size(); i++) {
            if(playerAbilities.elementAt(i) != null) {
                playerAbilities.elementAt(i).currentDuration++;
                if(playerAbilities.elementAt(i).currentDuration >= playerAbilities.elementAt(i).duration) {
                    playerAbilities.elementAt(i).currentDuration = 0;
                    for(int j = 0; j < playerAbilities.elementAt(i).projectiles.length; j++) {
                        playerAbilities.elementAt(i).projectiles[j].image.ResetToFirst();
                        playerAbilities.elementAt(i).projectiles[j].itHit = false;
                        playerAbilities.elementAt(i).projectiles[j].timeUntilNextHit = 0;
                        playerAbilities.elementAt(i).projectiles[j].coordSet = false;
                    }
                    playerAbilities.remove(i);
                    player.usingAbility = false;
                }
                else
                    playerAbilities.elementAt(i).update(this, player.getWorldX(), player.getWorldY());
            }
        }
    }
    public void update() throws IOException {
        if(!this.getUi().isDialogueCheck()) {
            player.update();
            updateEnemies();
            if(timeStop) {
                if(timeStopTimer >= 300)
                    timeStop = false;
                timeStopTimer++;
            }
        }
    }

    //Display On Screen
    void drawObjects(Graphics2D g2) {
        for(int i = 0; i < obj[onMap].length; i++) {
            if (obj[onMap][i] != null) {
                obj[onMap][i].draw(this, g2);
            }
        }
    }

    void drawNpcs(Graphics2D g2) {
        for(int i = 0; i < npc[onMap].length; i++) {
            if(npc[onMap][i] != null) { npc[onMap][i].draw(this, g2); }
        }
    }

    //to change to Enemies and another one for abilities
    void drawEnemiesAndAbilities(Graphics2D g2) {
        for(int i = 0; i < enemies[onMap].length; i++) {
            if(enemies[onMap][i] != null) {
                enemies[onMap][i].draw(this, g2);
            }
        }
        for(int i = 0; i < enemyAbilities.size(); i++) {
            if(enemyAbilities.elementAt(i) != null) {
                enemyAbilities.elementAt(i).draw(this, g2);
            }
        }
        for(int i = 0; i < playerAbilities.size(); i++) {
            if(playerAbilities.elementAt(i) != null) {
                playerAbilities.elementAt(i).draw(this, g2);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //For Double Buffer
        BufferedImage back = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = back.createGraphics();

        //Draws
        tileM[onMap].draw(g2);          //Tiles
        drawObjects(g2);                //Objects
        drawNpcs(g2);                   //NPCs
        drawEnemiesAndAbilities(g2);    //Enemies & their Abilities
        player.draw(g2);                //Player
        ui.draw(g2);                    //UI

        //Double Buffer
        g2.dispose();
        Graphics2D g2Front = (Graphics2D) g;
        g2Front.drawImage(back, 0, 0, null);
        g2Front.dispose();
    }

    //Main Loop
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
            try {
                update();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
