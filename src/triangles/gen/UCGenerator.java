package triangles.gen;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import triangles.Computer;
import triangles.Polyangle;
import vectors.TheVector;

public class UCGenerator implements PolGenerator {

    @Override
    public Polyangle gen(int qOfP, int seed, int width, int height) {
        Random r1 = new Random(seed);
        Polyangle c1 = Polyangle.randGen(qOfP, r1.nextInt(), width, height);
        Polyangle c2 = Polyangle.randGen(qOfP, r1.nextInt(), width, height);
        ArrayList<Point2D> points = new ArrayList<>();
        points.addAll(c1.getApexs());
        points.addAll(c2.getApexs());
        double x = 0;
        double y = 0;
        for (int i = 0; i < points.size(); i++) {
            Point2D p = points.get(i);
            x = x + p.getX();
            y = x + p.getY();
        }
        x = x / points.size();
        y = y / points.size();
        TheVector move = new TheVector(new Point2D[]{new Point2D.Double(x, y), new Point2D.Double()});
        for (int i = 0; i < points.size(); i++) {
            Point2D p = points.get(i);
            points.set(i, move.sumPoint(p));
        }
        move.swap();
        Point2D aps[] = new Point2D[points.size()];
        points.toArray(aps);
        Computer.pointsSortAngle(aps);
        Polyangle answer = new Polyangle(aps, true);
        answer.translate(move);
        
        if (!answer.isConvex()) {
            return answer;
        } else {
            return null;
        }
    }
}
