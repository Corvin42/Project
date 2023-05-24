import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

public class MyFrame extends JFrame implements KeyEventDispatcher, MouseListener, MouseMotionListener {

    int prob = 55;
    int schet = 0;
    boolean first_upgrade;
    boolean second_upgrade ;
    boolean third_upgrade;
    boolean shotgun_upgrade1 = false;
    boolean shotgun_upgrade2 = false;
    boolean shotgun_upgrade3 = false;

    Maze maze;
    Player sveta;
    long time = 0;
    boolean w_flag;
    boolean s_flag;
    boolean a_flag;
    boolean d_flag;
    boolean mouse_left_flag;
    boolean pause;

    int difficult;

    private long previousWorldUpdateTime;
    public MyFrame() throws IOException {
        first_upgrade = false;
        second_upgrade = false;
        third_upgrade = false;
        difficult =1;
        maze = new Maze(20,20,prob,difficult);
        int x =(maze.room.get(0).j[0]+1)*40+100+20;
        int y = (maze.room.get(0).j[1]+1)*40+20;
        sveta = new Player(5,x,y);
        this.setSize(1650,1650);
        pause = true;
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(this);
        createBufferStrategy(2);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();
            if (bufferStrategy == null) {
                createBufferStrategy(2);
                bufferStrategy = getBufferStrategy();
            }
            g = bufferStrategy.getDrawGraphics();
            g.fillRect(0, 0, getWidth(), getHeight());
            maze.paint(g);
            sveta.paint(g);
            if(sveta.hp <=0){
                g.setColor(Color.GREEN);
                Font h = g.getFont();
                g.setFont(new Font("TimesRoman",Font.PLAIN, 100));
                g.drawString("Поражение",1000,450);
                g.setFont(h);
                g.setColor(Color.black);
            }
            g.setColor(Color.GREEN);
            g.drawString("Ваш счёт: "+schet, 1000, 600);
            g.setColor(Color.black);
            for(int i = 0;i<sveta.hp;i++){
                g.setColor(Color.red);
                g.fillRect(1000+50*i,300,45,45);
                g.setColor(Color.black);
            }
            if(first_upgrade){
                g.setColor(Color.GREEN);
                g.fillRect(1000,600,300,200);
                g.setColor(Color.black);
                g.setColor(Color.magenta);
                g.fillRect(1020,650,70,100);
                g.setColor(Color.black);
                g.drawString("Дробовик",1020, 700);
                g.setColor(Color.blue);
                g.fillRect(1110,650,70,100);
                g.setColor(Color.black);
                g.drawString("Снайперка",1110, 700);
                g.setColor(Color.pink);
                g.fillRect(1200,650,70,100);
                g.setColor(Color.black);
                g.drawString("Пулемет",1200, 700);
            }
        if(second_upgrade){
            g.setColor(Color.GREEN);
            g.fillRect(1000,600,300,200);
            g.setColor(Color.black);
            g.setColor(Color.magenta);
            g.fillRect(1020,650,70,100);
            g.setColor(Color.WHITE);
            g.drawString("+Число",1020, 700);
            g.drawString("Снарядов",1020, 715);
            g.setColor(Color.blue);
            g.fillRect(1110,650,70,100);
            g.setColor(Color.WHITE);
            g.drawString("+Качество",1110, 700);
            g.drawString("Стрельбы",1110, 715);
            g.setColor(Color.pink);
            g.fillRect(1200,650,70,100);
            g.setColor(Color.WHITE);
            g.drawString("+Безумие",1200, 700);
        }
        if(third_upgrade){
            g.setColor(Color.GREEN);
            g.fillRect(1000,600,300,200);
            g.setColor(Color.black);
            g.setColor(Color.magenta);
            g.fillRect(1020,650,70,100);
            g.setColor(Color.blue);
            g.fillRect(1110,650,70,100);
            g.setColor(Color.pink);
            g.fillRect(1200,650,70,100);
            g.setColor(Color.black);
        }
            g.dispose();
            bufferStrategy.show();
        }

    void updateWorldPhysics() throws IOException {
        if(sveta.hp>0 && !maze.molodec && !pause) {
            long currentTime = System.currentTimeMillis();
            long dt = currentTime - previousWorldUpdateTime;
            sveta.update(dt);
            maze.update(dt, sveta);
             boolean left_up = maze.get_cell_screen((int) (sveta.x[0]-sveta.size/2), (int) sveta.x[1] - sveta.size/2).status;
             boolean left_down =  maze.get_cell_screen((int) (sveta.x[0]-sveta.size/2), (int) (sveta.x[1]+sveta.size/2)).status;
             boolean right_up =  maze.get_cell_screen((int) (sveta.x[0]+sveta.size/2), (int) (sveta.x[1]-sveta.size/2)).status;
             boolean right_down =  maze.get_cell_screen((int) (sveta.x[0]+sveta.size/2), (int) (sveta.x[1]+sveta.size/2)).status;
             double h = 5;
             if (!(left_up && left_down)) {
                sveta.x[0]-=h*Math.signum(sveta.v[0]);
             }
             if (!(right_up && right_down)) {
                sveta.x[0]-=h*Math.signum(sveta.v[0]);
             }
             if (!(left_up && right_up)) {
                sveta.x[1]-=h*Math.signum(sveta.v[1]);
             }
             if (!(left_down && right_down)) {
                sveta.x[1]+=h*Math.signum(sveta.v[1]);
             }
             if (!(left_down || right_down || left_up)) {
                sveta.x[0]-=h*Math.signum(sveta.v[0]);
                sveta.x[1]+=h*Math.signum(sveta.v[1]);
             }
             if (!(left_down || right_down || right_up)) {
                sveta.x[0]-=h*Math.signum(sveta.v[0]);
                sveta.x[1]-=h*Math.signum(sveta.v[1]);
             }
             if (!(left_up || right_up || right_down)) {
                sveta.x[0]+=h*Math.signum(sveta.v[0]);
                 sveta.x[1]-=h*Math.signum(sveta.v[1]);
             }
            if (!(left_up || right_up || left_down)) {
                sveta.x[0]+=h*Math.signum(sveta.v[0]);
                sveta.x[1]+=h*Math.signum(sveta.v[1]);
            }
            Point e = MouseInfo.getPointerInfo().getLocation();
            if (System.currentTimeMillis() - time > sveta.gun.cooldown && mouse_left_flag && (sveta.gun instanceof MachineGun)) {
                double x1 = e.getX() - (sveta.x[0] );
                double y1 = e.getY() - (sveta.x[1]);
                Vector2D vel = new Vector2D(x1, y1);
                vel.normalize();
                time = System.currentTimeMillis();
                if (sveta.gun instanceof MachineGun) {
                    Random r = new Random();
                    vel.setX(vel.x + r.nextDouble()*4/10 -0.2 );
                    vel.setY(vel.y + r.nextDouble()*4/10 - 0.2);
                    vel.normalize();
                    maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.67 * vel.x, 0.67 * vel.y, sveta.gun.c, 300));
                }
            }
            previousWorldUpdateTime = currentTime;
        }
        else{
            if(maze.molodec){
                schet+=1;
                if(sveta.hp == 5){
                    schet+=1;
                }
                if(difficult == 2){
                    first_upgrade = true;
                }
                if(difficult ==4){
                    second_upgrade = true;
                }
//                if(difficult == 5){
//                    third_upgrade = true;
//                }
                difficult++;
                maze = new Maze(20,20,prob,difficult);
                int x =(maze.room.get(0).j[0]+1)*40+100+20;
                int y = (maze.room.get(0).j[1]+1)*40+20;
                sveta = new Player(5,x,y,sveta.gun);
                pause = true;
            }

        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_SPACE && !first_upgrade && !second_upgrade&& !third_upgrade){
            pause = !pause;
            sveta.v[0] = 0;
            sveta.v[1] = 0;
        }
        if(!pause) {
            double s = 0.1;
            if(sveta.gun  instanceof SniperRifle && shotgun_upgrade3){
                s= 0.15;
                a_flag = false;
                w_flag = false;
                s_flag = false;
                d_flag = false;
            }
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_A) {
                sveta.v[0] = -s;
                a_flag = true;
            }
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_D) {
                sveta.v[0] = s;
                d_flag = true;
            }
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_W) {
                sveta.v[1] = -s;
                w_flag = true;
            }
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_S) {
                sveta.v[1] = s;
                s_flag = true;
            }
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_A) {
                a_flag = false;
                if (!d_flag) {
                    sveta.v[0] = -0;
                } else {
                    sveta.v[0] = s;
                }
            }
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_D) {
                d_flag = false;
                if (!a_flag) {
                    sveta.v[0] = -0;
                } else {
                    sveta.v[0] = -s;
                }
            }
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_W) {
                w_flag = false;
                if (!s_flag) {
                    sveta.v[1] = -0;
                } else {
                    sveta.v[1] = s;
                }
            }
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_S) {
                s_flag = false;
                if (!w_flag) {
                    sveta.v[1] = -0;
                } else {
                    sveta.v[1] = -s;
                }
            }
        }


        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(first_upgrade){
            Rectangle2D l = new Rectangle2D.Float(e.getX(),e.getY(),5,5);
            Rectangle2D a1 = new Rectangle2D.Float(1020,650,70,100);
            Rectangle2D a2 = new Rectangle2D.Float(1110,650,70,100);
            Rectangle2D a3 = new Rectangle2D.Float(1200,650,70,100);
            if(l.intersects(a1)){
                try {
                    sveta.gun = new Shotgun();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                first_upgrade = false;
            }
            else{if(l.intersects(a2)){
                try {
                    sveta.gun = new SniperRifle();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                first_upgrade = false;
            }else{
                if(l.intersects(a3)){
                    try {
                        sveta.gun = new MachineGun();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    first_upgrade = false;
                }
            }}
        }
        if(second_upgrade){
            Rectangle2D l = new Rectangle2D.Float(e.getX(),e.getY(),5,5);
            Rectangle2D a1 = new Rectangle2D.Float(1020,650,70,100);
            Rectangle2D a2 = new Rectangle2D.Float(1110,650,70,100);
            Rectangle2D a3 = new Rectangle2D.Float(1200,650,70,100);
            if(l.intersects(a1)){
                    shotgun_upgrade1 = true;
                second_upgrade = false;
            }
            else{if(l.intersects(a2)){
                    shotgun_upgrade2 = true;
                second_upgrade = false;
            }else{
                if(l.intersects(a3)){
                        shotgun_upgrade3 = true;
                    second_upgrade = false;
                }
            }}
        }
        if(!pause){
        if ((e.getButton() == MouseEvent.BUTTON1)) {
            double x1 = e.getX() - (sveta.x[0]);
            double y1 = e.getY() - (sveta.x[1]);
            Vector2D vel = new Vector2D(x1, y1);
            vel.normalize();
            mouse_left_flag = true;
            if (System.currentTimeMillis() - time > sveta.gun.cooldown) {
            if (sveta.gun instanceof DefaultGun) {

                vel.normalize();
                PlayerBullet t = new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.5 * vel.x, 0.5 * vel.y, sveta.gun.c, 200);
                maze.playerBullets.add(t);
            }
                if(sveta.gun instanceof SniperRifle){
                    if(shotgun_upgrade1) {
                        maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.8 * vel.x, 0.8 * vel.y, sveta.gun.c, -1));
                    }
                    else{
                        if (shotgun_upgrade2) {
                            Vector2D vel1 = vel.rotated(Math.PI / 5);
                            Vector2D vel2 = vel.rotated(-Math.PI / 5);
                            maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.6 * vel1.x, 0.6 * vel1.y, sveta.gun.c, -1));
                            maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.6 * vel2.x, 0.6 * vel2.y, sveta.gun.c, -1));
                        }
                        else {
                            maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.6 * vel.x, 0.6 * vel.y, sveta.gun.c, -1));
                        }
                    }

                }
            if (sveta.gun instanceof Shotgun) {
                Vector2D vel1 = vel.rotated(Math.PI / 9);
                Vector2D vel2 = vel.rotated(-Math.PI / 9);
                Vector2D vel3 = vel.rotated(Math.PI / 18);
                Vector2D vel4 = vel.rotated(-Math.PI / 18);
                if(shotgun_upgrade2){
                  vel1 = vel.rotated(Math.PI / 18);
                  vel2 = vel.rotated(-Math.PI / 18);
                     vel3 = vel.rotated(Math.PI / 27);
                     vel4 = vel.rotated(-Math.PI / 27);

                }
                Random r = new Random();
                int t = r.nextInt(20)-10;
                int u = r.nextInt(20)-10;
                double z = (r.nextDouble()-0.5)/5;
                double b = (r.nextDouble()-0.5)/5;
                double n = (r.nextDouble()-0.5)/5;
                if(shotgun_upgrade3){
                    t = r.nextInt(80)-40;
                    u = r.nextInt(80)-40;
                    z = (r.nextDouble()-0.4)/3;
                    b = (r.nextDouble()-0.4)/3;
                    n = (r.nextDouble()-0.4)/3;
                }
                    maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage,  (0.6+z)*vel.x, (0.6+z)* vel.y, sveta.gun.c, 115));
                maze.playerBullets.add(new PlayerBullet(sveta.x[0],sveta.x[1], sveta.gun.damage ,(0.6+2*b)*vel3.x,(0.6+2*b)* vel3.y , sveta.gun.c, 100+2*t));
                maze.playerBullets.add(new PlayerBullet(sveta.x[0],sveta.x[1], sveta.gun.damage ,(0.6+2*n)*vel4.x, (0.6+2*n)*vel4.y , sveta.gun.c, 100+2*u));
               int c = r.nextInt(100);
                if(shotgun_upgrade1 || (shotgun_upgrade3 && c<50)) {
                    maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, (0.6 + n) * vel1.x, (0.6 + n) * vel1.y, sveta.gun.c, 100 + u));
                    maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, (0.6 + b) * vel2.x, (0.6 + b) * vel2.y, sveta.gun.c, 100 + t));
                }
                }
                if(sveta.gun instanceof MachineGun) {
                    Random r = new Random();
                    vel.setX(vel.x + (r.nextDouble()*7/10)-0.35);
                    vel.setY(vel.y + (r.nextDouble()*7/10)-0.35);
                    vel.normalize();
                    maze.playerBullets.add(new PlayerBullet(sveta.x[0], sveta.x[1], sveta.gun.damage, 0.67 * vel.x, 0.67 * vel.y, sveta.gun.c, 300));
                }
                time = System.currentTimeMillis();
            }
        }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if((e.getButton() == MouseEvent.BUTTON1)){
            mouse_left_flag = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
