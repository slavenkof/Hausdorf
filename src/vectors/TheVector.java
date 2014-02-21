package vectors;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import lines.Line;
import lines.Parabola;
import triangles.Computer;
import static triangles.Computer.ROUND_KOEF;
import triangles.Polyangle;

/**
 * Класс представляет вектор в двумерном координатном пространстве. Возможны два
 * состояния вектора: конкретизирован, то есть известны его начальная и конечная
 * точки и не конкретизирован, когда известны только координаты вектора.
 *
 * @author Славенко Матвей slavenkofm@yandex.ru
 *
 */
public class TheVector implements Cloneable {

    private double vCoords[] = new double[2];
    private Point2D[] points = new Point2D.Double[2];
    private boolean concretic;

    /**
     * Создает новый вектор, начальная точка которого points[0], а конечная -
     * points[1]. Вектор конкретизирован.
     *
     * @param points массив с начальной и конечной точками вектора.
     */
    public TheVector(Point2D points[]) {
        this.points = points;
        this.vCoords = TheVector.getVCoords(points);
        concretic = true;
    }

    /**
     * Создает новый вектор с координатами {vCoords[0], vCoords[1]}. Вектор не
     * конкретизирован.
     *
     * @param vCoords массив с координатами вектора.
     */
    public TheVector(double[] vCoords) {
        this.vCoords = vCoords;
        concretic = false;
    }

    /**
     * Создает новый вектор с целочисленными координатами. Вектор не
     * конкретизирован.
     *
     * @param vCoords массив с координатами вектора.
     */
    public TheVector(int[] vCoords) {
        this.vCoords = new double[]{vCoords[0], vCoords[1]};
        concretic = false;
    }

    /**
     * Создает новый вектор с указанными началом и концом.
     *
     * @param start начало
     * @param end конец
     */
    public TheVector(Point2D start, Point2D end) {
        this(new Point2D[]{start, end});
    }

    /**
     * Создает новый вектор с дробными координатами {x, y}. Идентичен
     * конструктору TheVector(double[] vcoords).
     *
     * @param x первая координата создаваемого вектора.
     * @param y вторая координата создаваемого вектора.
     */
    public TheVector(double x, double y) {
        vCoords = new double[]{x, y};
        concretic = false;
    }

    /**
     * Метод конкретизации существующего вектора.
     *
     * @param point опорная точка.
     * @param fromStart логическая величина, указывающая, является ли опорная
     * точка началом вектора.
     */
    public void concretize(Point2D point, boolean fromStart) {
        if (fromStart) {
            this.points = new Point2D[]{point, this.getOtherPoint(point, fromStart)};
        } else {
            this.points = new Point2D[]{this.getOtherPoint(point, fromStart), point};
        }
        concretic = true;
    }

    /**
     * Конкретен ли вектор.
     *
     * @return
     */
    public boolean isConcretic() {
        return concretic;
    }

    /**
     * Метод деконкретизации существующего вектора. Очищает поле points.
     */
    public void deConcretize() {
        this.points = null;
        concretic = false;
    }

    /**
     * Нормирует вектор, то есть делит на собственную длину. Если вектор
     * конкретен, то сначала вызывается <code>deConcretize</code>, а затем
     * вектор вновь конкретизируется от своей начальной точки.
     */
    public void normalize() {
        Point2D start = null;
        boolean wasC = false;
        if (this.concretic) {
            start = points[0];
            this.deConcretize();
            wasC = true;
        }
        double k = this.getVLength();
        this.nDerivate(1 / k);
        if (wasC) {
            this.concretize(start, true);
        }
    }

    /**
     * Метод, позволяющий получить вектор, ортогональный данному. Длина
     * ортогонального вектора равна длине исходного.
     *
     * @return объект класса TheVector, ортогональный данному.
     */
    public TheVector getPerpen() {
        double a = vCoords[0];
        double b = vCoords[1];
        return new TheVector(new double[]{b, -a});
    }

    /**
     * Метод, позволяющий получить скалярное произведение двух указанных
     * векторов. Для вычисления используется координатный вариант, так что
     * погрешности при вычислении косинуса угла исключены.
     *
     * @param vectors массив векторов, произведение которых нужно найти.
     * @return величина, численно равная произведению длин двух векторов на
     * косинус угла между ними.
     */
    public static double scalarDerivate(TheVector[] vectors) {
        double scalar = vectors[0].vCoords[0] * vectors[1].vCoords[0]
                + vectors[0].vCoords[1] * vectors[1].vCoords[1];
        return scalar;
    }

    /**
     * Метод для расчета скалярного произведения векторов. Вызов переадресуется
     * в <code>TheVector.scalarDerivate(TheVector[] vectors)</code>.
     *
     * @param a первый множитель.
     * @param b второй множитель.
     * @return величина, численно равная произведению длин двух векторов на
     * косинус угла между ними.
     */
    public static double scalarDerivate(TheVector a, TheVector b) {
        return TheVector.scalarDerivate(new TheVector[]{a, b});
    }

    /**
     * Метод, позволяющий получить псевдоскалярное (также известно как косое,
     * или векторное) произведение векторов. При вычислениях используется
     * координатный вариант, таким образом исключается ошибка при вычислении
     * синуса угла между векторами.
     *
     * @param vectors массив векторов, произведение которых ищем.
     * @return величина, численно равная произвдению длин векторов на синус угла
     * между ними.
     */
    public static double pseudoScalarDerivate(TheVector vectors[]) {
        double answer = vectors[0].vCoords[0] * vectors[1].vCoords[1]
                - vectors[0].vCoords[1] * vectors[1].vCoords[0];
        return answer;
    }

    /**
     * Вычисление псевдоскалярного произведения векторов. Вызов делегируется
     * <code>TheVector.pseudoScalarDerivate(TheVector vectors[])</code>.
     *
     * @param a первый множитель.
     * @param b второй множитель.
     * @return величина, численно равная произвдению длин векторов на синус угла
     * между ними.
     */
    public static double pseudoScalarDerivate(TheVector a, TheVector b) {
        return TheVector.pseudoScalarDerivate(new TheVector[]{a, b});
    }

    /**
     * Метод, позволяющий умножить вектор на число. Если вектор конкретен, то
     * сначала вызывается <code>deConcretize</code>, а затем вектор вновь
     * конкретизируется от своей начальной точки.
     *
     * @param n число, на которое производится умножение.
     * @return ссылка на вектор, над которым была произведена операция.
     */
    public TheVector nDerivate(double n) {
        boolean wasC = false;
        Point2D start = null;
        if (concretic) {
            wasC = true;
            start = points[0];
            this.deConcretize();
        }

        vCoords[0] *= n;
        vCoords[1] *= n;
        if (wasC) {
            this.concretize(start, true);
        }
        return this;
    }

    /**
     * Метод, который позволяет находить сумму двух векторов. Если вектор
     * конкретен, то сначала вызывается <code>deConcretize</code>, а затем
     * вектор вновь конкретизируется от своей начальной точки.
     *
     * @param vect вектор, который нужно прибавить.
     * @return ссылку на вектор, над которым производилась операция.
     */
    public TheVector sumVect(TheVector vect) {
        boolean wasC = false;
        Point2D point = null;
        if (concretic) {
            wasC = true;
            point = points[0];
            this.deConcretize();
        }
        vCoords[0] += vect.vCoords[0];
        vCoords[1] += vect.vCoords[1];
        if (wasC) {
            this.concretize(point, true);
        }
        return this;
    }

    /**
     * Параллельный перенос точки на вектор. Реализовано путем прибавления
     * соотв. координат вектора.
     *
     * @param point перенсимая точка.
     * @return точка, полученная сдвигом.
     */
    public Point2D sumPoint(Point2D point) {
        double x = (vCoords[0] + point.getX());
        double y = (vCoords[1] + point.getY());
        Point2D np = new Point2D.Double(x, y);
        return np;
    }

    /**
     * Метод, позволяющий получить скалярный квадрат данного вектора.
     * Вычисляется как квадрат длины вектора.
     *
     * @return величина, численно равная скалярному квадрату этого вектора.
     */
    //TODO: Изменить метод вычисления
    public double scalarSq() {
        return Math.pow(getVLength(), 2);
    }

    /**
     * Метод, возвращающий координаты данного вектора.
     *
     * @return массив с координатами вектора.
     */
    public double[] getVCoords() {
        return this.vCoords;
    }

    /**
     * Позволяет получить координаты вектора, заданного двумя точками.
     *
     * @param points массив с начальной и конечной точками данного вектора.
     * @return массив с координатами вектора, задающегося указанными точками.
     */
    public static double[] getVCoords(Point2D[] points) {
        double vCoords[] = {(points[1].getX() - points[0].getX()),
            (points[1].getY() - points[0].getY())};
        return vCoords;
    }

    /**
     * Метод, позволяющий получить начальную и конечную точки данного вектора.
     * Если вектор не конкретизирован, возвращает массив  <code>new Point[]{null,
     * null}</code>
     *
     * @return массив с начальной и конечной точками вектора.
     */
    public Point2D[] getPoints() {
        if (concretic) {
            return points;
        } else {
            return new Point[]{null, null};
        }
    }

    /**
     * Позволяет получить длину вектора. Вычисление по теореме Пифагора.
     *
     * @return величина, численно равная длине вектора.
     */
    public double getVLength() {
        double answer = Math.pow(vCoords[0], 2) + Math.pow(vCoords[1], 2);
        answer = Math.sqrt(answer);
        return answer;
    }

    /**
     * Этот метод позволяет найти противоположную точку вектора.
     *
     * @param firstPoint опорная точка.
     * @param fromStart аргумент, определяющий, является ли опорная точка
     * наначалом или концом вектора.
     * @return в зависимости от аргумента <code>fromStart</code> возвращает:<ul>
     * <li> Если <code>fromStart == true</code>, то опорная точка считается
     * начальной, метод возвращает конечную точку.</li> <li>
     * Если <code>fromStart == false</code>, то опорная точка считается
     * конечной, метод возвращает координаты начальной точки вектора.</ul>
     */
    public Point2D getOtherPoint(Point2D firstPoint, boolean fromStart) {
        double vCoord[] = this.getVCoords();
        if (fromStart) {
            double firstCoord = (firstPoint.getX() + vCoord[0]);
            double secondCoord = (firstPoint.getY() + vCoord[1]);
            return new Point2D.Double(firstCoord, secondCoord);
        } else {
            double firstCoord = (firstPoint.getX() - vCoord[0]);
            double secondCoord = (firstPoint.getY() - vCoord[1]);
            return new Point2D.Double(firstCoord, secondCoord);
        }
    }

    /**
     * Данный метод откладывает все вектора от одной точки, а затем проверяет,
     * принадлежит ли данная точка выпуклой оболочке этих векторов.
     *
     * @param vectorss массив проверяемых векторов.
     * @return логическая величина: <code>true</code>, если принадлежит * * и
     * <code>false</code>, если не принадлежит.
     */
    //TODO: Разобраться WTF?
    public static boolean insideOf(TheVector vectorss[]) {
        HashSet<TheVector> vecs = new HashSet<>();
        vecs.addAll(Arrays.asList(vectorss));
        TheVector vectors[] = new TheVector[vecs.size()];
        vecs.toArray(vectors);
        TheVector pr = vectors[0];
        for (int i = 1; i < vectors.length; i++) {
            if (Math.abs(TheVector.scalarDerivate(new TheVector[]{pr, vectors[i]})
                    + pr.getVLength() * vectors[i].getVLength()) < ROUND_KOEF) {
                return true;
            }
        }
        Point2D endPoints[] = new Point2D[vectors.length];
        for (int i = 0; i < endPoints.length; i++) {
            endPoints[i] = vectors[i].getOtherPoint(new Point(0, 0), true);
        }
        endPoints = Computer.pointsSortAngle(endPoints);
        boolean inside = Computer.contains(endPoints, new Point(0, 0));
        return inside;
    }

    /**
     * Метод "переворачивает" вектор - умножает его на -1. Точки при перевороте
     * вектора, если он был конкретизирован, также меняются местами.
     *
     * @return вектор, равный исходный умножить на -1.
     */
    public TheVector swap() {
        if (concretic) {
            this.points = new Point2D[]{points[1], points[0]};
        }
        vCoords[0] *= -1;
        vCoords[1] *= -1;
        return this;
    }

    /**
     * Функция-обертка для получения расстояния Хаусдорфа.
     *
     * @param stab -стабильный многоугольник.
     * @param move - подвижный многоугольник.
     * @return массив векторов, на которых достигается расстояние Хаусдорфа.
     */
    //TODO: Разобраться WTF?
    public static TheVector[] getHDistance(Polyangle stab, Polyangle move) {
        if (!(stab.isConvex()) || !(move.isConvex())) {
            return TheVector.getUCHDistance(stab, move);
        }
        return TheVector.getCHDistance(stab, move);
    }

    /**
     * Тестовый технический метод, используется для рисования окружностей.
     *
     * @param stab
     * @param move
     * @return
     */
    //TODO: Разобраться со свапами, иначе это ужас какой-то...
    public static TheVector[] getUSHDistance(Polyangle stab, Polyangle move) {
        if (!(stab.isConvex()) || !(move.isConvex())) {
            return TheVector.getUSUCHDistance(stab, move);
        }
        return TheVector.getUSCHDistance(stab, move);
    }

    /**
     * Возвращает вектор, на котором достигается расстояние между точкой и
     * отрезком. Возможны два случая: <ul><li> Если перпендикуляр из точки
     * падает на отрезок, то расстояние достигается на перпендикуляре.</li> <li>
     * Если перпендикуляр не падает на отрезок, то расстояние достигается на
     * одной из вершин.</li></ul>
     * <p>
     * В любом случае, начальной точкой вектора всегда является опорная точка.
     * </p>
     * Ответ через решение параметрического уравнения.
     *
     * @param sect отрезок, заданный массивом со своими граничными точками.
     * @param p опорная точка.
     * @return вектор, на котором достигается расстояние от точки до отрезка.
     */
    public static TheVector getDistanceVector(Point2D[] sect, Point2D p) {
        TheVector v[] = new TheVector[3];
        v[0] = new TheVector(sect);
        v[1] = new TheVector(new Point2D[]{sect[0], p});
        v[2] = new TheVector(new Point2D[]{sect[1], p});
        boolean falls[] = new boolean[2];
        falls[0] = TheVector.scalarDerivate(new TheVector[]{v[0], v[1]}) > 0;
        v[0] = v[0].swap();
        falls[1] = TheVector.scalarDerivate(new TheVector[]{v[0], v[2]}) > 0;
        v[0] = v[0].swap();
        if (falls[0] == false) {
            return new TheVector(new Point2D[]{p, sect[0]});
        }
        if (falls[1] == false) {
            return new TheVector(new Point2D[]{p, sect[1]});
        }
        double t;
        v[1] = v[1].swap();
        t = -((TheVector.scalarDerivate(new TheVector[]{v[1], v[0]}) / v[0].scalarSq()));
        TheVector tAB = v[0].nDerivate(t);
        Point2D ans = tAB.sumPoint(sect[0]);
        TheVector answer = new TheVector(new Point2D[]{p, ans});
        return answer;
    }

    /**
     * Позволяет получить массив векторов, на которых достигается расстояние от
     * опорной точки до сторон многоугольника. Циклический вызов
     * <code>getDistanceVector</code>, проверка на внутренность.
     *
     * @param plan мнгоугольник, до которго нужно найти расстояния.
     * @param p опорная точка.
     * @return массив векторов, на которых достигаются расстояния до сторон
     * многоугольника.
     */
    public static TheVector[] getDistanceVector(Polyangle plan, Point2D p) {
        Object pointss[] = plan.getApexs().toArray();
        Point2D points[] = new Point2D[pointss.length];
        for (int i = 0; i < pointss.length; i++) {
            points[i] = (Point2D) pointss[i];
        }
        TheVector vecs[] = new TheVector[points.length];
        if (Computer.contains(points, p)) {
            for (int i = 0; i < vecs.length; i++) {
                vecs[i] = new TheVector(0, 0);
            }
            return vecs;
        }
        for (int i = 0; i < (vecs.length - 1); i++) {
            vecs[i] = TheVector.getDistanceVector(new Point2D[]{points[i], points[i + 1]}, p);
        }
        vecs[vecs.length - 1] = TheVector.getDistanceVector(new Point2D[]{points[points.length - 1], points[0]}, p);
        return vecs;
    }

    /**
     * Позволяет получить массив векторов, на которых достигается расстояние от
     * опорной точки до сторон многоугольника. Циклический вызов
     * <code>getDistanceVector</code>, проверка на внутренность.
     *
     * @param plan мнгоугольник, до которго нужно найти расстояния.
     * @param p опорная точка.
     * @return массив векторов, на которых достигаются расстояния до сторон
     * многоугольника.
     *
     *
     */
    //TODO:Объединить с аналогичым выпуклым.
    public static TheVector[] getUCDistanceVector(Polyangle plan, Point2D p) {
        Object pointss[] = plan.getApexs().toArray();
        Point2D points[] = new Point2D[pointss.length];
        for (int i = 0; i < pointss.length; i++) {
            points[i] = (Point2D) pointss[i];
        }
        TheVector vecs[] = new TheVector[points.length];
        if (Computer.UCcontains(points, p)) {
            for (int i = 0; i < vecs.length; i++) {
                vecs[i] = new TheVector(0, 0);
            }
            return vecs;
        }
        for (int i = 0; i < (vecs.length - 1); i++) {
            vecs[i] = TheVector.getDistanceVector(new Point2D[]{points[i], points[i + 1]}, p);
        }
        vecs[vecs.length - 1] = TheVector.getDistanceVector(new Point2D[]{points[points.length - 1], points[0]}, p);
        return vecs;
    }

    /**
     * Этот метод позволяет получить двумерный массив векторов, на которых
     * достигаются расстояния от каждой из вершин многоугольника
     * <code>move</code> до каждой из сторон <code>stab</code>. Многоугольники
     * выпуклые.
     *
     * @param stab многоугольник до которого ищутся расстояния.
     * @param move многоугольник от которого ищутся расстояния.
     * @return двумерный массив векторов, в котором первый индекс указывает на
     * номер точки из <code>move</code>, а второй индекс на номер стороны из
     * <code>stab</code>.
     */
    public static TheVector[][] getCDistanceVector(Polyangle stab, Polyangle move) {
        Object pointss[] = move.getApexs().toArray();
        Point2D movePoints[] = new Point2D[pointss.length];
        for (int i = 0; i < pointss.length; i++) {
            movePoints[i] = (Point2D) pointss[i];
        }
        TheVector answer[][] = new TheVector[move.getQuantOfPoints()][stab.getQuantOfPoints()];
        for (int i = 0; i < move.getQuantOfPoints(); i++) {
            answer[i] = TheVector.getDistanceVector(stab, movePoints[i]);
        }
        return answer;
    }

    /**
     * Возвращает массив векторов, на которых достигается Хаусдорфово расстояние
     * между <b>выпуклыми</b> многоугольниками.
     *
     * @param stab первый многоугольник.
     * @param move второй многоугольник.
     * @return массив векторов, на которых достигается расстояние Хаусдорфа.
     */
    //TODO: Разобраться с переворачиваниями.
    public static TheVector[] getCHDistance(Polyangle stab, Polyangle move) {
        TheVector moveStab[][] = TheVector.getCDistanceVector(move, stab);
        TheVector stabMove[][] = TheVector.getCDistanceVector(stab, move);
        for (int i = 0; i < moveStab.length; i++) {
            moveStab[i] = TheVector.getMin(moveStab[i]);
        }
        for (int i = 0; i < stabMove.length; i++) {
            stabMove[i] = TheVector.getMin(stabMove[i]);
        }
        for (int i = 0; i < stabMove.length; i++) {
            for (int m = 0; m < stabMove[i].length; m++) {
                stabMove[i][m].swap();
            }
        }
        TheVector blackMS[] = Computer.mixArrays(moveStab);
        TheVector blackSM[] = Computer.mixArrays(stabMove);
        TheVector bAnswer[] = Computer.mixArrays(new TheVector[][]{blackMS, blackSM});
        return TheVector.getMax(bAnswer);
    }

    /**
     * Возвращает массив векторов, на которых достигается Хаусдорфово расстояние
     * между <b>невыпуклыми</b> многоугольниками.
     *
     * @param stab первый многоугольник.
     * @param move второй многоугольник.
     * @return массив векторов, на которых достигается расстояние Хаусдорфа.
     */
    //TODO: Разобраться с переворачиваниями.
    public static TheVector[] getUSCHDistance(Polyangle stab, Polyangle move) {
        TheVector moveStab[][] = TheVector.getCDistanceVector(move, stab);
        TheVector stabMove[][] = TheVector.getCDistanceVector(stab, move);
        for (int i = 0; i < moveStab.length; i++) {
            moveStab[i] = TheVector.getMin(moveStab[i]);
        }
        for (int i = 0; i < stabMove.length; i++) {
            stabMove[i] = TheVector.getMin(stabMove[i]);
        }
        int l = 0;
        TheVector blackMS[] = Computer.mixArrays(moveStab);
        TheVector blackSM[] = Computer.mixArrays(stabMove);
        TheVector bAnswer[] = Computer.mixArrays(new TheVector[][]{blackMS, blackSM});
        return TheVector.getMax(bAnswer);
    }

    /**
     * Метод позволяет выбрать минимальные по длине вектора из массива,
     * переданного методу.
     *
     * @param source исходный массив векторов.
     * @return массив векторов с наимньшей длиной. Сравнение производится с
     * точностью, определяемой константой
     * <code>triangles.Computer.ROUND_KOEF</code>.
     */
    //TODO: Проанализировать, оптимизировать.
    public static TheVector[] getMin(TheVector[] source) {
        TheVector src[] = source.clone();
        TheVectorDistCompare comp = new TheVectorDistCompare();
        Arrays.sort(src, comp);
        double minDist = src[0].getVLength();
        ArrayList<TheVector> answers = new ArrayList<>();
        answers.add(src[0]);
        for (int i = 1; i < src.length; i++) {
            if (Math.abs(src[i].getVLength() - minDist) < ROUND_KOEF) {
                answers.add(src[i]);
            }
        }
        TheVector a[] = new TheVector[answers.size()];
        answers.toArray(a);
        return a;
    }

    /**
     * Метод позволяет получить массив векторов с максимальной длиной из
     * массива, переданного методу.
     *
     * @param source массив исходных векторов.
     * @return массив векторов с максимальной длиной. Точность сравнения
     * определяет константа <code>triangles.Computer.ROUND_KOEF</code>.
     */
    //TODO: Пранализировать, оптимизировать
    public static TheVector[] getMax(TheVector[] source) {
        TheVector src[] = source.clone();
        TheVector m = new TheVector(0, 0);
        TheVectorDistCompare comp = new TheVectorDistCompare();
        for (int i = 0; i < src.length; i++) {
            if (comp.compare(m, src[i]) < 0) {
                m = src[i].clone();
            }
        }
        double maxDist = m.getVLength();
        ArrayList<TheVector> answers = new ArrayList<>();
        for (int i = 0; i < src.length; i++) {
            if (Math.abs(src[i].getVLength() - maxDist) < ROUND_KOEF) {
                answers.add(src[i]);
            }
        }
        TheVector a[] = new TheVector[answers.size()];
        answers.toArray(a);
        return a;
    }

    /**
     * Получение точек, находящихся на пересечении биссектрис.
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого ищем расстояния.
     * @return массив точек, пересечения с биссектрисами.
     */
    //TODO: Проанализировать, оптимизировать.
    private static Point2D[] getBisPoints(Polyangle stab, Polyangle move) {
        Point2D sects[][] = stab.breakTo();
        Point2D pairs[][][] = new Point2D[(sects.length * (sects.length - 1)) / 2][2][2];
        int m = 0;
        for (int i = 0; i < sects.length; i++) {
            for (int l = i + 1; l < sects.length; l++) {
                pairs[m] = new Point2D[][]{sects[i], sects[l]};
                m++;
            }
        }
        ArrayList<Point2D> black = new ArrayList<>(pairs.length);
        for (int i = 0; i < pairs.length; i++) {
            black.addAll(Arrays.asList(Line.getPolIntersection(move, Line.getBis(pairs[i][0], pairs[i][1]))));
        }
        Iterator<Point2D> itera = black.iterator();
        while (itera.hasNext()) {
            Point2D p = itera.next();
            if (p == null) {
                itera.remove();
            }
        }
        Point2D points[] = new Point2D[black.size()];
        black.toArray(points);
        return points;
    }
//stab - до которого ищем расстояния

    /**
     * Получение расстояний, порожденных точками, которые были порождены
     * биссектрисами <code>move</code>
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого возникают биссектрисы.
     * @return массив векторов, на которых достигаются расстояния.
     */
    public static TheVector[] getBisDistance(Polyangle stab, Polyangle move) {
        Point2D points[] = TheVector.getBisPoints(stab, move);
        TheVector rawDistances[][] = new TheVector[points.length][stab.getQuantOfPoints()];
        for (int i = 0; i < points.length; i++) {
            rawDistances[i] = TheVector.getDistanceVector(stab, points[i]);
        }
        TheVector washedDistances[][] = new TheVector[points.length][];
        for (int i = 0; i < points.length; i++) {
            washedDistances[i] = TheVector.getMin(rawDistances[i]);
        }
        TheVector answer[] = Computer.mixArrays(washedDistances);
        return answer;
    }

    /**
     * Получение точек, находящихся на пересечении медиатрис, возникающих из
     * <code>move</code>.
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого ищем расстояния.
     * @return массив точек, пересечения с медиатрисами.
     */
    private static Point2D[] getMPerpenPoints(Polyangle stab, Polyangle move) {
        Point2D points[] = new Point2D[stab.getQuantOfPoints()];
        stab.getApexs().toArray(points);
        Point2D pairs[][] = new Point2D[(points.length * (points.length - 1)) / 2][2];
        int m = 0;
        for (int i = 0; i < points.length; i++) {
            for (int l = i + 1; l < points.length; l++) {
                pairs[m] = new Point2D[]{points[i], points[l]};
                m++;
            }
        }
        ArrayList<Point2D> black = new ArrayList<>(pairs.length);
        for (int i = 0; i < pairs.length; i++) {
            black.addAll(Arrays.asList(Line.getPolIntersection(move, Line.getMPerpen(pairs[i]))));
        }
        Iterator<Point2D> itera = black.iterator();
        while (itera.hasNext()) {
            Point2D p = itera.next();
            if (p == null) {
                itera.remove();
            }
        }
        Point2D answer[] = new Point2D[black.size()];
        black.toArray(answer);
        return answer;
    }
//stab - до которого ищем вектора

    /**
     * Получение расстояний, порожденных точками, которые были порождены
     * медиатрисами <code>move</code>
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого возникают биссектрисы.
     * @return массив векторов, на которых достигаются расстояния.
     */
    public static TheVector[] getMPerpenDistance(Polyangle stab, Polyangle move) {
        Point2D points[] = TheVector.getMPerpenPoints(stab, move);
        TheVector rawDistances[][] = new TheVector[points.length][stab.getQuantOfPoints()];
        for (int i = 0; i < points.length; i++) {
            rawDistances[i] = TheVector.getDistanceVector(stab, points[i]);
        }
        TheVector washedDistances[][] = new TheVector[points.length][];
        for (int i = 0; i < points.length; i++) {
            washedDistances[i] = TheVector.getMin(rawDistances[i]);
        }
        TheVector answer[] = Computer.mixArrays(washedDistances);
        return answer;
    }

    /**
     * Получение точек, находящихся на пересечении парабол, возникающих из
     * <code>move</code>.
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого ищем расстояния.
     * @return массив точек, пересечения с парабол.
     */
    private static Point2D[] getParaPoints(Polyangle stab, Polyangle move) {
        Point2D points[] = new Point2D[stab.getQuantOfPoints()];
        stab.getApexs().toArray(points);
        Point2D sects[][] = stab.breakTo();
        ArrayList<Point2D> black = new ArrayList<>(sects.length * sects.length);
        for (int i = 0; i < points.length; i++) {
            for (int l = 0; l < sects.length; l++) {
                black.addAll(Arrays.asList(Parabola.getPolIntersection(move, sects[l], points[i])));
            }
        }
        Iterator<Point2D> itera = black.iterator();
        while (itera.hasNext()) {
            Point2D p = itera.next();
            if (p == null) {
                itera.remove();
            }
        }
        Point2D answer[] = new Point2D[black.size()];
        black.toArray(answer);
        return answer;
    }

    /**
     * Получение расстояний, порожденных точками, которые были порождены
     * параболами <code>move</code>
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого возникают биссектрисы.
     * @return массив векторов, на которых достигаются расстояния.
     */
    public static TheVector[] getParaDistance(Polyangle stab, Polyangle move) {
        Point2D points[] = TheVector.getParaPoints(stab, move);
        TheVector rawDistances[][] = new TheVector[points.length][stab.getQuantOfPoints()];
        for (int i = 0; i < points.length; i++) {
            rawDistances[i] = TheVector.getDistanceVector(stab, points[i]);
        }
        TheVector washedDistances[][] = new TheVector[points.length][];
        for (int i = 0; i < points.length; i++) {
            washedDistances[i] = TheVector.getMin(rawDistances[i]);
        }
        TheVector answer[] = Computer.mixArrays(washedDistances);
        return answer;
    }
    //stab - до которого ищем расстояния

    /**
     * Этот метод позволяет получить двумерный массив векторов, на которых
     * достигаются расстояния от каждой из вершин многоугольника
     * <code>move</code> до каждой из сторон <code>stab</code>. Многоугольники
     * невпыуклые.
     *
     * @param stab многоугольник до которого ищутся расстояния.
     * @param move многоугольник от которого ищутся расстояния.
     * @return двумерный массив векторов, в котором первый индекс указывает на
     * номер точки из <code>move</code>, а второй индекс на номер стороны из
     * <code>stab</code>.
     */
    public static TheVector[] getUCApDistanceVector(Polyangle stab, Polyangle move) {
        Object pointss[] = move.getApexs().toArray();
        Point2D movePoints[] = new Point2D[pointss.length];
        for (int i = 0; i < pointss.length; i++) {
            movePoints[i] = (Point2D) pointss[i];
        }
        TheVector black[][] = new TheVector[move.getQuantOfPoints()][stab.getQuantOfPoints()];
        for (int i = 0; i < move.getQuantOfPoints(); i++) {
            black[i] = TheVector.getUCDistanceVector(stab, movePoints[i]);
        }
        for (int i = 0; i < black.length; i++) {
            black[i] = TheVector.getMin(black[i]);
        }
        TheVector answer[] = Computer.mixArrays(black);
        return answer;
    }

    /**
     * Получение Хаусдорфова отклонения многоугольника stab от move.
     *
     * @param stab до которого ищем расстояния.
     * @param move от которого ищем расстояния.
     * @return массив векторов, на которых достигается отклонение Хаусдорфа.
     */
    public static TheVector[] getUCDistanceVector(Polyangle stab, Polyangle move) {
        TheVector rawDis[][] = new TheVector[4][];
        rawDis[0] = TheVector.getUCApDistanceVector(stab, move);
        rawDis[1] = TheVector.getMPerpenDistance(stab, move);
        rawDis[2] = TheVector.getBisDistance(stab, move);
        rawDis[3] = TheVector.getParaDistance(stab, move);
        TheVector black[] = Computer.mixArrays(rawDis);
        return TheVector.getMax(black);
    }

    /**
     * Метод получения расстояния Хаусдорфа между <b>невыпуклыми</b>
     * многоугольниками.
     *
     * @param stab -первый многоугольник.
     * @param move - второй многоугольник.
     * @return массив векторов, на которых достигается расстояние Хаусдорфа.
     */
    //TODO: разобратьс с свапами.
    public static TheVector[] getUCHDistance(Polyangle stab, Polyangle move) {
        TheVector MSDis[] = TheVector.getUCDistanceVector(stab, move);
        TheVector SMDis[] = TheVector.getUCDistanceVector(move, stab);
        for (int i = 0; i < SMDis.length; i++) {
            SMDis[i] = SMDis[i].swap();
        }
        TheVector black[] = Computer.mixArrays(new TheVector[][]{MSDis, SMDis});
        return Computer.washUpHDistance(TheVector.getMax(black));
    }

    /**
     * Тестовый технический метод, используется для рисования окружностей.
     *
     * @param stab
     * @param move
     * @return
     */
    //TODO: Разобраться со свапами, иначе это ужас какой-то...
    public static TheVector[] getUSUCHDistance(Polyangle stab, Polyangle move) {
        TheVector MSDis[] = TheVector.getUCDistanceVector(stab, move);
        TheVector SMDis[] = TheVector.getUCDistanceVector(move, stab);
        TheVector black[] = Computer.mixArrays(new TheVector[][]{MSDis, SMDis});
        return Computer.washUpHDistance(TheVector.getMax(black));
    }

    /**
     * Метод возвращает координаты вектора в текстовом формате. Координаты
     * заключены в фигурные скобки и разделены точкой с запятой.
     *
     * @return координаты вектора в строковом представлении.
     */
    @Override
    public String toString() {
        String s = "{ " + vCoords[0] + "; " + vCoords[1] + " }";
        return s;
    }

    /**
     * Возвращает точную копию вектора, включая состояние конкретизированности и
     * граничные точки.
     *
     * @return вектор, идентичный исходному.
     */
    @Override
    public TheVector clone() {
        if (this.concretic) {
            return new TheVector(points);
        }
        return new TheVector(vCoords);
    }

    /**
     * Сравнение векторов с точностью до константы
     * <code>Computer.ROUND_KOEF</code>.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TheVector)) {
            return false;
        }
        TheVector vect = (TheVector) obj;
        if ((Math.abs(vCoords[0] - vect.vCoords[0]) < ROUND_KOEF) && (Math.abs(vCoords[1] - vect.vCoords[1]) < ROUND_KOEF)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 33;
        hash = 79 * hash + Arrays.hashCode(this.vCoords);
        hash = 79 * hash;
        return hash;
    }
}
