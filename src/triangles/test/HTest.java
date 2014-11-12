package triangles.test;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import triangles.Computer;
import triangles.Polyangle;
import vectors.TheVector;

/**
 * Создание "картинки с точечками" для заданной пары многоугольников.
 *
 * @author Матвей
 */
public class HTest {

    static String way = "D:/Кольцо/Проект/R/CurrentHTest3/";

    /**
     *
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String a = "C:/try/10_PolA2.txt";
        String b = "C:/try/10_PolB2.txt";
//        String a = "C:/try/1_PolA1.txt";
//        String b = "C:/try/1_PolB1.txt";
//        String a = "C:/try/23_PolA1.txt";
//        String b = "C:/try/23_PolB1.txt";
//        String a = "C:/try/30_PolA2.txt";
//        String b = "C:/try/30_PolB2.txt";
//        String a = "C:/try/22_PolA2.txt";
//        String b = "C:/try/22_PolB2.txt";
//        String a = "C:/try/24_PolA2.txt";
//        String b = "C:/try/24_PolB2.txt";
//        String a = "C:/try/7_PolA1.txt";
//        String b = "C:/try/7_PolB1.txt";
//        String a = "C:/try/6_PolA2.txt";
//        String b = "C:/try/6_PolB2.txt";
//        String a = "C:/try/2_PolA2.txt";
//        String b = "C:/try/2_PolB2.txt";
//        String a = "C:/try/p2.txt";
//        String b = "C:/try/p2.txt";
        System.out.println(a);
        System.out.println(b);
        Polyangle A = Polyangle.read(new File(a), false);
        Polyangle B = Polyangle.read(new File(b), false);
        B.translate(new TheVector(new int[]{33, -32}));
        int result[][] = HTest.testPols(A, B);
        int width = result.length;
        int height = result[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (result[x][y] == 1) {
                    image.setRGB(x, y, Color.GREEN.getRGB());
                } else if (result[x][y] == 0) {
                    image.setRGB(x, y, Color.red.getRGB());
                }
            }
        }
        ImageIO.write(image, "bmp", new File("D:/Кольцо/Проект/R/CurrentHTest/diagram.bmp"));
    }

    static int[][] testPols(Polyangle stab, Polyangle move) throws FileNotFoundException {
        int k = 10;
        Log TM = new Log(way + "TrueMatrix.txt");
        Log HDVecs = new Log(way + "HDVecs.txt");
        Log HDVLen = new Log(way + "HDVLength.txt");
        Polyangle A = stab.clone();
        Polyangle B = move.clone();
//        Point2D pro[] = Computer.calcRange(A, B);
        Point2D pro[] = new Point2D[]{new Point2D.Double(A.getBounds().x, A.getBounds().y),
            new Point2D.Double(A.getBounds().x + A.getBounds().width, A.getBounds().y - A.getBounds().height)};
        int answer[][] = new int[(int) (Math.abs(pro[0].getX() - pro[1].getX())) / k + 1][(int) (Math.abs(pro[1].getY() - pro[0].getY())) / k + 1];
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                answer[i][j] = -1;
            }
        }
        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(B.getBounds().x, B.getBounds().y), pro[0]});
        A.translate(norma);
        TheVector i = new TheVector(k, 0);
        TheVector j = new TheVector(0, -k);
        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + (pro[1].getX() - pro[0].getX()) % k), 0);
        Computer.exSearchOptimize(A, B, new Log(way + "HTestEvM.txt"));
        double hOptimum = TheVector.getHDistance(A, B)[0].getVLength();
        A = stab.clone();
        B = move.clone();
        TM.post(pro[0] + " " + pro[1]);
        TM.post("hOptimum = " + hOptimum);
        TM.post("ROUND_KOEF = " + Computer.ROUND_KOEF);
        TM.post("Second Koef = ROUND_KOEF * 10 = " + Computer.ROUND_KOEF * 10);
        TM.post("Step Koef = k = " + k);
        TM.post(stab.toString());
        TM.post(move.toString());
        TM.postnb("[");
        HDVecs.post(pro[0] + " " + pro[1]);
        HDVecs.postnb("[");
        HDVLen.post(pro[0] + " " + pro[1]);
        HDVLen.postnb("[");
        int a = 0;
        int b = 0;
        for (int l = (int) pro[0].getY() / k; l > (int) pro[1].getY() / k; l--) {
            TM.post("");
            TM.postnb("[");
            HDVecs.post("");
            HDVecs.postnb("[");
            System.out.println("l = " + (-l + pro[0].getY()));
            System.out.println(-((int) pro[1].getY()) + l + " ************left");
//            System.out.println(move.getBounds());
            for (int m = (int) pro[0].getX() / k; m < (int) pro[1].getX() / k; m++) {
                Computer.optimize(A, B);

                TheVector haus[] = TheVector.getHDistance(A, B);
                if (m != (int) pro[0].getX() / k) {
                    TM.postnb(", ");
                    HDVecs.postnb(", ");
                    HDVLen.postnb(", ");
                }
                if (haus[0].getVLength() - hOptimum <= Computer.ROUND_KOEF * 10) {
                    answer[a][b] = 1;
                    TM.postnb("1");
                } else {
                    answer[a][b] = 0;
                    TM.postnb("0");
                }
                HDVecs.postnb(haus[0].toString());
                HDVLen.postnb(Double.toString(haus[0].getVLength()));
                if (m == (int) pro[1].getX() / k - 1) {
                    TM.postnb("],");
                    HDVecs.postnb("],");
                    HDVLen.postnb("],");
                }
                move.translate(i);
                A = stab.clone();
                B = move.clone();
                a++;
            }
            a = 0;
            move.translate(j);
            move.translate(norma);
            b++;
        }
        TM.postnb("]");
        HDVecs.postnb("]");
        HDVLen.postnb("]");
        TM.die();
        HDVecs.die();
        HDVLen.die();
        return answer;
    }

}
