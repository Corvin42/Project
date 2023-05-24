import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Zombie extends Enemy{


        public Zombie(double x, double y) throws IOException {
            super(x, y, 6, 3, 50,new Color(9, 26, 2), ImageIO.read(new File("data\\zombi.jpg")));
        }
    }


