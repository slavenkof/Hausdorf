package triangles.test;

import java.awt.Color;
import java.io.*;
import triangles.*;
import vectors.TheVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Создание "картинки с точечками" для заданной пары многоугольников.
 * @author Матвей
 */
public class HTest {

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String a = "C:/try/2_PolA2.txt";
        String b = "C:/try/2_PolB2.txt";
        Polyangle A = Polyangle.read(new File(a), false);
        Polyangle B = Polyangle.read(new File(b), false);
        int result[][] = HTest.testPols(A, B);
        int width = result.length;
        int height = result[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int y=0; y<height;y++){
            for(int x=0; x<width;x++){
                if(result[x][y]==1){
                    image.setRGB(x, y, Color.GREEN.getRGB());
                }
            }
        }
        ImageIO.write(image, "bmp", new File("D:/Кольцо/Проект/R/picture.txt"));
    }

    static int[][] testPols(Polyangle stab, Polyangle move) throws FileNotFoundException {
        Log log = new Log("D:/Кольцо/Проект/R/o.txt");
        Polyangle A = stab.clone();
        Polyangle B = move.clone();
        Point2D pro[] = Computer.calcRange(A, B);
        int answer[][] = new int[(int) (Math.abs(pro[0].getX() - pro[1].getX()))/5][(int) (Math.abs(pro[1].getY() - pro[0].getY()))/5];
        TheVector norma = new TheVector(new Point2D[]{new Point2D.Double(B.getBounds().x, B.getBounds().y), pro[0]});
        A.translate(norma);
        TheVector i = new TheVector(5, 0);
        TheVector j = new TheVector(0, -5);
        norma = new TheVector(-(pro[1].getX() - pro[0].getX() + 1), 0);
        Computer.exSearchOptimize(A, B, new Log("D:/Кольцо/Проект/R/output.txt"));
        double hOptimum = TheVector.getUCHDistance(A, B)[0].getVLength();
        A = stab.clone();
        B = move.clone();
        log.post(pro[0] + " " + pro[1]);
        log.postnb("[");
        int a = 0;
        int b = 0;
        for (int l = (int) pro[0].getY()/5; l >= (int) pro[1].getY()/5; l--) {
            log.post("");
            log.postnb("[");
            System.out.println("l = " + (-l + pro[0].getY()));
            System.out.println(-((int) pro[1].getY()) + l + " left");
            for (int m = (int) pro[0].getX()/5; m <= (int) pro[1].getX()/5; m++) {
                Computer.optimize(A, B);
                TheVector haus[] = TheVector.getUCHDistance(A, B);
                if (m != (int) pro[0].getX()) {
                    log.postnb(", ");
                }
                if (haus[0].getVLength() <= hOptimum) {
                    answer[a][b] = 1;
                    log.postnb("1");
                } else{
                    answer[a][b] = 0;
                    log.postnb("0");
                }
                if (m == (int) pro[1].getX()) {
                    log.postnb("],");
                }
                move.translate(i);
                A = stab.clone();
                B = move.clone();
                a++;
            }
            a=0;
            move.translate(j);
            move.translate(norma);
            b++;
        }
        log.postnb("]");
        log.die();
        return answer;
    }

}
