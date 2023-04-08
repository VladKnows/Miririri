package Tiles;

public class Map {
    String source;
    int maxWorldRow;
    int maxWorldCol;

    public Map(String source, int x, int y) {
        this.source = source;
        maxWorldRow = x;
        maxWorldCol = y;
    }

    String getMap() {
        return source;
    }
}
