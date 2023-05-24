import java.awt.*;
import java.io.IOException;

public class Cell {


        int x;
        int y;
        boolean status;
        int size;
        boolean is_wall;

        Color c = new Color(0, 0, 0);

        public Cell(int x, int y, boolean status, int size) throws IOException {
            this.x = x;
            this.y = y;
            this.status = status;
            this.size = size;
            this.is_wall = false;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public void setC(Color c) {
            this.c = c;
        }

}
