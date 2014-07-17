package triangles.test;

import java.io.File;
import java.io.FileNotFoundException;
import triangles.Polyangle;
import triangles.gen.PolGenerator;

/**
 * Проверка успешной работы системы после внесенных поправок.
 *
 * @author Матвей
 */
public class Verificator {

    static int i = 1;
    static private String catalogue = ("D:\\Кольцо\\Проект\\R\\Tests\\new\\TestCase1\\Pols");
    static private String pfx1 = "_PolA.txt";
    static private String pfx2 = "_PolB.txt";
    static PolGenerator A = new PolGenerator() {
        @Override
        public Polyangle gen(int qOfP, int seed, int width, int height) {
            return Polyangle.read(new File(catalogue + "\\" + i + pfx1), false);
        }
    };
    static PolGenerator B = new PolGenerator() {
        @Override
        public Polyangle gen(int qOfP, int seed, int width, int height) {

            return Polyangle.read(new File(catalogue + "\\" + i++ + pfx2), false);

        }
    };

    public static void main(String[] args) throws FileNotFoundException {
        String paths[] = {"D:\\Кольцо\\Проект\\R\\Tests\\verificator\\TestCase1\\",
            "D:\\Кольцо\\Проект\\R\\Tests\\verificator\\TestCase1\\Pols\\",
            "D:\\Кольцо\\Проект\\R\\Tests\\verificator\\TestCase1\\EvM\\"};
        int para[] = {0, 0, 0};
        Test test = new Test(23, A, B, paths, para);
        for (int l = 0; l < 30; l++) {
            test.TestN++;
            test.test();
            test.close();
        }
        test.die();
    }
}
