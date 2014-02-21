package triangles.gen;

import triangles.Polyangle;

/**
 * Генератор выпуклых многоугольников. Алгоритм состоит из следующих шагов:
 * <ul>
 * <li>Случайным образом (на основе "зерна") в заданном прямоугольнике
 * разбрасываются точки. </li>
 * <li>При помощи алгоритма Грэхема строится выпуклая этих точек, которая и
 * является результатом работы алгоритма.</li>
 * </ul>
 *
 * @author Матвей
 */
//TODO: перенести всю логику генерации многоугольника внутрь этого класса.
public class CGenerator implements PolGenerator {

    /**
     * Метод генерации выпуклых многоугольников. На данный момент вызов
     * делегируется статическому методу класса <code>Polyangle</code>.
     *
     * @param qOfP количество точек, разбрасываемое внутри прямоугольника.
     * @param seed "зерно", используемое для инициализации генератора случайных
     * чисел.
     * @param width ширина многоугольника, внутри которого должен раходится
     * многоугольник.
     * @param height высота многоугольника.
     * @return выпуклый многоугольник.
     */
    @Override
    public Polyangle gen(int qOfP, int seed, int width, int height) {
        return Polyangle.randGen(qOfP, seed, width, height);
    }
}
