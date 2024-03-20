package commands;

import java.util.List;

public class Save implements Command{

    private final Receiver receiver;

    public Save(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда save не принимает никаких аргументов");
        receiver.save();
    }

    @Override
    public String description() {
        return "save - сохраняет коллекцию в файл, указанный при запуске";
    }
}
