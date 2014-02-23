package vectors;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import triangles.Polyangle;

/**
 *
 * @author Славенко Матвей
 * @author slavenkofm@yandex.ru;
 */
public class TheVectorTest {

    @Test
    public void testScalarDerivate() {
        System.out.println("scalarDerivate (8tests)");
        TheVector data[] = new TheVector[4];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        TheVector data1[][] = new TheVector[4][2];
        data1[0] = new TheVector[]{new TheVector(new int[]{1, -4}), new TheVector(new int[]{8, 12})};
        data1[1] = new TheVector[]{new TheVector(new int[]{-5, 6}), new TheVector(new int[]{6, 5})};
        data1[2] = new TheVector[]{new TheVector(new int[]{3, 6}), new TheVector(new int[]{8, -1})};
        data1[3] = new TheVector[]{new TheVector(new int[]{0, -3}), new TheVector(new int[]{5, 0})};
        double etData[] = new double[4];
        etData[0] = 78.0;
        etData[1] = 235.0;
        etData[2] = 0;
        etData[3] = 0;
        int output[] = {-40, 0, 18, 0};
        for (int i = 0; i < 3; i++) {
            assertEquals(etData[i], TheVector.scalarDerivate(new TheVector[]{data[i], data[i + 1]}), 0.0001);
        }
        assertEquals(etData[3], TheVector.scalarDerivate(new TheVector[]{data[0], data[3]}), 0.0001);
        for (int i = 0; i < 4; i++) {
            assertEquals(output[i], TheVector.scalarDerivate(data1[i]), 0.0001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testPseudoScalarDeivate() {
        System.out.println("pseudoScalarDerivate (9 tests)");
        TheVector data[] = new TheVector[4];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        TheVector data1[][] = new TheVector[5][2];
        data1[0] = new TheVector[]{new TheVector(new int[]{-5, 6}), new TheVector(new int[]{8, 12})};
        data1[1] = new TheVector[]{new TheVector(new int[]{1, -4}), new TheVector(new int[]{6, 5})};
        data1[2] = new TheVector[]{new TheVector(new int[]{3, 6}), new TheVector(new int[]{5, 0})};
        data1[3] = new TheVector[]{new TheVector(new int[]{0, -3}), new TheVector(new int[]{8, -1})};
        data1[4] = new TheVector[]{new TheVector(new int[]{-100, 0}), new TheVector(new int[]{0, -150})};
        int output[] = {-108, 29, -30, 24, 15000};
        double etData[] = new double[4];
        etData[0] = 39.0;
        etData[1] = 125.0;
        etData[2] = 0;
        etData[3] = 0;
        for (int i = 0; i < 3; i++) {
            assertEquals(etData[i], TheVector.pseudoScalarDerivate(new TheVector[]{data[i], data[i + 1]}), 0.0001);
        }
        assertEquals(etData[3], TheVector.pseudoScalarDerivate(new TheVector[]{data[0], data[3]}), 0.0001);
        for (int i = 0; i < output.length; i++) {
            assertEquals(output[i], TheVector.pseudoScalarDerivate(data1[i]), 0.0001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testNDerivate() {
        System.out.println("nDerivate (4tests)");
        TheVector data[] = new TheVector[4];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        int facts[] = {5, 1, 6, 10};
        TheVector etData[] = new TheVector[4];
        etData[0] = new TheVector(new int[]{45, 30});
        etData[1] = new TheVector(new int[]{4, 7});
        etData[2] = new TheVector(new int[]{6, 198});
        etData[3] = new TheVector(new int[]{0, 0});
        for (int i = 0; i < 4; i++) {
            assertEquals(etData[i], data[i].nDerivate(facts[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testSumVect() {
        System.out.println("sumVect(4tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{9, 6});
        TheVector etData[] = new TheVector[4];
        etData[0] = new TheVector(new int[]{13, 13});
        etData[1] = new TheVector(new int[]{5, 40});
        etData[2] = new TheVector(new int[]{1, 33});
        etData[3] = new TheVector(new int[]{9, 6});
        for (int i = 0; i < 3; i++) {
            assertEquals(etData[i], data[i].sumVect(data[i + 1]));
        }
        assertEquals(etData[3], data[4].sumVect(data[3]));
        System.out.println("-------------------");
    }

    @Test
    public void testSumPoint() {
        System.out.println("sumPoint (8tests)");
        Point data[] = new Point[4];
        data[0] = new Point(38, 295);
        data[1] = new Point(616, 42);
        data[2] = new Point(202, 420);
        data[3] = new Point(64, 67);
        TheVector data1[] = new TheVector[5];
        data1[0] = new TheVector(new int[]{9, 6});
        data1[1] = new TheVector(new int[]{4, 7});
        data1[2] = new TheVector(new int[]{1, 33});
        data1[3] = new TheVector(new int[]{0, 0});
        Point etData[] = new Point[8];
        etData[0] = new Point(47, 301);
        etData[1] = new Point(620, 49);
        etData[2] = new Point(203, 453);
        etData[3] = new Point(64, 67);
        etData[4] = new Point(42, 302);//0+1
        etData[5] = new Point(617, 75);//1+2
        etData[6] = new Point(202, 420);//2+3
        etData[7] = new Point(73, 73);//3+0

        for (int i = 0; i < 4; i++) {
            assertEquals(etData[i], data1[i].sumPoint(data[i]));
        }
        System.out.println("{First Asserted}");
        for (int i = 4; i < 7; i++) {
            assertEquals(etData[i], data1[i - 3].sumPoint(data[i - 4]));
        }
        System.out.println("{Second Asserted}");
        assertEquals(etData[7], data1[0].sumPoint(data[3]));
        System.out.println("-------------------");
    }

    @Test
    public void testScalarSq() {
        System.out.println("scalarSq(5tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{50, 14});
        double etData[] = new double[5];
        etData[0] = 117;
        etData[1] = 65;
        etData[2] = 1090;
        etData[3] = 0;
        etData[4] = 2696;
        for (int i = 0; i < 5; i++) {
            assertEquals(etData[i], data[i].scalarSq(), 0.0001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testEquals() {
        System.out.println("equals(4tests)");
        TheVector data[] = new TheVector[3];
        data[0] = new TheVector(new int[]{10, 51});
        data[1] = new TheVector(new int[]{33, 33});
        data[2] = new TheVector(new int[]{0, 0});
        TheVector data1[] = new TheVector[3];
        data1[0] = new TheVector(new int[]{10, 51});
        data1[1] = new TheVector(new int[]{33, 33});
        data1[2] = new TheVector(new int[]{59, 70});
        assertTrue(data[0].equals(data1[0]));
        assertTrue(data[1].equals(data1[1]));
        assertFalse(data[2].equals(data1[2]));
        assertFalse(data[0].equals(data[2]));
        System.out.println("-------------------");
    }

    @Test
    public void testThisGetVcoords() {
        System.out.println("getVcoords(this)(5tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{50, 14});
        double etData[][] = new double[5][2];
        etData[0] = new double[]{9, 6};
        etData[1] = new double[]{4, 7};
        etData[2] = new double[]{1, 33};
        etData[3] = new double[]{0, 0};
        etData[4] = new double[]{50, 14};
        for (int i = 0; i < 5; i++) {
            assertArrayEquals(etData[i], data[i].getVCoords(), 0.0001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testPointsGetVCoords() {
        System.out.println("getVCoords(points)(5tests)");
        Point data[] = new Point[4];
        data[0] = new Point(38, 295);
        data[1] = new Point(616, 42);
        data[2] = new Point(202, 420);
        data[3] = new Point(64, 67);
        double etData[][] = new double[5][2];
        etData[0] = new double[]{578, -253};
        etData[1] = new double[]{-414, 378};
        etData[2] = new double[]{-138, -353};
        etData[3] = new double[]{26, -228};
        etData[4] = new double[]{0, 0};
        for (int i = 0; i < 3; i++) {
            assertArrayEquals(etData[i], TheVector.getVCoords(new Point[]{data[i], data[i + 1]}), 0.0001);
        }
        assertArrayEquals(etData[3], TheVector.getVCoords(new Point[]{data[0], data[3]}), 0.0001);
        assertArrayEquals(etData[4], TheVector.getVCoords(new Point[]{data[0], data[0]}), 0.0001);
        System.out.println("-------------------");
    }

    @Test
    public void testGetVDistance() {
        System.out.println("getVDistance(5tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{50, 14});
        double etData[] = new double[5];
        etData[0] = 10.8166538;
        etData[1] = 8.06225774;
        etData[2] = 33.0151480;
        etData[3] = 0.0;
        etData[4] = 51.923019943;
        for (int i = 0; i < 5; i++) {
            assertEquals(etData[i], data[i].getVLength(), 0.000001);
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetOtherPoint() {
        System.out.println("getOtherPoint(10tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{50, 14});
        Point p = new Point(33, 23);
        Point etDataT[] = new Point[5];
        etDataT[0] = new Point(42, 29);
        etDataT[1] = new Point(37, 30);
        etDataT[2] = new Point(34, 56);
        etDataT[3] = new Point(33, 23);
        etDataT[4] = new Point(83, 37);
        Point etDataF[] = new Point[5];
        etDataF[0] = new Point(24, 17);
        etDataF[1] = new Point(29, 16);
        etDataF[2] = new Point(32, -10);
        etDataF[3] = new Point(33, 23);
        etDataF[4] = new Point(-17, 9);

        for (int i = 0; i < 5; i++) {
            assertEquals(etDataT[i], data[i].getOtherPoint(p, true));
        }
        System.out.println("{(From start) Asserted}");
        for (int i = 0; i < 4; i++) {
            assertEquals(etDataF[i], data[i].getOtherPoint(p, false));
        }
        System.out.println("{(From end) Asserted}");
        System.out.println("-------------------");
    }

    @Test
    public void testInsideOf() {
        System.out.println("insideOf(6 tests)");
        TheVector data[][] = new TheVector[6][];
        data[0] = new TheVector[]{
            new TheVector(-1, 5),
            new TheVector(5, 0),
            new TheVector(1, 5),
            new TheVector(3, 4),
            new TheVector(5, 1)};
        data[1] = new TheVector[]{
            new TheVector(1, 1),
            new TheVector(2, 2),
            new TheVector(3, 3),
            new TheVector(4, 4)};
        data[2] = new TheVector[]{
            new TheVector(4, 0),
            new TheVector(0, 3),
            new TheVector(4, 4)};
        data[3] = new TheVector[]{
            new TheVector(-1, -1),
            new TheVector(2, 2),
            new TheVector(2, -1),
            new TheVector(-1, 2)};
        data[4] = new TheVector[]{
            new TheVector(100.0000001718019, -99.99999982819821),
            new TheVector(100.0000001718019, 100.0000001718019),
            new TheVector(100.0000001718019, 100.0000001718019),
            new TheVector(-99.99999982819821, 100.0000001718019),
            new TheVector(-99.99999982819821, 100.0000001718019),
            new TheVector(-99.99999982819821, -99.99999982819821),
            new TheVector(-99.99999982819821, -99.99999982819821),
            new TheVector(100.0000001718019, -99.99999982819821)};
        data[5] = new TheVector[]{
            new TheVector(-1, -1),
            new TheVector(1, 1)};
        boolean etData[] = {false, false, false, true, true, true};
        for (int i = 0; i < data.length; i++) {
            assertEquals(etData[i], TheVector.insideOf(data[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testSwap() {
        System.out.println("swap(5tests)");
        TheVector data[] = new TheVector[5];
        data[0] = new TheVector(new int[]{9, 6});
        data[1] = new TheVector(new int[]{4, 7});
        data[2] = new TheVector(new int[]{1, 33});
        data[3] = new TheVector(new int[]{0, 0});
        data[4] = new TheVector(new int[]{50, 14});
        TheVector etData[] = new TheVector[5];
        etData[0] = new TheVector(new int[]{-9, -6});
        etData[1] = new TheVector(new int[]{-4, -7});
        etData[2] = new TheVector(new int[]{-1, -33});
        etData[3] = new TheVector(new int[]{0, 0});
        etData[4] = new TheVector(new int[]{-50, -14});
        for (int i = 0; i < 5; i++) {
            assertEquals(etData[i], data[i].swap());
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetDistanceVector() {
        System.out.println("getDistance(sect+point)(7tests)");
        Point2D sect[][] = new Point2D[7][2];
        sect[0][0] = new Point(0, 0);
        sect[0][1] = new Point(10, 0);
        sect[1] = new Point[]{new Point(3, 0), new Point(6, 0)};
        sect[2] = new Point[]{new Point(3, 0), new Point(0, 6)};
        sect[3] = new Point[]{new Point(2, 1), new Point(8, 6)};
        sect[4] = new Point[]{new Point(1, 4), new Point(2, 1)};
        sect[5] = new Point[]{new Point(1, 4), new Point(2, 1)};
        sect[6] = new Point[]{new Point(2, 5), new Point(4, 1)};
        Point2D p[] = new Point2D[7];
        p[0] = new Point(5, 5);
        p[1] = new Point(4, 4);
        p[2] = new Point(4, 4);
        p[3] = new Point(3, 2);
        p[4] = new Point(2, 7);
        p[5] = new Point(7, 1);
        p[6] = new Point(1, 1);
        TheVector etData[] = new TheVector[7];
        etData[0] = new TheVector(new int[]{0, -5});
        etData[1] = new TheVector(new double[]{0, -4});
        etData[2] = new TheVector(new double[]{-2.4, -1.2000000000000002});
        etData[3] = new TheVector(new double[]{0.08196721311475441, -0.0983606557377048});
        etData[4] = new TheVector(new double[]{-1, -3});
        etData[5] = new TheVector(new double[]{-5, 0});
        etData[6] = new TheVector(new double[]{2.3999999999999995, 1.2000000000000006});
        for (int i = 0; i < 7; i++) {
            assertEquals(etData[i], TheVector.getDistanceVector(sect[i], p[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testGetDistVectPol() {
        System.out.println("getDistance(polyangle+point)(6tests)");
        Polyangle plans[] = new Polyangle[6];
        plans[0] = new Polyangle(new int[]{4, 8, 2}, new int[]{3, 5, 3}, true);
        plans[1] = new Polyangle(new int[]{6, 5, 9}, new int[]{3, 1, 7}, true);
        plans[2] = new Polyangle(new int[]{1, 6, 7}, new int[]{1, 1, 7}, true);
        plans[3] = new Polyangle(new int[]{2, 7, 9}, new int[]{3, 8, 1}, true);
        plans[4] = new Polyangle(new int[]{2, 4, 8}, new int[]{1, 7, 3}, true);
        plans[5] = new Polyangle(new int[]{2, 2, 9}, new int[]{1, 5, 1}, true);
        Point p[] = new Point[6];
        p[0] = new Point(8, 8);
        p[1] = new Point(2, 4);
        p[2] = new Point(0, 8);
        p[3] = new Point(6, 4);
        p[4] = new Point(3, 4);
        p[5] = new Point(9, 1);
        TheVector etData[][] = new TheVector[6][3];
        etData[0] = new TheVector[]{new TheVector(new int[]{0, -3}),
            new TheVector(new int[]{-4,-5}),
            new TheVector(new int[]{0, -3})};
        etData[1] = new TheVector[]{
            new TheVector(new double[]{3.5999999999999996, -1.7999999999999998}),
            new TheVector(new double[]{4.0, -1.0}),
            new TheVector(new double[]{3.4615384615384617, -2.3076923076923075})};
        etData[2] = new TheVector[]{new TheVector(new double[]{6.972972972972973, -1.1621621621621614}),
            new TheVector(new double[]{3.999999999999999, -4.000000000000001}),
            new TheVector(new double[]{1.0, -7.0})};
        etData[3] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0})};
        etData[4] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0.0, 0})};
        etData[5] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{-7, 0}),
            new TheVector(new double[]{0, 0})};
        for (int i = 0; i < 6; i++) {
            assertArrayEquals(etData[i], TheVector.getDistanceVector(plans[i], p[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testUCGetDistanceVector() {
        System.out.println("UCGetDistance(polyangle+point)(6tests)");
        Polyangle plans[] = new Polyangle[6];
        plans[0] = new Polyangle(new int[]{4, 8, 2}, new int[]{3, 5, 3}, true);
        plans[1] = new Polyangle(new int[]{6, 5, 9}, new int[]{3, 1, 7}, true);
        plans[2] = new Polyangle(new int[]{1, 6, 7}, new int[]{1, 1, 7}, true);
        plans[3] = new Polyangle(new int[]{2, 7, 9}, new int[]{3, 8, 1}, true);
        plans[4] = new Polyangle(new int[]{2, 4, 8}, new int[]{1, 7, 3}, true);
        plans[5] = new Polyangle(new int[]{2, 2, 9}, new int[]{1, 5, 1}, true);
        Point2D p[] = new Point[6];
        p[0] = new Point(8, 8);
        p[1] = new Point(2, 4);
        p[2] = new Point(0, 8);
        p[3] = new Point(6, 4);
        p[4] = new Point(3, 4);
        p[5] = new Point(9, 1);
        TheVector etData[][] = new TheVector[6][3];
        etData[0] = new TheVector[]{new TheVector(new int[]{0, -3}),
            new TheVector(new int[]{-4, -5}),
            new TheVector(new int[]{0, -3})};
        etData[1] = new TheVector[]{
            new TheVector(new double[]{3.5999999999999996, -1.7999999999999998}),
            new TheVector(new double[]{4.0, -1.0}),
            new TheVector(new double[]{3.4615384615384617, -2.3076923076923075})
        };
        etData[2] = new TheVector[]{new TheVector(new double[]{6.972972972972973, -1.1621621621621614}),
            new TheVector(new double[]{3.999999999999999, -4.000000000000001}),
            new TheVector(new double[]{1.0, -7.0})};
        etData[3] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0})};
        etData[4] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{0.0, 0})};
        etData[5] = new TheVector[]{new TheVector(new double[]{0, 0}),
            new TheVector(new double[]{-7, 0}),
            new TheVector(new double[]{0, 0})
        };
        for (int i = 0; i < 6; i++) {
            assertArrayEquals(etData[i], TheVector.getUCDistanceVector(plans[i], p[i]));
        }
        System.out.println("-------------------");
    }

    @Test
    public void testCompare() {
        System.out.println("comparator(2tests)");
        TheVectorDistCompare tvdc = new TheVectorDistCompare();
        TheVector data1[];
        data1 = new TheVector[]{
            new TheVector(new double[]{3.5, -1.7}),
            new TheVector(new double[]{3.5, -2}),
            new TheVector(new double[]{4, -1})};
        TheVector data2[];
        data2 = new TheVector[]{
            new TheVector(new double[]{0.3, 5.1}),
            new TheVector(new double[]{7.3, 5.6}),
            new TheVector(new double[]{7.2, 9}),
            new TheVector(new double[]{9.09, 2.3}),
            new TheVector(new double[]{4.5, 3.09}),
            new TheVector(new double[]{3.13, 5.7})};
        TheVector etData1[] = {new TheVector(new double[]{3.5, -1.7}),
            new TheVector(new double[]{3.5, -2}),
            new TheVector(new double[]{4, -1})};
        TheVector etData2[] = {new TheVector(new double[]{0.3, 5.1}),
            new TheVector(new double[]{4.5, 3.09}),
            new TheVector(new double[]{3.13, 5.7}),
            new TheVector(new double[]{7.3, 5.6}),
            new TheVector(new double[]{9.09, 2.3}),
            new TheVector(new double[]{7.2, 9}),};
        Arrays.sort(data1, tvdc);
        Arrays.sort(data2, tvdc);
        assertArrayEquals(etData1, data1);
        assertArrayEquals(etData2, data2);
        System.out.println("-------------------");
    }

    @Test
    public void testGetDistVectPolPol() {
        System.out.println("getDistanceVector(Pol+Pol)(1test)");
        Polyangle data1[] = new Polyangle[1];
        data1[0] = new Polyangle(new Point2D[]{
            new Point(2, 1),
            new Point(2, 6),
            new Point(4, 6)}, true);
        Polyangle data2[] = new Polyangle[1];
        data2[0] = new Polyangle(new Point2D[]{
            new Point(7, 1),
            new Point(9, 6),
            new Point(9, 1)}, true);
        TheVector etData1[][] = new TheVector[3][3];
        etData1[0] = new TheVector[]{
            new TheVector(new double[]{-6.0344827586206895, 2.4137931034482762}),
            new TheVector(new double[]{-5, 5}),
            new TheVector(new double[]{-7, 0})};
        etData1[1] = new TheVector[]{
            new TheVector(new double[]{-4.310344827586206, 1.724137931034483}),
            new TheVector(new double[]{-3, 5}),
            new TheVector(new double[]{-5, 0})};
        etData1[2] = new TheVector[]{
            new TheVector(new double[]{-5, 0}),
            new TheVector(new double[]{-5, 0}),
            new TheVector(new double[]{-7, 0})};
        assertArrayEquals(etData1, TheVector.getCDistanceVector(data1[0], data2[0]));
        System.out.println("-------------------");
    }

    @Test
    public void testGetMax() {
        System.out.println("getMax (1 test)");
        TheVector data[] = new TheVector[]{
            new TheVector(10, 0),
            new TheVector(0, 10),
            new TheVector(-10, 0),
            new TheVector(0, -10),
            new TheVector(0, 0),
            new TheVector(0, 5)};
        TheVector etData1[] = {
            new TheVector(10, 0),
            new TheVector(0, 10),
            new TheVector(-10, 0),
            new TheVector(0, -10)};
        assertArrayEquals(etData1, TheVector.getMax(data));
        System.out.println("-------------------");
    }

    @Test
    public void testGetMin() {
        System.out.println("getMin (1test)");
        TheVector data[] = new TheVector[]{
            new TheVector(10, 0),
            new TheVector(0, 10),
            new TheVector(-10, 0),
            new TheVector(0, -10),
            new TheVector(5, 0),
            new TheVector(-5, 0)};
        TheVector etData1[] = {
            new TheVector(5, 0),
            new TheVector(-5, 0)};
        assertArrayEquals(etData1, TheVector.getMin(data));
        System.out.println("-------------------");
    }
}
