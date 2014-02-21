package triangles.gen;

import triangles.Polyangle;

public interface PolGenerator {
    Polyangle gen(int qOfP, int seed, int width, int height);
}
