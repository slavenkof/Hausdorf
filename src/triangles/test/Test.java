package triangles.test;

import java.io.FileNotFoundException;
import java.util.Random;
import triangles.Computer;
import triangles.Polyangle;
import triangles.gen.PolGenerator;
import triangles.gen.UCGenerator;
import vectors.TheVector;

/**
 * Класс для проведения массового тестирования работоспособности алгоритма
 * оптимизации многоугольников.
 */
public class Test {

    private final LogLeader leader;
    private final int seed;
    private final PolGenerator a;
    private final PolGenerator b;
    private final int[] Parametres;
    private Polyangle A;
    private Polyangle B;
    private final Random ran;
    public int TestN = 0;

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Test test = new Test(892213690, new UCGenerator(), new UCGenerator(),
                new String[]{"D:/Кольцо/Проект/R/", "D:/Кольцо/Проект/R/Pols/", "D:/Кольцо/Проект/R/EvM/"},
                new int[]{6, 500, 500});
        test.initLogs();
        for (int i = 0; i < 1; i++) {
            test.TestN++;
            test.test();
            test.close();
        }
        test.die();
    }

    /**
     * Инициализация объекта-тестировщика.
     *
     * @param Seed зерно, используемое для получения случайных чисел в
     * генераторах.
     * @param A генератор случайных многоугольников, поставляющий стабильные
     * многоугольники.
     * @param B генератор многоугольников, поставляющий движимые многоугольники.
     * @param paths массив с путями для инициализации менеджера логов.
     * @param para параметры для генерации многоугольников - максимальное число
     * вершин, высота и ширина прямоугольника, внутри которого должны находится
     * все остальные многоугольники.
     * @throws FileNotFoundException
     */
    public Test(int Seed, PolGenerator A, PolGenerator B, String[] paths, int[] para) throws FileNotFoundException {
        seed = Seed;
        a = A;
        b = B;
        leader = new LogLeader(paths, new String[]{"_PolA", "_PolB", "_EvM"}, new String[]{"Main3", "Imp3"});
        Parametres = para;
        ran = new Random(seed);
    }

    /**
     * Проведение очередного теста. В рамках метода выводится индикация на
     * консоль о старте нового теста, производится генерация двух
     * многоугольников, оптимизация положения двумя алгоритмами, вывод
     * информации в логи.
     *
     * @throws FileNotFoundException
     */
    public void test() throws FileNotFoundException {
        System.out.println("Test " + TestN);
        while (A == null) {
            A = a.gen(Parametres[0], ran.nextInt(), Parametres[1], Parametres[2]);
        }
        while (B == null) {
            B = b.gen(Parametres[0], ran.nextInt(), Parametres[1], Parametres[2]);
        }
        leader.prepare(TestN);
        leader.postPols(A, B);
        leader.header();
        Polyangle AA = A.clone();
        Polyangle BB = B.clone();
        Computer.optimize(A, B);
        System.out.println("Shore");
        double Haus = Computer.middle(TheVector.getHDistance(A, B));
        A = AA;
        B = BB;
        Computer.exSearchOptimize(AA, BB, leader.getEvM());
        System.out.println("Grid");
        double Grid = Computer.middle(TheVector.getHDistance(AA, BB));
        leader.postData(Haus, Grid);
        System.out.println("--------------");
    }

    /**
     * Завершение очередного теста. Финализация теста в менеджере логов,
     * обнуление текущих многоугольников. Вызывается каждый при завершении
     * очередного теста.
     */
    public void close() {
        leader.close();
        A = null;
        B = null;
    }

    /**
     * Метод для инициализации логов. Проводит все необходимые действия по
     * подготовке лгов к работе. Вызывается единственный раз при начале работы.
     */
    public void initLogs() {
        leader.postInf(seed, Computer.ROUND_KOEF, Parametres[1], Parametres[2], Parametres[0]);
    }

    /**
     * Завершение тестирования. Проводит все необходимые действия по завершению
     * работы, вызывается единожды в момент окончания тестирования.
     */
    public void die() {
        leader.die();
    }
}
