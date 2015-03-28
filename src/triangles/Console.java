package triangles;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import vectors.TheVector;

public class Console {
    public static void main(String[] args) throws FileNotFoundException {
        int nums[] = {30, 33, 40};
        String pathPols = "D:\\Ring\\Project\\R\\Tests\\new\\TestCase";
        String pathResults = "D:\\Ring\\Project\\R\\Tests\\new\\out.txt";
        
        File results = new File(pathResults);
        try (PrintStream out = new PrintStream(new FileOutputStream(results))) {
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j <= nums[i-1]; j++) {
                    System.out.println(Integer.toString(i)+"."+Integer.toString(j));
                    File first = new File(pathPols+Integer.toString(i)+"\\Pols\\"+Integer.toString(j)+"_PolA.txt");
                    File second = new File(pathPols+Integer.toString(i)+"\\Pols\\"+Integer.toString(j)+"_PolB.txt");
                    Polyangle A = Polyangle.read(first, false);
                    Polyangle B = Polyangle.read(second, false);
                    Computer.optimize(A, B);
                    out.println(Computer.middle(TheVector.getHDistance(A, B)));
                    out.println(Computer.iterations);
                }
            }
        }
    }
}
