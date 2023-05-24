import java.awt.*;

public class EnemyBullet extends Bullet{
    public EnemyBullet(double dx, double dy, int damage, double x, double y, int dist_max) {
        super(x, y, damage, dx, dy, Color.red, dist_max);
    }


    @Override
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)x[0], (int)x[1],  4,4);
        g.setColor(Color.black);
    }
}
