package app;

import data.Ticket;
import parsing.Parser;

import java.io.*;
import java.util.*;

/*
 * Это моя 5-ая лабораторная по предмету программирование
 * 1 курс 2 семестр
 * @author Шнейдерис Г.Г.
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
        Set<Ticket> storage = null;
        try {
            storage = new LinkedHashSet<>(Parser.getFromFile(storageFile));
            System.out.println("файл успешно прочитан");
        } catch (IOException e) {
            System.out.println("Файла с таким именем не существует, либо он недоступен. Перезапустите приложение.");
        }

        var receiver = new Receiver(storage,filename);
        var invoker = new Invoker(receiver);
        var console = new AppConsole();
        console.runApp(new InputStreamReader(System.in), invoker);
    }
}