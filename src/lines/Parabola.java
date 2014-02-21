package lines;

import java.awt.Point;
import java.awt.geom.Point2D;
import triangles.Polyangle;
import vectors.TheVector;
import java.util.*;

/**
 *
 * @author Матвей
 */
public class Parabola {

    private final double ROUND_KOEF = 0.0000000001;
    private double p;
    //TODO: отследить поведение Transform При всяких действиях. Является ли этот объект иммутабельным?
    private Point2D focus;
    private Line direct;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Point2D intersect[] = {new Point(0,0), new Point(1, -1)};
        Point2D foc = new Point(1, 2);
        Point2D direct[] = {new Point(0, 8), new Point(5, 0)};
        Line inters = Line.approximate(intersect[0], intersect[1]);
        Line direc = Line.approximate(direct[0], direct[1]);
        Parabola para = new Parabola(foc, direc);
        log log = new log();
        Point2D newCenter = para.getApex();
        System.out.println(newCenter);
        System.out.println();
        log.centreMove = new Point2D.Double(-newCenter.getX(), -newCenter.getY());
        direc = Transform.move(direc, newCenter);
        foc = Transform.move(foc, newCenter);
        inters = Transform.move(inters, newCenter);
        System.out.println(direc);
        System.out.println(inters);
        System.out.println(foc);
        Parabola parab = new Parabola(foc, direc);
        System.out.println(parab.getApex());
        System.out.println();
        log.theta = -direc.getRAngle();
        double theta = direc.getRAngle();
        direc = Transform.roll(direc, theta);
        inters = Transform.roll(inters, theta);
        foc = Transform.roll(foc, theta);
        para = new Parabola(foc, direc);
        System.out.println(direc);
        System.out.println(inters);
        System.out.println(foc);
        Point2D blackAnswer[] = para.getIntersection(inters);
        System.out.println(Arrays.toString(blackAnswer));
    }
    //TODO: разобраться с этой портянкой и перетащить в соответствующее ей место.

    /**
     * Стандартный контруктр для параболы. 
     * @param focus фокус параболы.
     * @param direct директриса параболы.
     */
    public Parabola(Point2D focus, Line direct) {
        this.focus = focus;
        this.direct = direct;
        p = (StrictMath.abs(direct.getA() * focus.getX() + direct.getB() * focus.getY()
                + direct.getC()) / StrictMath.sqrt(StrictMath.pow(direct.getA(), 2) + StrictMath.pow(direct.getB(), 2)));
    }

    /**
     * Доступ к фкальному параемтру параболы.
     * @return фокальный параметр.
     */
    public double getP() {
        return p;
    }

    /**
     * Доступ к фокусу параболы.
     * @return фокус параболы.
     */
    public Point2D getFocus() {
        return focus;
    }

    /**
     * Доступ к директрисе параболы.
     * @return директриса параболы.
     */
    public Line getDirect() {
        return direct;
    }

    /**
     * Нахождение пересечения параболы с прямой. NB: применимо только тогда, когда параболу можно описать квадратным уравнением.
     * @param l прямая-секущая.
     * @return массив точек-пересечений. NB: возмжны null значения, нужно отследить и оттестировать все плохие случаи.
     */
    //TODO: разобраться с "плхими" случаями, внести в документацию.
    protected Point2D[] getIntersection(Line l) {
        Point2D apex = this.getApex();
        double a = l.getA();
        double b = l.getB();
        double c = l.getC();
        double d = 1 / (2 * p);
        TheVector FF = new TheVector(new Point2D[]{focus, apex});
        if ((FF.getVCoords()[1]) > 0) {
            d = -d;
        }
        double e = -(apex.getX() / p);
        double f = (StrictMath.pow(apex.getX(), 2) / (2 * p)) + apex.getY();
        if ((StrictMath.abs(b) - ROUND_KOEF) < 0) {
            double x = -c / a;
            double y = ((c * c * d) / (a * a)) - ((e * c) / a) + f;
            Point2D answer = new Point2D.Double(x, y);
            return new Point2D[]{answer, answer};
        }
        double D = StrictMath.pow((e + (a / b)), 2) - 4 * d * (c / b + f);
        if (D < 0) {
            return new Point2D[]{null, null};
        }
        if ((StrictMath.abs(D) - ROUND_KOEF) < 0) {
            double x = -(e + (a / b)) / 2d;
            double y = (-a * x - c) / b;
            Point2D answer = new Point2D.Double(x, y);
            return new Point2D[]{answer, answer};
        }
        double x1 = (-(e + (a / b)) + StrictMath.sqrt(D)) / (2 * d);
        double x2 = (-(e + (a / b)) - StrictMath.sqrt(D)) / (2 * d);
        double y1 = (-a * x1 - c) / b;
        double y2 = (-a * x2 - c) / b;
        Point2D[] answer = {new Point2D.Double(x1, y1), new Point2D.Double(x2, y2)};
        return answer;
    }

    /**
     * Нахождение пересечений с многоугольником.
     * @param pol многоугольник.
     * @param direct директриса параболы.
     * @param focus фокус параболы.
     * @return массив пересечний с многоугольником. NB: протестировать вырожденный случаи.
     */
    //TODO: протестировать вырожденные случаи.
    public static Point2D[] getPolIntersection(Polyangle pol, Point2D[] direct, Point2D focus) {
        Point2D[][] sect = pol.breakTo();
        ArrayList<Point2D> black = new ArrayList<>(sect.length * 2);
        for (int i = 0; i < sect.length; i++) {
            black.addAll(Arrays.asList(Line.findPoint(sect[i], direct, focus)));
        }
        Iterator<Point2D> itera = black.iterator();
        while (itera.hasNext()) {
            Point2D p = itera.next();
            if (p == null) {
                itera.remove();
            }
        }
        Point2D[] ans = new Point2D[black.size()];
        black.toArray(ans);
        return ans;
    }

    /**
     * Нахождение вершины параболы.
     * @return вершина парабола.
     */
    public Point2D.Double getApex() {
        TheVector normal = direct.getNormal();
        Point2D secP = normal.getOtherPoint(focus, true);
        Line ortogonal = Line.approximate(focus, secP);
        Point2D intersection = Line.lineSystem(direct, ortogonal);
        Point2D.Double apex = new Point2D.Double((focus.getX() + intersection.getX()) / 2, (focus.getY() + intersection.getY()) / 2);
        return apex;
    }

    /**
     * Нахождение фокального параметра по заданным фокусу и директрисе.
     * @param focus фокус параболы.
     * @param direct директриса параболы.
     * @return вершина параболы. NB: не тестирован граничный случай.
     */
    //TODO: протестировать вырожденный случай.
    public static double getP(Point2D focus, Line direct) {
        double p = (StrictMath.abs(direct.getA() * focus.getX() + direct.getB() * focus.getY()
                + direct.getC()) / StrictMath.sqrt(StrictMath.pow(direct.getA(), 2) + StrictMath.pow(direct.getB(), 2)));
        return p;
    }
}