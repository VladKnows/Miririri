package Objects;

import java.io.IOException;

import static Objects.SuperObject.*;

public class SuperObjectFactory {

    public static SuperObject makeObject(int objectType, String name, String message, int worldX, int worldY, int... param) throws IOException {
        worldX *= 96;
        worldY *= 96;
        if(param.length > 2) {
            param[1] *= 96;
            param[2] *= 96;
        }
        return switch (objectType) {
            case OBJECT_ITEM -> new Item(name, message, worldX, worldY);
            case OBJECT_POWERUP -> new PowerUp(name, message, worldX, worldY);
            case OBJECT_TELEPORTER -> new Teleporter(name, message, worldX, worldY, param[0], param[1], param[2]);
            default -> null;
        };
    }
}
