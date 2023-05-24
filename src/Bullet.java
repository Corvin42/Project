import java.awt.*;

public class Bullet {
    double[] x = new double[2];

    double[] v  = new double[2];

    Color color;

    int max_dist;
    int dist;

    int damage;
    public Bullet(double x, double y,int damage, double vx, double vy, Color color, int max_dist){
        this.x[0] = x;
        this.x[1] = y;
        this.v[0] = vx;
        this.v[1] = vy;
        this.color = color;
        this.damage = damage;
        this.max_dist = max_dist;
        dist =0;
    }
    public void update(long dt){
        x[0]+=v[0]*dt;
        x[1]+=v[1]*dt;
        Vector2D f=  new Vector2D(v[0]*dt,v[1]*dt);
        dist+=f.len();
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect((int) x[0],(int) x[1],(int) 4,(int) 4);
        g.setColor(Color.BLACK);
    }


}
