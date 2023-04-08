package Entities;

public class MovingEntity extends Entity {
    public int HP;
    public int initSpeed;
    public int speed;

    public boolean standing;
    public String direction;

    public boolean collisionOn1 = false;
    public boolean collisionOn2 = false;
}
