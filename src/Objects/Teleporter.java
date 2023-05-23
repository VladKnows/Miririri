package Objects;

import Entities.Player;
import Main.GameScreen;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Teleporter extends SuperObject {
    int toX, toY, toMap;

    public Teleporter(String name, String message, int worldX, int worldY, int toMap, int toX, int toY) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        this.toMap = toMap;
        this.toX = toX;
        this.toY = toY;
        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Objects/" + this.name + ".png")));
        this.message = message;
    }

    public void teleport(GameScreen gs, Player player) {
        gs.onMap = toMap;
        player.setWorldX(toX);
        player.setWorldY(toY);
    }

    @Override
    public void update() throws IOException {
        super.update();
        teleport(GameScreen.getInstance(), Player.getInstance());
    }
}
