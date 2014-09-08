package triangles;

public class Mover {

    private final Rectangle area;
    private final double step;
    private Polyangle moveObj;

    public Mover(Rectangle area, double step) {
        this.area = area;
        this.step = step;
    }

    public Rectangle getArea() {
        return area;
    }

    public double getStep() {
        return step;
    }

    public void setMoveObj(Polyangle moveObj) {
        this.moveObj = moveObj;
    }
    
//    public void reset()
}
