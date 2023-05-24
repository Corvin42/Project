import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    int hp;

    BufferedImage link = ImageIO.read(new File("data\\sveta.png"));
    int size = 13;
    long t;
    long t_shield;
    boolean is_damaged ;
    boolean shield;
    int damage;
    int extra_damage;
    int extra_hp;
    Gun gun;
    long t_swap;
    double[] x = new double[2];

    double[] v  = new double[2];
    public Player(int hp,double x, double y) throws IOException {
        this.hp = hp;

        this.damage = damage;
        is_damaged = false;
        gun = new DefaultGun();
        extra_hp = 0;
        extra_damage = 0;
        shield = false;
        this.hp = hp;
        this.x[0] = x;
        this.x[1] = y;
       v[0] = 0;
       v[1] = 0;
    }
    public Player(int hp,double x, double y, Gun gun) throws IOException {
        this.hp = hp;
        this.damage = damage;
        is_damaged = false;
        this.gun = gun;
        extra_hp = 0;
        extra_damage = 0;
        shield = false;
        this.hp = hp;
        this.x[0] = x;
        this.x[1] = y;
        v[0] = 0;
        v[1] = 0;
    }
    public void update(long dt){
        x[0]+=v[0]*dt;
        x[1]+=v[1]*dt;
        if(System.currentTimeMillis() - t > 300){
            is_damaged = false;
        }

    }

    public void damage(int damage){
        if(!is_damaged && !shield){
            hp -= damage;
            t = System.currentTimeMillis();
            is_damaged = true;
        }
    }

    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        int t = (int) x[0];
        int w = (int) x[1];
        g.drawImage(link,t-size/2, w-size/2, null);
        g.setColor(Color.BLACK);
    }


}
