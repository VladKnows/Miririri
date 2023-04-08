package Tiles;

import Main.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    public Tile[] tile = new Tile[80];
    public int [][]mapTile;
    GameScreen gs;

    public TileManager(GameScreen gs, int mapNumber) throws IOException {
        this.gs = gs;

        getTileImage();
        mapTile = new int[gs.maps[mapNumber].maxWorldCol][gs.maps[mapNumber].maxWorldRow];
        loadMap("/res/Maps/" + gs.maps[mapNumber].source, mapNumber);
        for(int i = 0; i < gs.maps[mapNumber].maxWorldCol; i++) {
            for(int j = 0; j < gs.maps[mapNumber].maxWorldRow; j++) {
                System.out.print(mapTile[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void loadMap(String a, int mapNumber) throws IOException {
        InputStream is = getClass().getResourceAsStream(a);
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        for(int i = 0; i < gs.maps[mapNumber].maxWorldRow; i++) {
            String s = br.readLine();
            s = s.replaceAll("  ", " ");
            String []numbers = s.split(" ");
            for(int j = 0; j < gs.maps[mapNumber].maxWorldCol; j++) {
                int num = Integer.parseInt(numbers[j]);
                mapTile[j][i] = num;
            }
        }
    }

    BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }

    void setMultipleCollisions(int []v, int x, int y, int width, int height) {
        for(int i = 0; i < v.length; i++) {
            tile[v[i]].setCollision(x, y, width, height);
        }
    }

    public void getTileImage() {
        try {
            BufferedImage Map = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Ground/Tiles.png")));
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 10; j++) {
                    int k = i * 10 + j;
                    tile[k] = new Tile(Map.getSubimage(j * 32, i * 32, 32, 32));
                    tile[k].image = Map.getSubimage(j * 32, i * 32, 32, 32);
                    tile[k].image = scaleImage(tile[k].image, gs.tileSize, gs.tileSize);
                }
            }

            setMultipleCollisions(new int[]{14, 15, 16, 17, 24, 25, 26, 27}, 0, 0, 32, 32);
            setMultipleCollisions(new int[]{34, 35, 36, 37, 44, 45, 46, 47}, 0, 0, 32, 23);
            setMultipleCollisions(new int[]{2, 18, 19, 38, 39, 48, 49, 67, 68, 69}, 0, 8, 32, 24);
            setMultipleCollisions(new int[]{50, 52, 54}, 4, 0, 28, 32);
            setMultipleCollisions(new int[]{51, 53, 55}, 0, 0, 28, 32);
            setMultipleCollisions(new int[]{60, 62, 64}, 4, 0, 28, 25);
            setMultipleCollisions(new int[]{61, 63, 65}, 0, 0, 28, 25);
            setMultipleCollisions(new int[]{28, 58}, 0, 0, 4, 32);
            setMultipleCollisions(new int[]{29, 59}, 28, 0, 4, 32);

            tile[1].setCollision(4, 0, 26, 24);
            tile[4].setCollision(3, 5, 29, 14);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int screenX = gs.player.worldX + gs.tileSizeHalf - gs.screenWidthHalf;
        int screenY = gs.player.worldY + gs.tileSizeHalf - gs.screenHeightHalf;
        int renderScreenX1 = (screenX / gs.tileSize) * gs.tileSize - screenX;
        int renderScreenY1 = (screenY / gs.tileSize) * gs.tileSize - screenY;
        int tileX = screenX / gs.tileSize;
        int tileY = screenY / gs.tileSize;
        int tileYCopy = tileY;

        for(int i = renderScreenX1; i < gs.screenWidth; i += gs.tileSize, tileX++, tileY = tileYCopy) {
            for(int j = renderScreenY1; j < gs.screenHeight; j += gs.tileSize, tileY++) {
                g2.drawImage(tile[mapTile[tileX][tileY]].image, i, j, null);
            }
        }
    }
}
