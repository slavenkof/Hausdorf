package triangles.test;

import java.io.*;
import triangles.Polyangle;

public class AlphaC {

    public static void main(String[] args) throws FileNotFoundException {
        File cat = new File("D:/Кольцо/Проект/R/Tests/new/TestCase3/Pols/");
        File output = new File("D:/Кольцо/Проект/R/Tests/new/TestCase3/Alpha.txt");
        try (PrintStream out = new PrintStream(new FileOutputStream(output))) {
            String[] list = cat.list();
            for (String s : list) {
                Polyangle a = Polyangle.read(new File(cat+"/"+s), false);
                out.println("Alpha: " + a.getAlpha());
            }
        }
    }
}
