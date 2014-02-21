package lines;

import java.awt.Point;
import java.awt.geom.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;

public class LineTest {

    public LineTest() {
    }

    @Test
    public void testApproximate() {
        System.out.println("approximate");
        Point2D data[][] = new Point2D[6][2];
        data[0] = new Point[]{
            new Point(1, 25), new Point(-3, 2)};
        data[1] = new Point[]{
            new Point(-5, 8), new Point(6, -6)};
        data[2] = new Point[]{
            new Point(3, -22), new Point(14, 6)};
        data[3] = new Point[]{
            new Point(3, 0), new Point(3, 5)};
        data[4] = new Point[]{
            new Point(0, 1), new Point(0, 33)};
        data[5] = new Point[]{
            new Point(-2, 3), new Point(1, 12)};
        Line etData[] = {new Line(-23, 4, -77), new Line(-14, -11, 18),
            new Line(28, -11, -326), new Line(5, 0, -15), new Line(32, 0, 0), new Line(9, -3, 27)};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], Line.approximate(data[i][0], data[i][1]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetRAngle() {
        System.out.println("getRAngle");
        Line data[] = {
            new Line(1, -1, 0),
            new Line(1, 0, -5),
            new Line(0, 1, -3),
            new Line(1, 1, -8),
            new Line(1, 0, -3),
            new Line(5, 3, 0)};
        double etData[] = {0.7853981633974483, 1.5707963267948966, 0,
            -0.7853981633974483, 1.5707963267948966, -1.0303768265243125};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], data[i].getRAngle(), 0.000000000001);
        }
        System.out.println("-------------------");
    }


    @Test
    public void testGetBis() {
        System.out.println("getBis");
        Point2D data[][][] = new Point2D[6][2][2];
        data[0][0] = new Point2D[]{
            new Point(3, 0), new Point(6, 0)};
        data[0][1] = new Point2D[]{
            new Point(1, 4), new Point(2, 2)};
        data[1][0] = new Point[]{
            new Point(0, 3), new Point(0, 1)};
        data[1][1] = new Point[]{
            new Point(3, 0), new Point(1, 0)};
        data[2][0] = new Point[]{
            new Point(0, 0), new Point(1, 2)};
        data[2][1] = new Point[]{
            new Point(0, 0), new Point(-1, 2)};
        data[3][0] = new Point[]{
            new Point(0, 0), new Point(1, 0)};
        data[3][1] = new Point[]{
            new Point(0, 0), new Point(0, 2)};
        data[4][0] = new Point[]{
            new Point(0, 0), new Point(4, 0)};
        data[4][1] = new Point[]{
            new Point(0, 0), new Point(0, 4)};
        data[5][0] = new Point[]{
            new Point(0, 0), new Point(1, 2)};
        data[5][1] = new Point[]{
            new Point(0, 0), new Point(4, 2)};
        Line etData[] = {
            new Line(-0.8944271909999159, -1.4472135954999583, 2.6832815729997477),
            new Line(-1, 1, 0),
            new Line(1, 0, 0),
            new Line(1, -1, 0),
            new Line(1, -1, 0),
            new Line(1, -1, 0)};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], Line.getBis(data[i][0], data[i][1]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testLineSystem() {
        System.out.println("lineSystem");
        Line data[][] = new Line[7][2];
        data[0] = new Line[]{new Line(1, 0, 0), new Line(0, 1, 0)};
        data[1] = new Line[]{new Line(8, -1, -64), new Line(3, 2, -5)};
        data[2] = new Line[]{new Line(2, -6, 42), new Line(2, 7, -75)};
        data[3] = new Line[]{new Line(6, 7, -924), new Line(1, 1, -148)};
        data[4] = new Line[]{new Line(7, -2, -27), new Line(5, 2, -33)};
        data[5] = new Line[]{new Line(5, 8, -31), new Line(3, 8, -25)};
        data[6] = new Line[]{new Line(7, 6, -29), new Line(-5, 8, -10)};
        Point2D etData[] = {new Point(0, 0),
            new Point(7, -8),
            new Point(6, 9),
            new Point(112, 36),
            new Point(5, 4),
            new Point(3, 2),
            new Point2D.Double(2, 2.5)};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], Line.lineSystem(data[i][0], data[i][1]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetMPerpen() {
        System.out.println("getMPerpen (7 tests)");
        Point2D data[][] = new Point2D[7][2];
        data[0] = new Point2D[]{
            new Point(0, 5),
            new Point(5, 0)};
        data[1] = new Point2D[]{
            new Point(1, 1),
            new Point(7, 2)};
        data[2] = new Point2D[]{
            new Point(-3, 2),
            new Point(-2, -2)};
        data[3] = new Point2D[]{//
            new Point(0, 1),
            new Point(1, 0)};
        data[4] = new Point2D[]{
            new Point(-2, -2),
            new Point(2, -4)};
        data[5] = new Point2D[]{
            new Point(-1, -4),
            new Point(3, -2)};
        data[6] = new Point2D[]{
            new Point(0, -4), new Point(4, 2)};
        Line etData[] = {
            new Line(5, -5, 0),
            new Line(6, 1, -25.5),
            new Line(1, -4, 2.5),
            new Line(1, -1, 0),//
            new Line(2, -1, -3),
            new Line(2, 1, 1),
            new Line(2, 3, -1)};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], Line.getMPerpen(data[i]));
        }
        System.out.println("-------------------");
    }
}