import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    BufferedImage sprite;
    double x;
    double y;
    int hp;
    int damage;
    int size;

    Color color = Color.PINK;

    public Enemy(double x, double y, int hp, int damage,  int size, Color color, BufferedImage image) throws IOException {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.size = size;
        this.color = color;
        this.sprite = image;
    }

    public void update(double dx, double dy){
        x += dx;
        y += dy;
    }
    public void paint(Graphics g){
        g.setColor(color);
        g.drawImage(sprite, (int)x-7,(int)y-7,null);
        g.setColor(Color.BLACK);
    }

    public int getSize() {
        return size;
    }
}