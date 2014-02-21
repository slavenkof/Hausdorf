package lines;

import java.awt.geom.Point2D;
import java.util.*;
import triangles.Computer;
import triangles.Polyangle;
import vectors.TheVector;

/**
 * Класс представляет прямую, заданную каноническим уравнением вида
 * <code>ax+by+c=0</code>. Представлена возможность восстановления уравнения по
 * двум точкам, нахождения пересечения двух прямых, восстановления уравнения
 * серединного перепендикуляра отрезка, восстановление уравнения биссектрисы.
 *
 */
public class Line {

    private static final double ROUND_KOEF = 0.0000000001;
    private final double A;
    private final double B;
    private final double C;

    /**
     * Стандартный конструктор прямой. Принимает три коэффициента.
     *
     * @param A коэффициент <code>a</code> в каноническом уравнении.
     * @param B коэффициент <code>b</code> в каноническом уравнении.
     * @param C коэффициент <code>c</code> в каноническом уравнении.
     */
    public Line(double A, double B, double C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    /**
     *
     * @return a
     */
    public double getA() {
        return A;
    }

    /**
     *
     * @return b
     */
    public double getB() {
        return B;
    }

    /**
     *
     * @return c
     */
    public double getC() {
        return C;
    }

    /**
     * Метод для восстановления уравнения прямой по двум точкам.
     *
     * @param p1 первая точка прямой.
     * @param p2 вторая точка прямой.
     * @return уравнение прямой, проходящей через эти точки.
     */
    public static Line approximate(Point2D p1, Point2D p2) {
        double x3 = p2.getX() - p1.getX();
        double y3 = p2.getY() - p1.getY();
        double A = y3;
        double B = -x3;
        double C = -(p1.getX() * y3) + p1.getY() * x3;
        return new Line(A, B, C);
    }

    /**
     * Возвращает угол наклона прямой по отношению к оси OX. Угол выражен в
     * радианах.
     *
     * @return угол наклона прямой по отношению к оси OX в радианах.
     */
    public double getRAngle() {
        if (Math.abs(B) < ROUND_KOEF) {
            return StrictMath.toRadians(90);
        }
        if ((Math.abs(-C / B) < ROUND_KOEF) && (A == 0)) {
            return StrictMath.toRadians(0);
        }
        return (StrictMath.atan(-(A / B)));
    }

    /**
     * Метод для получения направляющего вектора прямой.
     *
     * @return направляющий вектр прямой.
     */
    public TheVector getDirection() {
        return new TheVector(new double[]{-B, A});
    }

    /**
     * Метод для получения нормали к прямой.
     *
     * @return нормаль к прямой.
     */
    public TheVector getNormal() {
        return new TheVector(new double[]{A, B});
    }

    /**
     * Метод для нахождения пересечения прямой и параблы, заданной, возможно,
     * нестандартно.
     *
     * @param line прямая-секущая.
     * @param direct директриса пересекаемой параболы.
     * @param focus фокус пересекаемой параболы.
     * @return массив точек пересечения с параболой. NB: возможны null значения,
     * их нужно отследить и внести сюда.
     */
    //TODO: разобраться с "плохими" случаями, внести их в документацию.
    public static Point2D[] findPoint(Point2D[] line, Point2D[] direct, Point2D focus) {
        Line inters = Line.approximate(line[0], line[1]);
        Point2D foc = (Point2D) focus.clone();
        Line direc = Line.approximate(direct[0], direct[1]);
        Parabola para = new Parabola(foc, direc);
        log log = new log();
        Point2D newCenter = para.getApex();
        log.centreMove = new Point2D.Double(-newCenter.getX(), -newCenter.getY());
        direc = Transform.move(direc, newCenter);
        foc = Transform.move(foc, newCenter);
        inters = Transform.move(inters, newCenter);
        log.theta = -direc.getRAngle();
        double theta = direc.getRAngle();
        direc = Transform.roll(direc, theta);
        inters = Transform.roll(inters, theta);
        foc = Transform.roll(foc, theta);
        para = new Parabola(foc, direc);
        Point2D blackAnswer[] = para.getIntersection(inters);
        if ((blackAnswer[0] != null) && !(Double.isNaN(blackAnswer[0].getX()))
                && !(Double.isNaN(blackAnswer[0].getY()))) {
            Transform.roll(blackAnswer[0], log.theta);
            Transform.move(blackAnswer[0], log.centreMove);
        } else {
            blackAnswer[0] = null;
        }
        if ((blackAnswer[0] != null) && !(Double.isNaN(blackAnswer[1].getX()))
                && !(Double.isNaN(blackAnswer[1].getY()))) {
            Transform.roll(blackAnswer[1], log.theta);
            Transform.move(blackAnswer[1], log.centreMove);
        } else {
            blackAnswer[1] = null;
        }
        if ((blackAnswer[0] == null) || (!(Computer.sectContains(line, blackAnswer[0])))) {
            blackAnswer[0] = null;
        }
        if ((blackAnswer[1] == null) || (!(Computer.sectContains(line, blackAnswer[1])))) {
            blackAnswer[1] = null;
        }
        return blackAnswer;
    }

    /**
     * Метод позволяющий получить уравнение биссектрисы двух отрезков.
     *
     * @param fp первый отрезок, заданный своими граничными точками.
     * @param sp второй отрезок, заданный своими граничными точками.
     * @return уравнение прямой, содержащее биссектрису. NB: поведение на
     * вырожденных случаях неизвестно.
     */
    //TODO: протестировать случай совпадающих отрезкв, прочие вырожденные случаи.
    public static Line getBis(Point2D[] fp, Point2D[] sp) {
        TheVector one = new TheVector(fp);
        TheVector two = new TheVector(sp);
        one.normalize();
        two.normalize();
        TheVector answer = one.sumVect(two);
        Point2D fulcrum = Line.lineSystem(Line.approximate(fp[0], fp[1]), Line.approximate(sp[0], sp[1]));
        answer.concretize(fulcrum, true);
        Point2D points[] = answer.getPoints();
        Line l = Line.approximate(points[0], points[1]);
        return l;
    }

    /**
     * Метод, позволяющий получить решение системы линейных уравнений.
     *
     * @param l1 первая прямая.
     * @param l2 вторая прямая.
     * @return Точка пересечения двух прямых. NB: вырожденные случаи не
     * тестированы.
     */
    //TODO: тест на параллельные прямые, совпадающие.
    public static Point2D.Double lineSystem(Line l1, Line l2) {
        double a = l1.getA();
        double b = l1.getB();
        double c = l1.getC();
        double d = l2.getA();
        double e = l2.getB();
        double f = l2.getC();
        double x = (e * c - b * f) / (b * d - e * a);
        double y = (-a * x - c) / b;
        if (b == 0) {
            y = 0;
        }
        return new Point2D.Double(x, y);
    }

    /**
     * Метод, возвращающий уравнение серединного перепендикуляра к отрезку.
     *
     * @param p отрезок, заданный координатами своих граничных точек.
     * @return уравнение серединного перпендикуляра к отрезку. NB: отследить
     * вырожденные случаи.
     */
    //TODO: вырожденный случай нужно протестировать.
    public static Line getMPerpen(Point2D[] p) {
        double x = (p[0].getX() + p[1].getX()) / 2;
        double y = (p[0].getY() + p[1].getY()) / 2;
        Point2D.Double middle = new Point2D.Double(x, y);
        TheVector vec = new TheVector(p);
        TheVector perpen = vec.getPerpen();
        perpen.concretize(middle, true);
        Point2D points[] = perpen.getPoints();
        Line answer = Line.approximate(points[1], points[0]);
        return answer;
    }

    /**
     * Нахождение пересечения прямой и отрезка.
     *
     * @param sect пересекаемый отрезок.
     * @param line секущая.
     * @return точка пересечения. NB: возможны null-значения, вырожденные случаи
     * не тестировались.
     */
    //TODO: протестировать вырожденные задачи.
    public static Point2D getSectIntersection(Point2D[] sect, Line line) {
        Line isect = Line.approximate(sect[0], sect[1]);
        Point2D answer = Line.lineSystem(line, isect);
        if (Computer.sectContains(sect, answer)) {
            return answer;
        }
        return null;
    }

    /**
     * Нахождение точек пересечения с многоугольником.
     *
     * @param pol пересекаемый многоугольник.
     * @param line секущая.
     * @return массив точек-пересечений. NB: не тестированы различные
     * вырожденные случаи. //TODO: Протестировать вырожденные случаи!
     */
    public static Point2D[] getPolIntersection(Polyangle pol, Line line) {
        Point2D sect[][] = pol.breakTo();
        ArrayList<Point2D> answer = new ArrayList<>(sect.length);
        for (int i = 0; i < sect.length; i++) {
            answer.add(Line.getSectIntersection(sect[i], line));
        }
        Iterator<Point2D> itera = answer.iterator();
        while (itera.hasNext()) {
            Point2D p;
            p = itera.next();
            if (p == null) {
                itera.remove();
            }
        }
        Point2D ans[] = new Point2D[answer.size()];
        answer.toArray(ans);
        return ans;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 33 * hash + (int) (Double.doubleToLongBits(this.A) ^ (Double.doubleToLongBits(this.A) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.B) ^ (Double.doubleToLongBits(this.B) >>> 32));
        hash = 34 * hash + (int) (Double.doubleToLongBits(this.C) ^ (Double.doubleToLongBits(this.C) >>> 32));
        return hash;
    }

    /**
     * NB: Деления, возможность исключений, переписать и протестировать.
     *
     * @param obj
     * @return
     */
    //TODO: переписать. Не понятно поведение на нулях.
    @Deprecated
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        double aDer = this.A / other.getA();
        double bDer = this.B / other.getB();
        double cDer = this.C / other.getC();
        if (StrictMath.abs(aDer - bDer) > ROUND_KOEF) {
            return false;
        }
        if (StrictMath.abs(aDer - cDer) > ROUND_KOEF) {
            return false;
        }
        if (StrictMath.abs(bDer - cDer) > ROUND_KOEF) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return A + " * X + " + B + " * Y + " + C + " = 0";
    }
}
