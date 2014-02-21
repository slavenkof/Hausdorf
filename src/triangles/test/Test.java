package triangles.test;

import java.io.FileNotFoundException;
import java.util.Random;
import triangles.Computer;
import triangles.Polyangle;
import triangles.gen.*;
import vectors.TheVector;

public class Test {

    LogLeader leader;
    int seed;
    PolGenerator a;
    PolGenerator b;
    int[] par;
    Polyangle A;
    Polyangle B;
    Random ran;
    int i = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Test t = new Test(123456789, new UCGenerator(), new UCGenerator(),
                new String[]{"D:/Кольцо/Проект/R/rubbish/", "D:/Кольцо/Проект/R/rubbish/", "D:/Кольцо/Проект/R/rubbish/"},
                new int[]{4, 500, 500});
        t.prepare();
        for (int i = 0; i < 2; i++) {
            t.i++;
            t.test();
            t.close();
        }
        t.die();
    }

    public Test(int Seed, PolGenerator A, PolGenerator B, String[] paths, int[] para) throws FileNotFoundException {
        seed = Seed;
        a = A;
        b = B;
        leader = new LogLeader(paths, new String[]{"_PolA", "_PolB", "_EvM"}, new String[]{"Main3", "Imp3"});
        par = para;
        ran = new Random(seed);
    }

    public void test() throws FileNotFoundException {
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
        Computer.exSearchOptimize(AA, BB, leader.getEvM());
        System.out.println("Grid");
        double Grid = Computer.middle(TheVector.getHDistance(AA, BB));
        leader.postData(Haus, Grid);
        System.out.println("--------------");
    }

    public void close() {
        leader.close();
        A = null;
        B = null;
    }

    public void prepare() {
        leader.postInf(seed, Computer.ROUND_KOEF, par[1], par[2], par[0]);
    }

    public void die() {
        leader.die();
    }
}
