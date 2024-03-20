package Commands;

import java.util.List;

public class RemoveGreater implements Command{

    private final Receiver receiver;

    public RemoveGreater(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда remove_greater не принимает никаких аргументов, " +
                "значение element нужно вводить с новой строки");
        receiver.removeGreater();
    }

    @Override
    public String description() {
        return "remove_greater {element} - удаляет из коллекции все элементы, меньшие, чем заданный";
    }
}
