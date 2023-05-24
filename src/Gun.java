import java.awt.*;

public class Gun {

    int damage;
    Color c;
    int cooldown;

    public Gun(int damage, Color c, int cooldown) {
        this.damage = damage;
        this.c = c;
        this.cooldown = cooldown;
    }
}