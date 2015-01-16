package lines;

import java.awt.geom.Point2D;

/**
 * Класс, твечающий за проведение трансформаций системы координат: повороты,
 * параллельные переносы.
 *
 * @author Матвей
 */
public class Transform {

    /**
     * Поворот прямой на заданный угол
     * @param l прямая (immutable).
     * @param angle угол в радианах.
     * @return прямая после поворота. 
     */
    public static Line roll(Line l, double angle) {
        double a = l.getA() * StrictMath.cos(angle) + l.getB() * StrictMath.sin(angle);
        double b = -l.getA() * StrictMath.sin(angle) + l.getB() * StrictMath.cos(angle);
        return new Line(a, b, l.getC());
    }

    /**
     * Поворот точки на заданный угол.
     * @param p точка (immutable).
     * @param angle угол в радианах.
     * @return точка после поворота.
     */
    public static Point2D roll(Point2D p, double angle) {
        double x = p.getX() * StrictMath.cos(angle) + p.getY() * StrictMath.sin(angle);
        double y = -p.getX() * StrictMath.sin(angle) + p.getY() * StrictMath.cos(angle);
        return new Point2D.Double(x, y);
    }

    /**
     * Перенос системы координат для прямой в новый центр <code>centre</code>.
     * @param l прямая (immutable).
     * @param centre новый центр координат.
     * @return прямая после переноса.
     */
    public static Line move(Line l, Point2D centre) {
        double c = l.getC() + l.getA() * centre.getX() + l.getB() * centre.getY();
        return new Line(l.getA(), l.getB(), c);
    }

    /**
     * Перенос системы координат для точки в новый центр <code>centre</code>.
     * @param p точка, подлежащая переносу(immutable).
     * @param centre новый центр координат.
     * @return
     */
    public static Point2D move(Point2D p, Point2D centre) {
        double x = p.getX() - centre.getX();
        double y = p.getY() - centre.getY();
        return new Point2D.Double(x, y);
    }
}
