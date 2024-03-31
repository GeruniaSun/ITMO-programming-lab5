package commands;

import app.Receiver;

import java.util.List;

public class RemoveLower implements Command{

    private final Receiver receiver;

    public RemoveLower(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (this.receiver.isConsoleFlag() && args != null)
            throw new IllegalArgumentException("Команда remove_lower не принимает никаких аргументов, " +
                "значение element нужно вводить с новой строки");
        receiver.removeLower(args);
    }

    @Override
    public String description() {
        return "remove_lower {element} - удаляет из коллекции все элементы, большие, чем заданный";
    }
}
