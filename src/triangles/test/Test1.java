package triangles.test;

import java.io.FileNotFoundException;
import java.util.Random;
import triangles.Computer;
import triangles.Polyangle;
import triangles.gen.*;
import vectors.TheVector;

/**
 * Клон класса Test, применяется для профилирования.
 * @author Матвей
 */
public class Test1 {

    private final LogLeader leader;
    private final int seed;
    private final PolGenerator a;
    private final PolGenerator b;
    private final int[] par;
    private Polyangle A;
    private Polyangle B;
    private final Random ran;
    private int i = 0;

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Test1 t = new Test1(123456789, new UCGenerator(), new UCGenerator(),
                new String[]{"D:/Кольцо/Проект/R/rubbish/", "D:/Кольцо/Проект/R/rubbish/", "D:/Кольцо/Проект/R/rubbish/"},
                new int[]{4, 500, 500});
        t.prepare();
        for (int i = 0; i < 10; i++) {
            t.i++;
            t.test();
            t.close();
        }
        t.die();
    }

    /**
     *
     * @param Seed
     * @param A
     * @param B
     * @param paths
     * @param para
     * @throws FileNotFoundException
     */
    public Test1(int Seed, PolGenerator A, PolGenerator B, String[] paths, int[] para) throws FileNotFoundException {
        seed = Seed;
        a = A;
        b = B;
        leader = new LogLeader(paths, new String[]{"_PolA", "_PolB", "_EvM"}, new String[]{"Main3", "Imp3"});
        par = para;
        ran = new Random(seed);
    }

    private void test() throws FileNotFoundException {
        System.out.println("Test " + i);
        while (A == null) {
            A = a.gen(par[0], ran.nextInt(), par[1], par[2]);
        }
        while (B == null) {
            B = b.gen(par[0], ran.nextInt(), par[1], par[2]);
        }
        leader.prepare(i);
        leader.postPols(A, B);
        leader.header();
        Polyangle AA = A.clone();
        Polyangle BB = B.clone();
        Computer.optimize(A, B);
        System.out.println("Shore");
        double Haus = Computer.middle(TheVector.getHDistance(A, B));
        A = AA;
        B = BB;
//        Computer.exSearchOptimize(AA, BB, leader.EvM);
        System.out.println("Grid");
        double Grid = 0;
        leader.postData(Haus, Grid);
        System.out.println("--------------");
    }

    private void close() {
        leader.close();
        A = null;
        B = null;
    }

    private void prepare() {
        leader.postInf(seed, Computer.ROUND_KOEF, par[1], par[2], par[0]);
    }

    private void die() {
        leader.die();
    }
}
