import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MadMan extends Enemy{
    long t;
    public MadMan(double x, double y) throws IOException {
        super(x, y, 10, 2,13, new Color(8, 8, 246), ImageIO.read(new File("data\\madman.jpg")));
        t = System.currentTimeMillis();
    }


    ArrayList<EnemyBullet> shoot(){
        long time = System.currentTimeMillis();
        if(time - t > 500) {
            ArrayList<EnemyBullet> bullets = new ArrayList<>();
            Random r = new Random();
            for(int i = 0; i < 5; i++){
                Vector2D v = new Vector2D(2*r.nextDouble()-1, 2*r.nextDouble()-1);
                v.normalize();
                bullets.add(new EnemyBullet(0.5*v.x, 0.5*v.y, damage, x, y, -1));
            }
            t = time;
            return bullets;
        }else{
            return null;
        }
    }
}
