package lt.shgg.app;

import com.fasterxml.jackson.core.JsonParseException;
import lt.shgg.parsing.Parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;

/**
 * <h1>НАЦИОНАЛЬНЫЙ ИССЛЕДОВАТЕЛЬСКИЙ УНИВЕРСИТЕТ ИТМО</h1>
 * <h2>Факультет программной инженерии и компьютерных технологий</h2>
 * <h3>Программирование</h3>
 * <h4>Лабораторная работа № 5</h4>
 * <p>1 курс 2 семестр<br>
 * Группа № P3120<br>
 * <i>2024 год</i><br>
 * @author Шнейдерис Герардас
 * @see <a href="https://github.com/GeruniaSun/ITMO-programming-lab5">репозиторий на Github</a>
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("приложение не работает без имени файла для сохранения, " +
                    "пожалуйста перезапустите приложение указав корректный путь к файлу");
            System.exit(123);
        }
        else if (args.length > 1) System.out.println("вы указали несколько аргументов при запуске, " +
                "приложение попытается использовать в качестве имени файла первый из них");

        var filename = args[0];
        var storageFile = new File(filename);
        Storage storage = new Storage();
        try {
            storage = new Storage(new LinkedHashSet<>(Parser.getFromFile(storageFile)));
            System.out.println("файл успешно прочитан");
        } catch (JsonParseException e) {
            System.out.println("Файл пустой, это не страшно, но выводы сделаны");
            storage = new Storage();
        } catch (IOException e) {
            System.out.println("Файла с таким именем не существует, либо он недоступен. Перезапустите приложение.");
            System.out.println(e.getMessage());
        }

        var receiver = new Receiver(storage, filename);
        var invoker = new Invoker(receiver);
        var console = new AppConsole();
        console.runApp(new InputStreamReader(System.in), invoker);
    }
}