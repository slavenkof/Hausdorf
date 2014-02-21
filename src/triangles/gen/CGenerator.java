package triangles.gen;

import triangles.Polyangle;

public class CGenerator implements PolGenerator {

    @Override
    public Polyangle gen(int qOfP, int seed, int width, int height) {
        return Polyangle.randGen(qOfP, seed,width, height);
    }
}
