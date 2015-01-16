/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectors;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Матвей
 */
public class PolarAngleCompUpTest {

    public PolarAngleCompUpTest() {
    }

    /**
     * Test of compare method, of class PolarAngleCompUp.
     */
    @Test
    public void testCompare() {
        System.out.println("compareUp");
        Point[] points = new Point[]{
            new Point(0, 4),
            new Point(-1, 4),
            new Point(-3, 2),
            new Point(-3, 0),
            new Point(5, 0),
            new Point(4, 4),
            new Point(1, 1),};

        Point[] etData = new Point[]{
            new Point(5, 0),
            new Point(1, 1),
            new Point(4, 4),
            new Point(0, 4),
            new Point(-1, 4),
            new Point(-3, 2),
            new Point(-3, 0),};

        PolarAngleCompUp instance = new PolarAngleCompUp(new Point(0, 0));
        for (int i = 0; i < 50; i++) {
            ArrayList<Point2D> p = new ArrayList<>();
            p.addAll(Arrays.asList(points));
            Collections.shuffle(p);
            p.toArray(points);
            Arrays.sort(points, instance);
            Assert.assertArrayEquals(etData, points);
        }
        System.out.println("-------------------");
    }

}
