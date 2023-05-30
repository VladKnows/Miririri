package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Buttons {
    BufferedImage []images = new BufferedImage[3];
    Rectangle bounds;
    int currentLabel = 0;

    Buttons(String path, int x, int y, int width, int height) throws IOException {
        images[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + ".png")));
        images[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_Pressed.png")));
        images[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + ".png")));
        bounds = new Rectangle(x, y, width, height);
    }

    void setCurrentLabel() throws IOException {
        Mouse m = GameScreen.getInstance().mouse;
        if(m.x > bounds.x && m.x < bounds.x + bounds.width && m.y > bounds.y && m.y < bounds.y + bounds.height) {
            currentLabel = 1;
            if(m.leftClick)
                currentLabel = 2;
        } else {
            currentLabel = 0;
        }
    }

    public BufferedImage getImage() throws IOException {
        setCurrentLabel();
        return images[currentLabel];
    }
}
