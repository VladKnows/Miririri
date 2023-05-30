package Objects;

import Entities.Player;
import Main.GameScreen;
import Main.UI;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Teleporter extends SuperObject {
    int toX, toY, toMap;
    String alternativeMessage;

    public Teleporter(String name, String message, int worldX, int worldY, int toMap, int toX, int toY, String alternativeMessage) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        this.toMap = toMap;
        this.toX = toX * 96;
        this.toY = toY * 96;
        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Objects/" + this.name + ".png")));
        this.message = message;
        this.alternativeMessage = alternativeMessage;
    }

    public void teleport(GameScreen gs, Player player) {
        gs.onMap = toMap;
        player.setWorldX(toX);
        player.setWorldY(toY);
    }

    @Override
    public void update() throws IOException {
        if(GameScreen.getInstance().levelUnlocked[toMap]) {
            super.update();
            teleport(GameScreen.getInstance(), Player.getInstance());
        } else {
            UI.loadMessage(alternativeMessage);
        }
    }
}
