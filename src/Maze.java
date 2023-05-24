import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Maze {
    BufferedImage pol = ImageIO.read(new File("data\\pol.png"));
    BufferedImage stena = ImageIO.read(new File("data\\stena.jpg"));
    ArrayList<Enemy> enemies = new ArrayList<>();
    boolean molodec = false;
    int[][] maze;
    ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    ArrayList<Room> room;
    int[][] nemaze;
    ArrayList<ArrayList<Cell>> cell = new ArrayList<>();
    ArrayList<ArrayList<Integer>> graph;
    int k,l;
    Random r = new Random();
    //467
    void make_graph(){
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                        if(j >= 1) {
                            graph.get(20 * i + j).set(20  * i+ j - 1, boolean_to_int(cell.get(i).get( j - 1).status));
                        }
                        if(j < 19) {
                            graph.get(20 * i + j).set(20 * i + j + 1, boolean_to_int(cell.get(i ).get( j + 1).status));
                        }
                        if(i >= 1) {
                            graph.get(20 * i + j).set(20 * (i - 1) + j, boolean_to_int(cell.get(i - 1 ).get( j).status));
                        }
                        if(i < 19) {
                            graph.get(20 * i + j).set(20 * (i + 1) + j, boolean_to_int(cell.get(i + 1 ).get( j).status));
                        }
                    }
                }
            }
    public  Maze(int k, int l, int probably, int difficult) throws IOException {
// k = 125, l =100
        this.k = k;
        this.l = l;
        maze = new int[k][];
        for(int i =0; i<maze.length;i++){
            maze[i] = new int[l];
        }
        GeneratingMazeRoom(20);
        GeneratingLines();
        for(int i = 0; i<20;i++){
            for(int j = 0;j<20;j++){
                if(i == 0){
                    maze[i][j] = 0;
                }
                if(i==19){
                    maze[i][j] = 0;
                }
                if(j == 0){
                    maze[i][j] = 0;
                }
                if(j==19){
                    maze[i][j] = 0;
                }
            }
        }

        graph = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            cell.add(new ArrayList<Cell>());
            for(int j = 0; j < 20; j++){
                cell.get(i).add(new Cell(j, i, false, 40));
            }
        }
        for(int i = 0; i < 400; i++){
            graph.add(new ArrayList<Integer>());
            for(int j = 0; j < 400; j++){
                graph.get(i).add(0);
            }
        }
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(maze[i][j] == 1) {
                    cell.get(j).get(i).status = true;
                }
            }
        }
        make_graph();
        Random t = new Random(1406);

        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(maze[i][j] == 1 && i!= room.get(0).j[0] && j!= room.get(0).j[1] ) {
                    int y = t.nextInt(1000);
                    if(difficult<=2) {
                        if (y <= probably + 10 * difficult) {
                            enemies.add(new Zombie(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                        }
                    }
                    else{
                        if( difficult>2&& difficult<=4){
                            if (y <= probably + 10 * difficult) {
                                int z = r.nextInt(10);
                                if(z <= 1) {
                                    enemies.add(new Rifler(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                                }
                                else {
                                    enemies.add(new Zombie(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                                }
                            }
                        }
                        else {
                            if(difficult>=5){
                                if (y <= probably + 10 * difficult) {
                                    int z = r.nextInt(100);
                                    if (z <= 8) {
                                        enemies.add(new MadMan(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                                    } else {
                                        int mnmn = r.nextInt(10);
                                        if (mnmn <= 1) {
                                            enemies.add(new Rifler(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                                        } else {
                                            enemies.add(new Zombie(100 + (i + 1) * 40 + 20, (j + 1) * 40 + 20));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    int[][] GetMaze(){
        return maze;
    }
    void paint(Graphics g){
        for(int i =0;  i<20;i++){
            for(int j = 0; j < 20;j++ ) {

                    if (maze[i][j] == 0) {
                        g.drawImage(stena,100+40 * (i+1), 40 * (j+1 ),null);
                    } else {

                        if (maze[i][j] == 1) {
                            g.setColor(Color.WHITE);
                            g.drawImage(pol,100+40 * (i+1) , 40 * (j+1) , null);
                            g.setColor(Color.BLACK);
                        }

                    }
            }

        }
        for(EnemyBullet enemyBullet:enemyBullets){
            enemyBullet.paint(g);
        }

        for(Enemy enemy:enemies){
            enemy.paint(g);
        }
        for(PlayerBullet bullet:playerBullets){
            bullet.paint(g);
        }
    }
    public ArrayList<Room> getRoom() {
        return room;
    }
    void GeneratingMazeRoom(int n){
        room = new ArrayList<Room>();
        int[] h = new int[n];

        for(int i =0; i<n && h[i]<1000;i++){
            System.out.println(i);
            boolean v = true;
            int[] x = new int[2];
            x[0] = r.nextInt(maze.length-6)+3;
            x[1] = r.nextInt(maze[1].length - 6)+3;
            Room r = new Room();
            r.setJ(x);
            for (int j = 0; j < i; j++) {
                if (r.distance(room.get(j)) <4) {
                    if (v) {
                        h[i]++;
                        i--;
                        v = false;
                    }
                }
            }
            if(v){
                room.add(r);
            }
        }
        for(int i = 0;i< room.size();i++) {

            int[] x = room.get(i).getJ();
            for (int j = -1; j < 1; j++) {
                for (int k = -1; k < 1; k++) {
                    maze[x[0] + j][x[1] + k] = 1;
                }
            }

        }
    }
    void GeneratingLine(Room r1, Room r2){
        if(r1!=r2 ){
            int kx = 0;

            int dy = r1.getJ()[1]-r2.getJ()[1];  int ky = 0;
            Random t = new Random(140606);
            int dx = r1.getJ()[0]-r2.getJ()[0];
            int[] puty = new int[r1.distance(r2)];
            for(int i = 0; i<puty.length;i++){
                puty[i] = 0;
            }
            int wewe = 0;
            for(int i = 0; i<Math.abs(dx) && wewe<10000;i++){
                int h  = t.nextInt(puty.length);
                System.out.println(i);
                if(puty[h]!=1){
                    puty[h] = 1;
                }
                else{
                    wewe++;
                    i--;
                }
            }
            if(dx!=0){
                dx = Math.abs(dx) / dx;
            }
            if(dy != 0){
                dy = Math.abs(dy) / dy;
            }
            for (int i = 0; i < puty.length; i++) {
                kx += puty[i] * dx;
                ky += (1 - puty[i]) * dy;
                maze[r1.getJ()[0] - kx][r1.getJ()[1] - ky] = 1;
                int e = r.nextInt(3)-1;
                int f=0;
                if(f!=0){
                    f = r.nextInt(3)-1;
                }
                if((r1.getJ()[0] - kx+e<maze.length) && (r1.getJ()[0] - kx+e>0) && (r1.getJ()[1] - ky+f<maze[0].length) && (r1.getJ()[1] - ky+f>0)) {
                    maze[r1.getJ()[0] - kx + e][r1.getJ()[1] - ky + f] = 1;
                }

            }
        }
    }
    void GeneratingLines(){

        nemaze = new int[room.size()][];
        int[][] f = new int[room.size()][];
        for(int i = 0; i<nemaze.length;i++){
            nemaze[i] = new int[room.size()];
            f[i] = new int[room.size()];
        }
        for(int i = 1;i< room.size();i++){
            GeneratingLine(room.get(0),room.get(i));
        }
        for(int i = 0; i<room.size();i++){
            for(int j = 0;j< i;j++){
                f[i][j] = room.get(i).distance(room.get(j));
            }
        }
        int[] k = new int[room.size()];
        int[] t = new int[room.size()];
        for(int i = 0; i<f.length;i++){
            k[i] = f[i][0];
            t[i] = 0;
            for(int j = 0;j<f[i].length;j++){
                if(f[i][j]<=k[i]){
                    k[i] = f[i][j];
                    t[i] = j;
                }
            }
        }
        for(int i = 0; i<room.size();i++){
            nemaze[i][t[i]] = 1;
        }
        for(int i = 0;i<room.size();i++){
            for(int j = 0;j< room.size();j++){
                if(nemaze[j][i] == 1){
                    GeneratingLine(room.get(i),room.get(j));
                }
            }
        }
        boolean notexistway = true;
        for(int i = 0;i<room.size();i++){
            for(int j = 0;j< room.size();j++){
                if(nemaze[j][i] == 1){
                    notexistway = false;
                }
                if(j == room.size()-1 && notexistway){
                    GeneratingLine(room.get(i), room.get(j) );
                }
            }
        }
    }
    boolean kostul = false;
    ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    public void update( long dt, Player player) {
            update_enemies(player, dt);
            ArrayList<PlayerBullet> delete_player_bullets = new ArrayList<>();
            ArrayList<Enemy> delete_enemy = new ArrayList<>();
            ArrayList<EnemyBullet> delete_enemy_bullets = new ArrayList<>();
            if (enemies.size() == 0) {
                molodec = true;
            }
            for (Enemy enemy : enemies) {
                Rectangle2D r = new Rectangle2D.Float((int) enemy.x - 7, (int) enemy.y - 7, 13, 13);
                Rectangle2D l = new Rectangle2D.Float((int) player.x[0] - 7, (int) player.x[1] - 7, (int) 13, (int) 13);
                if (l.intersects(r)) {
                    player.damage(enemy.damage);
                }

            }
            for (EnemyBullet bullet : enemyBullets) {
                bullet.update(dt);
                boolean fgf = true;
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (maze[i][j] == 0) {
                            Line2D l = new Line2D.Float((int) bullet.x[0], (int) bullet.x[1], (int) (bullet.x[0] + 2 * bullet.v[0]), (int) (bullet.x[1] + 2 * bullet.v[1]));
                            Rectangle2D r = new Rectangle2D.Float(100 + 40 * (i + 1), 40 * (j + 1), 40, 40);
                            if (l.intersects(r)) {
                                delete_enemy_bullets.add(bullet);
                            }
                        }
                    }
                }
                Rectangle2D bul = new Rectangle2D.Float((int) bullet.x[0], (int) bullet.x[1], 4, 4);
                Rectangle2D r1 = new Rectangle2D.Float((int) player.x[0], (int) player.x[1], player.size, player.size);
                if (bul.intersects(r1)) {
                    delete_enemy_bullets.add(bullet);
                    player.damage(bullet.damage);
                }
            }
            for (PlayerBullet bullet : playerBullets) {
                bullet.update(dt);
                if ((bullet.max_dist != -1 && bullet.dist >= bullet.max_dist)) {
                    delete_player_bullets.add(bullet);
                }
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (maze[i][j] == 0) {
                            Line2D l = new Line2D.Float((int) bullet.x[0], (int) bullet.x[1], (int) (bullet.x[0] + 0.5 * bullet.v[0]), (int) (bullet.x[1] + 0.5 * bullet.v[1]));
                            Rectangle2D r = new Rectangle2D.Float(100 + 40 * (i + 1), 40 * (j + 1), 40, 40);
                            if (l.intersects(r)) {
                                delete_player_bullets.add(bullet);
                            }
                        }
                    }
                }
                for (Enemy enemy : enemies) {
                    Line2D l = new Line2D.Float((int) bullet.x[0], (int) bullet.x[1], (int) (bullet.x[0] +  4), (int) (bullet.x[1] +  4));
                    Rectangle2D r = new Rectangle2D.Float((int) enemy.x-7, (int) enemy.y-7 , 13, 13);
                    if (l.intersects(r)) {
                        enemy.hp -= bullet.damage;
                        delete_player_bullets.add(bullet);
                    }
                    if (enemy.hp <= 0) {
                        delete_enemy.add(enemy);
                    }
                }
            }

            for (PlayerBullet bullet : delete_player_bullets) {
                playerBullets.remove(bullet);
            }
            for (EnemyBullet bullet : delete_enemy_bullets) {
                enemyBullets.remove(bullet);
            }
            for (Enemy enemy : delete_enemy) {
                enemies.remove(enemy);
            }
        }

    public Cell get_cell_screen(int x, int y){
        int cell_x = (x-140) / 40 ;
        int cell_y = (y-40) / 40;
        return cell.get(cell_y).get(cell_x);
    }
    int boolean_to_int(boolean foo){
        if(foo){
            return 1;
        }
        return 10000;
    }
    public ArrayList<ArrayList<Integer>> shortestPath(int i) {
        double inf = 1000000000.0;
        ArrayList<Double> d = new ArrayList<>();
        ArrayList<ArrayList<Integer>> p = new ArrayList<>();
        ArrayList<Integer> A = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        for (int k = 0; k < graph.size(); k++) {
            d.add(0.0);
            p.add(new ArrayList<Integer>());
            if (k == i) {
                d.set(k, 0.0);
                ArrayList<Integer> a = new ArrayList<Integer>();
                a.add(k);
                p.set(k, a);
                B.add(i);
            } else {
                d.set(k, inf);
                B.add(k);
            }
        }
        while (B.size() > 0) {
            int V = 0;
            double d_v = inf + 1;
            if (B.size() > 1) {
                for (int v : B) {
                    if (d.get(v) < d_v) {
                        V = v;
                        d_v = d.get(v);
                    }
                }
            } else {
                V = B.get(0);
                d_v = d.get(V);
            }
            for (int U = 0; U < graph.size(); U++) {
                double a = graph.get(V).get(U);
                if (a > 0) {
                    if (d.get(U) < d.get(V) + graph.get(V).get(U)) {
                        d.set(U, d.get(U));
                    } else {
                        d.set(U, d.get(V) + graph.get(V).get(U));
                        ArrayList<Integer> c = (ArrayList<Integer>) p.get(V).clone();
                        c.add(U);
                        p.set(U, c);
                    }
                }
            }
            A.add(V);
            B.remove((Integer) V);
        }
        return p;
    }
   void update_enemies(Player player, long dt) {
       for (Enemy enemy : enemies) {
           Boolean s = true;
           Boolean fgf = true;
           Cell player_cell = get_cell_screen((int) (player.x[0]), (int) (player.x[1]));
           ArrayList<ArrayList<Integer>> paths = shortestPath((player_cell.x + (player_cell.y) * 20));
           Cell enemy_cell = get_cell_screen((int) enemy.x, (int) enemy.y);
           ArrayList<Integer> path = paths.get((enemy_cell.x) + (enemy_cell.y) * 20);
           Collections.reverse(path);
           Vector2D vel = new Vector2D(0, 0);
           for (int k : path) {
               boolean p = true;
               int x_cell = k % 20;
               int y_cell = k / 20;
               int tx = 140 + x_cell * 40+20 ;
               int ty = 40 + y_cell * 40 +20;
               Line2D l1 = new Line2D.Float((int) enemy.x, (int) enemy.y, (int) tx, (int) ty);
               for (int i = 0; i < 20; i++) {
                   for (int j = 0; j < 20; j++) {
                       Rectangle2D r = new Rectangle2D.Float(100 + 40 * (i + 1), 40 * (j + 1), 40, 40);
                       Rectangle2D g = new Rectangle2D.Float((int)enemy.x-7, (int)enemy.y-7, 13,13 );

                       if (maze[i][j] == 0) {
                           if (l1.intersects(r)   && !g.intersects(r) && fgf) {
                               p = false;
                           }
                           if(g.intersects(r)){
                               p = true;
                               fgf=false;
                           }
                       }
                   }
               }
               if (p) {
                   double x = tx - enemy.x;
                   double y = ty - enemy.y;
                   vel = new Vector2D(x, y);
                   vel.normalize();
               } else {
                   break;
               }
           }
           Line2D l = new Line2D.Float((int) enemy.x, (int) enemy.y, (int) player.x[0], (int) player.x[1]);
           for (int i = 0; i < 20; i++) {
               for (int j = 0; j < 20; j++) {
                   Rectangle2D r = new Rectangle2D.Float(100 + 40 * (i + 1), 40 * (j + 1), 40, 40);
                   if (maze[i][j] == 0) {
                       if (l.intersects(r)) {
                           s = false;
                       }
                   }
               }
           }
           if (enemy instanceof Rifler && s) {
               EnemyBullet bullet = ((Rifler) enemy).shoot((int) player.x[0], (int) player.x[1]);
               if (bullet != null) {
                   enemyBullets.add(bullet);
               } }
           else   if (enemy instanceof MadMan && s) {
               enemy.update(0.5*vel.x, 0.5*vel.y);
               ArrayList<EnemyBullet> bullets;
               bullets = ((MadMan) enemy).shoot();
               if (bullets != null) {
                   enemyBullets.addAll(bullets);
               }}else{
               if(fgf) {
                   enemy.update(0.75*vel.x, 0.75*vel.y);
                   fgf = true;
               }
               else{
                   enemy.update(vel.x, vel.y);
               }

               }

           }

       }
   }

