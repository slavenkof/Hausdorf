package triangles.test;

import java.io.FileNotFoundException;
import triangles.Computer;
import triangles.Polyangle;

/**
 * <p>
 * Класс предназначен для проведения тестов оптимизации многоугольников. Класс
 * ведет два лога, в которых отображается общий ход тестирования: Главный и
 * Ошибчный. В Главный лог записываются результаты всех тестов, в Ошибочный
 * помещаются результаты неудачных тестов. Также печатаются файлы с информацией
 * о многоугольниках и таблицы значений функции, получаемые в ходе перебора по
 * сетке.</p>
 * <p>
 * Структура Главного лога:
 * <ul>
 * <li>Заголовок теста: <ul>
 * <li>Заголовок Test args</li>
 * <li>Дата</li>
 * <li>Зерно для теста</li>
 * <li>Коэффициент точности</li>
 * <li>Высота прямугольника, внутри которого генерируются случайные
 * многоугольники</li>
 * <li>Ширина прямоугольника</li>
 * <li>Количество точек</li>
 * <li>Разделитель</li>
 * </ul></li>
 * <li> Записи о тестах:
 * <ul>
 * <li>Номер теста</li>
 * <li>Пути к файлам с записями о многугольниках + альфа для каждого
 * многоугольника</li>
 * <li>Расстояния Хаусдорфа после оптимизации субградиентами и перебором</li>
 * <li>Статус теста: принят ли</li>
 * <li>Строка-разделитель</li>
 * </ul></li>
 * </ul>
 * </p>
 * <p>
 * Структура Ошибчного лога включает себя дубликаты записей из главного лога +
 * разность расстояний после разных оптимизаций.
 * </p>
 * Названия Главного и Ошибчного логов задаются, названия файлов с
 * многоугольниками и EvM имеют вид НомерТеста+метка. Метки задаются при
 * инициализации объекта.
 */
public class LogLeader {

    private final Log Main;
    private final Log Imp;
    private final Log Pol[] = new Log[2];
    private Log EvM;
    private Polyangle A;
    private Polyangle B;
    private final String[] Paths;
    private final String Labels[];
    private int test;

    /**
     * Конструирование объекта.
     *
     * @param paths пути к папкам, куда будут помещаться файлы тестов. Первым
     * передается путь к папке, в кторой размещаются файлы Главного и Ошибочного
     * логов. Далее идет путь к папке с многоугольниками и папке с EvM.
     * @param labels строки, кторые будут добавляться к файлам многоугольников и
     * EvM. Порядок передачи: первый многоугольник, второй многоугольник, EvM.
     * @param names Названия Главного и Ошибчного логов.
     * @throws FileNotFoundException
     */
    public LogLeader(String[] paths, String[] labels, String[] names) throws FileNotFoundException {
        Main = new Log(paths[0] + names[0] + ".txt");
        Imp = new Log(paths[0] + names[1] + ".txt");
        Paths = new String[]{paths[1], paths[2]};
        Labels = labels;
    }

    /**
     * Метод для завершения рабты с менеджером логов. Осуществляет закрытие всех
     * потоков для вывода информации.
     */
    public void die() {
        Main.die();
        Imp.die();
    }

    /**
     * Осуществляет подготовку к проведению очередного теста. Проводит
     * инициализацию логов для многоугольников и EvM.
     *
     * @param testN номер теста.
     * @throws FileNotFoundException
     */
    public void prepare(int testN) throws FileNotFoundException {
        test = testN;
        for (int i = 0; i < Pol.length; i++) {
            Pol[i] = new Log(getPaths()[0] + testN + getLabels()[i] + ".txt");
        }
        EvM = new Log(getPaths()[1] + testN + getLabels()[2] + ".txt");
    }

    /**
     * Осуществляет завершение очередного теста. Для этого закрываются логи
     * многоугольников и EvM, в Главный лог впечатывается строка-разделитель.
     */
    public void close() {
        for (Log pol : Pol) {
            pol.die();
        }
        getEvM().die();
        Main.split();
    }

    /**
     * Печать заголовка Главного лога.
     *
     * @param key зерно теста.
     * @param RoundKo коэффициент точности.
     * @param height высота многоугольника, в котором генерируются
     * многоугольники.
     * @param width ширина многоугольника.
     * @param qofp количество вершин.
     */
    public void postInf(long key, double RoundKo, int height, int width, int qofp) {
        Main.post("         Test args");
        Main.timeStamp();
        Main.post("Key: " + key);
        Main.post("ROUND_KOEF: " + RoundKo);
        Main.post("Height: " + height);
        Main.post("Width: " + width);
        Main.post("Quantity of Points: " + qofp);
        Main.split();
    }

    /**
     * Печать информации об очередном тесте: номер теста, пути к файлам, альфы
     * для каждого многоугольника.
     */
    public void header() {
        Main.post("     Test № " + test);
        Main.post("     Files:");
        Main.post("Stable: " + Pol[0].file);
        Main.post("SAlpha: " + A.getAlpha());
        Main.post("Moveable: " + Pol[1].file);
        Main.post("MAlpha: " + B.getAlpha());
        Main.post("Eval Matrix: " + getEvM().file);
    }

    /**
     * Внесение записи в Ошибочный лог.
     *
     * @param Haus расстояние после оптимизации субградиентным методом.
     * @param Grid расстояние после перебора.
     */
    public void postImp(double Haus, double Grid) {
        Imp.post("     Test № " + test);
        Imp.post("     Files:");
        Imp.post("Stable: " + Pol[0].file);
        Imp.post("Moveable: " + Pol[1].file);
        Imp.post("Eval Matrix: " + getEvM().file);
        Imp.post("Haus: " + Haus);
        Imp.post("Grid: " + Grid);
        Imp.post("Substraction: " + (Grid - Haus));
        Imp.split();
    }

    /**
     * Печать информации о результатах теста. В случае неудачного теста
     * вызывается дополнительно <code>postImp(Haus, Grid)</code>.
     *
     * @param Haus расстояние после оптимизации субградиентным методом.
     * @param Grid расстояние после перебора.
     */
    public void postData(double Haus, double Grid) {
        Main.post("Haus: " + Haus);
        Main.post("Grid: " + Grid);
        if ((Math.abs(Haus - Grid) > Computer.ROUND_KOEF) && (Haus > Grid)) {
            Main.post("DENIED");
            postImp(Haus, Grid);
        } else {
            Main.post("Accepted");
        }
    }

    /**
     * Печать информации о многоугольниках в соответствующие файлы.
     *
     * @param stab стабильный многоугольник.
     * @param move подвижный многоугольник.
     */
    public void postPols(Polyangle stab, Polyangle move) {
        Pol[0].post(stab.toString());
        Pol[1].post(move.toString());
        A = stab;
        B = move;
    }

    /**
     * Получение лога для EvM
     *
     * @return the EvM
     */
    public Log getEvM() {
        return EvM;
    }

    /**
     * Получение путей, заданных при инициализации.
     *
     * @return the Paths
     */
    public String[] getPaths() {
        return Paths;
    }

    /**
     * Получение меток, заданных при инициализации.
     *
     * @return the Labels
     */
    public String[] getLabels() {
        return Labels;
    }
}
