package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static Main.GameScreen.scale;

public class Item extends SuperObject {
    public Item(int worldX, int worldY, int x, int y, int width, int height, String name, String message) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        collision = true;
        solid = new Rectangle(x * scale, y * scale, width * scale, height * scale);
        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Objects/" + this.name + ".png")));
        this.message = message;
    }
}
