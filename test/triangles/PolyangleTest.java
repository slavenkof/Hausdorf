package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.io.*;

public class PolyangleTest {

    public PolyangleTest() {
    }

    @Test
    public void testReadWithSort() {
        System.out.println("readWithSort(4 tests)");
        File file[] = {
            new File("C:/Users/Матвей/Documents/test/Polyangle/test1.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test2.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test3.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test4.txt")};
        Polyangle etData[] = {
            new Polyangle(new Point2D[]{new Point(63, 273), new Point2D.Double(68, 86), new Point2D.Double(226, 316)}, true),
            new Polyangle(new Point2D[]{new Point(600, 50), new Point(650, 700), new Point(1000, 200)}, true),
            null,
            Polyangle.read(file[1], true)};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Polyangle.read(file[i], true));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testReadAndReadAllWithSort() {
        System.out.println("readAndReadAllWithSort(4 tests)");
        File file[] = {
            new File("C:/Users/Матвей/Documents/test/Polyangle/test1.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test2.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test3.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test4.txt"),
            new File("C:/Users/Матвей/Documents/test/Polyangle/test5.txt")};
        Point2D a[] = {new Point(600, 50), new Point(650, 700), new Point(1000, 200)};
        Point2D b[] = {new Point(300, 700), new Point2D.Double(213.3974597, 550), new Point2D.Double(386.6025403, 550)};
        Point2D c[] = {new Point2D.Double(159, 124), new Point2D.Double(360, 159), new Point2D.Double(373, 194), new Point(257, 282)};
        Point2D d[] = {new Point(700, 250), new Point(550, 500), new Point(750, 650), new Point(950, 450), new Point(900, 200)};
        Point2D e[] = {new Point(100, 100), new Point(100, 500), new Point(250, 500), new Point(500, 200), new Point(500, 100)};
        Point2D f[] = {new Point(82, 91), new Point2D.Double(295, 126), new Point2D.Double(326, 319)};
        Point2D g[] = {new Point2D.Double(173.2050807, 200), new Point2D.Double(86.6025403, 50), new Point2D.Double(0, 200)};
        Point2D h[] = {new Point2D.Double(500, 500), new Point2D.Double(500, 700), new Point2D.Double(900, 700), new Point(900, 500)};
        Point2D i[] = {new Point(63, 273), new Point2D.Double(68, 86), new Point2D.Double(226, 316)};
        Point2D j[] = {new Point(100, 100), new Point(150, 500), new Point(600, 550), new Point(450, 50)};
        Polyangle etData[] = {
            new Polyangle(a, true), new Polyangle(b, true),
            new Polyangle(c, true), new Polyangle(d, true),
            new Polyangle(e, true), new Polyangle(f, true),
            new Polyangle(g, true), new Polyangle(h, true),
            new Polyangle(i, true), new Polyangle(j, true)};
        assertArrayEquals(Polyangle.readAll(file[4], true), Polyangle.readAll(file[0], true));
        assertArrayEquals(new Polyangle[]{new Polyangle(new Point2D[]{new Point(600, 50), new Point(650, 700), new Point(1000, 200)}, true)},
                Polyangle.readAll(file[1], true));
        assertArrayEquals(new Polyangle[]{Polyangle.read(file[1], true)},
                Polyangle.readAll(file[1], true));
        assertArrayEquals(etData, Polyangle.readAll(file[3], true));
        System.out.println("-------------------");
    }

    @Test
    public void testIsConvex() {
        System.out.println("isConvex (8 tests");
        Polyangle pols[] = Polyangle.readAll(new File("C:/Users/Матвей/Documents/test/Polyangle/test5.txt"), false);
        boolean etData[] = {true, true, true, true, true, true};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], pols[i].isConvex());
        }
        Polyangle pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test2.txt"), true);
        assertEquals(false, pol.isConvex());
        pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Polyangle/test8.txt"), false);
        assertEquals(false, pol.isConvex());
        System.out.println("-------------------");
    }

    @Test
    public void testIsConvex2() {
        System.out.println("isConvex2 (50 tests)");
        int n = 2;
        Polyangle pol[] = new Polyangle[n];
        for (int i = 0; i < n; i++) {
            pol[i] = Polyangle.randGen(5, i, 100, 100);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(pol[i]);
            assertTrue(Integer.toString(i), pol[i].isConvex());
        }
        System.out.println("-------------------");
    }

    @Test
    public void testReadWithoutSort() throws FileNotFoundException {
        System.out.println("readWithoutSort (5 tests)");
        Polyangle pols[] = new Polyangle[]{
            new Polyangle(new int[]{
                28,
                49,
                60,
                180,
                108,
                246,
                386,
                378,
                358,
                453
            }, new int[]{
                448,
                251,
                153,
                72,
                307,
                70,
                89,
                149,
                339,
                431
            }, false),
            new Polyangle(new int[]{
                100,
                150,
                50,
                150,
                250,
                350,
                350,
                350,
                250,
                200
            }, new int[]{
                50,
                150,
                200,
                250,
                200,
                300,
                300,
                100,
                150,
                50
            }, false),
            new Polyangle(new int[]{
                250,
                200,
                100,
                200,
                250,
                300,
                400,
                300
            }, new int[]{
                200,
                300,
                350,
                400,
                500,
                400,
                350,
                300
            }, false),
            new Polyangle(new int[]{
                100,
                100,
                300,
                300,
                200,
                250
            }, new int[]{
                150,
                250,
                250,
                150,
                100,
                150
            }, false),
            new Polyangle(new int[]{
                66,
                85,
                449,
                257,
                339,
                336,
                218
            }, new int[]{
                297,
                249,
                95,
                355,
                428,
                440,
                436
            }, false)
        };
        File file = new File("C:/Users/Матвей/Documents/test/Polyangle/readTest.txt");
        for (Polyangle pol : pols) {
            try (PrintStream fos = new PrintStream(new FileOutputStream(file))) {
                fos.println(pol);
            }
            Polyangle p = Polyangle.read(file, false);
            try {
                assertEquals(pol, p);
            } catch (AssertionError e){
                file.delete();
                throw e;
            } finally{
                file.delete();
            }
        }
    }
}
