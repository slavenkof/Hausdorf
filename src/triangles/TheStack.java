package triangles;

import java.util.ArrayList;

/**
 * Простейшая реализация класса-стека с возможностью увидеть второй элемент в стеке.
 * Писался для алгоритма Грэхема.
 * @author Матвей
 * @param <E>
 */
public class TheStack<E> {

    private ArrayList<E> elements = new ArrayList<>();
    private int length = 0;

    /**
     * Помещение в стек элемента.
     * @param obj
     */
    public void push(E obj) {
        length++;
        elements.add(obj);
    }

    /**
     * Выталкивание элемента, лежащего на вершине стека.
     * @return вершина стека.
     */
    public E pop() {
        E obj = elements.remove(length - 1);
        length--;
        return obj;
    }

    /**
     * Возвращает элемент на вершине стека без выталкивания.
     * @return элемент на вершине стека.
     */
    public E peek() {
        return elements.get(length - 1);
    }

    /**
     * Элемент, лежащий под вершиной, возвращается без изъятия из стека.
     * @return второй сверху элемент в стеке.
     */
    public E deepPeek() {
        return elements.get(length - 2);
    }

    /**
     * Возвращает количество элементов в стеке.
     * @return количество элементов в стеке.
     */
    public int length() {
        return length;
    }

    /**
     * Возвращает свои элементы в виде коллекции.
     * @return все элементы, сдержащиеся в стеке.
     */
    public ArrayList<E> asArrayList() {
        return elements;
    }
}
