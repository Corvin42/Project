public class Room {
    int[] j;
    int id;
    int distance(Room r){
        return  Math.abs(j[0]+j[1]-r.j[0]-r.j[1]);
    }

    public void setJ(int[] j) {
        this.j = j;
    }

    public int[] getJ() {
        return j;
    }
}

