package commands;

import java.util.List;

public class AddIfMax implements Command{

    private final Receiver receiver;

    public AddIfMax(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда add_if_max не принимает никаких аргументов, " +
                "значение element нужно вводить с новой строки");
        receiver.addIfMax();
    }

    @Override
    public String description() {
        return "add_if_max {element} - добавляет элемент в коллекцию, если он превышает максимальный";
    }
}