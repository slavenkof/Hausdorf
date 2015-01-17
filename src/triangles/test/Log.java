package triangles.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Класс предназначен для легкой работы с логами.
 *
 */
public class Log {

    final File file;
    final PrintStream outStr;
    private String splitter = "===========================";

    /**
     * Инициализация лога. В качестве параметра передается файл, в который будет
     * производиться запись.
     *
     * @param file файл, предназначенны для ведения лога.
     * @throws FileNotFoundException
     */
    public Log(String file) throws FileNotFoundException {
        this.file = new File(file);
        outStr = new PrintStream(new FileOutputStream(this.file));
    }

    /**
     * Метод выводит в файл строку-разделитель.
     */
    public void split() {
        outStr.println(splitter);
    }

    /**
     * Метод возвращает текущую строку-разделитель.
     *
     * @return теекущая строка-разделитель.
     */
    public String getSplitter() {
        return splitter;
    }

    /**
     * Метод для замены строки-разделителя, представленной по умолчанию.
     *
     * @param splitter новая строка-разделитель.
     */
    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    /**
     * Метод, позволяющий впечатать в лог текущую дату и время.
     *
     * @return дата создания метки.
     */
    public long timeStamp() {
        outStr.println("Date: " + new Date());
        return System.currentTimeMillis();
    }

    public long getTimeStamp() {
        return System.currentTimeMillis();
    }
    
    public long timeStamp(String message) {
        outStr.println(message + new Date());
        return System.currentTimeMillis();
    }

    /**
     * Метод впечатывает переданную строку в лог и переводит строку.
     *
     * @param s строка, предназначенная для внесения в лог.
     */
    public void post(String s) {
        outStr.println(s);
    }

    /**
     * Метод печатает строку в лог без пследующего перевода каретки.
     *
     * @param s строка для внесения в лог.
     */
    public void postnb(String s) {
        outStr.print(s);
    }

    /**
     * Метод для завершения рабты с логом. Закрывает поток вывода данных.
     */
    public void die() {
        outStr.close();
    }
}
