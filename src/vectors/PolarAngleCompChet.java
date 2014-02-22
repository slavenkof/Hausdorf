package vectors;

import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 *
 * @author Матвей
 */
public class PolarAngleCompChet implements Comparator<Point2D> {

    private final Point2D fulcrum;

    /**
     *
     * @param fulcrum
     */
    public PolarAngleCompChet(Point2D fulcrum) {
        this.fulcrum = fulcrum;
    }

    @Override
    public int compare(Point2D p1, Point2D p2) {
        if (p1 == p2 || p1.equals(p2)) {
            return 0;
        }
        Point2D f1 = new Point2D.Double(fulcrum.getX(), fulcrum.getY() + 1);
        TheVector et = new TheVector(new Point2D[]{fulcrum, f1});
        TheVector v1 = new TheVector(new Point2D[]{fulcrum, p1});
        TheVector v2 = new TheVector(new Point2D[]{fulcrum, p2});
        double cos1 = TheVector.scalarDerivate(new TheVector[]{et, v1}) / (et.getVLength() * v1.getVLength());
        double cos2 = TheVector.scalarDerivate(new TheVector[]{et, v2}) / (et.getVLength() * v2.getVLength());
        if (cos1 > cos2) {
            return -1;
        }
        if (cos1 < cos2) {
            return 1;
        }
        return 0;
    }
}
