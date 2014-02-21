package lines;

import java.awt.Point;
import java.awt.geom.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;

public class ParabolaTest {

    public ParabolaTest() {
    }

    @Test
    public void testParabola() {
        System.out.println("parabola Constructor");
        Line lData[] = {new Line(0.2, -1, 0.2),
            new Line(4, -3, 35),
            new Line(0.5, -1, 1),
            new Line(2, 0, -3),
            new Line(0, 1, 1)};
        Point2D pData[] = {new Point2D.Double(2, 3),
            new Point2D.Double(-1, 2),
            new Point2D.Double(8, 0),
            new Point2D.Double(-2, 4),
            new Point2D.Double(-2, 4)};
        double etData[] = {2.3533936216582085, 5, 4.47213595499958, 3.5, 5};
        for (int i = 0; i < lData.length; i++) {
            assertEquals(etData[i], new Parabola(pData[i], lData[i]).getP(), 0.00000000001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection (1 pack)");
        Parabola para = new Parabola(new Point(0, 2), new Line(0, 1, 2));
        Line data[] = {new Line(0, 1, -2), new Line(0, 1, 0), new Line(1, 0, 0), new Line(1, -1, 0)};
        Point2D etData[][] = new Point2D[4][2];
        etData[0] = new Point2D[]{new Point2D.Double(4, 2), new Point2D.Double(-4, 2)};
        etData[1] = new Point2D[]{new Point2D.Double(0, 0), new Point(0, 0)};
        etData[2] = new Point2D[]{new Point2D.Double(0, 0), new Point(0, 0)};
        etData[3] = new Point2D[]{new Point2D.Double(8, 8), new Point2D.Double(0, 0)};
        for (int i = 0; i < data.length; i++) {
            assertArrayEquals(etData[i], para.getIntersection(data[i]));
        }
        System.out.println("-------------------");
    }
    /**/

    @Test
    public void testGetIntersection2() {
        System.out.println("getIntersection (2 pack)");
        Parabola paraData[] = new Parabola[5];
        paraData[0] = new Parabola(new Point2D.Double(0, -0.3035714285714286),
                new Line(0, 1, -1.4464285714285714));
        paraData[1] = new Parabola(new Point2D.Double(0, 6.534090909090909),
                new Line(0, 1, -9.28409090909091));
        paraData[2] = new Parabola(new Point2D.Double(0, 0.1),
                new Line(0, 1, -5.1));
        paraData[3] = new Parabola(new Point2D.Double(0, -0.75),
                new Line(0, 1, 1.25));
        paraData[4] = new Parabola(new Point2D.Double(0, 7.666666666666667),
                new Line(0, 1, -10.333333333333334));
        Line lData[] = new Line[5];
        lData[0] = new Line(2, 1, -4);
        lData[1] = new Line(2, -1, 13);
        lData[2] = new Line(1, 1, -5);
        lData[3] = new Line(1, 1, -6);
        lData[4] = new Line(9, 4, -60);
        Point2D etData[][] = new Point2D[5][2];
        etData[0] = new Point2D[]{new Point(3, -2),
            new Point2D.Double(4.000000000000001, -4.000000000000002)};
        etData[1] = new Point2D[]{new Point2D.Double(-7.000000000000005, -1.0000000000000107),
            new Point2D.Double(-3.999999999999997, 5.000000000000006)};
        etData[2] = new Point2D[]{new Point2D.Double(4.000000000000002, 0.9999999999999982),
            new Point2D.Double(5.999999999999997, -0.9999999999999973)};
        etData[3] = new Point2D[]{new Point2D.Double(2.192582403567252, 3.807417596432748),
            new Point2D.Double(-3.192582403567252, 9.192582403567252)};
        etData[4] = new Point2D[]{new Point2D.Double(3.9999999999999996, 6.000000000000002),
            new Point2D.Double(8.000000000000002, -3.0000000000000036)};
        for (int i = 0; i < etData.length; i++) {
            assertArrayEquals(etData[i], paraData[i].getIntersection(lData[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetApex() {
        System.out.println("getApex (5 tests)");
        Parabola paraData[] = new Parabola[5];
        paraData[0] = new Parabola(new Point2D.Double(0, -0.3035714285714286),
                new Line(0, 1, -1.4464285714285714));
        paraData[1] = new Parabola(new Point2D.Double(0, 6.534090909090909),
                new Line(0, 1, -9.28409090909091));
        paraData[2] = new Parabola(new Point2D.Double(0, 0.1),
                new Line(0, 1, -5.1));
        paraData[3] = new Parabola(new Point2D.Double(0, -0.75),
                new Line(0, 1, 1.25));
        paraData[4] = new Parabola(new Point2D.Double(0, 7.666666666666667),
                new Line(0, 1, -10.333333333333334));
        Point2D etData[] = {new Point2D.Double(0, 4.0 / 7),
            new Point2D.Double(0, 7.90909090909091),
            new Point2D.Double(0, 2.5999999999999996),
            new Point2D.Double(0, -1),
            new Point2D.Double(0, 9)};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], paraData[i].getApex());
        }
        System.out.println("-------------------");
    }
}