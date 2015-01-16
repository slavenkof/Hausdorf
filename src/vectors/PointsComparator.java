package vectors;

import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 * Сортировка точек специфично для алгоритма Грэхема.
 * @author Матвей
 */
public class PointsComparator implements Comparator<Point2D> {

    @Override
    public int compare(Point2D p1, Point2D p2) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        if (x1 > x2) {
            return 1;
        }
        if (x1 < x2) {
            return -1;
        }
        if (x1 == x2) {
            OrdComparator ord = new OrdComparator();
            return ord.compare(p1, p2);
        } else {
            throw new RuntimeException("Check sorting " + p1.toString() + " " + p2.toString());
        }
    }

    private class OrdComparator implements Comparator<Point2D> {

        @Override
        public int compare(Point2D p1, Point2D p2) {
            double y1 = p1.getY();
            double y2 = p2.getY();
            if (y1 == y2) {
                return 0;
            }
            if (y1 < y2) {
                return -1;
            }
            if (y1 > y2) {
                return 1;
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }
}
