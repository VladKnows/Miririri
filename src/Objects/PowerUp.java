package Objects;

import Entities.Entity;
import Main.GameScreen;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class PowerUp extends SuperObject {

    public PowerUp(String name, String message, int worldX, int worldY) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Objects/" + this.name + ".png")));
        this.message = message;
    }

    @Override
    public void update() throws IOException {
        super.update();
        GameScreen.getInstance().setObj(markedForDeletion);
    }
}
