package Commands;

import java.util.List;

public class Info implements Command{

    private final Receiver receiver;

    public Info(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда info не принимает никаких аргументов");
        receiver.info();
    }

    @Override
    public String description() {
        return "info - выводит информацию о коллекции";
    }
}
