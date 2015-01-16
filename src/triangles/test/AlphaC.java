package triangles.test;

import java.io.*;
import triangles.Polyangle;

/**
 * Скрипт для подсчета альфа-выпуклости в уже выполненных тесткейсах. В сновную
 * сборку входить не должен.
 *
 * @author Матвей
 */
public class AlphaC {

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        File cat = new File("D:/Кольцо/Проект/R/Tests/new/TestCase3/Pols/");
        File output = new File("D:/Кольцо/Проект/R/Tests/new/TestCase3/Alpha.txt");
        try (PrintStream out = new PrintStream(new FileOutputStream(output))) {
            String[] list = cat.list();
            for (String s : list) {
                Polyangle a = Polyangle.read(new File(cat + "/" + s), false);
                out.println("Alpha: " + a.getAlpha());
            }
        }
    }
}
