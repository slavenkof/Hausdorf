package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class ComputerTest {

    public ComputerTest() {
    }

    @Ignore
    @Test
    public void testPointsSort() {
        System.out.println("pointsSort");
        Point2D[] points = null;
        Point2D[] expResult = null;
        Point2D[] result = Computer.pointsSort(points);
        assertArrayEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testPointsSortAngle() {
        System.out.println("pointsSortAngle");
        Point2D[] points = null;
        Point2D[] expResult = null;
        Point2D[] result = Computer.pointsSortAngle(points);
        assertArrayEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testContains() {
        System.out.println("contains(5+6 tests)");
        Polyangle pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test3.txt"), true);
        Point2D points[] = new Point2D[pol.getQuantOfPoints()];
        pol.getApexs().toArray(points);
        Point2D data[] = {
            new Point(4, 3),
            new Point(3, 5),
            new Point(2, 2),
            new Point(9, 5),
            new Point(1, 6)};
        boolean etData[] = {true, true, true, false, false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.contains(points, data[i]));
        }
        pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test4.txt"), true);
        points = new Point2D[pol.getQuantOfPoints()];
        pol.getApexs().toArray(points);
        data = new Point[]{
            new Point(4, 1),
            new Point(2, 2),
            new Point(9, 3),
            new Point(5, 4),
            new Point(0, 3),
            new Point(10, 7)};
        etData = new boolean[]{false, false, false, true, false, false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.contains(points, data[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testUCcontains() {
        System.out.println("UCcontains (5+5 tests)");
        Polyangle pol[] = Polyangle.readAll(new File("C:/Users/Матвей/Documents/test/Computer/test1.txt"), true);
        Point2D points[][] = new Point2D[pol.length][];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point2D[pol[i].getQuantOfPoints()];
            pol[i].getApexs().toArray(points[i]);
        }
        boolean etData[] = {false, false, false, true};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.UCcontains(points[i], new Point(0, 0)));
        }
        Polyangle p = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test2.txt"), true);
        Point2D ps[] = new Point2D[p.getQuantOfPoints()];
        p.getApexs().toArray(ps);
        Point2D data[] = {
            new Point(1, 1),
            new Point(5, 1),
            new Point(3, 4),
            new Point(5, 3),
            new Point(2, 3)};
        etData = new boolean[]{false, false, true, true, false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.UCcontains(ps, data[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testSectContains() {
        System.out.println("sectContains");
        Point2D[] sect = {new Point(1, 1), new Point(10, 5)};
        Point2D p[] = {
            new Point(1, 1),
            new Point(10, 5),
            new Point2D.Double(5, 25.0 / 9),//
            new Point2D.Double(4, 7.0 / 3),
            new Point2D.Double(0.5, 7.0 / 9),
            new Point(0, 10),
            new Point2D.Double(1, 0.99),
            new Point(9, 5),
            new Point2D.Double(0.5, 1),
            new Point(4, 6),
            new Point(4, 0)};
        boolean etData[] = {
            true,
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            false,
            false,
            false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.sectContains(sect, p[i]));
        }
    }

    @Test
    public void testMixArrays_Point2DArrArr() {
        System.out.println("mixArrays (2 tests)");
        Point2D[][] arrays1 = new Point2D[7][6];
        arrays1[0] = new Point[]{
            new Point(0, 1),
            new Point(0, 2),
            new Point(0, 3),
            new Point(0, 4),
            new Point(0, 5),
            new Point(0, 6)};
        arrays1[1] = new Point[]{
            new Point(1, 1),
            new Point(1, 2),
            new Point(1, 3),
            new Point(1, 4),
            new Point(1, 5),
            new Point(1, 6)};
        arrays1[2] = new Point[]{
            new Point(2, 1),
            new Point(2, 2),
            new Point(2, 3),
            new Point(2, 4),
            new Point(2, 5),
            new Point(2, 6)};
        arrays1[3] = new Point[]{
            new Point(3, 1),
            new Point(3, 2),
            new Point(3, 3),
            new Point(3, 4),
            new Point(3, 5),
            new Point(3, 6)};
        arrays1[4] = new Point[]{
            new Point(4, 1),
            new Point(4, 2),
            new Point(4, 3),
            new Point(4, 4),
            new Point(4, 5),
            new Point(4, 6)};
        arrays1[5] = new Point[]{
            new Point(5, 1),
            new Point(5, 2),
            new Point(5, 3),
            new Point(5, 4),
            new Point(5, 5),
            new Point(5, 6)};
        arrays1[6] = new Point[]{
            new Point(6, 1),
            new Point(6, 2),
            new Point(6, 3),
            new Point(6, 4),
            new Point(6, 5),
            new Point(6, 6)};
        Point2D[] expResult1 = {
            new Point(0, 1),
            new Point(0, 2),
            new Point(0, 3),
            new Point(0, 4),
            new Point(0, 5),
            new Point(0, 6),
            new Point(1, 1),
            new Point(1, 2),
            new Point(1, 3),
            new Point(1, 4),
            new Point(1, 5),
            new Point(1, 6),
            new Point(2, 1),
            new Point(2, 2),
            new Point(2, 3),
            new Point(2, 4),
            new Point(2, 5),
            new Point(2, 6),
            new Point(3, 1),
            new Point(3, 2),
            new Point(3, 3),
            new Point(3, 4),
            new Point(3, 5),
            new Point(3, 6),
            new Point(4, 1),
            new Point(4, 2),
            new Point(4, 3),
            new Point(4, 4),
            new Point(4, 5),
            new Point(4, 6),
            new Point(5, 1),
            new Point(5, 2),
            new Point(5, 3),
            new Point(5, 4),
            new Point(5, 5),
            new Point(5, 6),
            new Point(6, 1),
            new Point(6, 2),
            new Point(6, 3),
            new Point(6, 4),
            new Point(6, 5),
            new Point(6, 6)};
        Point2D arrays2[][] = new Point2D[7][];
        arrays2[0] = new Point2D[]{
            new Point(0, 0)};
        arrays2[1] = new Point2D[]{
            new Point(1, 0),
            new Point(1, 1)};
        arrays2[2] = new Point2D[]{
            new Point(2, 0),
            new Point(2, 1),
            new Point(2, 2)};
        arrays2[3] = new Point2D[]{
            new Point(3, 0),
            new Point(3, 1),
            new Point(3, 2),
            new Point(3, 3)};
        arrays2[4] = new Point2D[]{
            new Point(4, 0),
            new Point(4, 1),
            new Point(4, 2),
            new Point(4, 3),
            new Point(4, 4)};
        arrays2[5] = new Point2D[]{
            new Point(5, 0),
            new Point(5, 1),
            new Point(5, 2),
            new Point(5, 3),
            new Point(5, 4),
            new Point(5, 5)};
        arrays2[6] = new Point2D[]{
            new Point(6, 0),
            new Point(6, 1),
            new Point(6, 2),
            new Point(6, 3),
            new Point(6, 4),
            new Point(6, 5),
            new Point(6, 6)};
        Point2D[] result = Computer.mixArrays(arrays1);
        assertArrayEquals(expResult1, result);
        result = Computer.mixArrays(arrays2);
        Point2D expResult2[] = {
            new Point(0, 0),
            new Point(1, 0),
            new Point(1, 1),
            new Point(2, 0),
            new Point(2, 1),
            new Point(2, 2),
            new Point(3, 0),
            new Point(3, 1),
            new Point(3, 2),
            new Point(3, 3),
            new Point(4, 0),
            new Point(4, 1),
            new Point(4, 2),
            new Point(4, 3),
            new Point(4, 4),
            new Point(5, 0),
            new Point(5, 1),
            new Point(5, 2),
            new Point(5, 3),
            new Point(5, 4),
            new Point(5, 5),
            new Point(6, 0),
            new Point(6, 1),
            new Point(6, 2),
            new Point(6, 3),
            new Point(6, 4),
            new Point(6, 5),
            new Point(6, 6)};
        assertArrayEquals(expResult2, result);
        System.out.println("-------------------");
    }
}