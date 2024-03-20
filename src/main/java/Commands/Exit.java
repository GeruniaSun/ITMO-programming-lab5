package Commands;

import java.util.List;

public class Exit implements Command{

    private final Receiver receiver;

    public Exit(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда exit не принимает никаких аргументов");
        receiver.exit();
    }

    @Override
    public String description() {
        return "exit - завершает работу приложения";
    }
}
