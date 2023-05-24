import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Rifler extends Enemy{
    long t;
    public Rifler(double x, double y) throws IOException {
        super(x, y, 4, 3, 13,new Color(241, 100, 0), ImageIO.read(new File("data\\rifler.png")));
        t = System.currentTimeMillis();
    }

    EnemyBullet shoot(int x, int y){
        long time = System.currentTimeMillis();
        if(time - t > 300) {
            Vector2D v = new Vector2D(x - (this.x + size / 2.0), y - (this.y + size / 2.0));
            v.normalize();
            t = time;
            return new EnemyBullet(0.1 * v.getX(), 0.1 * v.getY(), damage, (this.x ), (this.y), -1);
        }else{
            return null;
        }
    }
}


