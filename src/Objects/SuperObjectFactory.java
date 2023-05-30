package Objects;

import java.io.IOException;

import static Objects.SuperObject.*;

public class SuperObjectFactory {

    public static SuperObject makeObject(int objectType, String name, String message, int worldX, int worldY, Object... param) throws IOException {
        worldX *= 96;
        worldY *= 96;
        return switch (objectType) {
            case OBJECT_ITEM -> new Item(name, message, worldX, worldY);
            case OBJECT_POWERUP -> new PowerUp(name, message, worldX, worldY);
            case OBJECT_TELEPORTER -> new Teleporter(name, message, worldX, worldY, (int)param[0], (int)param[1], (int)param[2], (String) param[3]);
            default -> null;
        };
    }
}
