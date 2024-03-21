package commands;

import data.Coordinates;
import data.Ticket;
import data.Ticket.Builder;
import data.Venue;
import parsing.Parser;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Receiver {
    private final Set<Ticket> data;
    private final String filename;
    private final int recursionLimit = 100;
    private InputStreamReader inputStream = new InputStreamReader(System.in);
    private InputStreamReader lastInputStream = new InputStreamReader(System.in);
    private boolean consoleFlag = true;
    private Set<Path> recursionDefense = new HashSet<Path>();

    public Receiver(Set<Ticket> data, String filename) {
        this.data = data;
        this.filename = filename;
    }

    public void help(Collection<Command> commands){
        System.out.println("вот список доступных вам команд: ");
        commands.forEach(cmd -> {
            System.out.println(cmd.description());
        });
    }

    public void info(){
        System.out.println("Тип коллекции: " + data.getClass().getSimpleName());
        System.out.println("Размер коллекции: " + data.size());
    }

    public void show(){
        if (data.isEmpty()) System.out.println("Коллекция пуста. Используйте команду add, чтоб добавить элементы");
        data.forEach(System.out::println);
    }

    public void exit(){
        System.out.println("Выход из приложения...");
        System.exit(666);
    }

    public void save(){
        try {
            Parser.saveToFile(new File(filename),data);
            System.out.println("Коллекция сохранена успешно");
        } catch (IOException e) {
            System.out.println("Ошибка во время сохранения коллекции в файл. Возможно файл перемещен или удален");
        }
    }

    private Ticket create(Builder builder){
        var in = new Scanner(new BufferedReader(this.inputStream));

        while (this.consoleFlag || in.hasNextLine()) {
            try {
                System.out.println("Введите название");
                String input = in.nextLine();
                builder.withName(input);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                System.exit(999);
            }
        }

        while (this.consoleFlag || in.hasNextLine()) {
            try {
                System.out.println("Введите координаты через пробел");
                String[] input = in.nextLine().split(" ");
                float x = Float.parseFloat(input[0]);
                int y = Integer.parseInt(input[1]);
                try{
                    if (input.length > 2) throw new IllegalArgumentException("Введено слишком много аргументов");
                    builder.withCoordinates(new Coordinates(x, y));
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NoSuchElementException e) {
                System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                System.exit(999);
            }
            catch (Exception e){
                System.out.println("Это не подойдет для значения координат");
            }
        }

        while (this.consoleFlag || in.hasNextLine()) {
            try {
                System.out.println("Введите цену");
                String input = in.nextLine();
                var price = Long.parseLong(input);
                try {
                    builder.withPrice(price);
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Это не подойдет для значения цены");
            } catch (NoSuchElementException e) {
                System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                System.exit(999);
            }
        }

        while (this.consoleFlag || in.hasNextLine()) {
            try {
                System.out.println("Введите один из нижеприведенных типов билета");
                System.out.println(Arrays.toString(Ticket.TicketType.values()));
                String input = in.nextLine().toUpperCase();
                var type = Ticket.TicketType.valueOf(input);
                builder.withType(type);
                break;
            }
            catch (IllegalArgumentException e){
                System.out.println("Такого варианта нет");
            } catch (NoSuchElementException e) {
                System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                System.exit(999);
            }
        }

        var venueFlag = false;
        while (this.consoleFlag || in.hasNextLine()) {
            System.out.println("Вы хотите добавить место проведения?");
            System.out.println("Введите YES/NO");
            var answer = in.nextLine();
            if (answer.equalsIgnoreCase("NO")) break;
            else if (answer.equalsIgnoreCase("YES")) {
                venueFlag = true;
                break;
            } else {
                System.out.println("Пожалуйста, введите 'YES' или 'NO'");
            }
        }

        if (venueFlag) {
            var venueBuilder = new Venue.Builder();

            while (this.consoleFlag || in.hasNextLine()) {
                try {
                    System.out.println("Введите название места проведения");
                    String input = in.nextLine();
                    venueBuilder.withName(input);
                    break;
                } catch (NoSuchElementException e) {
                    System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                    System.exit(999);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            while (this.consoleFlag || in.hasNextLine()) {
                try {
                    System.out.println("Введите вместимость места проведения");
                    String input = in.nextLine();
                    var capacity = Integer.parseInt(input);
                    try {
                        venueBuilder.withCapacity(capacity);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                    System.exit(999);
                } catch (Exception e) {
                    System.out.println("это не подойдет для вместимости");
                }
            }

            var addressFlag = false;
            while (this.consoleFlag || in.hasNextLine()) {
                System.out.println("Вы хотите добавить адрес места проведения?");
                System.out.println("Введите YES/NO");
                var answer = in.nextLine();
                if (answer.equalsIgnoreCase("NO")) break;
                else if (answer.equalsIgnoreCase("YES")) {
                    addressFlag = true;
                    break;
                } else System.out.println("Пожалуйста, введите 'YES' или 'NO'");
            }

            if (addressFlag){
                while (this.consoleFlag || in.hasNextLine()) {
                    try {
                        System.out.println("Введите адрес места проведения");
                        String input = in.nextLine();
                        venueBuilder.withAddress(new Venue.Address(input));
                        break;
                    } catch (NoSuchElementException e) {
                        System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                        System.exit(999);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            builder.withVenue(venueBuilder.build());
        }

        if (builder.isReadyToBuild()){
            return builder.build();
        } else throw new IllegalStateException("Что-то пошло не так во время описания билета");
    }

    private Ticket createFromFile(Builder builder, List<String> args){
        try {
            String input = String.join(" ", args);
            ArrayList<String> values = new ArrayList<>(List.of(input.substring(1)
                    .replace("}", "").split(", ")));
            values.addAll(List.of("", "", ""));
            builder.withName(values.get(0).substring(1).replace("'", ""));
            builder.withCoordinates(new Coordinates(Float.parseFloat(values.get(1)), Integer.parseInt(values.get(2))));
            builder.withPrice(Long.parseLong(values.get(3)));
            System.out.println("4");

            builder.withType(Ticket.TicketType.valueOf(values.get(4)));
            if (!values.get(5).contains("null")) {
                var venueBuilder = new Venue.Builder();
                venueBuilder.withName(values.get(5).substring(1).replace("'", ""));
                venueBuilder.withCapacity(Integer.parseInt(values.get(6)));
                if (!values.get(7).contains("null"))
                    venueBuilder.withAddress(new Venue.Address(values.get(7)
                            .substring(1).replace("'", "")));
                builder.withVenue(venueBuilder.build());
            }

            if (builder.isReadyToBuild()) return builder.build();
            else throw new IllegalArgumentException("что-то не так со значениями полей билета");
        } catch (Exception e){
            throw new IllegalStateException("что-то пошло не так во время описания билета");
        }
    }

    public void add(List<String> args){
        var builder = new Ticket.Builder();
        try {
            if (consoleFlag){
                data.add(this.create(builder));
            } else data.add(this.createFromFile(builder, args));
            System.out.println("Вы успешно добавили билет");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage() + " команда add не выполнена");
        }
    }

    public void addIfMax(List<String> args){
        try {
            var builder = new Ticket.Builder();
            Ticket noob;
            if (consoleFlag){
                noob = this.create(builder);
            } else noob = this.createFromFile(builder, args);
            if (noob.compareTo(Collections.max(data)) > 0) data.add(noob);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage() + " команда add_if_max не выполнена");
        }
    }

    public void removeGreater(List<String> args){
        try {
            var builder = new Ticket.Builder();
            Ticket compared;
            if (consoleFlag){
                compared = this.create(builder);
            } else compared = this.createFromFile(builder, args);
            data.removeIf(ticket -> ticket.compareTo(compared) > 0);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage() + " команда remove_greater не выполнена");
        }
    }

    public void removeLower(List<String> args){
        try {
            var builder = new Ticket.Builder();
            Ticket compared;
            if (consoleFlag){
                compared = this.create(builder);
            } else compared = this.createFromFile(builder, args);
            data.removeIf(ticket -> ticket.compareTo(compared) < 0);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage() + " команда remove_lower не выполнена");
        }
    }

    public void printDescending() {
        data.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }

    public void filterByType(Ticket.TicketType type){
        data.stream().filter(ticket -> ticket.getType() == type).forEach(System.out::println);
    }

    public void countGreaterThanType(Ticket.TicketType type){
        System.out.println(data.stream().filter(ticket -> ticket.getType().compareTo(type) > 0).count());
    }

    public void clear(){
        data.clear();
        System.out.println("Коллекция успешно очищена");
    }

    public void removeById(Long id){
        var success = data.removeIf(ticket -> ticket.getId().equals(id));
        if (success) System.out.println("Элемент успешно удалён");
        else System.out.println("В коллекции нет элемента с id " + id);
    }

    public void update(Long id, List<String> args){
        Ticket oldTicket = null;
        for (Ticket ticket : data){
            if (ticket.getId().equals(id)){
                oldTicket = ticket;
                break;
            }
        }
        if (oldTicket == null) System.out.println("Элемента с таким id не существует");
        else {
            try {
                var builder = new Ticket.Builder(oldTicket);
                if (consoleFlag){
                    data.add(this.create(builder));
                } else data.add(this.createFromFile(builder, args));
                System.out.println("Значения полей элемента успешно обновлены");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage() + "команда update не выполнена");
            }
        }
    }

    public void executeScript(String filename) throws AccessDeniedException, FileNotFoundException {
        var path = Path.of(filename).toAbsolutePath();
        if (!Files.isReadable(path))
            throw new AccessDeniedException("Такого файла не существует или у программы нет прав, чтоб его прочитать");
        if (this.recursionDefense.contains(path))
            throw new IllegalArgumentException("в ваших скриптах обнаружена рекурсия, а рекурсия вредна для здоровья!");
        this.recursionDefense.add(path);
        var fileIn = new FileReader(path.toFile());
        this.setInputStream(fileIn);
        this.setConsoleFlag(false);
        var fileInvoker = new Invoker(this);
        System.out.println("начинаю читать ваш скрипт...");
        fileInvoker.runApp(fileIn);
        System.out.println("достигнут конец файла " + filename);
        this.setInputStream(this.lastInputStream);
        this.setConsoleFlag(true);
    }

    public void setInputStream(InputStreamReader inputStream) {
        this.lastInputStream = this.inputStream;
        this.inputStream = inputStream;
    }

    public boolean isConsoleFlag() {
        return consoleFlag;
    }

    public void setConsoleFlag(boolean consoleFlag) {
        this.consoleFlag = consoleFlag;
    }
}
