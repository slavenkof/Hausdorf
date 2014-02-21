package triangles;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import vectors.TheVector;

/* TODO:
 * Нужно:
 * + убрать quantOfPoints;
 * + убрать ArrayList в apexs;
 * + вынести алгоритм Грэхема в отдельный метод;
 * + переделать randGen().
 */
/**
 * Этот класс реализует представление многоугольника. Он спроектирован с
 * использованием композиции точек. В класс были включены удобные методы для
 * работы. Например, реализованы алгоритм параллельного переноса, генерации
 * случайного выпуклого многоугольника, проверки на выпуклость, разбития
 * многоугольника на отрезки и ряд других.
 *
 * @author Славенко Матвей slavenkofm@yandex.ru
 */
public class Polyangle implements Cloneable {

    private final boolean sorted;

    /**
     * В этом поле содержится множество точек, представляющих вершины
     * многоугольника. Доступ через <code>getApexs()</code>.
     */
    protected ArrayList<Point2D> apexs = new ArrayList<>();
    /**
     * Поле содержит информацию о количестве точек в многоугольнике. Доступ
     * осуществляется через метод <code>getQuantOfPoints()</code>
     */
    protected int quantOfPoints = apexs.size();

    /**
     * Конструктор, удобен когда необходимо инициализировать многоугольник с
     * вершинами с целочисленными координатами.
     *
     * @param x массив с x-координатами вершин многоугольника.
     * @param y массив с y-координатами вершин многоугольика.
     * @param needSort
     */
    public Polyangle(int x[], int y[], boolean needSort) {
        Point p[] = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            p[i] = (new Point(x[i], y[i]));
        }
        Point2D pp[];
        if (needSort) {
            pp = Computer.pointsSort(p);
        } else {
            pp = p;
        }
        for (int i = 0; i < p.length; i++) {
            apexs.add(pp[i]);
        }
        sorted = true;
        countPoints();
    }

    /**
     * Конструктор, используемый в большинстве случаев. Точки, переданные этому
     * конструктору, сортируются таким образом, что получается замкнутая ломаная
     * без самопересечений.
     *
     * @param points массив с точками, которые являются вершинами.
     * @param needSort
     */
    public Polyangle(Point2D points[], boolean needSort) {
        if (needSort) {
            sorted = true;
            apexs.addAll(Arrays.asList(Computer.pointsSort(points)));
        } else {
            sorted = false;
            apexs.addAll(Arrays.asList(points));
        }
        countPoints();
    }

    /**
     * Метод удобно использовать для визуализации - возвращает экземпляр класса
     * <code>Polygon</code>, построенный на вершинах многоугольника.
     *
     * @return экземпляр класса <code>Polygon</code>.
     */
    public Polygon getPolygon() {
        Polygon polygon = new Polygon();
        for (int i = 0; i < this.getQuantOfPoints(); i++) {
            Point2D p = apexs.get(i);
            polygon.addPoint((int) p.getX(), (int) p.getY());
        }
        return polygon;
    }

    /**
     * Метод предоставляющий доступ к полю <code>quantOfPoints</code>. Таким
     * образом можно узнать количество точек в многоугольнике.
     *
     * @return число типа <code>int</code>, равное количеству вершин в
     * многоугодьнике.
     */
    public int getQuantOfPoints() {
        this.countPoints();
        return this.quantOfPoints;
    }

    /**
     * Метод предоставляющий доступ к вершинам многоугольника.
     *
     * @return экземпляр класса <code>ArrayList<Point2D></code>, содержащий
     * вершины этого многоугольника.
     */
    public ArrayList<Point2D> getApexs() {
        return apexs;
    }

    /**
     * Метод, реализующий параллельный перенос на вектор.
     *
     * @param vector вектор, на который осуществляется перенос.
     */
    public void translate(TheVector vector) {
        Point2D newPoints[] = new Point2D[this.getQuantOfPoints()];
        for (int i = 0; i < this.getQuantOfPoints(); i++) {
            newPoints[i] = vector.getOtherPoint(apexs.get(i), true);
        }
        ArrayList<Point2D> newApexs = new ArrayList<>();
        newApexs.addAll(Arrays.asList(newPoints));
        this.apexs = newApexs;
    }

    /**
     * Метод для получения прямоугольника, полностью содержащего текущий
     * многоугольник.
     * @return объект Rectangle, описывающий границы текущего многоугольника.
     */
    public Rectangle getBounds() {
        Rectangle bounds;
        double xpoints[] = new double[getQuantOfPoints()];
        double ypoints[] = new double[getQuantOfPoints()];
        Iterator<Point2D> itera = apexs.iterator();
        for (int i = 0; i < getQuantOfPoints(); i++) {
            Point2D p = itera.next();
            xpoints[i] = p.getX();
            ypoints[i] = p.getY();
        }
        Arrays.sort(xpoints);
        Arrays.sort(ypoints);
        bounds = new Rectangle(xpoints[0], ypoints[ypoints.length - 1],
                xpoints[xpoints.length - 1] - xpoints[0], ypoints[ypoints.length - 1] - ypoints[0]);
        return bounds;
    }

    /**
     * Метод позволяющий проверить многоугольник на выпуклость.
     *
     * @return возвращает <code>true</code> если многоугольник выпуклый и
     * <code>false</code>, в противном случае.
     */
    public boolean isConvex() {
        Point2D points[] = new Point2D[this.getQuantOfPoints()];
        apexs.toArray(points);
        double l;
        TheVector one = new TheVector(new Point2D[]{points[0], points[points.length - 1]});
        TheVector two = new TheVector(new Point2D[]{points[0], points[1]});
        double der = TheVector.pseudoScalarDerivate(new TheVector[]{one, two});
        l = StrictMath.signum(der);
        for (int i = 1; i < points.length - 1; i++) {
            one = new TheVector(new Point2D[]{points[i], points[i - 1]});
            two = new TheVector(new Point2D[]{points[i], points[i + 1]});
            der = TheVector.pseudoScalarDerivate(new TheVector[]{one, two});
            double ll = StrictMath.signum(der);
            if (!(ll == l || ll == 0)) {
                return false;
            }
        }
        one = new TheVector(new Point2D[]{points[points.length - 1], points[points.length - 2]});
        two = new TheVector(new Point2D[]{points[points.length - 1], points[0]});
        der = TheVector.pseudoScalarDerivate(new TheVector[]{one, two});
        double ll = StrictMath.signum(der);
        return ll == l || ll == 0;
    }

    /**
     * Метод полезный для отладки и для реализации функции чтения/записи в файл.
     * Вывод осуществляется следующим образом: координаты открываются квадратной
     * скобкой. Затем в каждой строчке через точку с запятой идут координаты
     * вершин этого многоугольника. Перечисление заканчивается закрывающей
     * скобкой в собственной строке. Пример:
     * <pre>
     * [ 5.0 ; 0.0 ;
     * 12.0 ; -2.0 ;
     * 10.0 ; 8.0 ;
     * ]</pre>
     *
     * @return строка, отформатированная для удобства.
     */
    @Override
    public String toString() {
        String s = "[ ";
        Point2D p;
        for (int i = 0; i < this.getQuantOfPoints(); i++) {
            p = apexs.get(i);
            s = s + p.getX() + " ; ";
            s = s + p.getY() + " ; " + "\n";
        }
        s += "]";
        return s;
    }

    private void countPoints() {
        quantOfPoints = apexs.size();
    }

    /**
     * Метод для генерации случайных выпуклых многоугольников. Алгоритм прост:
     * сначала по плоскости раскидывается определенное количество точек в
     * заданном квдарате со случайными координатами, после чего строится
     * выпуклая оболочка этих точек. Именно она и является результатом работы
     * метода.
     *
     * @param qOfP
     * @param seed
     * @param width
     * @param height
     * @return случайный выпуклый многоугольник.
     */
    //TODO:Перекинуть в генератор
    public static Polyangle randGen(int qOfP, int seed, int width, int height) {
        Random rand = new Random(seed);
        int quantityOfPoints = qOfP;
        int x[] = new int[quantityOfPoints];
        int y[] = new int[quantityOfPoints];
        for (int i = 0; i < quantityOfPoints; i++) {
            x[i] = rand.nextInt(width);
            y[i] = rand.nextInt(height);
        }
        Point2D points[] = new Point[quantityOfPoints];
        for (int i = 0; i < quantityOfPoints; i++) {
            points[i] = new Point(x[i], y[i]);
        }
        points = Computer.pointsSort(points);
        TheStack<Point2D> stack = new TheStack<>();
        stack.push(points[0]);
        stack.push(points[1]);
        int actInd = 2;
        m1:
        while (true) {
            if (actInd == points.length) {
                Point2D result[] = new Point[stack.length()];
                int l = stack.length();
                for (int i = 0; i < l; i++) {
                    result[i] = stack.pop();
                }
                if (result.length < 3) {
                    return randGen(qOfP, seed, width, height);
                }
                return new Polyangle(result, true);
            }
            Point2D a0 = stack.peek();
            Point2D a = stack.deepPeek();
            Point2D t = points[actInd];
            TheVector etalon = new TheVector(new Point2D[]{a0, a});
            TheVector test = new TheVector(new Point2D[]{a0, t});
            if (needDelete(etalon, test)) {
                stack.pop();
                if (actInd == 2) {
                    stack.push(t);
                }
                continue m1;
            }
            stack.push(t);
            actInd++;
        }
    }

    @Override
    public boolean equals(Object n) {
        if (n.getClass() != this.getClass()) {
            return false;
        }
        Polyangle l = (Polyangle) n;
        return apexs.equals(l.getApexs());
    }

    /**
     * Алгоритм разбиения многоугольника на отрезки-стороны.
     *
     * @return двумерный массив экземпляров класса <code> Point2D</code>.
     */
    public Point2D[][] breakTo() {
        this.countPoints();
        Point2D answer[][] = new Point2D[this.getQuantOfPoints()][2];
        for (int i = 0; i < this.getQuantOfPoints() - 1; i++) {
            answer[i][0] = this.apexs.get(i);
            answer[i][1] = this.apexs.get(i + 1);
        }
        answer[this.getQuantOfPoints() - 1] = new Point2D[]{this.apexs.get(this.getQuantOfPoints() - 1), this.apexs.get(0)};
        return answer;
    }

    /**
     * Чрезвычайно сырой алгоритм подсчета альфа-выпуклости. Работает только
     * с многоугольниками, заданными в направлении обхода против часовой стрелки.
     * @return альфа-выпуклсть данного многоугольника.
     */
    //TODO: Разобраться с направлением обхода.
    //TODO: Реализовать более-менее общий алгоритм вычисления альфа-выпуклости.
    public double getAlpha() {
        Point2D ps[] = getUCPoints();
        double min = Math.PI;
        if (ps.length == 0) {
            return 0;
        }
        for (Point2D p : ps) {
            int i = apexs.indexOf(p);
            TheVector a = new TheVector(apexs.get(i), apexs.get(i - 1));
            TheVector b = new TheVector(apexs.get(i), apexs.get(i + 1));
            double der = TheVector.scalarDerivate(a, b);
            der /= (a.getVLength() * b.getVLength());
            der = Math.acos(der);
            if (der < min) {
                min = der;
            }
        }
        return Math.PI - min;
    }

    /**
     * Очень сырой алгоритм нахождения невыпуклых точек. Работает только с 
     * многоугольниками, заданными против часовой стрелки.
     * @return массив с невыпуклыми вершинами.
     */
    public Point2D[] getUCPoints() {//TODO: Организовать учет обхода вершин.
        //NB: Сейчас работает только с обходом вершин против часовой стрелки.
        ArrayList<Point2D> ps = new ArrayList<>();
        for (int i = 1; i < apexs.size() - 1; i++) {
            TheVector a = new TheVector(apexs.get(i), apexs.get(i - 1));
            TheVector b = new TheVector(apexs.get(i), apexs.get(i + 1));
            if (TheVector.pseudoScalarDerivate(a, b) > 0) {
                ps.add(apexs.get(i));
            }
        }
        TheVector a = new TheVector(apexs.get(0), apexs.get(apexs.size() - 1));
        TheVector b = new TheVector(apexs.get(0), apexs.get(1));
        if (TheVector.pseudoScalarDerivate(a, b) > 0) {
            ps.add(apexs.get(0));
        }
        Point2D answer[] = new Point2D[ps.size()];
        ps.toArray(answer);
        return answer;
    }

    /**
     * Метод для чтения многоугольника из файла
     * @param file файл для чтения.
     * @param needSort сортировка по полярному углу при построении многоугольника.
     * @return многоугольник, заданный в файле.
     */
    public static Polyangle read(File file, boolean needSort) {
        Pattern pattern = Pattern.compile("\\[.+?\\]", Pattern.DOTALL);
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            return null;
        }
        String black = scan.findWithinHorizon(pattern, 0);
        scan.close();
        String buffer[] = Polyangle.cleanNumbers(black);
        if (buffer.length == 0 || buffer[0].isEmpty()) {
            return null;
        }
        Point2D points[] = new Point2D.Double[buffer.length / 2];
        for (int i = 0, m = 0; i < points.length; i++) {
            points[i] = new Point2D.Double(Double.parseDouble(buffer[m++]), Double.parseDouble(buffer[m++]));
        }
        return new Polyangle(points, needSort);
    }

    /**
     * Метод для чтения всех многоугольников из файла.
     * @param file файл для чтения.
     * @param needSort сортировка при построении многоугольника.
     * @return массив многоугольников, описанных в файле.
     */
    public static Polyangle[] readAll(File file, boolean needSort) {
        Pattern pattern = Pattern.compile("\\[.+?\\]", Pattern.DOTALL);
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            return null;
        }
        TheStack<String> noir = new TheStack<>();
        String black;
        do {
            black = scan.findWithinHorizon(pattern, 0);
            noir.push(black);
        } while (black != null);
        scan.close();
        noir.pop();
        String strs[] = new String[noir.length()];
        noir.asArrayList().toArray(strs);
        Polyangle answer[] = new Polyangle[strs.length];
        for (int l = 0; l < strs.length; l++) {
            String buffer[] = Polyangle.cleanNumbers(strs[l]);
            Point2D points[] = new Point2D.Double[buffer.length / 2];
            for (int i = 0, m = 0; i < points.length; i++) {
                points[i] = new Point2D.Double(Double.parseDouble(buffer[m++]), Double.parseDouble(buffer[m++]));
            }
            answer[l] = new Polyangle(points, needSort);
        }
        return answer;
    }
    /**
     * Служебный метод, предназначен для очистки строк т шелухи и выделения чисел.
     * @param str строка для очистки.
     * @return массив чищенных строк
     */
    //TODO: разобраться, что он там чистит/возвращает.

    private static String[] cleanNumbers(String str) {
        str = str.replaceAll("\\[", "");
        str = str.replaceAll("\\]", "");
        str = str.replaceAll(" ", "");
        str = str.replaceAll("(\\r\\n)", "");
        String[] buffer = str.split(";\\n??");
        return buffer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.apexs);
        return hash;
    }

    @Override
    public Polyangle clone() {
        Point2D pp[] = new Point2D[apexs.size()];
        this.apexs.toArray(pp);
        return new Polyangle(pp, sorted);
    }
    /**
     * Служебный метод, часть алгоритма Грэхема.
     * @param fulc
     * @param test
     * @return 
     */
    //TODO: разобраться, что там происходит.
    //TODO: избавиться нафиг от этого метода.

    private static boolean needDelete(TheVector fulc, TheVector test) {
        return TheVector.pseudoScalarDerivate(new TheVector[]{fulc, test}) >= 0;
    }
}
