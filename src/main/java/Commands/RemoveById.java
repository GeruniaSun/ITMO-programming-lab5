package Commands;

import Data.Ticket;

import java.util.Arrays;
import java.util.List;

public class RemoveById implements Command{
    private final Receiver receiver;

    public RemoveById(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args == null || args.isEmpty())
            throw new NullPointerException("Команда remove_by_id не работает без аргумента id");
        if (args.size() > 1)
            throw new IllegalArgumentException("Команда remove_by_id принимает только 1 аргумент");
        long id;
        try {
            id = Long.parseLong(args.get(0).toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("аргумент id должен быть числом");
        }
        receiver.removeById(id);
    }

    @Override
    public String description() {
        return "remove_by_id id - удаляет из коллекции элемент с айди id";
    }
}
