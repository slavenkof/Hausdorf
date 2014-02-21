package vectors;

import java.util.Comparator;
import static triangles.Computer.ROUND_KOEF;

/**
 * Сортировка векторов по их длине.
 * @author Матвей
 */
public class TheVectorDistCompare implements Comparator<TheVector> {

    @Override
    public int compare(TheVector v1, TheVector v2) {
        double dist1 = v1.getVLength();
        double dist2 = v2.getVLength();
        if (Math.abs(dist1 - dist2) < ROUND_KOEF) {
            return 0;
        }
        if ((dist1 - dist2) < 0) {
            return -1;
        }
        if ((dist1 - dist2) > 0) {
            return 1;
        }
        throw new RuntimeException();
    }
}