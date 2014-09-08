package triangles;

import triangles.draw.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import triangles.test.Log;
import vectors.*;

/**
 *
 * @author Славенко Матвей slavenkofm@yandex.ru
 */
public class Computer {

    /**
     * Это поле содержит ссылку на массив векторов. Оно используется для
     * визуализации.
     */
    public static TheVector Haus[];
    /**
     * Это поле отвечает за точность расчетов.
     */
    public static final double ROUND_KOEF = 0.01;

    /**
     * Основная точка входа в приложение, тест одной пары многоугольников.
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("ROUND" + ROUND_KOEF);
        String a = "C:/try/p2.txt";
        String b = "C:/try/p2.txt";
//        String a = "D:\\Кольцо\\Проект\\R\\Tests\\new\\TestCase2\\Pols\\5_PolA.txt";
//        String b = "D:\\Кольцо\\Проект\\R\\Tests\\new\\TestCase2\\Pols\\5_PolB.txt";
        Polyangle A = Polyangle.read(new File(a), false);
        Polyangle B = Polyangle.read(new File(b), false);
        B.translate(new TheVector(100, 150));
        Polyangle AA = A.clone();
        Polyangle BB = B.clone();
        System.out.println("    FILES");
        System.out.println(a);
        System.out.println(b);
        System.out.println("------------------------------");
        System.out.println("STABLE:" + A);
        System.out.println("------------------------------");
        System.out.println("MOVEABLE:" + B);
        System.out.println("------------------------------");
        Haus = TheVector.getUSHDistance(A, B);
        for (int i = 0; i < Haus.length; i++) {
            System.out.println("Length: " + Haus[i].getVLength());
            System.out.println("Points: " + Arrays.toString(Haus[i].getPoints()));
        }
        TPainter.once(A.getPolygon(), B.getPolygon());
        Computer.optimize(A, B);
        System.out.println("               AFTER OPTIMIZATION");
        System.out.println("STABLE:" + A);
        System.out.println("------------------------------");
        System.out.println("MOVEABLE:" + B);
        System.out.println("------------------------------");
        System.out.println(TheVector.getHDistance(A, B)[0].getVLength());
        System.out.println("------------------------------");
        Haus = TheVector.getHDistance(A, B);
        TPainter.once(A.getPolygon(), B.getPolygon());
//        TPAinterr.once(A.getPolygon(), B.getPolygon());
        for (int i = 0; i < Haus.length; i++) {
            System.out.println("Length: " + Haus[i].getVLength());
            System.out.println("Points: " + Arrays.toString(Haus[i].getPoints()));
            System.out.println(Haus[i]);
        }
        System.out.println("------------------------------");
        A = AA;
        B = BB;
        Computer.exSearchOptimize(A, B, new Log("D:/Кольцо/Проект/R/rubbish.txt"));
        System.out.println("        AFTER EXHAUSTIVE SEARCH OPTIMIZATION");
        System.out.println("STABLE:" + A);
        System.out.println("------------------------------");
        System.out.println("MOVEABLE:" + B);
        System.out.println("------------------------------");
        System.out.println(TheVector.getHDistance(A, B)[0].getVLength());
        System.out.println("------------------------------");
        Haus = TheVector.getUSHDistance(A, B);
        TPainter.once(A.getPolygon(), B.getPolygon());
//        TPAinterr.once(A.getPolygon(), B.getPolygon());
        for (int i = 0; i < Haus.length; i++) {
            System.out.println(Haus[i].getVLength());
            System.out.println(Arrays.toString(Haus[i].getPoints()));
            System.out.println(Haus[i]);
        }
        System.out.println("THE END");
    }

    /**
     * Метод, используемый для сортировки точек. Используется в алгоритме
     * Грэхема. Внимание! Аргумент остается иммутабельным!
     *
     * @param points массив точек, которые необходимо рассортировать.
     * @return массив отсортированных точек.
     */
    public static Point2D[] pointsSort(Point2D points[]) {
        Point2D act[] = points.clone();
        PointsComparator pc = new PointsComparator();
        Arrays.sort(act, pc);
        PolarAngleCompNechet pac = new PolarAngleCompNechet(act[0]);
        Arrays.sort(act, pac);
        return act;
    }

    /**
     * Метод реализует сортировку точек по полярному углу. Сортировка
     * производится следующим образом:
     * <ul>
     * <li>Наименьшей считается точка с меньшим углом относительно
     * положительного направления оси Ox.</li>
     * <li>Если углы равны, то наименьшей считается точка, наиболее приближенная
     * к началу координат.</li>
     * </ul>
     *
     * @param points массив точек, которые необходимо рассортировать.
     * @return отсортированный массив точек.
     */
    public static Point2D[] pointsSortAngle(Point2D points[]) {
        Point2D act[] = points.clone();
        ArrayList<Point2D> opUp = new ArrayList<>();
        ArrayList<Point2D> opDown = new ArrayList<>();
        for (Point2D act1 : act) {
            if (StrictMath.signum(act1.getY()) == 1) {
                opUp.add(act1);
            } else if (StrictMath.signum(act1.getY()) == 0 && StrictMath.signum(act1.getX()) >= 0) {
                opUp.add(act1);
            } else {
                opDown.add(act1);
            }
        }
        Point2D up[] = new Point2D[opUp.size()];
        opUp.toArray(up);
        Point2D down[] = new Point2D[opDown.size()];
        opDown.toArray(down);
        PolarAngleCompDown downComp = new PolarAngleCompDown(new Point(0, 0));
        Arrays.sort(down, downComp);
        PolarAngleCompUp upComp = new PolarAngleCompUp(new Point(0, 0));
        Arrays.sort(up, upComp);
        Point2D answer[] = Computer.mixArrays(new Point2D[][]{up, down});
        return answer;
    }

    /**
     * Метод, позволяющий определить, принадлежит ли точка <b>выпуклому</b>
     * многоугольнику.
     *
     * @param points массив точек, на которых находятся вершины многоульника.
     * @param p точка, чью принадлежность многоугольнику мы проверяем.
     * @return логическая величина: <code>true</code> если принадлежит и
     * <code>false</code> если не принадлежит.
     */
    public static boolean contains(Point2D[] points, Point2D p) {
        if (points.length < 2) {
            return false;
        }
        TheVector one = new TheVector(new Point2D[]{points[0], points[1]});
        TheVector two = new TheVector(new Point2D[]{points[0], p});
        boolean et
                = TheVector.pseudoScalarDerivate(new TheVector[]{one, two}) > 0;
        for (int i = 1; i < points.length - 1; i++) {
            one = new TheVector(new Point2D[]{points[i], points[i + 1]});
            two = new TheVector(new Point2D[]{points[i], p});
            if (!(TheVector.pseudoScalarDerivate(new TheVector[]{one, two}) > 0) == et) {
                return false;
            }
        }
        one = new TheVector(new Point2D[]{points[points.length - 1], points[0]});
        two = new TheVector(new Point2D[]{points[points.length - 1], p});
        if (!(TheVector.pseudoScalarDerivate(new TheVector[]{one, two}) >= 0) == et) {//xxx
            return false;
        }
        return true;
    }

    /**
     * Алгоритм проверки принадлежности невыпуклому многоугольнику. Используется
     * метод трассировки луча.
     *
     * @param points - точки многоугольника.
     * @param p - проверяемая точка.
     * @return
     */
    public static boolean UCcontains(Point2D[] points, Point2D p) {
        double x = p.getX();
        double y = p.getY();
        int npoints = points.length;
        double xpoints[] = new double[npoints];
        double ypoints[] = new double[npoints];
        for (int i = 0; i < npoints; i++) {
            xpoints[i] = points[i].getX();
            ypoints[i] = points[i].getY();
        }
        if (npoints <= 2) {
            return false;
        }
        int hits = 0;

        double lastx = xpoints[npoints - 1];
        double lasty = ypoints[npoints - 1];
        double curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
            curx = xpoints[i];
            cury = ypoints[i];

            if (cury == lasty) {
                continue;
            }

            double leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }

    /**
     * Метод-обертка для оптимизации расположения многоугольников независимо от
     * их типа. Используется субградиентный метод Шора либо его невыпуклый
     * эвристический аналог.
     *
     * @param stab неподвижный многоугольник.
     * @param move подвижный многоугольник.
     */
    public static void optimize(Polyangle stab, Polyangle move) {
        if (!(stab.isConvex()) || !(move.isConvex())) {
            Computer.optimizeUC(stab, move);
        } else {
            Computer.optimizeC(stab, move);
        }
    }

    /**
     * Алгоритм оптимизации положения двух <b>выпуклых</b> многоугольников.
     * Оптимальность оценивается с точки зрения расстояния Хаусдорфа.
     * Используется алгоритм Шора, коэффициент равен <code>cons/i</code>, где
     * cons - расстояние до оптимизации, i - номер итерации. Точность
     * определяется полем <code>ROUND_KOEF</code>.
     *
     * @param stab стабильный многоугольник - тот, который недвижим.
     * @param move подвижный многоугольник - тот, который смещается на вектор.
     */
    private static void optimizeC(Polyangle stab, Polyangle move) {
//        System.err.println("optimizeC");
        TheVector haus[] = TheVector.getCHDistance(stab, move);
        final double cons = haus[0].getVLength() / 3;//TODO: деление
        int l = 1;
        if (TheVector.insideOf(haus) || (haus[0].getVLength() < ROUND_KOEF)) {
            return;
        }
        do {
            TheVector movement = new TheVector(0, 0);
            for (int i = 0; i < haus.length; i++) {
                movement = movement.sumVect(haus[i]);
            }
            double k = (cons) / (l * movement.getVLength());
            move.translate(movement.nDerivate(k).swap());
            haus = TheVector.getCHDistance(stab, move);
            l++;
            if (haus[0].getVLength() < ROUND_KOEF) {
                break;
            }
        } while (!TheVector.insideOf(haus));
        System.out.println("Optimized in: " + l);
    }

    /**
     * Реализация эвристического алгоритма оптимального расположения двух
     * <b>произвольных</b> многоугольников. Оптимальность оценивается с точки
     * зрения расстояния Хаусдорфа. Используется алгоритм Шора, коэффициент
     * равен <code>cons/i</code>, где cons - расстояние до оптимизации, i -
     * номер итерации. Точность определяется полем <code>ROUND_KOEF</code>.
     *
     * @param stab стабильный многоугольник - тот, который недвижим.
     * @param move подвижный многоугольник - тот, который смещается на вектор.
     */
    private static void optimizeUC(Polyangle stab, Polyangle move) {
//        System.err.println("optimizeUC");
        TheVector haus[] = TheVector.getUCHDistance(stab, move);
        double cons = haus[0].getVLength() / 3;
        long l = 1;
        if (TheVector.insideOf(haus) || (haus[0].getVLength() < ROUND_KOEF)) {
            return;
        }
        do {
            TheVector movement = new TheVector(0, 0);
            for (int i = 0; i < haus.length; i++) {
                movement = movement.sumVect(haus[i]);
            }
            double k = (cons) / (l * movement.getVLength());
            move.translate(movement.nDerivate(k));
            haus = TheVector.getUCHDistance(stab, move);
            l++;
            if (haus[0].getVLength() < ROUND_KOEF) {
                break;
            }
        } while (!TheVector.insideOf(haus));
        System.out.println("Optimized in: " + l);
    }

    /**
     * Метод-обертка над алгоритмом полного перебора по сетке.
     *
     * @param stab недвижимый многоугольник.
     * @param move подвижный многоугольник.
     * @param log Log для выведения карты высот.
     * @throws FileNotFoundException
     */
    public static void exSearchOptimize(Polyangle stab, Polyangle move, Log log) throws FileNotFoundException {
        if (!(stab.isConvex()) || !(move.isConvex())) {
            Computer.exSearchOptimizeUC(stab, move, log);
        } else {
            Computer.exSearchOptimizeC(stab, move, log);
        }
    }

    /**
     * Алгоритм оптимизации положения двух выпуклых многоугольников методом
     * полного перебора. Оптимальность оценивается с точки зрения расстояния
     * Хаусдорфа - оно должно быть минимальным.
     *
     * @param stab стабильный многоугольник.
     * @param move подвижный многоугольник.
     * @param log
     * @throws java.io.FileNotFoundException
     */
//    private static void exSearchOptimizeC(Polyangle stab, Polyangle move, Log log) throws FileNotFoundException {
//        System.err.println("exSearchOptimizeC");
//        Polyangle optimum;
//        Point2D pro[] = Computer.calcRange(stab, move);
//        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(move.getBounds().x, move.getBounds().y), pro[0]});
//        move.translate(norma);
//        TheVector i = new TheVector(1, 0);
//        TheVector j = new TheVector(0, -1);
//        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
//        optimum = move.clone();
//        double hOptimum = TheVector.getCHDistance(stab, move)[0].getVLength();
//        log.post(pro[0] + " " + pro[1]);
//        log.postnb("[");
//        for (int l = (int) pro[0].getY(); l >= (int) pro[1].getY(); l--) {
//            log.post("");
//            log.postnb("[");
//            System.out.println("l = " + (-l + pro[0].getY()));
//            System.out.println(-((int) pro[1].getY()) + l + " left");
//            for (int m = (int) pro[0].getX(); m <= (int) pro[1].getX(); m++) {
//                TheVector haus[] = TheVector.getCHDistance(stab, move);
//                if (haus[0].getVLength() < hOptimum) {
//                    optimum = move.clone();
//                    hOptimum = haus[0].getVLength();
//                }
//                move.translate(i);
//                if (m != (int) pro[0].getX()) {
//                    log.postnb(", ");
//                }
//                log.postnb(Double.toString(haus[0].getVLength()));
//                if (m == (int) pro[1].getX()) {
//                    log.postnb("],");
//                }
//            }
//            move.translate(j);
//            move.translate(norma);
//        }
//        log.postnb("]");
//        log.die();
//        move.translate(new TheVector(new Point2D[]{move.getApexs().get(0), optimum.getApexs().get(0)}));
//    }
    private static void exSearchOptimizeC(Polyangle stab, Polyangle move, Log log) throws FileNotFoundException {
        System.err.println("exSearchOptimizeC");
        Polyangle optimum;
        Point2D pro[] = Computer.calcRange(stab, move);
        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(move.getBounds().x, move.getBounds().y), pro[0]});
        move.translate(norma);
        TheVector i = new TheVector(1, 0);
        TheVector j = new TheVector(0, -1);
        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
        optimum = move.clone();
        TheVector optim = TheVector.getCHDistance(stab, move)[0];
//        double hOptimum = TheVector.getCHDistance(stab, move)[0].getVLength();
        log.post(pro[0] + " " + pro[1]);
        log.postnb("[");
        for (int l = (int) pro[0].getY(); l >= (int) pro[1].getY(); l--) {
            log.post("");
            log.postnb("[");
            System.out.println("l = " + (-l + pro[0].getY()));
            System.out.println(-((int) pro[1].getY()) + l + " left");
            for (int m = (int) pro[0].getX(); m <= (int) pro[1].getX(); m++) {
                TheVector haus[] = TheVector.getCHDistance(stab, move);
                if (haus[0].compareTo(optim) < 0) {
                    optimum = move.clone();
                    optim = haus[0];
                }
                move.translate(i);
                if (m != (int) pro[0].getX()) {
                    log.postnb(", ");
                }
                log.postnb(Double.toString(haus[0].getVLength()));
                if (m == (int) pro[1].getX()) {
                    log.postnb("],");
                }
            }
            move.translate(j);
            move.translate(norma);
        }
        log.postnb("]");
        log.die();
        move.translate(new TheVector(new Point2D[]{move.getApexs().get(0), optimum.getApexs().get(0)}));
    }

    /**
     * Алгоритм оптимизации положения двух невыпуклых многоугольников методом
     * полного перебора. Оптимальность оценивается с точки зрения расстояния
     * Хаусдорфа - оно должно быть минимальным.
     *
     * @param stab стабильный многоугольник.
     * @param move подвижный многоугольник.
     * @param log
     * @throws java.io.FileNotFoundException
     */
    private static void exSearchOptimizeUC(Polyangle stab, Polyangle move, Log log) throws FileNotFoundException {
        System.err.println("exSearchOptimizeUC");
        Polyangle optimum;
        Point2D pro[] = Computer.calcRange(stab, move);
        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(move.getBounds().x, move.getBounds().y), pro[0]});
        move.translate(norma);
         TheVector i = new TheVector(1, 0);
        TheVector j = new TheVector(0, -1);
        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
        optimum = move.clone();
        TheVector optim = TheVector.getUCHDistance(stab, move)[0];
        log.post(pro[0] + " " + pro[1]);
        log.postnb("[");
        for (int l = (int) pro[0].getY(); l >= (int) pro[1].getY(); l--) {
            log.post("");
            log.postnb("[");
            System.out.println("l = " + (-l + pro[0].getY()));
            System.out.println(-((int) pro[1].getY()) + l + " left");
            for (int m = (int) pro[0].getX(); m <= (int) pro[1].getX(); m++) {
                TheVector haus[] = TheVector.getUCHDistance(stab, move);
                if (haus[0].compareTo(optim) < 0) {
                    optimum = move.clone();
                    optim = haus[0];
                }
                move.translate(i);
                if (m != (int) pro[0].getX()) {
                    log.postnb(", ");
                }
                log.postnb(Double.toString(haus[0].getVLength()));
                if (m == (int) pro[1].getX()) {
                    log.postnb("],");
                }
            }
                        move.translate(j);
            move.translate(norma);
        }
        log.postnb("]");
        log.die();
        move.translate(new TheVector(new Point2D[]{move.getApexs().get(0), optimum.getApexs().get(0)}));
      }
//    private static void exSearchOptimizeUC(Polyangle stab, Polyangle move, Log log) throws FileNotFoundException {
//        System.err.println("exSearchOptimizeUC");
//        Polyangle optimum;
//        Point2D pro[] = Computer.calcRange(stab, move);
//        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(move.getBounds().x, move.getBounds().y), pro[0]});
//        move.translate(norma);
//        TheVector i = new TheVector(1, 0);
//        TheVector j = new TheVector(0, -1);
//        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
//        optimum = move.clone();
//        double hOptimum = TheVector.getUCHDistance(stab, move)[0].getVLength();
//        log.post(pro[0] + " " + pro[1]);
//        log.postnb("[");
//        for (int l = (int) pro[0].getY(); l >= (int) pro[1].getY(); l--) {
//            log.post("");
//            log.postnb("[");
//            System.out.println("l = " + (-l + pro[0].getY()));
//            System.out.println(-((int) pro[1].getY()) + l + " left");
//            for (int m = (int) pro[0].getX(); m <= (int) pro[1].getX(); m++) {
//                TheVector haus[] = TheVector.getUCHDistance(stab, move);
//                if (haus[0].getVLength() < hOptimum) {
//                    optimum = move.clone();
//                    hOptimum = haus[0].getVLength();
//                }
//                move.translate(i);
//                if (m != (int) pro[0].getX()) {
//                    log.postnb(", ");
//                }
//                log.postnb(Double.toString(haus[0].getVLength()));
//                if (m == (int) pro[1].getX()) {
//                    log.postnb("],");
//                }
//            }
//            move.translate(j);
//            move.translate(norma);
//        }
//        log.postnb("]");
//        log.die();
//        move.translate(new TheVector(new Point2D[]{move.getApexs().get(0), optimum.getApexs().get(0)}));
//    }

    /**
     * Проверка принадлежности точки отрезку. Отрезок задается граничными
     * точками.
     *
     * @param sect массив с граничными точками отрезка.
     * @param p проверяемая точка.
     * @return логическая величина, указывающая на принадлежность точки отрезку.
     */
    public static boolean sectContains(Point2D[] sect, Point2D p) {
        TheVector vecs[] = new TheVector[2];
        vecs[0] = new TheVector(new Point2D[]{p, sect[0]});
        vecs[1] = new TheVector(new Point2D[]{p, sect[1]});
        if (Math.abs(TheVector.pseudoScalarDerivate(vecs)) < ROUND_KOEF) {
            if (TheVector.scalarDerivate(vecs) < ROUND_KOEF) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для склейки двумерного массива в один многомерный. Реализовано
     * через <code>System.arraycopDowny</code>.
     *
     * @param arrays исходный массив.
     * @return
     */
    public static Point2D[] mixArrays(Point2D[][] arrays) {
        int length = 0;
        for (Point2D[] array : arrays) {
            length += array.length;
        }
        Point2D answer[] = new Point2D[length];
        length = 0;
        for (Point2D[] array : arrays) {
            System.arraycopy(array, 0, answer, length, array.length);
            length += array.length;
        }
        return answer;
    }

    /**
     * Метод для склейки двумерного массива в один многомерный. Реализовано
     * через <code>System.arraycopDowny</code>.
     *
     * @param arrays исходный массив.
     * @return
     */
    public static TheVector[] mixArrays(TheVector[][] arrays) {
        int length = 0;
        for (TheVector[] array : arrays) {
            length += array.length;
        }
        TheVector answer[] = new TheVector[length];
        length = 0;
        for (TheVector[] array : arrays) {
            System.arraycopy(array, 0, answer, length, array.length);
            length += array.length;
        }
        return answer;
    }

    /**
     * Метод для удаления фиктивных точек, и, как следствие векторов.
     *
     * @param hDist
     * @return
     */
    public static TheVector[] washUpHDistance(TheVector hDist[]) {
        ArrayList<TheVector> black = new ArrayList<>(hDist.length);
        black.addAll(Arrays.asList(hDist));
        Iterator<TheVector> itera = black.iterator();
        while (itera.hasNext()) {
            TheVector vec = itera.next();
            if ((vec.getPoints()[0] == null) || (vec.getPoints()[1] == null)) {
                itera.remove();
            }
        }
        TheVector answer[] = new TheVector[black.size()];
        black.toArray(answer);
        return answer;
    }

    /**
     * Метод служит для просчета диапазона точек, внутри которого необходимо
     * проводить перебор.
     *
     * @param stab статичный многоугольник.
     * @param move подвижный многоугольник.
     * @return
     */
    public static Point2D[] calcRange(Polyangle stab, Polyangle move) {
        Rectangle s = stab.getBounds();
        Rectangle m = move.getBounds();
        Point2D answer[] = new Point2D[2];
        answer[0] = new Point2D.Double(s.x - m.width, s.y + m.height);
        answer[1] = new Point2D.Double(s.x + s.width, s.y - s.height);
        return answer;
    }

    /**
     * Метод для получения средней длины векторов в массиве.
     *
     * @param vectors массив векторов.
     * @return средняя длина векторов в массиве.
     */
    public static double middle(TheVector[] vectors) {
        double l = 0;
        for (TheVector v : vectors) {
            l = l + v.getVLength();
        }
        l = l / vectors.length;
        return l;
    }

    protected static class PolarAngleCompNechet implements Comparator<Point2D> {
//TODO: ÐÐµÑÐµÐ¿Ð¸ÑÐ°ÑÑ!!!

        Point2D fulcrum;

        /**
         *
         * @param fulcrum
         */
        public PolarAngleCompNechet(Point2D fulcrum) {
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
            if (cos1 < cos2) {
                return -1;
            }
            if (cos1 > cos2) {
                return 1;
            }
            return 0;
        }
    }
}
