package vectors;

import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 * Класс реализует сортировку точек по полярному углу в нижней полуплоскости.
 * Сортировка производится следующим образом:
 * <ul>
 * <li>Наименьшей считается точка с меньшим углом относительно положительного
 * направления оси Ox.</li>
 * <li>Если углы равны, то наименьшей считается точка, наиболее приближенная к
 * опорной точке.</li>
 * <li>Нужно быть внимательным, если есть по нескольку точек с одинаковым
 * углом.</li>
 * </ul>
 *
 */
public class PolarAngleCompDown implements Comparator<Point2D> {

    private final Point2D fulcrum;

    /**
     * Конструктор компаратора. В качестве аргумента принимает точку,
     * относительно которой будет производится сортировка.
     *
     * @param fulcrum опорная точка.
     */
    public PolarAngleCompDown(Point2D fulcrum) {
        this.fulcrum = fulcrum;
    }

    @Override
    public int compare(Point2D p1, Point2D p2) {
        if (p1 == p2 || p1.equals(p2)) {
            return 0;
        }
        Point2D f1 = new Point2D.Double(fulcrum.getX() + 1, fulcrum.getY());
        TheVector et = new TheVector(new Point2D[]{fulcrum, f1});
        TheVector v1 = new TheVector(new Point2D[]{fulcrum, p1});
        TheVector v2 = new TheVector(new Point2D[]{fulcrum, p2});
        if (p1 == fulcrum || p1.equals(fulcrum) || p2 == fulcrum || p2.equals(fulcrum)) {
            if (v1.scalarSq() > v2.scalarSq()) {
                return -1;
            }
            if (v1.scalarSq() < v2.scalarSq()) {
                return 1;
            }
        }
        double cos1 = TheVector.scalarDerivate(new TheVector[]{et, v1}) / (et.getVLength() * v1.getVLength());
        double cos2 = TheVector.scalarDerivate(new TheVector[]{et, v2}) / (et.getVLength() * v2.getVLength());
        if (cos1 > cos2) {
            return 1;
        }
        if (cos1 < cos2) {
            return -1;
        }
        if (v1.getVLength() > v2.getVLength()) {
            return 1;
        }
        if (v1.getVLength() < v2.getVLength()) {
            return -1;
        }
        return 0;
    }
}
