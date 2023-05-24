public class Vector2D {

    double x;
    double y;

    public Vector2D() {
        this.x = 1;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double len(){
        return(Math.sqrt(x * x + y * y));
    }

    public void x(double k){
        x *= k;
        y *= k;
    }

    public void plus(Vector2D other){
        this.x += other.x;
        this.y += other.y;
    }

    public void minus(Vector2D other){
        this.x -= other.x;
        this.y -= other.y;
    }

    public Vector2D sum(Vector2D other){
        return new Vector2D(x + other.x, y + other.y);
    }

    public static Vector2D sum(Vector2D first, Vector2D second){
        return new Vector2D(first.x + second.x, first.y + second.y);
    }

    public Vector2D mult(double a){
        return new Vector2D(a * x, a * y);
    }

    public double mult(Vector2D other){
        return x*other.x + y * other.y;
    }

    public static double mult(Vector2D first, Vector2D second){
        return first.x * second.x + first.y * second.y;
    }

    public static Vector2D mult(Vector2D other, double a){
        return new Vector2D(other.x * a, other.y * a);
    }

    public void normalize(){
        double len = len();
        if(len != 0){
            x /= len;
            y /= len;
        }
    }

    public Vector2D norm(){
        double len = len();
        if(len == 0){
            return new Vector2D(0, 0);
        }
        return  new Vector2D(x / len, y /len);
    }

    public void rotate(double angle){
        double x1 = Math.cos(angle) * x - Math.sin(angle) * y;
        double y1 = Math.sin(angle) * x + Math.cos(angle) * y;
        x = x1;
        y = y1;
    }

    public Vector2D rotated(double angle){
        double x1 = Math.cos(angle) * x - Math.sin(angle) * y;
        double y1 = Math.sin(angle) * x + Math.cos(angle) * y;
        return new Vector2D(x1, y1);
    }

    public Vector2D ort(){
        Vector2D v = this.rotated(Math.PI / 2);
        return v.norm();
    }

    public double phi(){
        return Math.atan(y/x);
    }

    public int getQuarter(){
        if(x == 0 || y == 0){
            return 0;
        }
        if(x > 0 && y > 0){
            return 1;
        }
        if(x < 0 && y > 0){
            return 2;
        }
        if(x < 0 && y < 0){
            return 3;
        }
        else{
            return 4;
        }
    }

    public boolean equals(Vector2D other){
        return x == other.x && y == other.y;
    }

    @Override
    public String toString(){
        return "(" + String.format("%.2f", x) + "," + String.format("%.2f", y) + ")";
    }
}

