package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import vectors.TheVector;
import triangles.test.Log;

public class Console {

    static Polyangle stab = new Polyangle(new int[]{
        410,
        303,
        282,
        107,
        29,
        38,
        100,
        208,
        415,
        469,
        463}, new int[]{
        364,
        229,
        319,
        194,
        125,
        119,
        84,
        36,
        2,
        39,
        81}, false);
    static Polyangle move = new Polyangle(new int[]{
        483,
        413,
        466,
        136,
        88,
        148,
        210,
        447,
        454}, new int[]{
        340,
        410,
        495,
        410,
        237,
        141,
        6,
        188,
        220}, false);

//    public static void main(String[] args) throws FileNotFoundException {
//        Log log = new Log("D:/Кольцо/Проект/R/rubbish.txt");
//        System.err.println("exSearchOptimizeC");
//        Polyangle optimum;
//        Point2D pro[] = Computer.calcRange(stab, move);
//        System.out.println(pro[0]);//*
//        System.out.println(pro[1]);//*
//        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(move.getBounds().x, move.getBounds().y), pro[0]});
//        move.translate(norma);
//        //System.out.println(move.getBounds());//*
//        TheVector i = new TheVector(1, 0);
//        TheVector j = new TheVector(0, -1);
//        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
//        optimum = move.clone();
//        TheVector optim = TheVector.getUCHDistance(stab, move)[0];
////        double hOptimum = TheVector.getCHDistance(stab, move)[0].getVLength();
//        log.post(pro[0] + " " + pro[1]);
//        log.postnb("[");
//        for (int l = (int) pro[0].getY(); l >= (int) pro[1].getY(); l--) {
//            log.post("");
//            log.postnb("[");
////            System.out.println(move.getBounds());**
//            System.out.println("l = " + (-l + pro[0].getY()));
//            System.out.println(-((int) pro[1].getY()) + l + " left");//Состояние обсчета
//            for (int m = (int) pro[0].getX(); m <= (int) pro[1].getX(); m++) {
////                TheVector haus[] = TheVector.getUCHDistance(stab, move);
////                if (haus[0].compareTo(optim) < 0) {
////                    optimum = move.clone();
////                    optim = haus[0];
////                }
//                move.translate(i);
//                if (m != (int) pro[0].getX()) {
//                    log.postnb(", ");
//                }
////                log.postnb(Double.toString(haus[0].getVLength()));
//                if (m == (int) pro[1].getX()) {
//                    log.postnb("],");
//                }
//            }
////            if(l==(int) pro[1].getY()){**
////                System.out.println(move.getBounds());
////            }
//            move.translate(j);
//            System.out.println(move.getBounds());
//            move.translate(norma);
//        }
//        log.postnb("]");
//        log.die();
//        System.out.println(optimum.getBounds());
//        ArrayList<Point2D> p1 = move.getApexs();
//        ArrayList<Point2D> p2 = optimum.getApexs();
//        Iterator<Point2D> i1 = p1.iterator();
//        Iterator<Point2D> i2 = p2.iterator();
//        while(i1.hasNext()){
//            System.out.println(new TheVector(new Point2D[]{i1.next(), i2.next()}));
//        }
//        System.out.println(TheVector.getUCHDistance(stab, optimum)[0]);
//
//        move.translate(new TheVector(new Point2D[]{move.getApexs().get(0), optimum.getApexs().get(0)}));
//        System.out.println();
//        System.out.println(move.getBounds());
//        System.out.println(TheVector.getUCHDistance(stab, move)[0]);
//    }
    public static void main(String[] args) {
        Polyangle C = stab.clone();
        System.out.println(stab);
        System.out.println(C);
        
        stab.translate(new TheVector (new int[]{1, 1}));
        System.out.println(stab);
        ArrayList<Point2D> p1 = C.getApexs();
        ArrayList<Point2D> p2 = stab.getApexs();
        Iterator<Point2D> i1 = p1.iterator();
        Iterator<Point2D> i2 = p2.iterator();
        while(i1.hasNext()){
            System.out.println(new TheVector(new Point2D[]{i1.next(), i2.next()}));
        }
        
    }
}
