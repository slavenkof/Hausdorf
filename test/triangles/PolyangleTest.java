package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class PolyangleTest {

    public PolyangleTest() {
    }

    @Test
    public void testRead() {
        System.out.println("read(4 tests)");
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
    public void testReadAndReadAll() {
        System.out.println("readAndReadAll(4 tests)");
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
    public void testIsConvex(){
        System.out.println("isConvex (8 tests");
        Polyangle pols[] = Polyangle.readAll(new File("C:/Users/Матвей/Documents/test/Polyangle/test5.txt"), false);
        boolean etData[] = {true, true, true, true, true, true};
        for (int i=0; i<etData.length; i++){
            assertEquals(etData[i], pols[i].isConvex());
        }
        Polyangle pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test2.txt"), true);
        assertEquals(false, pol.isConvex());
        pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Polyangle/test8.txt"), false);
        assertEquals(false, pol.isConvex());
        System.out.println("-------------------");
    }
}