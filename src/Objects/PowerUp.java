package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class PowerUp extends SuperObject {
    public PowerUp(int worldX, int worldY, String name, String message) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = name;
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Objects/" + this.name + ".png")));
        this.message = message;
    }
}
