package lines;

import java.awt.geom.Point2D;

public class Transform {

    /**
     *
     * @param l
     * @param angle угол в радианах
     * @return
     */
    public static Line roll(Line l, double angle) {
        double a = l.getA() * StrictMath.cos(angle) + l.getB() * StrictMath.sin(angle);
        double b = -l.getA() * StrictMath.sin(angle) + l.getB() * StrictMath.cos(angle);
        return new Line(a, b, l.getC());
    }

    /**
     *
     * @param p
     * @param angle угол в радианах
     * @return
     */
    public static Point2D roll(Point2D p, double angle) {
        double x = p.getX() * StrictMath.cos(angle) + p.getY() * StrictMath.sin(angle);
        double y = -p.getX() * StrictMath.sin(angle) + p.getY() * StrictMath.cos(angle);
        return new Point2D.Double(x, y);
    }

    /**
     *
     * @param l
     * @param p
     * @return
     */
    public static Line move(Line l, Point2D p) {
        double c = l.getC() + l.getA() * p.getX() + l.getB() * p.getY();
        return new Line(l.getA(), l.getB(), c);
    }

    /**
     *
     * @param p
     * @param cent
     * @return
     */
    public static Point2D move(Point2D p, Point2D cent) {
        double x = p.getX() - cent.getX();
        double y = p.getY() - cent.getY();
        return new Point2D.Double(x, y);
    }
}
