package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class ComputerTest {

    public ComputerTest() {
    }

    @Test
    public void testPointsSort() {
        System.out.println("pointsSort");
        Point2D points[];
        int[][][] expCoords = new int[][][]{{{15, 53}, {41, 20}, {29, 47}, {95, 44}, {60, 48}, {91, 61}, {73, 62}, {19, 54}, {84, 75}, {77, 77}},
        {{17, 63}, {34, 6}, {47, 13}, {54, 4}, {62, 34}, {78, 48}, {92, 62}, {69, 73}, {96, 89}, {85, 88}},
        {{6, 19}, {14, 16}, {34, 14}, {89, 50}, {30, 31}, {94, 86}, {47, 68}, {67, 99}, {40, 67}, {8, 72}},
        {{10, 81}, {28, 2}, {81, 6}, {77, 14}, {34, 60}, {76, 37}, {49, 64}, {59, 61}, {85, 82}, {86, 99}},
        {{3, 58}, {11, 46}, {67, 5}, {92, 8}, {62, 27}, {62, 48}, {62, 52}, {62, 52}, {52, 60}, {69, 76}},
        {{6, 5}, {31, 3}, {78, 15}, {74, 24}, {22, 21}, {87, 92}, {17, 17}, {47, 60}, {54, 91}, {28, 90}},
        {{8, 68}, {12, 11}, {29, 24}, {41, 3}, {53, 0}, {77, 4}, {90, 41}, {51, 58}, {66, 78}, {11, 76}},
        {{0, 12}, {8, 11}, {85, 44}, {61, 38}, {50, 34}, {80, 54}, {95, 62}, {68, 49}, {36, 64}, {7, 79}},
        {{2, 29}, {28, 8}, {53, 0}, {30, 21}, {31, 31}, {82, 57}, {72, 58}, {64, 56}, {83, 75}, {54, 90}},
        {{11, 68}, {48, 35}, {39, 44}, {26, 58}, {59, 57}, {73, 76}, {95, 98}, {89, 96}, {46, 86}, {13, 93}},
        {{13, 80}, {81, 14}, {91, 8}, {46, 56}, {73, 38}, {86, 53}, {95, 80}, {97, 88}, {93, 90}, {23, 99}},
        {{11, 55}, {28, 6}, {33, 7}, {40, 33}, {62, 18}, {87, 9}, {93, 7}, {94, 91}, {38, 68}, {14, 94}},
        {{5, 3}, {61, 7}, {66, 12}, {96, 21}, {50, 15}, {24, 11}, {56, 33}, {95, 64}, {36, 31}, {68, 72}},
        {{5, 55}, {56, 14}, {63, 10}, {60, 16}, {92, 0}, {60, 24}, {93, 13}, {62, 54}, {44, 69}, {75, 98}},
        {{15, 84}, {16, 73}, {19, 54}, {22, 58}, {67, 1}, {62, 36}, {98, 8}, {94, 65}, {37, 81}, {25, 87}},
        {{27, 92}, {28, 26}, {49, 34}, {38, 67}, {65, 6}, {41, 72}, {59, 70}, {85, 60}, {77, 77}, {84, 80}},
        {{6, 21}, {71, 0}, {50, 15}, {99, 15}, {51, 32}, {75, 50}, {53, 42}, {81, 68}, {13, 70}, {8, 59}},
        {{4, 15}, {62, 8}, {94, 16}, {76, 20}, {50, 21}, {63, 27}, {92, 93}, {48, 58}, {63, 95}, {7, 53}},
        {{0, 4}, {97, 69}, {48, 40}, {28, 28}, {78, 80}, {82, 91}, {40, 61}, {38, 70}, {27, 92}, {15, 72}},
        {{11, 72}, {14, 30}, {25, 44}, {80, 4}, {52, 38}, {90, 22}, {70, 73}, {90, 77}, {56, 84}, {14, 91}},};
        Point2D expResult[][] = new Point2D[expCoords.length][];
        for (int i = 0; i < expCoords.length; i++) {
            expResult[i] = new Point2D[expCoords[i].length];
            for (int j = 0; j < expCoords[i].length; j++) {
                expResult[i][j] = new Point(expCoords[i][j][0], expCoords[i][j][1]);
            }
        }
        for (int i = 0; i < expCoords.length; i++) {
            ArrayList<Point2D> ar = new ArrayList<>();
            ar.addAll(Arrays.asList(expResult[i]));
            Collections.shuffle(ar);
            points = new Point2D[ar.size()];
            ar.toArray(points);
            points = Computer.pointsSort(points);
            assertArrayEquals(expResult[i], points);
        }
    }

    /**
     * Тест сортировки точек по полярному углу.
     */
    @Test
    public void testPointsSortAngle() {
        System.out.println("pointsSortAngle");
        Point2D[] points = new Point[]{
            new Point(5, 0),
            new Point(4, 4),
            new Point(1, 1),
            new Point(0, 4),
            new Point(-1, 4),
            new Point(-3, 2),
            new Point(-3, 0),
            new Point(0, -3),
            new Point(5, -2),};
        Point2D[] expResult = new Point[]{
            new Point(5, 0),
            new Point(1, 1),
            new Point(4, 4),
            new Point(0, 4),
            new Point(-1, 4),
            new Point(-3, 2),
            new Point(-3, 0),
            new Point(0, -3),
            new Point(5, -2)
        };
        for (int i = 0; i < 50; i++) {
            ArrayList<Point2D> p = new ArrayList<>();
            p.addAll(Arrays.asList(points));
            Collections.shuffle(p);
            p.toArray(points);
            Point2D[] result = Computer.pointsSortAngle(points);
            assertArrayEquals(expResult, result);
        }
        System.out.println("-------------------");
    }

    /**
     * Тест проверки принадлежности выпуклому многоугольнику.
     */
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
        pol = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test4.txt"), false);
        points = new Point2D[pol.getQuantOfPoints()];
        pol.getApexs().toArray(points);
        data = new Point[]{
            new Point(4, 1),
            new Point(2, 2),
            new Point(9, 3),
            new Point(5, 4),
            new Point(0, 3),
            new Point(10, 7)};
        etData = new boolean[]{false, true, true, true, false, false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.contains(points, data[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testContains2() {
        Random r = new Random(3722580);
        System.out.println("testContains2");
        int n = 100;
        int m = 10;
        Polyangle pols[] = new Polyangle[n];
        for (int i = 0; i < n; i++) {
            pols[i] = Polyangle.randGen(20, i, 300, 300);
            assertTrue("Graham's error!!! \n" + pols[i].toString() + "\n" + 
                    Integer.toString(i) + "\n===================", pols[i].isConvex());
        }
        for (int i = 0; i < n; i++) {
            Point2D points[] = new Point2D[pols[i].getQuantOfPoints()];
            pols[i].getApexs().toArray(points);
            for (int j = 0; j < m; j++) {
                Point p = new Point(r.nextInt(300), r.nextInt(300));
                assertEquals("Contain error!!!", Computer.contains(points, p), Computer.UCcontains(points, p));
            }

        }
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
        Polyangle p = Polyangle.read(new File("C:/Users/Матвей/Documents/test/Computer/test2.txt"), false);
        Point2D ps[] = new Point2D[p.getQuantOfPoints()];
        p.getApexs().toArray(ps);
        Point2D data[] = {
            new Point(1, 1),
            new Point(5, 1),
            new Point(3, 3),
            new Point(5, 3),
            new Point(2, 3)};
        etData = new boolean[]{false, false, true, true, false};
        for (int i = 0; i < etData.length; i++) {
            assertEquals(etData[i], Computer.UCcontains(ps, data[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testCandUCcontains() {
        Random r = new Random(254);
        for (int i = 0; i < 100; i++) {
            Point p = new Point(r.nextInt(500), r.nextInt(500));
            Polyangle pol = Polyangle.randGen(4, r.nextInt(), 500, 500);
            Point2D points[] = new Point2D[pol.getQuantOfPoints()];
            pol.getApexs().toArray(points);
            assertEquals(Computer.UCcontains(points, p), Computer.contains(points, p));
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
        System.out.println("-------------------");
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
