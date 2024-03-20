package commands;

import data.Coordinates;
import data.Ticket;
import data.Ticket.Builder;
import data.Venue;
import parsing.Parser;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Receiver {
    private final Set<Ticket> data;
    private final String filename;
    private final int recursionLimit = 100;

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
        Scanner in = new Scanner(System.in);

        for(int i = 0; i < recursionLimit; i++) {
            try {
                System.out.println("Введите название");
                String input = in.nextLine();
                builder.withName(input);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }

        for(int i = 0; i < recursionLimit; i++) {
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
            }
            catch (Exception e){
                System.out.println("Это не подойдет для значения координат");
            }
        }

        for(int i = 0; i < recursionLimit; i++) {
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
            }
        }

        for(int i = 0; i < recursionLimit; i++) {
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
            }
        }

        var venueFlag = false;
        var recursionFlag = false;
        for(int i = 0; i < recursionLimit; i++) {
            System.out.println("Вы хотите добавить место проведения?");
            System.out.println("Введите YES/NO");
            var answer = in.nextLine();
            if (answer.equalsIgnoreCase("NO")) break;
            else if (answer.equalsIgnoreCase("YES")) {
                venueFlag = true;
                break;
            }
            else {
                System.out.println("Пожалуйста, введите 'YES' или 'NO'");
                if (i > recursionLimit - 2) recursionFlag = true;
            }
        }

        if (venueFlag) {
            var venueBuilder = new Venue.Builder();

            for(int i = 0; i < recursionLimit; i++) {
                try {
                    System.out.println("Введите название места проведения");
                    String input = in.nextLine();
                    venueBuilder.withName(input);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            for(int i = 0; i < recursionLimit; i++) {
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
                } catch (Exception e) {
                    System.out.println("это не подойдет для вместимости");
                }
            }

            var addressFlag = false;
            for(int i = 0; i < recursionLimit; i++) {
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
                for(int i = 0; i < recursionLimit; i++) {
                    try {
                        System.out.println("Введите адрес места проведения");
                        String input = in.nextLine();
                        venueBuilder.withAddress(new Venue.Address(input));
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        if (i > recursionLimit - 2) recursionFlag = true;
                    }
                }
            }

            builder.withVenue(venueBuilder.build());
        }

        if(recursionFlag) {
            System.out.println("что-то мне нехорошо, мистер пользователь...");
            System.exit(777);
        }
        return builder.build();
    }

    public void add(){
        var builder = new Ticket.Builder();
        data.add(this.create(builder));
        System.out.println("Вы успешно добавили билет");
    }

    public void addIfMax(){
        var builder = new Ticket.Builder();
        var noob = this.create(builder);
        if (noob.compareTo(Collections.max(data)) > 0) data.add(noob);
    }

    public void removeGreater(){
        var builder = new Ticket.Builder();
        Ticket compared = this.create(builder);
        data.removeIf(ticket -> ticket.compareTo(compared) > 0);
    }

    public void removeLower(){
        var builder = new Ticket.Builder();
        Ticket compared = this.create(builder);
        data.removeIf(ticket -> ticket.compareTo(compared) < 0);
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

    public void update(Long id){
        Ticket oldTicket = null;
        for (Ticket ticket : data){
            if (ticket.getId().equals(id)){
                oldTicket = ticket;
                break;
            }
        }
        if (oldTicket == null) System.out.println("Элемента с таким id не существует");
        else {
            var builder = new Ticket.Builder(oldTicket);
            this.create(builder);
            System.out.println("Значения полей элемента успешно обновлены");
        }
    }
}
