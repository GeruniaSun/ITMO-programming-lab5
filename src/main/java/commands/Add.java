package commands;

import java.util.List;

public class Add implements Command{

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null && this.receiver.isConsoleFlag())
            throw new IllegalArgumentException("Команда add не принимает никаких аргументов");
        receiver.add(args);
    }

    @Override
    public String description() {
        return "add - добавляет элемент в коллекцию";
    }
}
