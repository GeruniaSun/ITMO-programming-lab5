package commands;

import java.util.List;

public class Show implements Command{

    private final Receiver receiver;

    public Show(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда show не принимает никаких аргументов");
        receiver.show();
    }

    @Override
    public String description() {
        return "show - выводит все элементы коллекции";
    }
}
