package triangles;

/**
 * Класс, представляющий многоугольник. Используется для задания области, в 
 * которой нужно производить перебор по сетке.
 * @author Матвей
 */
public class Rectangle {

    /**
     * X-координата верхнего левого угла.
     */
    public double x;

    /**
     * Y-координата верхнего левого угла.
     */
    public double y;

    /**
     * Ширина многоугольника.
     */
    public double width;

    /**
     * Высота
     */
    public double height;
    
    /**
     * Создание прямоугольника с заданными параметрами.
     * @param x X-координата верхнего левого угла.
     * @param y Y-координата верхнего левого угла.
     * @param width Ширина многоугольника.
     * @param height Высота
     */
    public Rectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
